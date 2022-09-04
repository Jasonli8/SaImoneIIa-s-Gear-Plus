package saimoneiia.mods.saimoneiiasgearplus.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.command.MemorySetCommand;
import saimoneiia.mods.saimoneiiasgearplus.player.MemoryProgression;
import saimoneiia.mods.saimoneiiasgearplus.player.MemoryProgressionProvider;

@Mod.EventBusSubscriber(modid = SaimoneiiasGearPlus.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG).isPresent()) {
                event.addCapability(new ResourceLocation(SaimoneiiasGearPlus.MODID, "properties"), new MemoryProgressionProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getPlayer().getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG).ifPresent(newStore -> {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG).ifPresent(oldStore -> {
                newStore.copyFrom(oldStore);
            });
            event.getOriginal().invalidateCaps();
        });
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(MemoryProgression.class);
    }

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new MemorySetCommand(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }
}
