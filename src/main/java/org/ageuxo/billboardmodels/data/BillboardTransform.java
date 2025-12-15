package org.ageuxo.billboardmodels.data;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;

@FunctionalInterface
public interface BillboardTransform {
    void transform(PoseStack poseStack, Camera camera);
}
