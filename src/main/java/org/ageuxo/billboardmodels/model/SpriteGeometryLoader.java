package org.ageuxo.billboardmodels.model;

import com.google.gson.*;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import org.ageuxo.billboardmodels.BillboardMod;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

public class SpriteGeometryLoader implements IGeometryLoader<SpriteGeometry> {

    public static final SpriteGeometryLoader INSTANCE = new SpriteGeometryLoader();
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public SpriteGeometry read(JsonObject jsonObject, JsonDeserializationContext context) throws JsonParseException {
        Map<String, String> textures = Codec.unboundedMap(Codec.STRING, Codec.STRING).parse(JsonOps.INSTANCE, jsonObject.get("textures")).getOrThrow(true, LOGGER::warn);
        var spriteResult = SpriteGeometry.Sprite.CODEC.listOf().parse(JsonOps.INSTANCE, jsonObject.get("sprites"));
        List<SpriteGeometry.Sprite> sprites = spriteResult.resultOrPartial(s ->LOGGER.warn("SpriteGeometryLoader failed parsing sprites: {}", s))
                .orElseThrow( ()-> new JsonParseException("SpriteGeometryLoader failed parsing sprites") );
        var transformResult = ResourceLocation.CODEC.parse(JsonOps.INSTANCE, jsonObject.get("transform")).result();
        ResourceLocation transformLocation = transformResult.orElseGet( ()-> BillboardMod.modRL("camera_relative") );

        return new SpriteGeometry(sprites, textures, transformLocation);
    }

}
