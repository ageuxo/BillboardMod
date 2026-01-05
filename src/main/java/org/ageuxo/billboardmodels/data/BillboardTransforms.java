package org.ageuxo.billboardmodels.data;

import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import net.minecraft.resources.ResourceLocation;
import org.ageuxo.billboardmodels.BillboardMod;
import org.joml.Quaternionf;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public static class SimpleRegistry<V> {

        private final ResourceLocation id;
        private final Map<ResourceLocation, V> entries = new HashMap<>();
        private final Map<V, ResourceLocation> reversed = new HashMap<>();

        public SimpleRegistry(ResourceLocation id) {
            this.id = id;
        }

        public V register(ResourceLocation location, V entry) {
            entries.put(Objects.requireNonNull(location, "Location must be non-null"), Objects.requireNonNull(entry, "Entry value must be non-null"));
            reversed.put(entry, location); // Add to reverse lookup as well
            return entry;
        }

        public V get(ResourceLocation location) {
            return entries.get(location);
        }

        public V getOrDefault(ResourceLocation location, V fallback) {
            return entries.getOrDefault(location, fallback);
        }

        public V getOrThrow(ResourceLocation location) {
            var entry = get(location);
            if (entry == null) {
                throw new IllegalStateException("Attempted to get non-registered value from %s: %s".formatted(this, location));
            }

            return entry;
        }

        public ResourceLocation getLocation(V entry) {
            return reversed.get(entry);
        }

        public boolean containsKey(ResourceLocation location) {
            return entries.containsKey(location);
        }

        public boolean containsEntry(V entry) {
            return entries.containsValue(entry);
        }



        @Override
        public String toString() {
            return "SimpleRegistry{" +
                    "id=" + id +
                    ", entries=" + entries +
                    '}';
        }
    }
}
