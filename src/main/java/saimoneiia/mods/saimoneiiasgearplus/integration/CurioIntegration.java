package saimoneiia.mods.saimoneiiasgearplus.integration;

import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.w3c.dom.ranges.Range;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderRegistry;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.BaseEquipment;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.MeleeWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.RangedWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;
import saimoneiia.mods.saimoneiiasgearplus.proxy.Proxy;
import saimoneiia.mods.saimoneiiasgearplus.util.CapabilityUtil;
import saimoneiia.mods.saimoneiiasgearplus.util.handler.EquipmentHandler;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;
import java.util.function.Predicate;

public class CurioIntegration extends EquipmentHandler {
    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CurioIntegration::sendImc);
    }

    public static EquipmentHandler tryCreateEquipmentHandler() {
        CurioIntegration.init();
        return new CurioIntegration();
    }
    public static void sendImc(InterModEnqueueEvent evt) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> (new SlotTypeMessage.Builder("weapon").priority(1).icon(new ResourceLocation(CuriosApi.MODID, "slot/empty_curio_slot"))).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().cosmetic().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().cosmetic().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(2).cosmetic().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().size(2).cosmetic().build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BELT.getMessageBuilder().cosmetic().build());
    }

    public ICapabilityProvider initCapability(ItemStack stack) {
        return CapabilityUtil.makeProvider(CuriosCapability.ITEM, new Wrapper(stack));
    }

    @Override
    public void onInit(Item item) {
        Proxy.INSTANCE.runOnClient(() -> () -> CuriosRendererRegistry.register(item, () -> Renderer.INSTANCE));
    }

    @Override
    protected Container getAllWornItems(LivingEntity living) {
        return CuriosApi.getCuriosHelper().getEquippedCurios(living)
                .<Container>map(RecipeWrapper::new)
                .orElseGet(() -> new SimpleContainer(0));
    }

    @Override
    protected ItemStack findItem(Item item, LivingEntity living) {
        return CuriosApi.getCuriosHelper().findFirstCurio(living, item)
                .map(SlotResult::stack)
                .orElse(ItemStack.EMPTY);
    }

    @Override
    protected ItemStack findItem(Predicate<ItemStack> pred, LivingEntity living) {
        return CuriosApi.getCuriosHelper().findFirstCurio(living, pred)
                .map(SlotResult::stack)
                .orElse(ItemStack.EMPTY);
    }

    public static class Wrapper implements ICurio {

        private final ItemStack stack;

        Wrapper(ItemStack stack) {
            this.stack = stack;
        }

        private BaseEquipment getBaseEquipmentItem() {
            return (BaseEquipment) stack.getItem();
        }

        private MeleeWeaponItem getMeleeWeaponItemItem() {
            return (MeleeWeaponItem) stack.getItem();
        }

        private RangedWeaponItem getRangedWeaponItemItem() {
            return (RangedWeaponItem) stack.getItem();
        }


        @Override
        public ItemStack getStack() {
            return stack;
        }

        @Override
        public void curioTick(SlotContext slotContext) {
            slotContext.entity().getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> battleMode.tickResources());
            if (stack.getItem() instanceof BaseEquipment) {
                getBaseEquipmentItem().itemTick(stack, slotContext.entity());
            } else if (stack.getItem() instanceof MeleeWeaponItem) {
                getMeleeWeaponItemItem().itemTick(stack, slotContext.entity());
            } else if (stack.getItem() instanceof RangedWeaponItem) {
                getRangedWeaponItemItem().itemTick(stack, slotContext.entity());
            }
        }

        @Override
        public boolean canEquip(SlotContext slotContext) {
            if (stack.getItem() instanceof BaseEquipment) {
                return getBaseEquipmentItem().canEquip(stack, slotContext.entity());
            } else if (stack.getItem() instanceof MeleeWeaponItem) {
                return getMeleeWeaponItemItem().canEquip(stack, slotContext.entity());
            } else if (stack.getItem() instanceof RangedWeaponItem) {
                return getRangedWeaponItemItem().canEquip(stack, slotContext.entity());
            }
            return false;
        }

        @Override
        public void onUnequip(SlotContext slotContext, ItemStack newStack) {
            if (stack.getItem() instanceof BaseEquipment) {
                getBaseEquipmentItem().onUnequip(stack, slotContext.entity());
            } else if (stack.getItem() instanceof MeleeWeaponItem) {
                getMeleeWeaponItemItem().onUnequip(stack, slotContext.entity());
            } else if (stack.getItem() instanceof RangedWeaponItem) {
                getRangedWeaponItemItem().onUnequip(stack, slotContext.entity());
            }
        }

        @Override
        public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
            if (stack.getItem() instanceof BaseEquipment) {
                return getBaseEquipmentItem().getEquippedAttributeModifiers(stack);
            } else if (stack.getItem() instanceof MeleeWeaponItem) {
                return getMeleeWeaponItemItem().getEquippedAttributeModifiers(stack);
            } else {
                return getRangedWeaponItemItem().getEquippedAttributeModifiers(stack);
            }
        }

        @Override
        public boolean canSync(SlotContext slotContext) {
            return true;
        }

        @Override
        public boolean canEquipFromUse(SlotContext slotContext) {
            return true;
        }
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

            if (stack.getItem() instanceof BaseEquipment) {
                BaseEquipment item = (BaseEquipment) stack.getItem();

                if (!item.hasRender(stack, livingEntity)) {
                    return;
                }
            }

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
