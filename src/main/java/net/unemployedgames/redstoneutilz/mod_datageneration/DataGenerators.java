package net.unemployedgames.redstoneutilz.mod_datageneration;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.unemployedgames.redstoneutilz.RedstoneMod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = RedstoneMod.Mod_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent gatherDataEvent) {

        DataGenerator generator = gatherDataEvent.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = gatherDataEvent.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> providerCompletableFuture = gatherDataEvent.getLookupProvider();

        generator.addProvider(gatherDataEvent.includeServer(), new ModRecipeProvider(packOutput));
        generator.addProvider(gatherDataEvent.includeServer(), ModLootTableProvider.create(packOutput));

        generator.addProvider(gatherDataEvent.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(gatherDataEvent.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));

        ModBlockTagGenerator blockTagGenerator = generator.addProvider(gatherDataEvent.includeServer(),
        new ModBlockTagGenerator(packOutput, providerCompletableFuture, existingFileHelper));
        generator.addProvider(gatherDataEvent.includeServer(), new ModItemTagGenerator(packOutput, providerCompletableFuture, blockTagGenerator.contentsGetter(), existingFileHelper));
    }

}
