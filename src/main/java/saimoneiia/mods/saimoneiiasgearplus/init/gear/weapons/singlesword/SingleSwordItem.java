package saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword;

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
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderer;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.WeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.util.handler.ModifiedRenderableModels;

public class SingleSwordItem extends WeaponItem {
    public SingleSwordItem(String name, int skillInputs) {
        super (name, skillInputs);
    }

    public class Renderer implements EquipmentRenderer {
        public void doRender(HumanoidModel<?> bipedModel, ItemStack stack, LivingEntity living, PoseStack ms, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            System.out.println("Attempting to perform doRender for SingleSwordItem");
            VertexConsumer buffer = buffers.getBuffer(Sheets.cutoutBlockSheet());

            BakedModel sheathModel = ModifiedRenderableModels.INSTANCE.modelMap.get(name + "_sheath");
            System.out.println(name + "_sheath");
            boolean armor = !living.getItemBySlot(EquipmentSlot.LEGS).isEmpty();
            ms.pushPose();
            bipedModel.body.translateAndRotate(ms);
//            ms.mulPose(Vector3f.XP.rotationDegrees(180.0F));
            ms.mulPose(Vector3f.YN.rotationDegrees(90.0F));
            ms.mulPose(Vector3f.ZP.rotationDegrees(130.0F));
            ms.translate(0.1F, -1.7F, armor ? -0.8 : -0.75F);
            Minecraft.getInstance().getBlockRenderer().getModelRenderer()
                    .renderModel(ms.last(), buffer, null, sheathModel, 1, 1, 1, light, OverlayTexture.NO_OVERLAY);

            BakedModel bladeModel = ModifiedRenderableModels.INSTANCE.modelMap.get(name + "_blade");
            if (ClientBattleModeData.isBattleMode) {
                ms.popPose();
                bipedModel.rightArm.translateAndRotate(ms);
                ms.mulPose(Vector3f.YP.rotationDegrees(90.0F));
                ms.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
                ms.translate(0.05F, -1.75F, -0.5F);
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
