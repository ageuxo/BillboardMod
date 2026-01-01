package org.ageuxo.billboardmodels.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.ageuxo.billboardmodels.BillboardMod;
import org.jetbrains.annotations.NotNull;

public class ModelProvider extends BlockStateProvider {

    public ModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BillboardMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        replaceDoubleTall(Blocks.TALL_GRASS,
                doubleTallTintedBillboard("tall_grass", vanillaRL("block/tall_grass")),
                "tall_grass",
                vanillaRL("block/tall_grass_bottom")
        );
        
        replaceDoubleTall(Blocks.LARGE_FERN,
                doubleTallTintedBillboard("large_fern", vanillaRL("block/large_fern")),
                "large_fern",
                vanillaRL("block/large_fern_bottom")
        );

        replaceDoubleTall(Blocks.ROSE_BUSH,
                doubleTallBillboard("rose_bush", vanillaRL("block/rose_bush_top"), vanillaRL("block/rose_bush_bottom")),
                "rose_bush",
                vanillaRL("block/rose_bush_bottom")
        );

        replaceDoubleTall(Blocks.LILAC,
                doubleTallBillboard("lilac", vanillaRL("block/lilac_top"), vanillaRL("block/lilac_bottom")),
                "lilac",
                vanillaRL("block/lilac_bottom")
        );

        replaceDoubleTall(Blocks.PEONY,
                doubleTallBillboard("peony", vanillaRL("block/peony_top"), vanillaRL("block/peony_bottom")),
                "peony",
                vanillaRL("block/peony_bottom")
        );

        replaceDoubleTall(Blocks.PITCHER_PLANT,
                doubleTallBillboard("pitcher_plant", vanillaRL("block/pitcher_crop_top_stage_4"), vanillaRL("block/pitcher_crop_bottom_stage_4")),
                "pitcher_plant",
                vanillaRL("block/pitcher_crop_bottom_stage_4")
        );

        replaceDoubleTall(Blocks.SUNFLOWER,
                billboard("sunflower")
                        .addSprite(vanillaRL("block/sunflower_top"), 0.5f, 0.5f, 0f, 1f)
                        .addSprite(vanillaRL("block/sunflower_front"), 0.5f, 0.5f, 0f, 1f)
                        .addSprite(vanillaRL("block/sunflower_bottom"), 0.5f, 0.5f, 0f, 0f)
                        .addParticleTexture(vanillaRL("block/sunflower_bottom"))
                        .end(),
                "sunflower",
                vanillaRL("block/sunflower_bottom")
        );

    }

    private static @NotNull ResourceLocation vanillaRL(String path) {
        return new ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, path);
    }

    private BlockModelBuilder doubleTallTintedBillboard(String billboardLocation, ResourceLocation unprefixedTexture) {
        return doubleTallTintedBillboard(billboardLocation, unprefixedTexture.withSuffix("_top"), unprefixedTexture.withSuffix("_bottom"));
    }

    private BlockModelBuilder doubleTallTintedBillboard(String billboardLocation, ResourceLocation textureTop, ResourceLocation textureBottom) {
        return billboard(billboardLocation)
                .addTintedSprite(textureTop, 0, 0.5f, 0.5f, 0f, 1f)
                .addTintedSprite(textureBottom, 0, 0.5f, 0.5f, 0f, 0f)
                .addParticleTexture(textureBottom)
                .end();
    }

    private BlockModelBuilder doubleTallBillboard(String billboardLocation, ResourceLocation textureTop, ResourceLocation textureBottom) {
        return billboard(billboardLocation)
                .addSprite(textureTop, 0.5f, 0.5f, 0f, 1f)
                .addSprite(textureBottom, 0.5f, 0.5f, 0f, 0f)
                .addParticleTexture(textureBottom)
                .end();
    }

    private void replaceDoubleTall(Block tallPlant, ModelFile tallGrass, String modelName, ResourceLocation particleTexture) {
        getVariantBuilder(tallPlant)
                .forAllStates(state -> {
                    if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                        return ConfiguredModel.builder()
                                .modelFile(tallGrass)
                                .build();
                    }

                    return ConfiguredModel.builder()
                            .modelFile(
                                    billboard(modelName+"_top")
                                            .addParticleTexture(particleTexture)
                                            .end()
                            )
                            .build();
                });
    }

    private BillboardBuilder billboard(String location) {
        return models().getBuilder(location).customLoader(BillboardBuilder::new);
    }
}
