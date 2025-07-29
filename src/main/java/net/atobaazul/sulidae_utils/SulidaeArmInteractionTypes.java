package net.atobaazul.sulidae_utils;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.dries007.tfc.common.blocks.devices.BarrelBlock;
import net.dries007.tfc.common.blocks.devices.CharcoalForgeBlock;
import net.dries007.tfc.common.blocks.devices.CrucibleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;


// Credit: https://github.com/Manwe03/woodencog/blob/1.20.1-6.0.3/src/main/java/net/chauvedev/woodencog/interaction/CustomArmInteractionPointTypes.java
public class SulidaeArmInteractionTypes {

    static {
        register("crucible", new CrucibleType());
        register("charcoal_forge", new CharcoalForgeType());
        register("barrel", new BarrelType());
    }

    private static <T extends ArmInteractionPointType> void register(String name, T type) {
        Registry.register(CreateBuiltInRegistries.ARM_INTERACTION_POINT_TYPE,SulidaeUtils.MODID + name, type);
    }

    public static void init() {
    }

    public static class CrucibleType extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return state.getBlock() instanceof CrucibleBlock;
        }
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new ArmInteractionPoint(this, level, pos, state);
        }
    }

    public static class CharcoalForgeType extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return state.getBlock() instanceof CharcoalForgeBlock;
        }
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new CharcoalForgePoint(this, level, pos, state);
        }
    }

    public static class CharcoalForgePoint extends ArmInteractionPoint {
        public CharcoalForgePoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
            super(type, level, pos, state);
        }

        @Override
        public ItemStack extract(int slot, int amount, boolean simulate) {
            getHandler();
            return super.extract(slot, amount, simulate);
        }

        @Override
        public ItemStack insert(ItemStack stack, boolean simulate) {
            return super.insert(stack, simulate);
        }

    }

    public static class BarrelType extends ArmInteractionPointType {
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return state.getBlock() instanceof BarrelBlock;
        }
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new BarrelPoint(this, level, pos, state);
        }
    }

    public static class BarrelPoint extends ArmInteractionPoint {
        public BarrelPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
            super(type, level, pos, state);
        }

        @Override
        public ItemStack extract(int slot, int amount, boolean simulate) {
            getHandler();
            return super.extract(slot, amount, simulate);
        }

        @Override
        public ItemStack insert(ItemStack stack, boolean simulate) {
            return super.insert(stack, simulate);
        }
    }
}
