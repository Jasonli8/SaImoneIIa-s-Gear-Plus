package saimoneiia.mods.saimoneiiasgearplus;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression.MemoryProgressionScreen;
import saimoneiia.mods.saimoneiiasgearplus.init.BlockInit;
import saimoneiia.mods.saimoneiiasgearplus.init.ContainerInit;
import saimoneiia.mods.saimoneiiasgearplus.init.ItemInit;
import saimoneiia.mods.saimoneiiasgearplus.integration.CurioIntegration;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;

@Mod(SaimoneiiasGearPlus.MODID)

public class SaimoneiiasGearPlus {
    public static final String MODID = "saimoneiiasgearplus";

    public SaimoneiiasGearPlus() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        // Initialize basic items
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        ContainerInit.CONTAINERS.register(bus);

        bus.addListener(this::commonSetup);
        CurioIntegration.init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    // List of Creative Tabs
    public static final CreativeModeTab TAB = new CreativeModeTab(MODID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return ItemInit.FABRIC.get().getDefaultInstance();
        }
    };

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {});

        ModPackets.register();
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ContainerInit.MEMORY_PROGRESSION.get(), MemoryProgressionScreen::new);
        }
    }
}
