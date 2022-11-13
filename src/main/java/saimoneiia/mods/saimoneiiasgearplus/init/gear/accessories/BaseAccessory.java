package saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories;

import net.minecraft.world.item.Item;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;

public class BaseAccessory extends Item {

    public BaseAccessory() {
        super(new Item.Properties().tab(SaimoneiiasGearPlus.TAB).stacksTo(1));
    }

    // override with acccessory effect function
    public void itemTick() {}
}
