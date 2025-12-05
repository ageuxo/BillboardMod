package org.ageuxo.billboardmodels.be;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.ageuxo.billboardmodels.BlockEntities;

public class BillboardBlockEntity extends BlockEntity {

    public BillboardBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public BillboardBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.SIMPLE_BILLBOARD.get(), pos, state);
    }
}
