package net.atobaazul.sulidae_utils.mixin;

import com.jesz.createdieselgenerators.content.burner.BurnerBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.Logging;
import org.apache.logging.slf4j.Log4jLogger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BurnerBlockEntity.class)
public class BurnerBlockEntityMixin extends KineticBlockEntity {
    @Shadow
    public float heat;

    @Shadow
    boolean ignited;

    public BurnerBlockEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Inject(method = "tick", at = @At("HEAD"), remap = false)
    private void sulidae_utils$onTick(CallbackInfo ci) {

        Level level = this.level;

        assert level != null;


        if (heat > 0 && ignited) {
            HeatCapability.provideHeatTo(level, this.worldPosition.above(), heat * 675); //gasoline gives a bit warmer than yellow white, kerosene will be brilliant white.
        }
    }
}
