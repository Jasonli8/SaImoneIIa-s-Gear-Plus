package saimoneiia.mods.saimoneiiasgearplus.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.command.MemorySetCommand;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.BattleModeS2CPacket;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.MemoryS2CPacket;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleMode;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;
import saimoneiia.mods.saimoneiiasgearplus.player.memoryprogression.MemoryProgression;
import saimoneiia.mods.saimoneiiasgearplus.player.memoryprogression.MemoryProgressionProvider;

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
        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG).ifPresent(memProg -> {
                    ModPackets.sendToPlayer(new MemoryS2CPacket(memProg.getMem()), player);
                });
                player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                    ModPackets.sendToPlayer(new BattleModeS2CPacket(battleMode.get()), player);
                });
            }
        }
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
            event.getOriginal().invalidateCaps();
        });
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(MemoryProgression.class);
        event.register(BattleMode.class);
    }

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new MemorySetCommand(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }
}
