package saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import saimoneiia.mods.saimoneiiasgearplus.client.render.item.MitoSingleSwordSheathRenderer;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.BaseEquipment;

import java.util.function.Consumer;

public class MitoSingleSwordSheath  extends BaseEquipment {
    public MitoSingleSwordSheath() {
        super("mito_single_sword_sheath");
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new MitoSingleSwordSheathRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }
}
