package saimoneiia.mods.saimoneiiasgearplus.init;

import net.minecraft.world.Containers;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression.MemoryProgressionContainer;

public class ContainerInit {
    private ContainerInit() {}

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, SaimoneiiasGearPlus.MODID);
    public static final RegistryObject<MenuType<MemoryProgressionContainer>> MEMORY_PROGRESSION = CONTAINERS.register("memory_progression", () -> new MenuType<>(MemoryProgressionContainer::new));
}
