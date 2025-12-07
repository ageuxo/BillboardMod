package org.ageuxo.billboardmodels;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(BillboardModels.MODID)
public class BillboardModels {
    public static final String MODID = "billboard_models";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BillboardModels() {
        FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
        IEventBus bus = context.getModEventBus();
        BlockEntities.TYPES.register(bus);
    }

    public static ResourceLocation modRL(String path) {
        return new ResourceLocation(MODID, path);
    }
}
