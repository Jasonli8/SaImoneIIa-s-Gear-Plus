package saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.hands;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.BaseEquipment;

public class BasicHands {
    public static class ExampleHand extends HandItem {
        public ExampleHand() {
            super("example_hand");
        }

        @Override
        public void itemTick(ItemStack stack, LivingEntity livingEntity) {
            // add some functionality here
        }
    }
}
