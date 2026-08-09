package net.atobaazul.sulidae_utils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.ribs.scguns.common.Gun;
import top.ribs.scguns.init.ModSyncedDataKeys;
import top.ribs.scguns.item.GunItem;
import top.ribs.scguns.util.GunCompositeStatHelper;

import java.util.Map;
import java.util.WeakHashMap;

import static net.atobaazul.sulidae_utils.SulidaeUtils.MODID;

/**
 * Author: MrCrayfish
 */
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public class FixedBurstTracker
{
    private static final Map<Player, FixedBurstTracker> BURST_TRACKER_MAP = new WeakHashMap<>();

    private int burstTick;
    private final int slot;
    private final ItemStack stack;
    private final Gun gun;

    private FixedBurstTracker(Player player)
    {
        this.burstTick = player.tickCount-20;
        this.slot = player.getInventory().selected;
        this.stack = player.getInventory().getSelected();
        this.gun = ((GunItem) stack.getItem()).getModifiedGun(stack);
    }

    /**
     * Tests if the current item the player is holding is the same as the one being fired
     *
     * @param player the player to check
     * @return True if it's the same weapon and slot
     */
    private boolean isSameWeapon(Player player)
    {
        return !this.stack.isEmpty() && player.getInventory().selected == this.slot && player.getInventory().getSelected() == this.stack;
    }

    private int getDeltaTicks(Player player)
    {
        int deltaTicks = player.tickCount - this.burstTick;
        return deltaTicks;
    }

    private int getBurstDelayTicks(Player player)
    {
        int minTickDelay = GunCompositeStatHelper.getCompositeRate(stack,gun,player)+Gun.getBurstCooldown(stack);
        return minTickDelay-1;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if(event.phase == TickEvent.Phase.START && !event.player.level().isClientSide)
        {
            Player player = event.player;
            if(!BURST_TRACKER_MAP.containsKey(player))
            {
                if(!(player.getInventory().getSelected().getItem() instanceof GunItem))
                {
                    ModSyncedDataKeys.BURSTCOUNT.setValue(player, 0);
                    ModSyncedDataKeys.ONBURSTCOOLDOWN.setValue(player, false);
                    return;
                }
                BURST_TRACKER_MAP.put(player, new FixedBurstTracker(player));
            }
            FixedBurstTracker tracker = BURST_TRACKER_MAP.get(player);
            boolean resetBurst = false;

            if(player.getInventory().getSelected().getItem() instanceof GunItem && tracker.stack.getItem() instanceof GunItem gunItem)
            {
                if (ModSyncedDataKeys.SHOOTING.getValue(player) && Gun.hasBurstFire(tracker.stack))
                {
                    tracker.burstTick = player.tickCount;
                }

                if (tracker.isSameWeapon(player))
                {
                    if (!ModSyncedDataKeys.SHOOTING.getValue(player))
                    {
                        boolean onCooldown = tracker.getDeltaTicks(player) < tracker.getBurstDelayTicks(player);
                        if (!onCooldown)
                            ModSyncedDataKeys.ONBURSTCOOLDOWN.setValue(player, false);
                    }
                    else
                    if (Gun.hasBurstFire(tracker.stack))
                        ModSyncedDataKeys.ONBURSTCOOLDOWN.setValue(player, true);
                }
                else
                    resetBurst = true;
            }
            else
                resetBurst = true;

            if (resetBurst)
            {
                ModSyncedDataKeys.BURSTCOUNT.setValue(player, 0);
                if(BURST_TRACKER_MAP.containsKey(player))
                {
                    BURST_TRACKER_MAP.remove(player);
                }
                return;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerEvent.PlayerLoggedOutEvent event)
    {
        MinecraftServer server = event.getEntity().getServer();
        if(server != null)
        {
            server.execute(() -> BURST_TRACKER_MAP.remove(event.getEntity()));
        }
    }
}