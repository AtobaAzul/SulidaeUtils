package net.atobaazul.sulidae_utils;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.therighthon.afc.common.blocks.AFCBlocks;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.dries007.tfc.common.blocks.LargeVesselBlock;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
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
        helper.addToTag(AllCreatePonderTags.ARM_TARGETS).add(TFCBlocks.CRUCIBLE.getId());
        //helper.addToTag(AllCreatePonderTags.ARM_TARGETS).add(TFCBlocks.CHARCOAL_FORGE.getId());
        //doesn't have a block item :(
        TFCBlocks.WOODS.forEach((wood, map) -> {
            helper.addToTag(AllCreatePonderTags.ARM_TARGETS).add(map.get(Wood.BlockType.BARREL).getId());
        });

        TFCBlocks.WOODS.forEach((wood, map) -> {
            helper.addToTag(AllCreatePonderTags.ARM_TARGETS).add(map.get(Wood.BlockType.BARREL).getId());
        });
    }
}

