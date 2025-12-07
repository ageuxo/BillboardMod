package org.ageuxo.billboardmodels;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.ageuxo.billboardmodels.be.BillboardSpriteBlockEntityRenderer;
import org.ageuxo.billboardmodels.model.SpriteGeometryLoader;

@Mod.EventBusSubscriber(modid = BillboardModels.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntities.SIMPLE_BILLBOARD.get(), BillboardSpriteBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register("billboard", SpriteGeometryLoader.INSTANCE);
    }
}
