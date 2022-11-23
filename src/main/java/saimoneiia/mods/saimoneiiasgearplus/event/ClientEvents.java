package saimoneiia.mods.saimoneiiasgearplus.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.client.core.handler.MiscellaneousModels;
import saimoneiia.mods.saimoneiiasgearplus.client.keybindings.KeyBinding;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.BattleModeC2SPacket;

@Mod.EventBusSubscriber(modid = SaimoneiiasGearPlus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onModelRegister(ModelEvent.RegisterAdditional evt) {
        var resourceManager = Minecraft.getInstance().getResourceManager();
        MiscellaneousModels.INSTANCE.onModelRegister(resourceManager, evt::register);
    }


    @SubscribeEvent
    public static void onModelBake(ModelEvent.BakingCompleted evt) {
        MiscellaneousModels.INSTANCE.onModelBake(evt.getModelBakery(), evt.getModels());
    }
    @Mod.EventBusSubscriber(modid=SaimoneiiasGearPlus.MODID, value=Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.TOGGLE_BATTLE_MODE_KEY.consumeClick()) {
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
