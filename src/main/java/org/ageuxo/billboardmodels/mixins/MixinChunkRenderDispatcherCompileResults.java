package org.ageuxo.billboardmodels.mixins;

import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import org.ageuxo.billboardmodels.data.BillboardPlacement;
import org.ageuxo.billboardmodels.data.BillboardRenderStore;
import org.ageuxo.billboardmodels.data.IBillboardRenderStore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(ChunkRenderDispatcher.RenderChunk.RebuildTask.CompileResults.class)
public class MixinChunkRenderDispatcherCompileResults implements IBillboardRenderStore {

    @Unique
    private final BillboardRenderStore billboardMod$billboardRenderStore = new BillboardRenderStore();

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Unique
    @Override
    public List<BillboardPlacement> getBillboardRenders() {
        return billboardMod$billboardRenderStore.getBillboardRenders();
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Unique
    @Override
    public boolean addBillboardRender(BillboardPlacement billboard) {
        return billboardMod$billboardRenderStore.addBillboardRender(billboard);
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Unique
    @Override
    public void clearBillboardRenders() {
        billboardMod$billboardRenderStore.clearBillboardRenders();
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void replaceBillboards(List<BillboardPlacement> replacementBillboards) {
        billboardMod$billboardRenderStore.replaceBillboards(replacementBillboards);
    }


}
