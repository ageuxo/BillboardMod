package org.ageuxo.billboardmodels.datagen;

import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.CustomLoaderBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.ageuxo.billboardmodels.BillboardMod;
import org.ageuxo.billboardmodels.data.BillboardTransform;
import org.ageuxo.billboardmodels.data.BillboardTransforms;
import org.ageuxo.billboardmodels.model.SpriteGeometry;
import org.joml.Vector2f;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillboardBuilder extends CustomLoaderBuilder<BlockModelBuilder> {

    public static final Logger LOGGER = LogUtils.getLogger();
    private final List<SpriteGeometry.Sprite> sprites = new ArrayList<>();
    private final Map<String, String> textures = new HashMap<>();
    private ResourceLocation transform = BillboardMod.modRL("camera_relative"); // Default to camera relative transform

    public BillboardBuilder(BlockModelBuilder parent, ExistingFileHelper existingFileHelper) {
        super(BillboardMod.modRL("billboard"), parent, existingFileHelper);
    }

    public BillboardBuilder addTintedSprite(ResourceLocation sprite, int tintIndex, float originX, float originY, float offsetX, float offsetY) {
        return addTintedSprite(sprite, new Vector2f(originX, originY), new Vector2f(offsetX, offsetY), tintIndex);
    }

    public BillboardBuilder addTintedSprite(ResourceLocation sprite, Vector2f origin, Vector2f offset, int tintIndex) {
        String textureLocation = String.valueOf(textures.size());
        textures.put(textureLocation, sprite.toString());
        sprites.add(new SpriteGeometry.Sprite(textureLocation, origin, offset, tintIndex));

        return this;
    }

    public BillboardBuilder addSprite(ResourceLocation sprite, float originX, float originY, float offsetX, float offsetY) {
        return addSprite(sprite, new Vector2f(originX, originY), new Vector2f(offsetX, offsetY));
    }

    public BillboardBuilder addSprite(ResourceLocation sprite, Vector2f origin, Vector2f offset) {
        String textureLocation = String.valueOf(textures.size());
        textures.put(textureLocation, sprite.toString());
        sprites.add(new SpriteGeometry.Sprite(textureLocation, origin, offset, -1));

        return this;
    }

    public BillboardBuilder addParticleTexture(ResourceLocation particle) {
        textures.put("particle", particle.toString());

        return this;
    }

    public BillboardBuilder setTransform(BillboardTransform transform) {
        try {
            this.transform = BillboardTransforms.TRANSFORMS.getLocationOrThrow(transform);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Set non-registered transform in BillboardBuilder: %s".formatted(e.getMessage()), e);
        }

        return this;
    }

    public BillboardBuilder setTransform(ResourceLocation location) {
        try {
            BillboardTransforms.TRANSFORMS.getOrThrow(location);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Set non-registered transform in BillboardBuilder: %s".formatted(e.getMessage()), e);
        }

        return this;
    }

    @Override
    public JsonObject toJson(JsonObject json) {
        json.add("sprites",
                SpriteGeometry.Sprite.CODEC.listOf().encodeStart(JsonOps.INSTANCE, sprites)
                        .getOrThrow(false, LOGGER::error)
        );
        json.add("textures",
                Codec.unboundedMap(Codec.STRING, Codec.STRING).encodeStart(JsonOps.INSTANCE, textures)
                        .getOrThrow(false, LOGGER::error)
        );
        json.add("billboard_transform",
                ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, transform)
                        .getOrThrow(false, LOGGER::error)
                );
        return super.toJson(json);
    }

    @Override
    public BlockModelBuilder end() {
        if (textures.get("particle") == null) {
            throw new IllegalStateException("BillboardModelBuilder is missing particle texture! Bound textures:\n%s".formatted(textures));
        }
        return super.end();
    }
}
