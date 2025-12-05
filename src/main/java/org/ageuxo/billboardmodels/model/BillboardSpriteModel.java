package org.ageuxo.billboardmodels.model;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.pipeline.QuadBakingVertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class BillboardSpriteModel implements IDynamicBakedModel {

    private final List<BakedSprite> bakedSprites;
    private final TextureAtlasSprite sprite;
    private final ItemOverrides overrides;
    private final boolean hasAmbientOcclusion;
    private final boolean usesBlockLight;
    private final boolean isGui3d;
    private final ItemTransforms transforms;
    private final QuadBakingVertexConsumer.Buffered baker;

    public BillboardSpriteModel(List<BakedSprite> bakedSprites, TextureAtlasSprite sprite, ItemOverrides overrides, boolean hasAmbientOcclusion, boolean usesBlockLight, boolean isGui3d, ItemTransforms transforms) {
        this.bakedSprites = bakedSprites;
        this.sprite = sprite;
        this.overrides = overrides;
        this.hasAmbientOcclusion = hasAmbientOcclusion;
        this.usesBlockLight = usesBlockLight;
        this.isGui3d = isGui3d;
        this.transforms = transforms;
        this.baker = new QuadBakingVertexConsumer.Buffered();
        baker.setTintIndex(0);
        baker.setShade(false);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        List<BakedQuad> quads = new ArrayList<>();

        for (var bakedSprite : this.bakedSprites) {
            TextureAtlasSprite sprite = bakedSprite.sprite();
            baker.setSprite(sprite);
            baker.uv(sprite.getU0(), sprite.getV0());
            baker.uv2((int) sprite.getU1(), (int) sprite.getV1());

            quads.add(baker.getQuad());
        }

        return quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return hasAmbientOcclusion;
    }

    @Override
    public boolean isGui3d() {
        return isGui3d;
    }

    @Override
    public boolean usesBlockLight() {
        return usesBlockLight;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return sprite;
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return overrides;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull ItemTransforms getTransforms() {
        return transforms;
    }

    public record BakedSprite(TextureAtlasSprite sprite, Vector3f center, float radius) { }
}
