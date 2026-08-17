package net.atobaazul.sulidae_utils.mixin;

import com.jesz.createdieselgenerators.CDGRegistries;
import com.jesz.createdieselgenerators.CDGTags;
import com.jesz.createdieselgenerators.CreateDieselGenerators;
import com.jesz.createdieselgenerators.content.tools.lighter.LighterItem;
import com.jesz.createdieselgenerators.fuel_type.FuelType;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.simibubi.create.AllTags;
import net.dries007.tfc.util.events.StartFireEvent;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LighterItem.class)
public class LighterItemMixin extends Item {
    public LighterItemMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @WrapMethod(method = "useOn", remap = true)
    private InteractionResult suliade_utils$useOn(UseOnContext context, Operation<InteractionResult> original) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(pos);
        ItemStack stack = context.getItemInHand();
        if (stack.getTag() == null || stack.getTag().getInt("Type") != 2)
            return use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
        if (!CampfireBlock.canLight(blockstate) && !CandleBlock.canLight(blockstate) &&
                !CandleCakeBlock.canLight(blockstate) && !blockstate.is(CDGTags.LIGHTER_LIGHTABLE) && !StartFireEvent.startFire(level, pos, level.getBlockState(pos), context.getClickedFace(), player, stack)) {
            BlockPos blockpos1 = pos.relative(context.getClickedFace());
            if (BaseFireBlock.canBePlacedAt(level, blockpos1, context.getHorizontalDirection())) {
                level.playSound(player, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                BlockState blockstate1 = BaseFireBlock.getState(level, blockpos1);
                level.setBlock(blockpos1, blockstate1, 11);
                level.gameEvent(player, GameEvent.BLOCK_PLACE, pos);
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos1, stack);
                }
                CompoundTag tankCompound = stack.getTag().getCompound("Fluid");
                FluidStack fStack = FluidStack.loadFluidStackFromNBT(tankCompound);
                if (fStack.getAmount() == 0) {
                    stack.getTag().putInt("Type", 1);
                    return InteractionResult.FAIL;
                }
                boolean flammable = FuelType.getTypeFor(level.registryAccess().lookupOrThrow(CDGRegistries.FUEL_TYPE), fStack.getFluid()).normal().speed() != 0;
                if (flammable && stack.getTag().getInt("Type") == 2) {
                    fStack.setAmount(fStack.getAmount()-1);
                    fStack.writeToNBT(stack.getTag().getCompound("Fluid"));
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            } else {
                return use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
            }
        } else {
            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            if (blockstate.hasProperty(BlockStateProperties.LIT))
                level.setBlock(pos, blockstate.setValue(BlockStateProperties.LIT, true), 11);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            CompoundTag tankCompound = stack.getTag().getCompound("Fluid");
            FluidStack fStack = FluidStack.loadFluidStackFromNBT(tankCompound);
            if (fStack.getAmount() == 0){
                stack.getTag().putInt("Type", 1);
                return InteractionResult.FAIL;
            }

            boolean flammable = FuelType.getTypeFor(level.registryAccess().lookupOrThrow(CDGRegistries.FUEL_TYPE), fStack.getFluid()).normal().speed() != 0;

            if (flammable && stack.getTag().getInt("Type") == 2){
                fStack.setAmount(fStack.getAmount()-1);
                fStack.writeToNBT(stack.getTag().getCompound("Fluid"));
            }
            StartFireEvent.startFire(level, pos, level.getBlockState(pos), context.getClickedFace(), player, stack);

            return InteractionResult.SUCCESS;
        }

    }
}
