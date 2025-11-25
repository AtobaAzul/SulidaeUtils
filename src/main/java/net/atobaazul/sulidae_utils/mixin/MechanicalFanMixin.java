package net.atobaazul.sulidae_utils.mixin;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import net.dries007.tfc.common.blocks.devices.IBellowsConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EncasedFanBlockEntity.class)
public class MechanicalFanMixin extends KineticBlockEntity {
    @Unique
    private static final int BELLOWS_AIR = 100;

    public MechanicalFanMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Inject(method = "tick", at = @At("HEAD"), remap = false)
    private void sulidae_utils$tick(CallbackInfo ci) {
        if (level != null && this.getSpeed() != 0) {
            final Direction direction = getBlockState().getValue(BlockStateProperties.FACING);


            if (!direction.getAxis().isVertical()) {
                for (IBellowsConsumer.Offset offset : IBellowsConsumer.offsets()) {
                    final BlockPos airPosition = worldPosition.above(offset.up()).relative(direction, offset.out()).relative(direction.getClockWise(), offset.side());
                    final BlockState state = level.getBlockState(airPosition);
                    BlockEntity be = level.getBlockEntity(airPosition);

                    if (state.getBlock() instanceof IBellowsConsumer consumer && be != null) {
                        CompoundTag nbt = be.serializeNBT();
                        int airTicks = nbt.getInt("airTicks");

                        if (consumer.canAcceptAir(level, airPosition, state) && airTicks <= 1) { //offset by 1 because wasn't properly hitting max temp.
                            consumer.intakeAir(level, airPosition, state, (int) (BELLOWS_AIR + Math.abs(this.getSpeed())));
                        }
                    }
                }
            }
        }
    }
}
