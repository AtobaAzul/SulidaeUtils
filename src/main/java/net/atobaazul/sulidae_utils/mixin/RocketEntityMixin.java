package net.atobaazul.sulidae_utils.mixin;

import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.ribs.scguns.Config;
import top.ribs.scguns.entity.projectile.ProjectileEntity;
import top.ribs.scguns.entity.projectile.RocketEntity;

import static top.ribs.scguns.entity.projectile.ProjectileEntity.createExplosion;

@Mixin(RocketEntity.class)
public class RocketEntityMixin extends ProjectileEntity {
    public RocketEntityMixin(EntityType<? extends Entity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    @Inject(method= "onHitEntity", at = @At("HEAD"), remap = false)
    private void sulidae_utils$onHitEntity(CallbackInfo ci) {
        createExplosion(this, Config.COMMON.rockets.explosionRadius.get().floatValue(), true);
    }
}
