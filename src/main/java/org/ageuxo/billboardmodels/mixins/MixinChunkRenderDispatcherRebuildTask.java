package org.ageuxo.billboardmodels.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.ageuxo.billboardmodels.data.IBillboardRenderStore;
import org.ageuxo.billboardmodels.datagen.Tags;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChunkRenderDispatcher.RenderChunk.RebuildTask.class)
public class MixinChunkRenderDispatcherRebuildTask {

    @Unique
    private static final Logger billboardMod$LOGGER = LogUtils.getLogger();

    // TODO integrate this closer into the rebuild tasks handling of cancellation
    @WrapOperation(method = "compile",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/chunk/RenderChunkRegion;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", ordinal = 0))
    public BlockState compileAddBillboard(RenderChunkRegion instance, BlockPos pos, Operation<BlockState> original) {
        BlockState state = original.call(instance, pos);
        if (state.is(Tags.BlockTags.BILLBOARD_RENDER)) {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null) {
                if (level.getChunk(pos) instanceof IBillboardRenderStore chunk) {
                    BlockPos blockPos = pos.immutable();
                    int size = chunk.getBillboardRenders().size();
                    if (!chunk.addBillboardRender(new IBillboardRenderStore.BillboardRender(blockPos, state))) {
                        billboardMod$LOGGER.warn("Failed adding billboard: {} @ {}; Old size: {} New size: {}", state, blockPos, size, chunk.getBillboardRenders().size());
                    }
                }
            }
        }
        return state;
    }

//    @WrapOperation(method = "compile", TODO: move this to be based on the result of doTask instead
//    at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;immutable()Lnet/minecraft/core/BlockPos;") )
//    public BlockPos compileClearBillboards(BlockPos.MutableBlockPos instance, Operation<BlockPos> original) {
//        BlockPos pos = original.call(instance);
//        ClientLevel level = Minecraft.getInstance().level;
//        if (level != null) {
//            if (level.getChunk(pos) instanceof LevelChunk chunk) {
//                LazyOptional<IBillboardRenderStore> lazyOptional = chunk.getCapability(IBillboardRenderStore.CAPABILITY);
//            }
//        }
//        return pos;
//    }
}
