package net.atobaazul.sulidae_utils;

import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.simibubi.create.api.registry.CreateRegistries;
import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.source.SingleLineDisplaySource;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import com.simibubi.create.foundation.gui.ModularGuiLineBuilder;
//import net.dries007.tfc.common.blockentities.BlastFurnaceBlockEntity;
//import net.dries007.tfc.common.blockentities.CrucibleBlockEntity;
//import net.dries007.tfc.common.blocks.TFCBlocks;
//import net.dries007.tfc.config.TemperatureDisplayStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class SulidaeDisplaySources {
    /*public static final DeferredRegister<DisplaySource> DISPLAY_SOURCES = DeferredRegister.create(CreateRegistries.DISPLAY_SOURCE, SulidaeUtils.MODID);

    public static final RegistryObject<DisplaySource> CRUCIBLE = DISPLAY_SOURCES.register("tfc_crucible", () -> {
        DisplaySource source = new CrucibleDisplaySource();
        DisplaySource.BY_BLOCK.add(TFCBlocks.CRUCIBLE.get(), source);
        return source;
    });

    public static final RegistryObject<DisplaySource> BLAST_FURNACE = DISPLAY_SOURCES.register("tfc_blast_furnace", () -> {
        DisplaySource source = new BlastFurnaceDisplaySource();
        DisplaySource.BY_BLOCK.add(TFCBlocks.BLAST_FURNACE.get(), source);
        return source;
    });

    public static class CrucibleDisplaySource extends SingleLineDisplaySource {
        @Override
        protected MutableComponent provideLine(DisplayLinkContext displayLinkContext, DisplayTargetStats displayTargetStats) {


            if (displayLinkContext.getSourceBlockEntity() instanceof CrucibleBlockEntity crucible) {

                float temp = crucible.getTemperature();
                int config = displayLinkContext.sourceConfig().getInt("Units");

                if (config == 0) {
                    return Component.literal(temp + "ºC");
                } else {
                    return temp > 1.0F ? TemperatureDisplayStyle.COLOR.formatColored(temp) : Component.literal("No heat");
                }
            }
            return Component.empty();
        }

        @Override
        protected boolean allowsLabeling(DisplayLinkContext displayLinkContext) {
            return true;
        }

        @Override
        public void initConfigurationWidgets(DisplayLinkContext context, ModularGuiLineBuilder builder, boolean isFirstLine) {
            super.initConfigurationWidgets(context, builder, isFirstLine);
            if (!isFirstLine) {
                builder.addSelectionScrollInput(0, 137, (input, label) -> {
                    input.forOptions(List.of(Component.literal("Number"), Component.literal("Color"), Component.literal("Units"))).titled(Component.literal("Units"));
                }, "Units");
            }
        }

        @Override
        public Component getName() {
            return Component.translatable("block.tfc.crucible");
        }
    }

    public static class BlastFurnaceDisplaySource extends SingleLineDisplaySource {
        @Override
        protected MutableComponent provideLine(DisplayLinkContext displayLinkContext, DisplayTargetStats displayTargetStats) {
            if (displayLinkContext.getSourceBlockEntity() instanceof BlastFurnaceBlockEntity blastfurnace) {

                float temp = blastfurnace.getTemperature();
                int config = displayLinkContext.sourceConfig().getInt("Units");

                if (config == 0) {
                    return Component.literal(temp + "ºC");
                } else if (config == 1) {
                    return temp > 1.0F ? TemperatureDisplayStyle.COLOR.formatColored(temp) : Component.literal("No heat");
                } else if (config == 2) {
                    return Component.literal(String.valueOf(blastfurnace.getInputCount()));
                } else if (config == 3) {
                    return Component.literal(String.valueOf(blastfurnace.getCatalystCount()));
                } else if (config == 4) {
                    return Component.literal(String.valueOf(blastfurnace.getFuelCount()));
                } else {
                    return Component.literal((int) temp + "ºC | " + blastfurnace.getInputCount() + " | " + blastfurnace.getCatalystCount() + " | " + blastfurnace.getFuelCount());
                }
            }
            return Component.empty();

        }

        @Override
        protected boolean allowsLabeling(DisplayLinkContext displayLinkContext) {
            return true;
        }

        @Override
        public void initConfigurationWidgets(DisplayLinkContext context, ModularGuiLineBuilder builder, boolean isFirstLine) {
            super.initConfigurationWidgets(context, builder, isFirstLine);
            if (!isFirstLine) {
                builder.addSelectionScrollInput(0, 137, (input, label) -> {
                    input.forOptions(List.of(Component.literal("Temperature (Number)"), Component.literal("Temperature (Color)"), Component.literal("Input Count"), Component.literal("Catalyst Count"), Component.literal("Fuel Count"), Component.literal("Compact"))).titled(Component.literal("Type of Information"));
                }, "Units");
            }
        }

        @Override
        public Component getName() {
            return Component.translatable("block.tfc.blast_furnace");
        }
    }*/
}
