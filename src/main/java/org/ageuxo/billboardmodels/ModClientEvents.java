package org.ageuxo.billboardmodels;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.NewRegistryEvent;
import org.ageuxo.billboardmodels.data.BillboardTransforms;
import org.ageuxo.billboardmodels.model.BillboardRenderer;
import org.ageuxo.billboardmodels.model.SpriteGeometryLoader;

@Mod.EventBusSubscriber(modid = BillboardMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEvents {

    @SubscribeEvent
    public static void onRegistryEvent(NewRegistryEvent event) {
        BillboardTransforms.init();
    }

    @SubscribeEvent
    public static void registerGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register("billboard", SpriteGeometryLoader.INSTANCE);
    }

    @SubscribeEvent
    public static void addReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(BillboardRenderer.INSTANCE);
    }
}
