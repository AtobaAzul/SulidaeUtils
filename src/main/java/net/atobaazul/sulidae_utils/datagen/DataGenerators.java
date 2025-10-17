package net.atobaazul.sulidae_utils.datagen;

import net.atobaazul.sulidae_utils.datagen.providers.SulidaeBlockTagsProvider;
import net.atobaazul.sulidae_utils.datagen.providers.SulidaeItemModelProvider;
import net.atobaazul.sulidae_utils.datagen.providers.SulidaeItemTagsProvider;
import net.atobaazul.sulidae_utils.datagen.providers.SulidaeLangProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        SulidaeBlockTagsProvider blockTagProvider = generator.addProvider(event.includeServer(), new SulidaeBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new SulidaeItemTagsProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeClient(), new SulidaeItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new SulidaeLangProvider(packOutput));
    }
}
