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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.WeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.util.handler.MiscellaneousModels;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderRegistry;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderer;
import saimoneiia.mods.saimoneiiasgearplus.proxy.Proxy;

public class MitoSingleSword extends WeaponItem {
    public MitoSingleSword() {
        super(4);
        Proxy.INSTANCE.runOnClient(() -> () -> EquipmentRenderRegistry.register(this, new MitoSingleSword.Renderer()));
    }

    @Override
    public void itemTick(ItemStack stack, LivingEntity livingEntity) {
        // add some functionality here
    }

    @Override
    public void castSkill(Player player, int skillCode) {
        int skillId = skillCode - (int) Math.pow(2, skillInputs - 1);
        switch (skillId) {
            case 0 -> skill0(player);
            case 1 -> skill1(player);
            case 2 -> skill2(player);
            case 3 -> skill3(player);
            case 4 -> skill4(player);
            case 5 -> skill5(player);
            case 6 -> skill6(player);
            case 7 -> skill7(player);
            default -> System.out.println("Shouldn't get here when casting skills");
        }
    }

    @Override
    protected void skill0(Player player) {
        System.out.println("skill0 casted");
    }

    @Override
    protected void skill1(Player player) {
        System.out.println("skill1 casted");
    }

    @Override
    protected void skill2(Player player) { System.out.println("skill2 casted"); }

    @Override
    protected void skill3(Player player) {
        System.out.println("skill3 casted");
    }

    private void skill4(Player player) {
        System.out.println("skill4 casted");
    }

    private void skill5(Player player) {
        System.out.println("skill5 casted");
    }

    private void skill6(Player player) {
        System.out.println("skill6 casted");
    }

    private void skill7(Player player) {
        System.out.println("skill7 casted");
    }


    public static class Renderer implements EquipmentRenderer {
        public void doRender(HumanoidModel<?> bipedModel, ItemStack stack, LivingEntity living, PoseStack ms, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            VertexConsumer buffer = buffers.getBuffer(Sheets.cutoutBlockSheet());

            BakedModel sheathModel = MiscellaneousModels.INSTANCE.mitoSingleSwordSheath;
            boolean armor = !living.getItemBySlot(EquipmentSlot.LEGS).isEmpty();
            ms.pushPose();
            bipedModel.body.translateAndRotate(ms);
//            ms.mulPose(Vector3f.XP.rotationDegrees(180.0F));
            ms.mulPose(Vector3f.YN.rotationDegrees(90.0F));
            ms.mulPose(Vector3f.ZP.rotationDegrees(130.0F));
            ms.translate(0.1F, -1.7F, armor ? -0.8 : -0.75F);
            Minecraft.getInstance().getBlockRenderer().getModelRenderer()
                    .renderModel(ms.last(), buffer, null, sheathModel, 1, 1, 1, light, OverlayTexture.NO_OVERLAY);

            BakedModel bladeModel = MiscellaneousModels.INSTANCE.mitoSingleSwordBlade;
            if (ClientBattleModeData.get()) {
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
