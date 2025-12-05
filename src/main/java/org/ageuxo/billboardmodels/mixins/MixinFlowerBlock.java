package org.ageuxo.billboardmodels.mixins;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FlowerBlock.class)
public abstract class MixinFlowerBlock extends Block {

    public MixinFlowerBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        if (state.is(Blocks.DANDELION)){
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }

        return super.getRenderShape(state);
    }
}
