package net.atobaazul.sulidae_utils.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.atobaazul.sulidae_utils.common.item.LiquidWelderItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import rbasamoyai.createbigcannons.crafting.welding.CannonWelderItem;
import rbasamoyai.createbigcannons.network.ServerboundUseWelderPacket;

import java.util.concurrent.Executor;

@Mixin(ServerboundUseWelderPacket.class)
public class UseWelderPacketMixin {
    @Shadow @Final private BlockPos from;

    @Shadow @Final private BlockPos to;

    @WrapMethod(method="handle", remap = false)
    public void sulidae_utils$handle(Executor exec, PacketListener listener, ServerPlayer sender, Operation<Void> original) {
        if (sender == null) return;
        ItemStack stack = sender.getMainHandItem();
        if ((stack.getItem() instanceof LiquidWelderItem)) {
            ServerLevel level = sender.serverLevel();
            if (LiquidWelderItem.drainFuel(5, sender, stack, true) && CannonWelderItem.weldBlocks(level, this.from, this.to, false)) {
                LiquidWelderItem.drainFuel(5, sender, stack, false);
            }

        } else {
            original.call(exec, listener, sender);
        }
    }
}
