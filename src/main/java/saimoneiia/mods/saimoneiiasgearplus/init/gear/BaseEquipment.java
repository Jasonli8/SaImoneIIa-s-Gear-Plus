package saimoneiia.mods.saimoneiiasgearplus.init.gear;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.client.render.item.MitoSingleSwordRenderer;
import saimoneiia.mods.saimoneiiasgearplus.util.handler.EquipmentHandler;
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

public class BaseEquipment extends Item implements IAnimatable, ISyncable {
    public String name;

    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public String controllerName = "controller";

    public static final int ANIM_OPEN = 0;

    public BaseEquipment(String name) {
        super(new Item.Properties().tab(SaimoneiiasGearPlus.TAB).stacksTo(1));
        GeckoLibNetwork.registerSyncable(this);
        this.name = name;
        EquipmentHandler.instance.onInit(this);
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
