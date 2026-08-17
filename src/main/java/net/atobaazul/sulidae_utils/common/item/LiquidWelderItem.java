package net.atobaazul.sulidae_utils.common.item;

import com.jesz.createdieselgenerators.CDGRegistries;
import com.jesz.createdieselgenerators.content.tools.FueledToolItem;
import com.jesz.createdieselgenerators.fuel_type.FuelType;
import com.simibubi.create.AllEnchantments;
import net.atobaazul.sulidae_utils.SulidaeUtils;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import org.jetbrains.annotations.NotNull;
import rbasamoyai.createbigcannons.crafting.welding.CannonWelderItem;
import rbasamoyai.createbigcannons.crafting.welding.WeldableBlock;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static net.atobaazul.sulidae_utils.SulidaeUtils.ALLOWED_IN_WELDER;

public class LiquidWelderItem extends CannonWelderItem implements FueledToolItem {
    public LiquidWelderItem(Properties properties) {
        super(properties);
    }


    public static boolean drainFuel(int amount, Player player, ItemStack item, boolean simulate) {
        if (player.getAbilities().instabuild) return true;
        if (item.getTag() == null) return false;

        CompoundTag tankCompound = item.getTag().getCompound("Fluid");
        FluidStack fStack = FluidStack.loadFluidStackFromNBT(tankCompound);

        if (fStack.getAmount() < amount) {
            return false;
        } else if (fStack.getAmount() >= amount) {
            if (!simulate) {
                fStack.shrink(amount);
                fStack.writeToNBT(item.getTag().getCompound("Fluid"));
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack)
    {
        return stack.getCapability(Capabilities.FLUID_ITEM).map(cap -> !cap.getFluidInTank(0).isEmpty()).orElse(false);
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack)
    {
        ItemStack newStack = stack.copy();
        if (newStack.getTag() != null) {
            CompoundTag tankCompound = stack.getTag().getCompound("Fluid");
            FluidStack fStack = FluidStack.loadFluidStackFromNBT(tankCompound);
            fStack.shrink(5);
            fStack.writeToNBT(newStack.getTag().getCompound("Fluid"));
            return newStack;
        }

        return new ItemStack(this);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getCurrentFillLevel(stack) > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13 * (float) getCurrentFillLevel(stack) / getCapacity(stack));
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return getFluidHandler(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xEFEFEF;
    }


    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == AllEnchantments.CAPACITY.get()) return true;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, tooltip, tooltipFlag);
        createTooltip(tooltip, stack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public FluidHandlerItemStack getFluidHandler(ItemStack stack) {
        return new WelderFluidHandler(stack, this.getCapacity(stack), this::readFluid, this::writeFluid);
    }

    public static class WelderFluidHandler extends FluidHandlerItemStack {
        BiConsumer<ItemStack, FluidStack> write;
        Function<ItemStack, FluidStack> read;

        public WelderFluidHandler(ItemStack container, int capacity, Function<ItemStack, FluidStack> read, BiConsumer<ItemStack, FluidStack> write) {
            super(container, capacity);
            this.write = write;
            this.read = read;
        }

        public FluidStack getFluid() {
            return this.read.apply(this.container);
        }

        protected void setFluid(FluidStack fluid) {
            this.write.accept(this.container, fluid);
        }


        @Override
        public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
            return stack.getRawFluid().defaultFluidState().is(ALLOWED_IN_WELDER);
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return isFluidValid(0, fluid);
        }
    }
}
