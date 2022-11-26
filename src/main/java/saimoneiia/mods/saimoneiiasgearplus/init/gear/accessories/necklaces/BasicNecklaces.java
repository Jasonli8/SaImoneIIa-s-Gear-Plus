package saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.necklaces;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderRegistry;
import saimoneiia.mods.saimoneiiasgearplus.proxy.Proxy;

public class BasicNecklaces {
    public static class ExampleNecklace extends NecklaceItem {
        public ExampleNecklace() {
            super("example_necklace");
            Proxy.INSTANCE.runOnClient(() -> () -> EquipmentRenderRegistry.register(this, new BasicNecklaces.ExampleNecklace.Renderer()));
        }

        @Override
        public void itemTick(ItemStack stack, LivingEntity livingEntity) {
            // add some functionality here
            System.out.println("example necklace tick");
        }
    }
}
