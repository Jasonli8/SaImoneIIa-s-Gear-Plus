package saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.necklaces;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.util.handler.MiscellaneousModels;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderRegistry;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderer;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.BaseEquipment;
import saimoneiia.mods.saimoneiiasgearplus.proxy.Proxy;

public class BasicNecklaces {
    public static class ExampleNecklace extends BaseEquipment {
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
