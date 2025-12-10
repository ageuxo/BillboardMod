package org.ageuxo.billboardmodels;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.ageuxo.billboardmodels.data.IBillboardRenderStore;
import org.ageuxo.billboardmodels.datagen.Tags;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BillboardMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonEvents {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IBillboardRenderStore.class);
    }

    @SubscribeEvent
    public static void registerDataProvider(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();

        generator.addProvider(event.includeClient(),
                new Tags.BlockTags(output, provider, fileHelper)
        );

    }
}
