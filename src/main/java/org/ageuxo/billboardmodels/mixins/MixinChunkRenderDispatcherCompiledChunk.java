package org.ageuxo.billboardmodels.mixins;

import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import org.ageuxo.billboardmodels.data.BillboardPlacement;
import org.ageuxo.billboardmodels.data.BillboardRenderStore;
import org.ageuxo.billboardmodels.data.IBillboardRenderStore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(ChunkRenderDispatcher.CompiledChunk.class)
public class MixinChunkRenderDispatcherCompiledChunk implements IBillboardRenderStore {
    @Unique
    public IBillboardRenderStore billboardMod$billboards = new BillboardRenderStore();

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public List<BillboardPlacement> getBillboardRenders() {
        return billboardMod$billboards.getBillboardRenders();
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public boolean addBillboardRender(BillboardPlacement billboard) {
        return billboardMod$billboards.addBillboardRender(billboard);
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void clearBillboardRenders() {
        billboardMod$billboards.clearBillboardRenders();
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void replaceBillboards(List<BillboardPlacement> replacementBillboards) {
        billboardMod$billboards.replaceBillboards(replacementBillboards);
    }
}
