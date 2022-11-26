package saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderRegistry;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderer;
import saimoneiia.mods.saimoneiiasgearplus.client.render.item.MitoSingleSwordRenderer;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.WeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.proxy.Proxy;
import saimoneiia.mods.saimoneiiasgearplus.util.handler.ModifiedRenderableModels;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.function.Consumer;

public class MitoSingleSword extends SingleSwordItem {
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public String controllerName = "controller";

    public static final int ANIM_OPEN = 0;

    public MitoSingleSword() {
        super("mito_single_sword", 4);
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
