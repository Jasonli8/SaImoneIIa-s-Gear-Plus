package saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import saimoneiia.mods.saimoneiiasgearplus.client.render.item.MitoSingleSwordSheathRenderer;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.ModelItem;

import java.util.function.Consumer;

public class MitoSingleSwordSheath extends ModelItem {

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
