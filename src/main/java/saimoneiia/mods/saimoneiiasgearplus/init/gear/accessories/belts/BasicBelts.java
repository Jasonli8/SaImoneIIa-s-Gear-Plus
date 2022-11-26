package saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.belts;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.BaseEquipment;

public class BasicBelts {
    public static class ExampleBelt extends BeltItem {
        public ExampleBelt() {
            super("example_belt");
        }

        @Override
        public void itemTick(ItemStack stack, LivingEntity livingEntity) {
            // add some functionality here
        }
    }
}
