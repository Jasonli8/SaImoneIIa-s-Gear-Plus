package saimoneiia.mods.saimoneiiasgearplus;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import saimoneiia.mods.saimoneiiasgearplus.init.BlockInit;
import saimoneiia.mods.saimoneiiasgearplus.init.ContainerInit;
import saimoneiia.mods.saimoneiiasgearplus.init.ItemInit;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.BaseEquipment;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.MeleeWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.RangedWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.integration.CurioIntegration;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.util.handler.EquipmentHandler;
import software.bernie.geckolib3.GeckoLib;

@Mod(SaimoneiiasGearPlus.MODID)

public class SaimoneiiasGearPlus {
    public static final String MODID = "saimoneiiasgearplus";

    public SaimoneiiasGearPlus() {
        GeckoLib.initialize();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        EquipmentHandler.init();

        // Initialize basic items
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        ContainerInit.CONTAINERS.register(bus);

        bus.addListener(this::commonSetup);

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
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addGenericListener(ItemStack.class, this::attachItemCaps);
        event.enqueueWork(() -> {});
        ModPackets.register();
    }

    private void attachItemCaps(AttachCapabilitiesEvent<ItemStack> e) {
        var stack = e.getObject();

        if ((stack.getItem() instanceof BaseEquipment || stack.getItem() instanceof MeleeWeaponItem || stack.getItem() instanceof RangedWeaponItem)
                && EquipmentHandler.instance instanceof CurioIntegration ci) {
            e.addCapability(new ResourceLocation(SaimoneiiasGearPlus.MODID, "curio"), ci.initCapability(stack));
        }
    }
}
