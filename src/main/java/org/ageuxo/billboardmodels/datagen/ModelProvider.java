package org.ageuxo.billboardmodels.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.ageuxo.billboardmodels.BillboardMod;
import org.ageuxo.billboardmodels.data.BillboardTransforms;
import org.jetbrains.annotations.NotNull;

public class ModelProvider extends BlockStateProvider {

    public ModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BillboardMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        replace(Blocks.GRASS,
                billboard("grass")
                        .addTintedSprite(vanillaRL("block/grass"), 0, 0.5f, 0.5f, 0f, 0f)
                        .addParticleTexture(vanillaRL("block/grass"))
        );

        replace(Blocks.FERN,
                billboard("fern")
                        .addTintedSprite(vanillaRL("block/fern"), 0, 0.5f, 0.5f, 0f, 0f)
                        .addParticleTexture(vanillaRL("block/fern"))
        );

        replace(Blocks.KELP,
                billboard("kelp")
                        .addSprite(vanillaRL("block/kelp"), 0.5f, 0.5f, 0f ,0f)
                        .addParticleTexture(vanillaRL("block/kelp"))
                        .setTransform(BillboardTransforms.Y_AXIS_ALIGNED)
        );

        replace(Blocks.KELP_PLANT,
                billboard("kelp_plant")
                        .addSprite(vanillaRL("block/kelp_plant"), 0.5f, 0.5f, 0f ,0f)
                        .addParticleTexture(vanillaRL("block/kelp_plant"))
                        .setTransform(BillboardTransforms.Y_AXIS_ALIGNED)
        );

        replaceDoubleTall(Blocks.TALL_SEAGRASS,
                doubleTallBillboard("tall_seagrass", vanillaRL("block/tall_seagrass"))
                        .setTransform(BillboardTransforms.Y_AXIS_ALIGNED),
                "tall_seagrass",
                vanillaRL("block/tall_seagrass")
        );

        replaceDoubleTall(Blocks.TALL_GRASS,
                doubleTallTintedBillboard("tall_grass", vanillaRL("block/tall_grass"))
                        .setTransform(BillboardTransforms.Y_AXIS_ALIGNED),
                "tall_grass",
                vanillaRL("block/tall_grass_bottom")
        );
        
        replaceDoubleTall(Blocks.LARGE_FERN,
                doubleTallTintedBillboard("large_fern", vanillaRL("block/large_fern"))
                        .setTransform(BillboardTransforms.Y_AXIS_ALIGNED),
                "large_fern",
                vanillaRL("block/large_fern_bottom")
        );

        replaceDoubleTall(Blocks.ROSE_BUSH,
                doubleTallBillboard("rose_bush", vanillaRL("block/rose_bush"))
                        .setTransform(BillboardTransforms.Y_AXIS_ALIGNED),
                "rose_bush",
                vanillaRL("block/rose_bush_bottom")
        );

        replaceDoubleTall(Blocks.LILAC,
                doubleTallBillboard("lilac", vanillaRL("block/lilac"))
                        .setTransform(BillboardTransforms.Y_AXIS_ALIGNED),
                "lilac",
                vanillaRL("block/lilac_bottom")
        );

        replaceDoubleTall(Blocks.PEONY,
                doubleTallBillboard("peony", vanillaRL("block/peony"))
                        .setTransform(BillboardTransforms.Y_AXIS_ALIGNED),
                "peony",
                vanillaRL("block/peony_bottom")
        );

        replaceDoubleTall(Blocks.PITCHER_PLANT,
                doubleTallBillboard("pitcher_plant", vanillaRL("block/pitcher_crop_top_stage_4"), vanillaRL("block/pitcher_crop_bottom_stage_4"))
                        .setTransform(BillboardTransforms.Y_AXIS_ALIGNED),
                "pitcher_plant",
                vanillaRL("block/pitcher_crop_bottom_stage_4")
        );

        replaceDoubleTall(Blocks.SUNFLOWER,
                billboard("sunflower")
                        .addSprite(vanillaRL("block/sunflower_top"), 0.5f, 0.5f, 0f, 1f)
                        .addSprite(vanillaRL("block/sunflower_front"), 0.5f, 0.5f, 0f, 1f)
                        .addSprite(vanillaRL("block/sunflower_bottom"), 0.5f, 0.5f, 0f, 0f)
                        .addParticleTexture(vanillaRL("block/sunflower_bottom"))
                        .setTransform(BillboardTransforms.Y_AXIS_ALIGNED),
                "sunflower",
                vanillaRL("block/sunflower_bottom")
        );

    }

    private static @NotNull ResourceLocation vanillaRL(String path) {
        return new ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, path);
    }

    private BillboardBuilder doubleTallTintedBillboard(String billboardLocation, ResourceLocation unprefixedTexture) {
        return doubleTallTintedBillboard(billboardLocation, unprefixedTexture.withSuffix("_top"), unprefixedTexture.withSuffix("_bottom"));
    }

    private BillboardBuilder doubleTallTintedBillboard(String billboardLocation, ResourceLocation textureTop, ResourceLocation textureBottom) {
        return billboard(billboardLocation)
                .addTintedSprite(textureTop, 0, 0.5f, 0.5f, 0f, 1f)
                .addTintedSprite(textureBottom, 0, 0.5f, 0.5f, 0f, 0f)
                .addParticleTexture(textureBottom);
    }

    private BillboardBuilder doubleTallBillboard(String billboardLocation, ResourceLocation unsuffixedTexture) {
        return doubleTallBillboard(billboardLocation, unsuffixedTexture.withSuffix("_top"), unsuffixedTexture.withSuffix("_bottom"));
    }

    private BillboardBuilder doubleTallBillboard(String billboardLocation, ResourceLocation textureTop, ResourceLocation textureBottom) {
        return billboard(billboardLocation)
                .addSprite(textureTop, 0.5f, 0.5f, 0f, 1f)
                .addSprite(textureBottom, 0.5f, 0.5f, 0f, 0f)
                .addParticleTexture(textureBottom);
    }

    private void replaceDoubleTall(Block tallPlant, BillboardBuilder builder, String modelName, ResourceLocation particleTexture) {
        getVariantBuilder(tallPlant)
                .forAllStates(state -> {
                    if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                        return ConfiguredModel.builder()
                                .modelFile(builder.end())
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

    private void replace(Block block, BillboardBuilder builder) {
        getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(builder.end())
                        .build()
                );
    }

    private BillboardBuilder billboard(String location) {
        return models().getBuilder(location).customLoader(BillboardBuilder::new);
    }
}
