package net.atobaazul.sulidae_utils;

import com.mojang.logging.LogUtils;
import net.createmod.ponder.foundation.PonderIndex;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;
import rbasamoyai.createbigcannons.CBCTags;
import rbasamoyai.createbigcannons.effects.particles.impacts.SparkBurstParticleData;
import top.ribs.scguns.common.Gun;
import top.ribs.scguns.entity.projectile.ProjectileEntity;
import top.ribs.scguns.event.GunFireEvent;
import top.ribs.scguns.event.GunProjectileHitEvent;
import top.ribs.scguns.init.ModSounds;
import top.ribs.scguns.item.GunItem;

import static net.atobaazul.sulidae_utils.SulidaeDisplaySources.DISPLAY_SOURCES;

/* The TODO list.
- Prevent fueling blaze burners
- CDG gas burner and induction heater heats up crucibles
 */

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SulidaeUtils.MODID)
public class SulidaeUtils {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "sulidae_utils";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public SulidaeUtils(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        // Register the commonSetup method for modloading
        bus.addListener(this::commonSetup);

        DISPLAY_SOURCES.register(bus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        bus.addListener(SulidaeUtils::onRegister);
    }

    public static void onRegister(final RegisterEvent event) {
        SulidaeArmInteractionTypes.init();
    }

    static float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        //LOGGER.info("HELLO FROM COMMON SETUP");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        //LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            PonderIndex.addPlugin(new SulidaePonderPlugin());
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        @SubscribeEvent()
        public static void gunShootEvent(GunFireEvent event) {
            ItemStack item = event.getStack();
            if (HeatCapability.has(item)) {
                HeatCapability.addTemp(HeatCapability.get(item), 1100f, 10);
            }
        }

        @SubscribeEvent()
        public static void gunPostShootEvent(GunFireEvent.Post event) {

            Player player = event.getEntity();
            Level level = event.getEntity().level();
            ItemStack item = event.getStack();
            if (HeatCapability.has(item) && HeatCapability.getTemperature(item) > 700f) {
                level.playSound(player, player.blockPosition(), ModSounds.COPPER_GUN_JAM.get(), SoundSource.PLAYERS, 1.0F, 1.0f);
            }
        }

        @SubscribeEvent
        public static void gunPreShootEvent(GunFireEvent.Pre event) {

            Player player = event.getEntity();
            Level level = event.getEntity().level();
            ItemStack item = event.getStack();

            if (HeatCapability.has(item) && HeatCapability.getTemperature(item) > 580 && item.getItem() instanceof GunItem gunItem) {
                Gun gun = gunItem.getModifiedGun(item);

                if (Math.random() >= lerp(1, 0, HeatCapability.getTemperature(item) / 1100f)) {
                    event.getEntity().playSound(ModSounds.ITEM_PISTOL_COCK.get(), 1.0F, 1.0F);
                    int coolDown = gun.getGeneral().getRate() * 10;
                    if (coolDown > 30) {
                        coolDown = 30;
                    }
                    event.getEntity().getCooldowns().addCooldown(event.getStack().getItem(), coolDown);
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void gunProjectileHitEvent(GunProjectileHitEvent event) {
            ProjectileEntity projectile = event.getProjectile();
            HitResult hitResult = event.getRayTrace();
            Level level = projectile.level();

            if (!level.isClientSide) {
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    ServerLevel sLevel = (ServerLevel) level;
                    BlockHitResult bHitResult = (BlockHitResult) hitResult;
                    BlockPos pos = bHitResult.getBlockPos();
                    BlockState state = level.getBlockState(pos);
                    Direction opposite = bHitResult.getDirection();

                    Vec3 cPos = pos.relative(opposite).getCenter();
                    Vec3 speed = projectile.getDeltaMovement();
                    Vec3 mSpeed = new Vec3(speed.x, -speed.y, speed.z).scale(0.5);


                    if (state.is(CBCTags.CBCBlockTags.SPARK_EFFECT_ON_IMPACT)) {
                        sLevel.sendParticles(new SparkBurstParticleData(new Vec3(1, 1, 1).toVector3f(), false, 1 + level.getRandom().nextIntBetweenInclusive(3, 4)), cPos.x, cPos.y, cPos.z, 0, mSpeed.x, mSpeed.y, mSpeed.z, 0.5f);
                    }
                }
            }
        }
    }
}
