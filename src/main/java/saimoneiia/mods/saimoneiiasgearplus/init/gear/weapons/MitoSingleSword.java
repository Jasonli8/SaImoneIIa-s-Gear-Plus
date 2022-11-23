package saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.client.core.handler.MiscellaneousModels;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderRegistry;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderer;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.BaseEquipment;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.charms.BasicCharms;
import saimoneiia.mods.saimoneiiasgearplus.proxy.Proxy;

public class MitoSingleSword extends BaseEquipment {
    public MitoSingleSword() {
        super();
        Proxy.INSTANCE.runOnClient(() -> () -> EquipmentRenderRegistry.register(this, new MitoSingleSword.Renderer()));
    }
    public void itemTick(ItemStack stack, LivingEntity livingEntity) {
        // add some functionality here
    }

    public static class Renderer implements EquipmentRenderer {
        public void doRender(HumanoidModel<?> bipedModel, ItemStack stack, LivingEntity living, PoseStack ms, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            VertexConsumer buffer = buffers.getBuffer(Sheets.cutoutBlockSheet());

            BakedModel sheathModel = MiscellaneousModels.INSTANCE.mitoSingleSwordSheath;
            boolean armor = !living.getItemBySlot(EquipmentSlot.LEGS).isEmpty();
            ms.pushPose();
            bipedModel.body.translateAndRotate(ms);
            ms.mulPose(Vector3f.XP.rotationDegrees(180.0F));
            ms.mulPose(Vector3f.YN.rotationDegrees(90.0F));
            ms.mulPose(Vector3f.ZN.rotationDegrees(50.0F));
            ms.translate(0.1F, -1.8F, armor ? -0.2 : -0.25F);
            Minecraft.getInstance().getBlockRenderer().getModelRenderer()
                    .renderModel(ms.last(), buffer, null, sheathModel, 1, 1, 1, light, OverlayTexture.NO_OVERLAY);

            BakedModel bladeModel = MiscellaneousModels.INSTANCE.mitoSingleSwordBlade;
            if (ClientBattleModeData.get()) {
                ms.popPose();
                bipedModel.leftArm.translateAndRotate(ms);
                ms.mulPose(Vector3f.YP.rotationDegrees(90.0F));
                ms.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
                ms.translate(0.05F, -1.75F, -0.4F);
                Minecraft.getInstance().getBlockRenderer().getModelRenderer()
                        .renderModel(ms.last(), buffer, null, bladeModel, 1, 1, 1, light, OverlayTexture.NO_OVERLAY);
            } else {
                Minecraft.getInstance().getBlockRenderer().getModelRenderer()
                        .renderModel(ms.last(), buffer, null, bladeModel, 1, 1, 1, light, OverlayTexture.NO_OVERLAY);
                ms.popPose();
            }
        }
    }
}
