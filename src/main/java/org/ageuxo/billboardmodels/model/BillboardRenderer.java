package org.ageuxo.billboardmodels.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.FastColor;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.ageuxo.billboardmodels.ClientHelper;
import org.ageuxo.billboardmodels.data.BillboardPlacement;
import org.ageuxo.billboardmodels.data.BillboardTransform;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class BillboardRenderer implements PreparableReloadListener {
    public static final BillboardRenderer INSTANCE = new BillboardRenderer();

    private static final Quaternionf ROT = new Quaternionf();
    private static final Map<BlockState, TextureAtlasSprite> SPRITE_CACHE = new HashMap<>();
    private static final Map<BlockState, Optional<BillboardSpriteModel>> MODEL_CACHE = new HashMap<>();

    private BillboardRenderer() {
    }

    public static final BillboardTransform CAMERA_RELATIVE = (poseStack, camera) -> {
        poseStack.translate(0, -0.5f, 0);
        poseStack.mulPose(camera.rotation());
        poseStack.translate(0, 0.5f, 0);
    };
    public static final BillboardTransform Y_AXIS_ALIGNED = ((poseStack, camera) -> {
        var camRot = camera.rotation();
        ROT.set(0, camRot.y, 0, camRot.w);
        poseStack.mulPose(ROT);
    });

    public static void renderBillboard(PoseStack poseStack, VertexConsumer buf, Camera cam, BillboardPlacement billboard, Level level) {
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        var pos = billboard.pos();
        var state = billboard.state();
        var camPos = cam.getPosition();
        int light = LevelRenderer.getLightColor(level, billboard.pos());
        double worldOffsetX = pos.getX() - camPos.x();
        double worldOffsetY = pos.getY() - camPos.y();
        double worldOffsetZ = pos.getZ() - camPos.z();
        Vec3 randomOffset = state.getOffset(level, pos);

        poseStack.pushPose();

        Optional<BillboardSpriteModel> modelOptional = MODEL_CACHE.computeIfAbsent(state, ClientHelper::getBillboardModel);
        if (modelOptional.isPresent()) { // Has billboard model
            BillboardSpriteModel model = modelOptional.get();
            for (BillboardSpriteModel.BakedSprite sprite : model.bakedSprites()) {
                poseStack.pushPose();

                poseStack.translate(sprite.originX() + randomOffset.x + worldOffsetX, 0.5f + randomOffset.y + worldOffsetY, sprite.originY() + randomOffset.z + worldOffsetZ);
                billboard.transform().transform(poseStack, cam);
                poseStack.translate(sprite.offsetX(), sprite.offsetY(), 0);

                if (sprite.tinted()) {
                    int tint = blockColors.getColor(state, level, pos, sprite.tintIndex());
                    renderSprite(buf, poseStack, sprite.sprite(), light, OverlayTexture.NO_OVERLAY, tint);
                } else {
                    renderSprite(buf, poseStack, sprite.sprite(), light, OverlayTexture.NO_OVERLAY, 255, 255, 255);
                }
                poseStack.popPose();
            }
        } else {
            // Have the center of rotation be the bottom of the block
            poseStack.translate(0.5f + randomOffset.x + worldOffsetX, 0.5f + randomOffset.y + worldOffsetY, 0.5f + randomOffset.z + worldOffsetZ);
            // Do rotation stuff here
            billboard.transform().transform(poseStack, cam);
            renderSprite(buf, poseStack, SPRITE_CACHE.computeIfAbsent(state, ClientHelper::getParticleSprite), light, OverlayTexture.NO_OVERLAY, 255, 255, 255);
        }
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

    private static void addVert(PoseStack.Pose pose, VertexConsumer buf, float x, float y, int z, float u, float v, int packedLight, int packOverlay, int red, int green, int blue) {
        buf.vertex(pose.pose(), x, y, z)
                .color(red, green, blue, 255)
                .uv(u, v)
                .uv2(packedLight)
                .overlayCoords(packOverlay)
                .normal(pose.normal(), 0, 0, 1)
                .endVertex();
    }

    @Override
    public @NotNull CompletableFuture<Void> reload(@NotNull PreparationBarrier preparationBarrier, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller preparationsProfiler, @NotNull ProfilerFiller reloadProfiler, @NotNull Executor backgroundExecutor, @NotNull Executor gameExecutor) {
        return preparationBarrier.wait(clearCaches());
    }

    private static Void clearCaches() {
        SPRITE_CACHE.clear();
        MODEL_CACHE.clear();
        LogUtils.getLogger().info("Cleared caches");
        return null;
    }

    @Override
    public @NotNull String getName() {
        return "BillboardRenderer";
    }
}
