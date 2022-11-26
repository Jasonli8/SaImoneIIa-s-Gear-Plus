package saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.charms;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderRegistry;
import saimoneiia.mods.saimoneiiasgearplus.proxy.Proxy;

public class BasicCharms {
    public static class ExampleCharm extends CharmItem {
        public ExampleCharm() {
            super("example_charm");
            Proxy.INSTANCE.runOnClient(() -> () -> EquipmentRenderRegistry.register(this, new Renderer()));
        }

        @Override
        public void itemTick(ItemStack stack, LivingEntity livingEntity) {
            // add some functionality here
        }
    }
}
