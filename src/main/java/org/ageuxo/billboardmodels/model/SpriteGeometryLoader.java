package org.ageuxo.billboardmodels.model;

import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

public class SpriteGeometryLoader implements IGeometryLoader<SpriteGeometry> {

    public static final SpriteGeometryLoader INSTANCE = new SpriteGeometryLoader();
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public SpriteGeometry read(JsonObject jsonObject, JsonDeserializationContext context) throws JsonParseException {
        Map<String, String> textures = readTextures(jsonObject);
        var spriteResult = SpriteGeometry.Sprite.CODEC.listOf().parse(JsonOps.INSTANCE, jsonObject);
        List<SpriteGeometry.Sprite> sprites = spriteResult.resultOrPartial(s ->LOGGER.warn("SpriteGeometryLoader failed parsing sprites: {}", s))
                .orElseThrow( ()-> new JsonParseException("SpriteGeometryLoader failed parsing sprites") );

        return new SpriteGeometry(sprites, textures);
    }

    public static Map<String, String> readTextures(JsonObject json) {
        JsonObject textureObj = json.getAsJsonObject("textures");
        ImmutableMap.Builder<String, String> mapBuilder = ImmutableMap.builder();
        for (Map.Entry<String, JsonElement> entry : textureObj.entrySet()) {
            String location = entry.getValue().getAsString();
            mapBuilder.put(entry.getKey(), location);
        }

        return mapBuilder.build();
    }
}
