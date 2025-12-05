package org.ageuxo.billboardmodels.be;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.ageuxo.billboardmodels.ClientHelper;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
public class BillboardSpriteBlockEntityRenderer implements BlockEntityRenderer<BillboardBlockEntity> {

    private final Quaternionf rot = new Quaternionf();
    private final Map<BlockState, TextureAtlasSprite> CACHE = new HashMap<>();

    public BillboardSpriteBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(BillboardBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();

        BlockState state = blockEntity.getBlockState();
        VertexConsumer buf = buffer.getBuffer(RenderType.cutout());
        TextureAtlasSprite sprite = CACHE.computeIfAbsent(state, ClientHelper::getParticleSprite);

        poseStack.pushPose();
        Vec3 randomOffset = state.getOffset(blockEntity.getLevel(), blockEntity.getBlockPos());
        poseStack.translate(0.5f + randomOffset.x, 0f + randomOffset.y, 0.5f + randomOffset.z);
        Quaternionf cRot = camera.rotation();
        rot.set(cRot);

        poseStack.mulPose(rot);
        poseStack.translate(0, 0.5f, 0);
        renderSprite(buf, poseStack, sprite, packedLight, packedOverlay);
        poseStack.popPose();
    }

    public void renderSprite(VertexConsumer buf, PoseStack poseStack, TextureAtlasSprite sprite, int packedLight, int packOverlay) {
        addVert(poseStack.last(), buf, -0.5f, 0.5f, 0, sprite.getU0(), sprite.getV0(), packedLight, packOverlay);
        addVert(poseStack.last(), buf, 0.5f, 0.5f, 0, sprite.getU1(), sprite.getV0(), packedLight, packOverlay);
        addVert(poseStack.last(), buf, 0.5f, -0.5f, 0, sprite.getU1(), sprite.getV1(), packedLight, packOverlay);
        addVert(poseStack.last(), buf, -0.5f, -0.5f, 0, sprite.getU0(), sprite.getV1(), packedLight, packOverlay);
    }

    private static void addVert(PoseStack.Pose pose, VertexConsumer buf, float x, float y, int z, float u, float v, int packedLight, int packOverlay) {
        buf.vertex(pose.pose(), x, y, z)
                .color(1f, 1f, 1f, 1f)
                .uv(u, v)
                .uv2(packedLight)
                .overlayCoords(packOverlay)
                .normal(pose.normal(), 0, 0, 1)
                .endVertex();
    }
}
