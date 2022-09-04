package saimoneiia.mods.saimoneiiasgearplus.init;

import com.sun.jna.Memory;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.init.custom.MemoryNoteItem;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SaimoneiiasGearPlus.MODID);

    ////////////////////////////////////////////////////////

    // list of items to register
    public static final RegistryObject<Item> EXAMPLE_ITEM =
            ITEMS.register("example_item",
                    () -> new Item(new Item.Properties().tab(SaimoneiiasGearPlus.TAB)));

    public static final RegistryObject<Item> MEMORY_NOTE =
            ITEMS.register("memory_note",
                    () -> new MemoryNoteItem(new Item.Properties().tab(SaimoneiiasGearPlus.TAB).stacksTo(16)));
    public static final RegistryObject<Item> PURE_EXPERIENCE =
            ITEMS.register("pure_experience",
                    () -> new Item(new Item.Properties().tab(SaimoneiiasGearPlus.TAB).stacksTo(16)));
    public static final RegistryObject<Item> FABRIC =
            ITEMS.register("fabric",
                    () -> new Item(new Item.Properties().tab(SaimoneiiasGearPlus.TAB)));
    public static final RegistryObject<Item> ENCHANTED_FABRIC =
            ITEMS.register("enchanted_fabric",
                    () -> new Item(new Item.Properties().tab(SaimoneiiasGearPlus.TAB)));
    public static final RegistryObject<Item> ENCHANTED_INGOT =
            ITEMS.register("enchanted_ingot",
                    () -> new Item(new Item.Properties().tab(SaimoneiiasGearPlus.TAB)));
    public static final RegistryObject<Item> ENCHANTED_CRYSTAL =
            ITEMS.register("enchanted_crystal",
                    () -> new Item(new Item.Properties().tab(SaimoneiiasGearPlus.TAB)));
}
