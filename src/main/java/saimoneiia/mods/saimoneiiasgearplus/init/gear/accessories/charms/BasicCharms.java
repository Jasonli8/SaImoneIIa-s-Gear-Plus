package saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.charms;

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
import saimoneiia.mods.saimoneiiasgearplus.client.core.handler.MiscellaneousModels;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderRegistry;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderer;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.BaseAccessory;
import saimoneiia.mods.saimoneiiasgearplus.proxy.Proxy;

public class BasicCharms {
    public static class ExampleCharm extends BaseAccessory {
        public ExampleCharm() {
            super();
            Proxy.INSTANCE.runOnClient(() -> () -> EquipmentRenderRegistry.register(this, new Renderer()));
        }

        @Override
        public void itemTick() {
            // add some functionality here
        }

        public static class Renderer implements EquipmentRenderer {
            public void doRender(HumanoidModel<?> bipedModel, ItemStack stack, LivingEntity living, PoseStack ms, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                boolean armor = !living.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
                bipedModel.body.translateAndRotate(ms);
                ms.translate(-0.25, 0.5, armor ? 0.05 : 0.12);
                ms.scale(0.5F, -0.5F, -0.5F);
                BakedModel model = MiscellaneousModels.INSTANCE.exampleCharm;
                VertexConsumer buffer = buffers.getBuffer(Sheets.cutoutBlockSheet());
                Minecraft.getInstance().getBlockRenderer().getModelRenderer()
                        .renderModel(ms.last(), buffer, null, model, 1, 1, 1, light, OverlayTexture.NO_OVERLAY);
            }
        }
    }
}
