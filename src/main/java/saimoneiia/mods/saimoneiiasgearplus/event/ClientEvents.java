package saimoneiia.mods.saimoneiiasgearplus.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.client.keybindings.KeyBinding;
import saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression.MemoryProgressionScreen;
import saimoneiia.mods.saimoneiiasgearplus.init.ContainerInit;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.BattleModeC2SPacket;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.MemoryS2CPacket;

@Mod.EventBusSubscriber(modid = SaimoneiiasGearPlus.MODID)
public class ClientEvents {
    @Mod.EventBusSubscriber(modid=SaimoneiiasGearPlus.MODID, value=Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.TOGGLE_BATTLE_MODE_KEY.consumeClick()) {
                // TODO: implement toggle_battle_mode
                ClientBattleModeData.toggle();
                ModPackets.sendToServer(new BattleModeC2SPacket(ClientBattleModeData.get()));

                // debug
                if (ClientBattleModeData.get()) {
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("Activating Battle Mode!"));
                } else {
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("De-activating Battle Mode!"));
                }

            }
        }
    }

    @Mod.EventBusSubscriber(modid=SaimoneiiasGearPlus.MODID, value=Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.TOGGLE_BATTLE_MODE_KEY);
        }
    }
}
