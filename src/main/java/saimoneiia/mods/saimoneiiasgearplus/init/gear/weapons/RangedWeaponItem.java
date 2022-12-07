package saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;
import saimoneiia.mods.saimoneiiasgearplus.util.handler.EquipmentHandler;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class RangedWeaponItem extends BowItem implements IAnimatable, ISyncable {
    public String name;
    public int skillInputs = 3;
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public String controllerName = "controller";


    public static final int ANIM_OPEN = 0;

    public boolean skillLockout = false;
    protected int iTicks = 0;
    protected int attackSurroundingTicks = 0;
    protected float attackSurroundingAmount = 4.0F;
    protected IntList entitiesHitInCurrentSKill = new IntArrayList();
    protected List<EntityHitResult> hitResults = null;

    protected int skill0Ticks = -1;
    protected int skill1Ticks = -1;
    protected int skill2Ticks = -1;
    protected int skill3Ticks = -1;


    // NO LONG IN USE UNTIL INTEGRATION OF CURIO AND GECKOLIB CAN BE SOLVED
    // WILL USE REGULAR GECKOLIB ITEM RENDERING FOR NOW (MEANS NO MULTI MODEL RENDERING UNTIL CUSTOM RENDERER MADE)
//    public class Renderer implements EquipmentRenderer {
//        public void doRender(HumanoidModel<?> bipedModel, ItemStack stack, LivingEntity living, PoseStack ms, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
//            System.out.println("Attempting to perform doRender for SingleSwordItem");
//            VertexConsumer buffer = buffers.getBuffer(Sheets.cutoutBlockSheet());
//
//            BakedModel sheathModel = ModifiedRenderableModels.INSTANCE.modelMap.get(name + "_sheath");
//            System.out.println(sheathModel.toString());
//            boolean armor = !living.getItemBySlot(EquipmentSlot.LEGS).isEmpty();
//            ms.pushPose();
//            bipedModel.body.translateAndRotate(ms);
////            ms.mulPose(Vector3f.XP.rotationDegrees(180.0F));
//            ms.mulPose(Vector3f.YN.rotationDegrees(90.0F));
//            ms.mulPose(Vector3f.ZP.rotationDegrees(130.0F));
//            ms.translate(0.1F, -1.7F, armor ? -0.8 : -0.75F);
//            Minecraft.getInstance().getBlockRenderer().getModelRenderer()
//                    .renderModel(ms.last(), buffer, null, sheathModel, 1, 1, 1, light, OverlayTexture.NO_OVERLAY);
//
//            BakedModel bladeModel = ModifiedRenderableModels.INSTANCE.modelMap.get(name + "_blade");
//            System.out.println(bladeModel.toString());
//            if (ClientBattleModeData.isBattleMode) {
//                ms.popPose();
//                bipedModel.rightArm.translateAndRotate(ms);
//                ms.mulPose(Vector3f.YP.rotationDegrees(90.0F));
//                ms.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
//                ms.translate(0.05F, -1.75F, -0.5F);
//                Minecraft.getInstance().getBlockRenderer().getModelRenderer()
//                        .renderModel(ms.last(), buffer, null, bladeModel, 1, 1, 1, light, OverlayTexture.NO_OVERLAY);
//            } else {
//                Minecraft.getInstance().getBlockRenderer().getModelRenderer()
//                        .renderModel(ms.last(), buffer, null, bladeModel, 1, 1, 1, light, OverlayTexture.NO_OVERLAY);
//                ms.popPose();
//            }
//
//            Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(ms.last(), buffer, null, ForgeHooksClient.handleCameraTransforms(ms, ModifiedRenderableModels.INSTANCE.modelMap.get("mito_single_sword_in_hand"), ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, false), 1, 1, 1, light, OverlayTexture.NO_OVERLAY);
//        }
//    }

    public RangedWeaponItem(String name, int skillInputs, Item.Properties properties) {
        super(properties);

        GeckoLibNetwork.registerSyncable(this);
        this.name = name;
        this.skillInputs = skillInputs;
        EquipmentHandler.instance.onInit(this);
    }

    public int getRequiredSkillInputs() { return skillInputs; }

    protected void skill0(Player player) {}

    protected void skill1(Player player) {}

    protected void skill2(Player player) {}

    protected void skill3(Player player) {}

    public void castSkill(Player player, int skillCode) {
        int skillId = skillCode - (int) Math.pow(2, skillInputs);
        switch (skillId) {
            case 0 -> skill0(player);
            case 1 -> skill1(player);
            case 2 -> skill2(player);
            case 3 -> skill3(player);
            default -> System.out.println("Shouldn't get here when casting skills");
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) { // OVERRIDE THIS IF YOU HAVE CUSTOM MODELS
//        super.initializeClient(consumer);
//        consumer.accept(new IClientItemExtensions() {
//            private final BlockEntityWithoutLevelRenderer renderer = new <YOUR ITEM RENDERER HERE>();
//
//            @Override
//            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
//                return renderer;
//            }
//        });
    }

    // override with acccessory effect function
    public void itemTick(ItemStack stack, LivingEntity livingEntity) {}

    public InteractionResult attackEntity(Player player, Level world, InteractionHand hand, Entity target, @Nullable EntityHitResult hit) { return InteractionResult.PASS; }

    public void serverBasicAttack(ServerPlayer player) {}

    public void dodge(Player player, Vec3 directionVec) {
        float moveAmount = 1F;
        Vec3 moveVec = directionVec.normalize().scale(moveAmount);
        player.setDeltaMovement(moveVec);
    }

    public void powerJump(Player player) {
        float jumpHeight = 0.8F;
        Vec3 moveVec = Vec3.ZERO.add(0,1,0).normalize().scale(jumpHeight).add(player.getDeltaMovement());
        player.setDeltaMovement(moveVec);
    }

    public boolean canEquip(ItemStack stack, LivingEntity livingEntity) {
        return true;
    }

    public void onEquip(ItemStack stack, LivingEntity entity) {}

    public void onUnequip(ItemStack stack, LivingEntity entity) {
        Minecraft.getInstance().player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> battleMode.refreshToggle());
    }

    public Multimap<Attribute, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack) {
        return HashMultimap.create();
    }

    public boolean hasRender(ItemStack stack, LivingEntity livingEntity) {
        return true;
    }

    public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, controllerName, 1, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void onAnimationSync(int id, int state) {
        if (state == ANIM_OPEN) {
            final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
            controller.clearAnimationCache();
        }
    }
}
