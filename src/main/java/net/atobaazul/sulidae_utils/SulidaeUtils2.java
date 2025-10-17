package net.atobaazul.sulidae_utils;

import net.atobaazul.sulidae_utils.registries.SulidaeTabs;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(SulidaeUtils2.MOD_ID)
public class SulidaeUtils2 {
    public static final String MOD_ID = "sulidae_utils";

    public SulidaeUtils2(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);


        SulidaeTabs.CREATIVE_MODE_TABS.register(modEventBus);


        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class
        //NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }
}
