package net.atobaazul.sulidae_utils;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.atobaazul.sulidae_utils.registries.SulidaeItems;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.createmod.ponder.foundation.registration.GenericPonderSceneRegistrationHelper;
import net.dries007.tfc.common.blocks.LargeVesselBlock;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.resources.ResourceLocation;
import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.ponder.CannonCraftingScenes;
import com.tterrag.registrate.util.entry.RegistryEntry;

import static net.atobaazul.sulidae_utils.SulidaeUtils.MODID;

public class SulidaePonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return MODID;
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

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(SulidaeItems.WELDER)
                .addStoryBoard(new ResourceLocation(CreateBigCannons.MOD_ID, "cannon_crafting/cannon_welder"), CannonCraftingScenes::weldingCannons);
    }
}

