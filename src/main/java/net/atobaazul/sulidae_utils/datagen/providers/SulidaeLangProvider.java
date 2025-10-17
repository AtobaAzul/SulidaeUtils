package net.atobaazul.sulidae_utils.datagen.providers;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import static net.atobaazul.sulidae_utils.SulidaeUtils2.MOD_ID;

public class SulidaeLangProvider extends LanguageProvider {
    public SulidaeLangProvider(PackOutput output) {
        super(output, MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        /*this.addItem(TFCColdSweatItems.BURLAP_CHESTPLATE, "Burlap Shirt");
        this.addItem(TFCColdSweatItems.BURLAP_HELMET, "Burlap Hat");
        this.addItem(TFCColdSweatItems.BURLAP_LEGGINGS, "Burlap Pants");*/
    }
}
