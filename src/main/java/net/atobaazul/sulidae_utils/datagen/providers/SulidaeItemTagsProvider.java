package net.atobaazul.sulidae_utils.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static net.atobaazul.sulidae_utils.SulidaeUtils2.MOD_ID;

public class SulidaeItemTagsProvider extends ItemTagsProvider {
    public SulidaeItemTagsProvider(@NotNull PackOutput output, @NotNull CompletableFuture<HolderLookup.Provider> lookupProvider, @NotNull CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, MOD_ID, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.Provider provider) {
        /*tag(ItemTags.DYEABLE)
                .replace(false)
                .add(TFCColdSweatItems.WOOL_HELMET.get())
                .add(TFCColdSweatItems.WOOL_CHESTPLATE.get())
                .add(TFCColdSweatItems.WOOL_LEGGINGS.get())
                .add(TFCColdSweatItems.SILK_HELMET.get())
                .add(TFCColdSweatItems.SILK_CHESTPLATE.get())
                .add(TFCColdSweatItems.SILK_LEGGINGS.get())
                .add(TFCColdSweatItems.BURLAP_HELMET.get())
                .add(TFCColdSweatItems.BURLAP_CHESTPLATE.get())
                .add(TFCColdSweatItems.BURLAP_LEGGINGS.get());*/


    }
}
