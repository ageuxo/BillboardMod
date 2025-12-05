package org.ageuxo.billboardmodels.model;

import com.google.gson.*;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.client.model.geometry.IGeometryLoader;

public class SpriteGeometryLoader implements IGeometryLoader<SpriteGeometry> {
    @Override
    public SpriteGeometry read(JsonObject jsonObject, JsonDeserializationContext context) throws JsonParseException {

        JsonArray elements = GsonHelper.getAsJsonArray(jsonObject, "sprites");
        DataResult<SpriteGeometry> result = SpriteGeometry.CODEC.parse(JsonOps.INSTANCE, elements);

        return result.result().orElseThrow();
    }
}
