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
            boolean armor = !living.getItemBySlot(EquipmentSlot.LEGS).isEmpty();
            bipedModel.body.translateAndRotate(ms);
            ms.scale(1.0F, -1.0F, -1.0F); // (keep ratio and sign same, bigger+/smaller-)
            ms.mulPose(Vector3f.XP.rotationDegrees(50.0F));
            ms.mulPose(Vector3f.YN.rotationDegrees(90.0F));
            ms.translate(0.15F, -1.75F, armor ? -0.1 : -0.25F); // (left+/right- body widths from center), (down-/up+ from bodycenter), (back-/forward+ body widths from center)
            BakedModel model = MiscellaneousModels.INSTANCE.mitoSingleSword;
            VertexConsumer buffer = buffers.getBuffer(Sheets.cutoutBlockSheet());
            Minecraft.getInstance().getBlockRenderer().getModelRenderer()
                    .renderModel(ms.last(), buffer, null, model, 1, 1, 1, light, OverlayTexture.NO_OVERLAY);
        }
    }
}
