package net.atobaazul.sulidae_utils.mixin;

import com.alekiponi.alekiships.common.entity.vehicle.SloopUnderConstructionEntity;
import com.alekiponi.alekiships.util.BoatMaterial;
import com.alekiponi.firmaciv.common.entity.vehicle.FirmacivSloopUnderConstructionEntity;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import oshi.util.tuples.Pair;

@Mixin(FirmacivSloopUnderConstructionEntity.class)
public class FirmacivSloopUnderConstructionEntityMixin extends SloopUnderConstructionEntity {
    public FirmacivSloopUnderConstructionEntityMixin(EntityType<? extends SloopUnderConstructionEntity> entityType, Level level, BoatMaterial boatMaterial) {
        super(entityType, level, boatMaterial);
    }

    @Inject(method = "getRiggingItem", at= @At("HEAD"), remap = false, cancellable = true)
    public void Sulidae$getRiggingItem(CallbackInfoReturnable<Pair<Item, Integer>> cir) {
        cir.setReturnValue( new Pair<>(ModRegistry.ROPE_ITEM.get(), 8));
    }
}
