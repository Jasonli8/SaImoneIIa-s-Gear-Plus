package saimoneiia.mods.saimoneiiasgearplus.client.render;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class EquipmentRenderRegistry {
    private static final Map<Item, EquipmentRenderer> REGISTRATIONS = new HashMap<>();

    public static void register(Item item, EquipmentRenderer renderer) {
        REGISTRATIONS.put(item, renderer);
    }

    @Nullable
    public static EquipmentRenderer get(ItemStack stack) {
        return REGISTRATIONS.get(stack.getItem());
    }
}
