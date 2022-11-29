package saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.render.item.MitoSingleSwordRenderer;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.BaseWeaponTeir;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.MeleeWeaponItem;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.function.Consumer;

public class MitoSingleSword extends MeleeWeaponItem {
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public String controllerName = "controller";

    public static final int ANIM_OPEN = 0;

    public MitoSingleSword() {
        super("mito_single_sword", 4, new BaseWeaponTeir(), 20, -1, (new Item.Properties()).tab(SaimoneiiasGearPlus.TAB).stacksTo(1).fireResistant());
//        Proxy.INSTANCE.runOnClient(() -> () -> EquipmentRenderRegistry.register(this, new MitoSingleSword.Renderer()));
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
        Vec3 moveVec = player.getLookAngle();
        float moveAmount = 3F;
        if (moveVec.y > 0)  moveVec = moveVec.multiply(1,0,1);
        moveVec = moveVec.normalize().scale(moveAmount).add(player.getDeltaMovement());
        player.setDeltaMovement(moveVec);
        System.out.println("skill6 casted, quick dash example");
    }

    private void skill7(Player player) {
        Vec3 playerLookDir = player.getLookAngle();
        float moveAmount = 2F;
        player.setDeltaMovement(playerLookDir.normalize().scale(moveAmount));
        System.out.println("skill7 casted, pushing example");
    }

    @Override
    public void onAnimationSync(int id, int state) {
        System.out.println("onAnimationSync called");
        if (state == ANIM_OPEN) {
            final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
            controller.clearAnimationCache();
            controller.setAnimation(new AnimationBuilder().addAnimation("idle", true));
        }
    }
}
