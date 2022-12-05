package saimoneiia.mods.saimoneiiasgearplus.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.init.custom.MemoryNoteItem;
import saimoneiia.mods.saimoneiiasgearplus.init.custom.PlayerCardItem;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.belts.BasicBelts;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.charms.BasicCharms;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.hands.BasicHands;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.necklaces.BasicNecklaces;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.rings.BasicRings;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword.MitoSingleSword;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword.MitoSingleSwordSheath;


public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SaimoneiiasGearPlus.MODID);

    ////////////////////////////////////////////////////////

    // list of items to register
    // 1. equipment models
    public static final RegistryObject<MitoSingleSwordSheath> MITO_SWORD_SINGLE_SHEATH = ITEMS.register("mito_single_sword_sheath", MitoSingleSwordSheath::new);

    // 2. usable items
    public static final RegistryObject<MemoryNoteItem> MEMORY_NOTE = ITEMS.register("memory_note", () -> new MemoryNoteItem(new Item.Properties().tab(SaimoneiiasGearPlus.TAB).stacksTo(16)));
    public static final RegistryObject<PlayerCardItem> PLAYER_CARD = ITEMS.register("player_card", () -> new PlayerCardItem(new Item.Properties().tab(SaimoneiiasGearPlus.TAB).stacksTo(1)));
    public static final RegistryObject<Item> PURE_EXPERIENCE = ITEMS.register("pure_experience", () -> new Item(new Item.Properties().tab(SaimoneiiasGearPlus.TAB).stacksTo(16)));

    // 3. crafting items
    public static final RegistryObject<Item> FABRIC = ITEMS.register("fabric", () -> new Item(new Item.Properties().tab(SaimoneiiasGearPlus.TAB)));
    public static final RegistryObject<Item> ENCHANTED_FABRIC = ITEMS.register("enchanted_fabric", () -> new Item(new Item.Properties().tab(SaimoneiiasGearPlus.TAB)));
    public static final RegistryObject<Item> ENCHANTED_INGOT = ITEMS.register("enchanted_ingot", () -> new Item(new Item.Properties().tab(SaimoneiiasGearPlus.TAB)));
    public static final RegistryObject<Item> ENCHANTED_CRYSTAL = ITEMS.register("enchanted_crystal", () -> new Item(new Item.Properties().tab(SaimoneiiasGearPlus.TAB)));

    // 4. accessories
    public static final RegistryObject<BasicCharms.ExampleCharm> EXAMPLE_CHARM = ITEMS.register("example_charm", BasicCharms.ExampleCharm::new);
    public static final RegistryObject<BasicNecklaces.ExampleNecklace> EXAMPLE_NECKLACE = ITEMS.register("example_necklace", BasicNecklaces.ExampleNecklace::new);
    public static final RegistryObject<BasicHands.ExampleHand> EXAMPLE_HAND = ITEMS.register("example_hand", BasicHands.ExampleHand::new);
    public static final RegistryObject<BasicRings.ExampleRing> EXAMPLE_RING = ITEMS.register("example_ring", BasicRings.ExampleRing::new);
    public static final RegistryObject<BasicBelts.ExampleBelt> EXAMPLE_BELT = ITEMS.register("example_belt", BasicBelts.ExampleBelt::new);
    // 5. weapons
    public static final RegistryObject<MitoSingleSword> MITO_SWORD_SINGLE = ITEMS.register("mito_single_sword", MitoSingleSword::new);

    // 6. armour

}
