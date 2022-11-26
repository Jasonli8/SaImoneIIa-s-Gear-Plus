package saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import saimoneiia.mods.saimoneiiasgearplus.client.render.item.MitoSingleSwordBladeRenderer;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.BaseEquipment;

import java.util.function.Consumer;

public class MitoSingleSwordBlade extends BaseEquipment {
    public MitoSingleSwordBlade() {
        super("mito_single_sword_blade");
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new MitoSingleSwordBladeRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }
}
