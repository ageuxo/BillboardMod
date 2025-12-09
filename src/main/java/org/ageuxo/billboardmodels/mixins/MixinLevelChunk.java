package org.ageuxo.billboardmodels.mixins;

import net.minecraft.world.level.chunk.LevelChunk;
import org.ageuxo.billboardmodels.data.BillboardRenderStore;
import org.ageuxo.billboardmodels.data.IBillboardRenderStore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(LevelChunk.class)
public class MixinLevelChunk implements IBillboardRenderStore {

    @Unique
    private final BillboardRenderStore billboardMod$billboardRenderStore = new BillboardRenderStore();

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Unique
    @Override
    public List<BillboardRender> getBillboardRenders() {
        return billboardMod$billboardRenderStore.getBillboardRenders();
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Unique
    @Override
    public boolean addBillboardRender(BillboardRender billboard) {
        return billboardMod$billboardRenderStore.addBillboardRender(billboard);
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Unique
    @Override
    public void clearBillboardRenders() {
        billboardMod$billboardRenderStore.clearBillboardRenders();
    }
}
