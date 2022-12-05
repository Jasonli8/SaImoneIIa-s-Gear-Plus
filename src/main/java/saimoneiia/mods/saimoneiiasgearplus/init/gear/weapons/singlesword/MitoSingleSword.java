package saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.client.model.weapon.singlesword.MitoSingleSwordModel;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderRegistry;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderer;
import saimoneiia.mods.saimoneiiasgearplus.client.render.item.MitoSingleSwordRenderer;
import saimoneiia.mods.saimoneiiasgearplus.client.render.item.MitoSingleSwordSheathRenderer;
import saimoneiia.mods.saimoneiiasgearplus.init.ItemInit;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.BaseWeaponTeir;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.MeleeWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.proxy.Proxy;
import saimoneiia.mods.saimoneiiasgearplus.util.handler.ModifiedRenderableModels;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.util.EModelRenderCycle;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Collections;
import java.util.function.Consumer;

public class MitoSingleSword extends MeleeWeaponItem {
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public String controllerName = "controller";

    public static final int ANIM_OPEN = 0;

    private static boolean isChannelled = false;

    public MitoSingleSword() {
        super("mito_single_sword", 4, new BaseWeaponTeir(), 20, -1, (new Item.Properties()).tab(SaimoneiiasGearPlus.TAB).stacksTo(1).fireResistant());
        Proxy.INSTANCE.runOnClient(() -> () -> EquipmentRenderRegistry.register(this, new MitoSingleSword.Renderer()));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new MitoSingleSwordRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public void itemTick(Player player) {
        // add some functionality here
//        if (Minecraft.getInstance().level.isClientSide) {
//            if (!ClientBattleModeData.isBattleMode) isChannelled = false;
//        }
//        if (isChannelled) {
//            System.out.println("isChanneled");
//            if (Minecraft.getInstance().level.isClientSide) isChannelled = ClientBattleModeData.consumeMana(10);
//            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 3));
//        }
    }

    @Override
    public void dodge(Player player, Vec3 directionVec) {
        float moveAmount = isChannelled ? 2F : 1F;
        Vec3 moveVec = directionVec.normalize().scale(moveAmount);
        player.setDeltaMovement(moveVec);
    }

    @Override
    public void powerJump(Player player) {
        float jumpHeight = isChannelled ? 1.5F : 0.8F;
        Vec3 moveVec = Vec3.ZERO.add(0,1,0).normalize().scale(jumpHeight).add(player.getDeltaMovement());
        player.setDeltaMovement(moveVec);
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
        // Ebb & Flow, water attacks
        System.out.println("skill0 casted");
    }

    @Override
    protected void skill1(Player player) {
        // Channel, mana armour and enhanced movement
        isChannelled = !isChannelled;
        System.out.println("skill1 casted");
    }

    @Override
    protected void skill2(Player player) {
        // Rushing Tide, dash and slash
        Vec3 moveVec = player.getLookAngle();
        float moveAmount = 3F;
        if (moveVec.y > 0)  moveVec = moveVec.multiply(1,0,1);
        moveVec = moveVec.normalize().scale(moveAmount).add(player.getDeltaMovement());
        player.setDeltaMovement(moveVec);
        System.out.println("skill2 casted");
    }

    @Override
    protected void skill3(Player player) {
        // Cleave, 3 glaive slashes w large aoe
        System.out.println("skill3 casted");
    }

    private void skill4(Player player) {
        // Rapids, hitcount barrage
        System.out.println("skill4 casted");
    }

    private void skill5(Player player) {
        // Retrace
        System.out.println("skill5 casted");
    }

    private void skill6(Player player) {
        // Hydrolance
        System.out.println("skill6 casted");
    }

    private void skill7(Player player) {
        // Judgement of altair, hitcount mark
        System.out.println("skill7 casted");
    }

    @Override
    public void onAnimationSync(int id, int state) {
        System.out.println("onAnimationSync called");
        if (state == ANIM_OPEN) {
            final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
            controller.clearAnimationCache();
        }
    }

    public class Renderer implements EquipmentRenderer {
        public void doRender(HumanoidModel<?> bipedModel, ItemStack stack, LivingEntity living, PoseStack ms, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            boolean armor = !living.getItemBySlot(EquipmentSlot.LEGS).isEmpty();
            ms.pushPose();
            bipedModel.body.translateAndRotate(ms);
            ms.scale(0.6F, 0.6F, 0.6F);
            ms.mulPose(Vector3f.XN.rotationDegrees(130.0F));
            ms.translate(-0.05, -3.5F, 0.4F);

            new MitoSingleSwordSheathRenderer().render(ItemInit.MITO_SWORD_SINGLE_SHEATH.get(), ms, buffers, light, stack);

            if (!ClientBattleModeData.isBattleMode) {
                new MitoSingleSwordRenderer().render((MitoSingleSword) stack.getItem(), ms, buffers, light, stack);
            }
            ms.popPose();
        }
    }
}
