package saimoneiia.mods.saimoneiiasgearplus.init.gear;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import saimoneiia.mods.saimoneiiasgearplus.client.render.item.MitoSingleSwordSheathRenderer;
import saimoneiia.mods.saimoneiiasgearplus.util.handler.EquipmentHandler;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.function.Consumer;

public class ModelItem extends Item implements IAnimatable, ISyncable {
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public String controllerName = "controller";

    public ModelItem() {
        super(new Item.Properties());
        EquipmentHandler.instance.onInit(this);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) { // OVERRIDE IN EXNTESIONS WITH RENDERER
//        super.initializeClient(consumer);
//        consumer.accept(new IClientItemExtensions() {
//            private final BlockEntityWithoutLevelRenderer renderer = new <YOUR RENDERER HERE>();
//
//            @Override
//            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
//                return renderer;
//            }
//        });
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
    public void onAnimationSync(int id, int state) { }
}
