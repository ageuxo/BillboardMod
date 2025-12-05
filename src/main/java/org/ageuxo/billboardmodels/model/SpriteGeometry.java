package org.ageuxo.billboardmodels.model;

import com.mojang.math.Transformation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.minecraftforge.client.model.geometry.UnbakedGeometryHelper;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Function;

public record SpriteGeometry(List<Sprite> sprites) implements IUnbakedGeometry<SpriteGeometry> {

    public static final Codec<SpriteGeometry> CODEC = Sprite.CODEC.listOf().xmap(SpriteGeometry::new, SpriteGeometry::sprites);

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
                                s.center,
                                s.radius
                        )
                ).toList();

        return new BillboardSpriteModel(bakedSprites, particle, overrides, context.useAmbientOcclusion(), context.useBlockLight(), context.isGui3d(), ItemTransforms.NO_TRANSFORMS);
    }

    public record Sprite(String texture, Vector3f center, float radius) {
        public static final Codec<Sprite> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("texture").forGetter(Sprite::texture),
                ExtraCodecs.VECTOR3F.fieldOf("center").forGetter(Sprite::center),
                Codec.FLOAT.fieldOf("radius").forGetter(Sprite::radius)
        ).apply(instance, Sprite::new));
    }
}
