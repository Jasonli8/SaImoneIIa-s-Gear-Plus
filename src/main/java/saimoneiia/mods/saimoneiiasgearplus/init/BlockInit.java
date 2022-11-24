package saimoneiia.mods.saimoneiiasgearplus.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;

import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SaimoneiiasGearPlus.MODID);

    ////////////////////////////////////////////////////////

    public static final RegistryObject<Block> MEMORY_CORE = register(
            "memory_core",
            () -> new Block(BlockBehaviour.Properties.of(Material.AMETHYST)),
            new Item.Properties().tab(SaimoneiiasGearPlus.TAB));

    ////////////////////////////////////////////////////////

    // helper to register blocks into both block and item registry
    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier, Item.Properties properties) {
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        ItemInit.ITEMS.register(name, () ->new BlockItem(block.get(), properties));
        return block;
    };
}
