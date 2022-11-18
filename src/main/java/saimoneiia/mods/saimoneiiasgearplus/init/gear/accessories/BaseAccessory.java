package saimoneiia.mods.saimoneiiasgearplus.init.gear.accessories;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.integration.CurioIntegration;

public class BaseAccessory extends Item {

    public BaseAccessory() {
        super(new Item.Properties().tab(SaimoneiiasGearPlus.TAB).stacksTo(1));
        CurioIntegration.onInit(this);
    }

    // override with acccessory effect function
    public void itemTick() {}
}
