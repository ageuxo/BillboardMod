package org.ageuxo.billboardmodels;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BillboardModels.MODID)
public class BillboardModels {
    public static final String MODID = "billboard_models";

    public BillboardModels() {
        FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
        IEventBus bus = context.getModEventBus();
        BlockEntities.TYPES.register(bus);
    }
}
