package org.ageuxo.billboardmodels.data;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public record BillboardPlacement(BlockPos pos, BlockState state, BillboardTransform transform) {
}
