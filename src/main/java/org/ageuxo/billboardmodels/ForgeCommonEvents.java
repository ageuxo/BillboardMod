package org.ageuxo.billboardmodels;

import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.ageuxo.billboardmodels.capability.IBillboardRenderStore;

@Mod.EventBusSubscriber(modid = BillboardModels.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEvents {
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<LevelChunk> event) {
        event.addCapability(IBillboardRenderStore.LOCATION, new IBillboardRenderStore.Provider());
    }
}
