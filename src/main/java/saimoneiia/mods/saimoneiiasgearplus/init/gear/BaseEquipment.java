package saimoneiia.mods.saimoneiiasgearplus.init.gear;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.integration.CurioIntegration;

public class BaseEquipment extends Item {

    public BaseEquipment() {
        super(new Item.Properties().tab(SaimoneiiasGearPlus.TAB).stacksTo(1));
        CurioIntegration.onInit(this);
    }

    // override with acccessory effect function
    public void itemTick(ItemStack stack, LivingEntity livingEntity) {}

    public boolean canEquip(ItemStack stack, LivingEntity livingEntity) {
        return true;
    }

    public Multimap<Attribute, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack) {
        return HashMultimap.create();
    }

    public boolean hasRender(ItemStack stack, LivingEntity livingEntity) {
        return true;
    }
}
