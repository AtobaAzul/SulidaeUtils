package net.atobaazul.sulidae_utils.registries;

import net.atobaazul.sulidae_utils.SulidaeUtils;
import net.atobaazul.sulidae_utils.common.item.LiquidWelderItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.atobaazul.sulidae_utils.SulidaeUtils.MODID;

public class SulidaeItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> WELDER =  ITEMS.register("welder", () -> new LiquidWelderItem(new Item.Properties().stacksTo(1)));
}
