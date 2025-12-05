package org.ageuxo.billboardmodels;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonEvents {
    /*@SubscribeEvent
    public static void placeBlockEvent(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().getBlock() == Blocks.DANDELION) {
            if (event.getLevel() instanceof Level level) {
                level.setBlockEntity(Objects.requireNonNull(BlockEntities.SIMPLE_BILLBOARD.get().create(event.getBlockSnapshot().getPos(), event.getPlacedBlock())));
            }
        }
    }*/
}
