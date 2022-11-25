package saimoneiia.mods.saimoneiiasgearplus.init.gear;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
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
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderer;
import saimoneiia.mods.saimoneiiasgearplus.util.handler.EquipmentHandler;
import saimoneiia.mods.saimoneiiasgearplus.util.handler.MiscellaneousModels;

public class BaseEquipment extends Item {
    public String name;

    public BaseEquipment(String name) {
        super(new Item.Properties().tab(SaimoneiiasGearPlus.TAB).stacksTo(1));
        this.name = name;
        EquipmentHandler.instance.onInit(this);
    }

    // override with acccessory effect function
    public void itemTick(ItemStack stack, LivingEntity livingEntity) {}

    public boolean canEquip(ItemStack stack, LivingEntity livingEntity) {
        return true;
    }

    public void onEquip(ItemStack stack, LivingEntity entity) {}

    public void onUnequip(ItemStack stack, LivingEntity entity) {
        ClientBattleModeData.refreshToggle();
    }

    public Multimap<Attribute, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack) {
        return HashMultimap.create();
    }

    public boolean hasRender(ItemStack stack, LivingEntity livingEntity) {
        return true;
    }

    public class Renderer implements EquipmentRenderer {
        public void doRender(HumanoidModel<?> bipedModel, ItemStack stack, LivingEntity living, PoseStack ms, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            boolean armor = !living.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
            bipedModel.body.translateAndRotate(ms);
            ms.translate(-0.25, 0.5, armor ? 0.05 : 0.12);
            ms.scale(0.5F, -0.5F, -0.5F);
            BakedModel model = MiscellaneousModels.INSTANCE.modelMap.get(name);
            VertexConsumer buffer = buffers.getBuffer(Sheets.cutoutBlockSheet());
            Minecraft.getInstance().getBlockRenderer().getModelRenderer()
                    .renderModel(ms.last(), buffer, null, model, 1, 1, 1, light, OverlayTexture.NO_OVERLAY);
        }
    }
}
