package org.ageuxo.billboardmodels.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.ageuxo.billboardmodels.ClientHelper;
import org.ageuxo.billboardmodels.data.IBillboardRenderStore;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import java.util.HashMap;
import java.util.Map;

public class BillboardRenderer implements ResourceManagerReloadListener {
    public static final BillboardRenderer INSTANCE = new BillboardRenderer();

    private static final Quaternionf ROT = new Quaternionf();
    private static final Map<BlockState, TextureAtlasSprite> SPRITE_CACHE = new HashMap<>();

    public static void renderBillboard(PoseStack poseStack, VertexConsumer buf, Camera cam, IBillboardRenderStore.BillboardRender billboard, Level level) {
        var pos = billboard.pos();
        var state = billboard.state();
        var camRot = cam.rotation();
        var camPos = cam.getPosition();
        int light = LevelRenderer.getLightColor(level, billboard.pos());
        double worldOffsetX = pos.getX() - camPos.x();
        double worldOffsetY = pos.getY() - camPos.y();
        double worldOffsetZ = pos.getZ() - camPos.z();
        Vec3 randomOffset = state.getOffset(level, pos);

        poseStack.pushPose();
        poseStack.translate(0.5f + randomOffset.x + worldOffsetX, 0f + randomOffset.y + worldOffsetY, 0.5f + randomOffset.z + worldOffsetZ);
        ROT.set(camRot);
        // Do rotation stuff here
        poseStack.mulPose(ROT);
        poseStack.translate(0, 0.5f, 0);
        renderSprite(buf, poseStack, SPRITE_CACHE.computeIfAbsent(state, ClientHelper::getParticleSprite), light, OverlayTexture.NO_OVERLAY, 1, 1, 1);
        poseStack.popPose();

    }

    public static void renderSprite(VertexConsumer buf, PoseStack poseStack, TextureAtlasSprite sprite, int packedLight, int packOverlay, int packedTint) {
        int red = FastColor.ARGB32.red(packedTint);
        int green = FastColor.ARGB32.green(packedTint);
        int blue = FastColor.ARGB32.blue(packedTint);

        renderSprite(buf, poseStack, sprite, packedLight, packOverlay, red, green, blue);
    }

    public static void renderSprite(VertexConsumer buf, PoseStack poseStack, TextureAtlasSprite sprite, int packedLight, int packOverlay, int red, int green, int blue) {
        addVert(poseStack.last(), buf, -0.5f, 0.5f, 0, sprite.getU0(), sprite.getV0(), packedLight, packOverlay, red, green, blue);
        addVert(poseStack.last(), buf, 0.5f, 0.5f, 0, sprite.getU1(), sprite.getV0(), packedLight, packOverlay, red, green, blue);
        addVert(poseStack.last(), buf, 0.5f, -0.5f, 0, sprite.getU1(), sprite.getV1(), packedLight, packOverlay, red, green, blue);
        addVert(poseStack.last(), buf, -0.5f, -0.5f, 0, sprite.getU0(), sprite.getV1(), packedLight, packOverlay, red, green, blue);
    }

    private static void addVert(PoseStack.Pose pose, VertexConsumer buf, float x, float y, int z, float u, float v, int packedLight, int packOverlay, float red, float green, float blue) {
        buf.vertex(pose.pose(), x, y, z)
                .color(red, green, blue, 1f)
                .uv(u, v)
                .uv2(packedLight)
                .overlayCoords(packOverlay)
                .normal(pose.normal(), 0, 0, 1)
                .endVertex();
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
        SPRITE_CACHE.clear();
    }
}
