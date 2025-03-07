package net.atobaazul.sulidae_utils;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.minecraft.resources.ResourceLocation;

public class SulidaePonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return SulidaeUtils.MODID;
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        helper.addToTag(AllCreatePonderTags.DISPLAY_SOURCES).add(TFCBlocks.CRUCIBLE.getId()); // The block's ResourceLocation
        helper.addToTag(AllCreatePonderTags.DISPLAY_SOURCES).add(TFCBlocks.BLAST_FURNACE.getId()); // The block's ResourceLocation

    }
}

