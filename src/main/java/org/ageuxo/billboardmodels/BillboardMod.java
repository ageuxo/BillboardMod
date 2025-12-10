package org.ageuxo.billboardmodels;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod(BillboardMod.MODID)
public class BillboardMod {
    public static final String MODID = "billboard_models";

    public BillboardMod() {

    }

    public static ResourceLocation modRL(String path) {
        return new ResourceLocation(MODID, path);
    }
}
