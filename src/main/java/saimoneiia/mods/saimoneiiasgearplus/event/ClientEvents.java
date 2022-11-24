package saimoneiia.mods.saimoneiiasgearplus.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.controller.BattleModeController;
import saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression.MemoryProgressionScreen;
import saimoneiia.mods.saimoneiiasgearplus.init.ContainerInit;
import saimoneiia.mods.saimoneiiasgearplus.util.handler.MiscellaneousModels;
import saimoneiia.mods.saimoneiiasgearplus.client.keybindings.KeyBinding;

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
            }
        }

        @SubscribeEvent
        public static void preProcessKeyBindings(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.START) {
                BattleModeController.combatTick(event);
            }
        }
    }

    @Mod.EventBusSubscriber(modid=SaimoneiiasGearPlus.MODID, value=Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.TOGGLE_BATTLE_MODE_KEY);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ContainerInit.MEMORY_PROGRESSION.get(), MemoryProgressionScreen::new);
        }
    }
}
