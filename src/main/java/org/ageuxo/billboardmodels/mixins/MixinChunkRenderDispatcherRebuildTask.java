package org.ageuxo.billboardmodels.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.ageuxo.billboardmodels.data.IBillboardRenderStore;
import org.ageuxo.billboardmodels.datagen.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChunkRenderDispatcher.RenderChunk.RebuildTask.class)
public abstract class MixinChunkRenderDispatcherRebuildTask {

    @WrapOperation(method = "compile",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/chunk/RenderChunkRegion;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", ordinal = 0))
    public BlockState compileAddBillboard(RenderChunkRegion instance, BlockPos pos, Operation<BlockState> original, @Local ChunkRenderDispatcher.RenderChunk.RebuildTask.CompileResults compileResults) {
        BlockState state = original.call(instance, pos);
        if (state.is(Tags.BlockTags.BILLBOARD_RENDER)) {
            ((IBillboardRenderStore) compileResults).addBillboardRender(new IBillboardRenderStore.BillboardRender(pos.immutable(), state));
        }
        return state;
    }

    @ModifyExpressionValue(method = "doTask",
            at = @At(value = "NEW", target = "Lnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher$CompiledChunk;"))
    public ChunkRenderDispatcher.CompiledChunk propagateBillboards(ChunkRenderDispatcher.CompiledChunk compiledChunk, @Local ChunkRenderDispatcher.RenderChunk.RebuildTask.CompileResults compileResults) {
        IBillboardRenderStore resultStore = (IBillboardRenderStore) compileResults;
        IBillboardRenderStore originalStore = (IBillboardRenderStore) compiledChunk;
        originalStore.replaceFrom(resultStore);
        return compiledChunk;
    }

}
