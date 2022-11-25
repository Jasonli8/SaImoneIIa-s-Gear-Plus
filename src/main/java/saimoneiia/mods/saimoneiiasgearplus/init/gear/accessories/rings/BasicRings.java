package saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.rings;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.BaseEquipment;

public class BasicRings {
    public static class ExampleRing extends BaseEquipment {
        public ExampleRing() {
            super("example_ring");
        }

        @Override
        public void itemTick(ItemStack stack, LivingEntity livingEntity) {
            // add some functionality here
        }
    }
}
