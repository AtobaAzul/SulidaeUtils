package net.atobaazul.sulidae_utils.datagen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

import static net.atobaazul.sulidae_utils.SulidaeUtils2.MOD_ID;

public class SulidaeItemModelProvider extends ItemModelProvider {
    public SulidaeItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //dyeableItem(TFCColdSweatItems.WOOL_HELMET);

    }

    private ItemModelBuilder simpleItem(DeferredItem<Item> item) {
        return withExistingParent(item.getId().getPath(), ResourceLocation.fromNamespaceAndPath("minecraft", "item/generated")).texture("layer0", ResourceLocation.fromNamespaceAndPath(MOD_ID, "item/" + item.getId().getPath()));
    }
}
