package org.ageuxo.billboardmodels.util;

import net.minecraft.util.Mth;
import org.joml.Quaternionf;

public class Utils {

    public static Quaternionf extractYaw(Quaternionf value, Quaternionf target) {

        float yaw = (float) Mth.atan2(
                2f * (value.y * value.w + value.x * value.z),
                1f - 2f * (value.y * value.y + value.z * value.z)
        );

        return target.identity().rotateY(yaw);
    }

}
