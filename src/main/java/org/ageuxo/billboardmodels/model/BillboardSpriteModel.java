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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class BillboardSpriteModel implements IDynamicBakedModel {

    private final List<BakedSprite> bakedSprites;
    private final TextureAtlasSprite particle;
    private final ItemOverrides overrides;
    private final boolean hasAmbientOcclusion;
    private final boolean usesBlockLight;
    private final boolean isGui3d;
    private final ItemTransforms transforms;

    public BillboardSpriteModel(List<BakedSprite> bakedSprites, TextureAtlasSprite particle, ItemOverrides overrides, boolean hasAmbientOcclusion, boolean usesBlockLight, boolean isGui3d, ItemTransforms transforms) {
        this.bakedSprites = bakedSprites;
        this.particle = particle;
        this.overrides = overrides;
        this.hasAmbientOcclusion = hasAmbientOcclusion;
        this.usesBlockLight = usesBlockLight;
        this.isGui3d = isGui3d;
        this.transforms = transforms;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        return List.of();
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
        return particle;
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

    public List<BakedSprite> bakedSprites() {
        return bakedSprites;
    }

    public static final class BakedSprite {
        private final TextureAtlasSprite sprite;
        private final float[] floats;
        private final int tintIndex;
        private final boolean tinted;

        public BakedSprite(TextureAtlasSprite sprite, float[] floats, int tintIndex, boolean tinted) {
            this.sprite = sprite;
            this.floats = floats;
            this.tintIndex = tintIndex;
            this.tinted = tinted;
        }

        public BakedSprite(TextureAtlasSprite sprite, float[] floats, int tintIndex) {
            this(sprite, floats, tintIndex, tintIndex > 0);
        }

        public BakedSprite(TextureAtlasSprite sprite, Vector2f origin, Vector2f offset, int tintIndex) {
            this(sprite, new float[]{origin.x, origin.y, offset.x, offset.y}, tintIndex);
        }

        public float originX() {
            return floats[0];
        }

        public float originY() {
            return floats[1];
        }

        public float offsetX() {
            return floats[2];
        }

        public float offsetY() {
            return floats[3];
        }

        public TextureAtlasSprite sprite() {
            return sprite;
        }

        public float[] floats() {
            return floats;
        }

        public int tintIndex() {
            return tintIndex;
        }

        public boolean tinted() {
            return tinted;
        }

    }
}
