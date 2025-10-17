package net.atobaazul.sulidae_utils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = SulidaeUtils2.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SulidaeUtils2.MOD_ID, value = Dist.CLIENT)
public class SulidaeUtils2Client {
    public SulidaeUtils2Client(ModContainer container) {

    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {

    }
}
