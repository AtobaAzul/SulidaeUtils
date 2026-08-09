package net.atobaazul.sulidae_utils.mixin;


import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import top.ribs.scguns.common.BurstTracker;

@Mixin(BurstTracker.class)
public class BurstTrackerMixin {
    @WrapMethod(method = "onPlayerTick(Lnet/minecraftforge/event/TickEvent$PlayerTickEvent;)V", remap = false)
    static private void sulidae_utils$onPlayerTick(TickEvent.PlayerTickEvent event, Operation<Void> original) {
        //basically disabling this method.
    }
}
