package org.ageuxo.billboardmodels.model;

import com.mojang.math.Transformation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.minecraftforge.client.model.geometry.UnbakedGeometryHelper;
import org.ageuxo.billboardmodels.data.BillboardTransforms;
import org.joml.Vector2f;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public record SpriteGeometry(List<Sprite> sprites, Map<String, String> textures, ResourceLocation billboardTransform) implements IUnbakedGeometry<SpriteGeometry> {

    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
        Transformation rootTransform = context.getRootTransform();
        if (!rootTransform.isIdentity()) {
            modelState = UnbakedGeometryHelper.composeRootTransformIntoModelState(modelState, rootTransform);
        }

        TextureAtlasSprite particle = spriteGetter.apply(context.getMaterial("particle"));

        List<BillboardSpriteModel.BakedSprite> bakedSprites = sprites.stream()
                .map(
                        s -> new BillboardSpriteModel.BakedSprite(
                                spriteGetter.apply(context.getMaterial(s.texture)),
                                s.origin,
                                s.offset,
                                s.tintIndex
                        )
                ).toList();

        return new BillboardSpriteModel(bakedSprites, particle, overrides, context.useAmbientOcclusion(), context.useBlockLight(), context.isGui3d(), ItemTransforms.NO_TRANSFORMS, BillboardTransforms.TRANSFORMS.get(billboardTransform));
    }

    public record Sprite(String texture, Vector2f origin, Vector2f offset, int tintIndex) {
        public static final Codec<Vector2f> VEC2F = Codec.FLOAT.listOf().comapFlatMap(
                (floatList) -> Util.fixedSize(floatList, 2).map(
                                (floats) -> new Vector2f(floats.get(0), floats.get(1))
                        ),
                (vec) -> List.of(vec.x(), vec.y())
        );

        public static final Codec<Sprite> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("texture").forGetter(Sprite::texture),
                VEC2F.fieldOf("origin").forGetter(Sprite::origin),
                VEC2F.fieldOf("offset").forGetter(Sprite::offset),
                Codec.INT.optionalFieldOf("tintindex", -1).forGetter(Sprite::tintIndex)
        ).apply(instance, Sprite::new));
    }
}
