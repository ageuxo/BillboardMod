package org.ageuxo.billboardmodels.capability;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.ageuxo.billboardmodels.BillboardModels;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IBillboardRenderStore {
    ResourceLocation LOCATION = BillboardModels.modRL("billboard_render");
    Capability<IBillboardRenderStore> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });

    List<BillboardRender> getBillboardRenders();
    void addBillboardRender(BillboardRender billboard);
    void clear();

    class Provider implements ICapabilityProvider {

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == CAPABILITY) {
                return LazyOptional.of(BillboardRenderStore::new).cast();
            }
            return LazyOptional.empty();
        }
    }

    record BillboardRender(BlockPos pos, BlockState state) { }
}
