package org.ageuxo.billboardmodels;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.ageuxo.billboardmodels.data.IBillboardRenderStore;
import org.ageuxo.billboardmodels.mixins.LevelRendererAccessor;
import org.ageuxo.billboardmodels.mixins.RenderChunkInfoAccessor;
import org.ageuxo.billboardmodels.model.BillboardRenderer;

import java.util.List;

@Mod.EventBusSubscriber(modid = BillboardMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents {

    @SubscribeEvent
    public static void renderLevel(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            ObjectArrayList<LevelRenderer.RenderChunkInfo> renderChunksInFrustum = ((LevelRendererAccessor) event.getLevelRenderer()).getRenderChunksInFrustum();
            Minecraft minecraft = Minecraft.getInstance();
            ClientLevel level = minecraft.level;
            PoseStack poseStack = event.getPoseStack();
            Camera camera = event.getCamera();
            MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
            VertexConsumer buf = bufferSource.getBuffer(RenderType.cutout());

            if (level != null && !renderChunksInFrustum.isEmpty()) {
                for (LevelRenderer.RenderChunkInfo chunkInfo : renderChunksInFrustum) {
                    ChunkRenderDispatcher.RenderChunk renderChunk = ((RenderChunkInfoAccessor) chunkInfo).getChunk();
                    List<IBillboardRenderStore.BillboardRender> billboards = ((IBillboardRenderStore)renderChunk.getCompiledChunk()).getBillboardRenders();
                    for (IBillboardRenderStore.BillboardRender billboard : billboards) {
                        BillboardRenderer.renderBillboard(poseStack, buf, camera, billboard, level);
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
