package saimoneiia.mods.saimoneiiasgearplus.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.command.MemorySetCommand;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.MeleeWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.RangedWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.MemoryS2CPacket;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleMode;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;
import saimoneiia.mods.saimoneiiasgearplus.player.memoryprogression.MemoryProgression;
import saimoneiia.mods.saimoneiiasgearplus.player.memoryprogression.MemoryProgressionProvider;
import saimoneiia.mods.saimoneiiasgearplus.player.playerspecial.PlayerSpecialState;
import saimoneiia.mods.saimoneiiasgearplus.player.playerspecial.PlayerSpecialProvider;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

@Mod.EventBusSubscriber(modid = SaimoneiiasGearPlus.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG).isPresent()) {
                event.addCapability(new ResourceLocation(SaimoneiiasGearPlus.MODID, "memprog"), new MemoryProgressionProvider());
            }
            if(!event.getObject().getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).isPresent()) {
                event.addCapability(new ResourceLocation(SaimoneiiasGearPlus.MODID, "battlemode"), new BattleModeProvider());
            }
            if(!event.getObject().getCapability(PlayerSpecialProvider.PLAYER_PLAYER_SPECIAL).isPresent()) {
                event.addCapability(new ResourceLocation(SaimoneiiasGearPlus.MODID, "playerspecial"), new PlayerSpecialProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        // player joining the level, not the server. These keep on ticking in-game
        if(!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG).ifPresent(memProg -> {
                    ModPackets.sendToPlayer(new MemoryS2CPacket(memProg.getMem()), player);
                });
                player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                    battleMode.syncClient((ServerPlayer) player);
                });
                player.getCapability(PlayerSpecialProvider.PLAYER_PLAYER_SPECIAL).ifPresent(playerSpecialState -> {
                    playerSpecialState.syncClient((ServerPlayer) player);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLeaveWorld(EntityJoinLevelEvent event) {
        // player leaving the level, not the server. These keep on ticking in-game
//        event.getEntity().getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battlemode -> {
//            battlemode.isBattleMode = false;
//        });
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        // joining the world/server
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        // leaving the world/server
        event.getEntity().getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
            battleMode.isBattleMode = false;
            battleMode.syncClient((ServerPlayer) event.getEntity());
        });
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getEntity().getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG).ifPresent(newStore -> {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG).ifPresent(oldStore -> {
                newStore.copyFrom(oldStore);
            });
            event.getOriginal().invalidateCaps();
        });
        event.getEntity().getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(newStore -> {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(oldStore -> {
                newStore.copyFrom(oldStore);
            });
            newStore.isBattleMode = false;
            newStore.syncClient((ServerPlayer) event.getEntity());

            event.getOriginal().invalidateCaps();
        });
        event.getEntity().getCapability(PlayerSpecialProvider.PLAYER_PLAYER_SPECIAL).ifPresent(newStore -> {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(PlayerSpecialProvider.PLAYER_PLAYER_SPECIAL).ifPresent(oldStore -> {
                newStore.copyFrom(oldStore);
            });
            event.getOriginal().invalidateCaps();
            newStore.resetFromClone();
            newStore.syncClient((ServerPlayer) event.getEntity());
        });
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(MemoryProgression.class);
        event.register(BattleMode.class);
        event.register(PlayerSpecialState.class);
    }

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new MemorySetCommand(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent e) {
        CuriosApi.getCuriosHelper().getCuriosHandler(e.getEntity()).ifPresent(handler -> {
            ICurioStacksHandler stacksHandler = handler.getCurios().get("weapon");
            if (stacksHandler.getStacks().getStackInSlot(0).getItem() instanceof MeleeWeaponItem) {
                MeleeWeaponItem weapon = (MeleeWeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem();
                weapon.attackEntity(e.getEntity(), e.getEntity().level, InteractionHand.MAIN_HAND, e.getTarget(), null);
            } else {
                RangedWeaponItem weapon = (RangedWeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem();
                weapon.attackEntity(e.getEntity(), e.getEntity().level, InteractionHand.MAIN_HAND, e.getTarget(), null);
            }
        });
    }
}
