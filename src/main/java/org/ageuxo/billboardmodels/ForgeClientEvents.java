package org.ageuxo.billboardmodels;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.ageuxo.billboardmodels.capability.IBillboardRenderStore;
import org.ageuxo.billboardmodels.mixins.LevelRendererAccessor;
import org.ageuxo.billboardmodels.mixins.RenderChunkInfoAccessor;
import org.ageuxo.billboardmodels.model.BillboardRenderer;
import org.joml.Quaternionf;
import org.slf4j.Logger;

import java.util.List;

@Mod.EventBusSubscriber(modid = BillboardModels.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents {

    public static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void renderLevel(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            ObjectArrayList<LevelRenderer.RenderChunkInfo> renderChunksInFrustum = ((LevelRendererAccessor) event.getLevelRenderer()).getRenderChunksInFrustum();
            Minecraft minecraft = Minecraft.getInstance();
            ClientLevel level = minecraft.level;
            PoseStack poseStack = event.getPoseStack();
            Quaternionf rotation = event.getCamera().rotation();
            MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
            VertexConsumer buf = bufferSource.getBuffer(RenderType.cutout());

            if (level != null && !renderChunksInFrustum.isEmpty()) {

                for (LevelRenderer.RenderChunkInfo chunkInfo : renderChunksInFrustum) {
                    BlockPos origin = ((RenderChunkInfoAccessor) chunkInfo).getChunk().getOrigin();
//                    if (minecraft.player != null && minecraft.player.position().distanceTo(origin.getCenter()) < 45)
                        if (level.getChunk(origin) instanceof LevelChunk chunk) {
                            LazyOptional<IBillboardRenderStore> lazyOptional = chunk.getCapability(IBillboardRenderStore.CAPABILITY);
                            IBillboardRenderStore iBillboardRenderStore = lazyOptional.orElseThrow(IllegalStateException::new);
                            List<IBillboardRenderStore.BillboardRender> billboards = iBillboardRenderStore.getBillboardRenders();
                            int size = billboards.size();
                            if (size > 0){
                                LOGGER.warn("rendering: {} billboards in {}", size, chunk.getPos());
                            }
                            for (IBillboardRenderStore.BillboardRender billboard : billboards) {
                                LOGGER.warn("render: {} @ {}", billboard.state(), billboard.pos());
                                BillboardRenderer.renderBillboard(poseStack, buf, rotation, billboard, level);
                            }
                        }
                }
            }
        }
    }

    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(BillboardRenderer.INSTANCE);
    }
}
