package org.ageuxo.billboardmodels;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.ageuxo.billboardmodels.be.BillboardBlockEntity;

public class BlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BillboardModels.MODID);

    public static final RegistryObject<BlockEntityType<BillboardBlockEntity>> SIMPLE_BILLBOARD = TYPES.register("simple_billboard",
            ()-> BlockEntityType.Builder.of(BillboardBlockEntity::new, Blocks.DANDELION, Blocks.POPPY).build(null)
    );

}
