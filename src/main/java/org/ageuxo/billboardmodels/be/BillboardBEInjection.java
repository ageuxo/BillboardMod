package org.ageuxo.billboardmodels.be;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.ageuxo.billboardmodels.BlockEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BillboardBEInjection extends EntityBlock {
    @Nullable
    @Override
    default BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        if (state.is(Blocks.DANDELION)) {
            return BlockEntities.SIMPLE_BILLBOARD.get().create(pos, state);
        }

        return null;
    }
}
