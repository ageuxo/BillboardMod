package org.ageuxo.billboardmodels.data;

import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import org.ageuxo.billboardmodels.BillboardMod;
import org.ageuxo.billboardmodels.util.SimpleRegistry;
import org.joml.Quaternionf;

public class BillboardTransforms {
    public static final Quaternionf ROT_HOLDER = new Quaternionf();

    public static final SimpleRegistry<BillboardTransform> TRANSFORMS = new SimpleRegistry<>(BillboardMod.modRL("transforms"));

    /* BUILT IN */

    public static final BillboardTransform CAMERA_RELATIVE = builtIn("camera_relative",
            (poseStack, camera) -> {
                poseStack.translate(0, -0.5f, 0);
                poseStack.mulPose(camera.rotation());
                poseStack.translate(0, 0.5f, 0);
            }
    );

    public static final BillboardTransform CAMERA_Y_ALIGNED = builtIn("camera_y_aligned",
            (poseStack, camera) -> {
                var camRot = camera.rotation();
                ROT_HOLDER.set(0, camRot.y, 0, camRot.w);
                poseStack.mulPose(ROT_HOLDER);
            }
    );

    public static final BillboardTransform Y_AXIS_ALIGNED = builtIn("y_axis_aligned",
            (poseStack, camera) -> {
                poseStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot()));
            }
    );

    /* INTERNALS */

    private static BillboardTransform builtIn(String name, BillboardTransform transform) {
        return TRANSFORMS.register(BillboardMod.modRL(name), transform);
    }

    public static void init() {
        LogUtils.getLogger().debug("Initialising BillboardTransform registry.");
    }

}
