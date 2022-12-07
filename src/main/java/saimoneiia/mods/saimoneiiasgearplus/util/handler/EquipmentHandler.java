package saimoneiia.mods.saimoneiiasgearplus.util.handler;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.BaseEquipment;
import saimoneiia.mods.saimoneiiasgearplus.integration.CurioIntegration;

import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Predicate;

public abstract class EquipmentHandler {
    public static EquipmentHandler instance;

    public static void init() {
        if (instance == null) {
            instance = CurioIntegration.tryCreateEquipmentHandler();
        }

        // Fall back to hotbar
        if (instance == null) {
            instance = new InventoryEquipmentHandler();
        }
    }

    public static Container getAllWorn(LivingEntity living) {
        return instance.getAllWornItems(living);
    }

    public static ItemStack findOrEmpty(Item item, LivingEntity living) {
        return instance.findItem(item, living);
    }

    public static ItemStack findOrEmpty(Predicate<ItemStack> pred, LivingEntity living) {
        return instance.findItem(pred, living);
    }

    protected abstract Container getAllWornItems(LivingEntity living);

    protected abstract ItemStack findItem(Item item, LivingEntity living);

    protected abstract ItemStack findItem(Predicate<ItemStack> pred, LivingEntity living);

    public abstract void onInit(Item item);


    // Fallback equipment handler for curios-less (or baubles-less) installs.
    public static class InventoryEquipmentHandler extends EquipmentHandler {
        private final Map<Player, ItemStack[]> map = new WeakHashMap<>();

        public void onPlayerTick(Player player) {
            player.level.getProfiler().push("botania:tick_wearables");

            ItemStack[] oldStacks = map.computeIfAbsent(player, p -> {
                ItemStack[] array = new ItemStack[9];
                Arrays.fill(array, ItemStack.EMPTY);
                return array;
            });

            Inventory inv = player.getInventory();
            for (int i = 0; i < 9; i++) {
                ItemStack old = oldStacks[i];
                ItemStack current = inv.getItem(i);

                if (!ItemStack.matches(old, current)) {
                    if (old.getItem() instanceof BaseEquipment bauble) {
                        player.getAttributes().removeAttributeModifiers(bauble.getEquippedAttributeModifiers(old));
                        bauble.onUnequip(old, player);
                    }
                    if (canEquip(current, player)) {
                        BaseEquipment equipment = (BaseEquipment) current.getItem();
                        player.getAttributes().addTransientAttributeModifiers(equipment.getEquippedAttributeModifiers(current));
                        equipment.onEquip(current, player);
                    }
                    oldStacks[i] = current.copy(); // shift-clicking mutates the stack we stored,
                    // making it empty and failing the equality check - let's avoid that
                }

                if (canEquip(current, player)) {
                    ((BaseEquipment) current.getItem()).itemTick(current, player);
                }
            }
            player.level.getProfiler().pop();
        }

        @Override
        protected Container getAllWornItems(LivingEntity living) {
            return new SimpleContainer(0);
        }

        @Override
        protected ItemStack findItem(Item item, LivingEntity living) {
            if (living instanceof Player player) {
                Inventory inv = player.getInventory();
                for (int i = 0; i < 9; i++) {
                    ItemStack stack = inv.getItem(i);
                    if (stack.is(item) && canEquip(stack, living)) {
                        return stack;
                    }
                }
            }
            return ItemStack.EMPTY;
        }

        @Override
        protected ItemStack findItem(Predicate<ItemStack> pred, LivingEntity living) {
            if (living instanceof Player player) {
                Inventory inv = player.getInventory();
                for (int i = 0; i < 9; i++) {
                    ItemStack stack = inv.getItem(i);
                    if (pred.test(stack) && canEquip(stack, living)) {
                        return stack;
                    }
                }
            }
            return ItemStack.EMPTY;
        }

        @Override
        public void onInit(Item item) {}

        private static boolean canEquip(ItemStack stack, LivingEntity living) {
            return stack.getItem() instanceof BaseEquipment equipment && equipment.canEquip(stack, living);
        }
    }
}
