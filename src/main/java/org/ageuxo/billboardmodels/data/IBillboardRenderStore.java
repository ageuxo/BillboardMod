package org.ageuxo.billboardmodels.data;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public interface IBillboardRenderStore {

    List<BillboardRender> getBillboardRenders();
    boolean addBillboardRender(BillboardRender billboard);
    void clearBillboardRenders();

    record BillboardRender(BlockPos pos, BlockState state) { }
}
