package org.ageuxo.billboardmodels.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.ageuxo.billboardmodels.BillboardMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class Tags {

    public static class BlockTags extends BlockTagsProvider {

        public static final TagKey<Block> BILLBOARD_RENDER = new TagKey<>(Registries.BLOCK, BillboardMod.modRL("billboard_render"));

        public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, BillboardMod.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            tag(BlockTags.BILLBOARD_RENDER)
                    .add(Blocks.POPPY)
                    .add(Blocks.TALL_GRASS);
        }
    }
}
