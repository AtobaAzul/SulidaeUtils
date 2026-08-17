package net.atobaazul.sulidae_utils.registries;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.atobaazul.sulidae_utils.common.item.LiquidWelderItem;
import static net.atobaazul.sulidae_utils.SulidaeUtils.REGISTRATE;
import rbasamoyai.createbigcannons.ModGroup;

public class SulidaeItems {
    static { ModGroup.clearRegistrateModTab(); }

    public static final ItemEntry<LiquidWelderItem> WELDER = REGISTRATE.item("welder", LiquidWelderItem::new)
            .properties(p -> p.stacksTo(1))
            .model((c, p) -> {})
            .register();

    public static void register() {
    }
}
