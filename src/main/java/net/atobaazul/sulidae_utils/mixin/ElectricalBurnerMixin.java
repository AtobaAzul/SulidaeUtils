package net.atobaazul.sulidae_utils.mixin;

import net.dragonegg.moreburners.content.block.entity.BaseBurnerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.dragonegg.moreburners.content.block.entity.ElectricBurnerBlockEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;

@Mixin(ElectricBurnerBlockEntity.class)
public abstract class ElectricalBurnerMixin extends BaseBurnerBlockEntity {

    public ElectricalBurnerMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    //makes the electrical burner heat up crucibles
    @Inject(method= "tick", at = @At("HEAD"), remap = false)
    private void ptfc_utils$onTick(CallbackInfo ci) {
        Level level = this.level;

        assert level != null;

        HeatCapability.provideHeatTo(level, this.worldPosition.above(), (float) (this.heat));
    }
}

