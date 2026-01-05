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

        /**
         * Blocks that shouldn't render normally
         */
        public static final TagKey<Block> BILLBOARDS = new TagKey<>(Registries.BLOCK, BillboardMod.modRL("billboards"));

        public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, BillboardMod.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            tag(BILLBOARDS)
                    .addTag(net.minecraft.tags.BlockTags.SMALL_FLOWERS)
                    // PLANTS
                    .add(Blocks.DEAD_BUSH)
                    .add(Blocks.SEAGRASS)
                    .add(Blocks.FERN)
                    .add(Blocks.GRASS)
                    // CROPS
                    .add(Blocks.WHEAT)
                    .add(Blocks.POTATOES)
                    .add(Blocks.CARROTS)
                    .add(Blocks.BEETROOTS)
                    .add(Blocks.TORCHFLOWER_CROP)
                    .add(Blocks.PITCHER_CROP)
                    // SAPLINGS
                    .add(Blocks.OAK_SAPLING)
                    .add(Blocks.BIRCH_SAPLING)
                    .add(Blocks.SPRUCE_SAPLING)
                    .add(Blocks.JUNGLE_SAPLING)
                    .add(Blocks.DARK_OAK_SAPLING)
                    .add(Blocks.ACACIA_SAPLING)
                    .add(Blocks.CHERRY_SAPLING)
                    .add(Blocks.BAMBOO_SAPLING)
                    // TWO TALL
                    .addTag(net.minecraft.tags.BlockTags.TALL_FLOWERS)
                    .add(Blocks.KELP)
                    .add(Blocks.KELP_PLANT)
                    .add(Blocks.LARGE_FERN)
                    .add(Blocks.TALL_GRASS)
                    .add(Blocks.TALL_SEAGRASS);
        }
    }
}
