package saimoneiia.mods.saimoneiiasgearplus.integration;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderRegistry;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories.BaseAccessory;
import saimoneiia.mods.saimoneiiasgearplus.proxy.Proxy;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CurioIntegration{
    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CurioIntegration::sendImc);
    }
    public static void sendImc(InterModEnqueueEvent evt) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> (new SlotTypeMessage.Builder("weapon").priority(1).icon(new ResourceLocation(CuriosApi.MODID, "slot/empty_curio_slot"))).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().cosmetic().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().cosmetic().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(2).cosmetic().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().size(2).cosmetic().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BELT.getMessageBuilder().cosmetic().build());
    }

    public static void onInit(Item item) {
        Proxy.INSTANCE.runOnClient(() -> () -> CuriosRendererRegistry.register(item, () -> Renderer.INSTANCE));
    }

    private static class Renderer implements ICurioRenderer {
        private static final Renderer INSTANCE = new Renderer();

        @Override
        public <T extends LivingEntity, M extends EntityModel<T>>
        void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack,
                    RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
                    int light, float limbSwing, float limbSwingAmount, float partialTicks,
                    float ageInTicks, float netHeadYaw, float headPitch) {
            LivingEntity livingEntity = slotContext.entity();
            M contextModel = renderLayerParent.getModel();
            BaseAccessory item = (BaseAccessory) stack.getItem();

            if (!(contextModel instanceof HumanoidModel<?>)) {
                return;
            }

            matrixStack.pushPose();
            var renderer = EquipmentRenderRegistry.get(stack);
            if (renderer != null) {
                renderer.doRender((HumanoidModel<?>) contextModel, stack, livingEntity, matrixStack,
                        renderTypeBuffer, light, limbSwing, limbSwingAmount,
                        partialTicks, ageInTicks, netHeadYaw, headPitch);
            }
            matrixStack.popPose();
        }
    }
}
