package org.ageuxo.billboardmodels.data;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Quaternionf;

@FunctionalInterface
public interface BillboardTransform {
    void transform(PoseStack poseStack, Quaternionf camRot);
}
