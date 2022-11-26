package saimoneiia.mods.saimoneiiasgearplus.client.model.weapon.singlesword;

import net.minecraft.resources.ResourceLocation;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword.MitoSingleSword;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword.MitoSingleSwordBlade;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MitoSingleSwordBladeModel extends AnimatedGeoModel<MitoSingleSwordBlade> {

    @Override
    public ResourceLocation getAnimationResource(MitoSingleSwordBlade animatable) {
        return new ResourceLocation(SaimoneiiasGearPlus.MODID, "animations/mito_single_sword_blade.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(MitoSingleSwordBlade object) {
        return new ResourceLocation(SaimoneiiasGearPlus.MODID, "geo/mito_single_sword_blade.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MitoSingleSwordBlade animatable) {
        return new ResourceLocation(SaimoneiiasGearPlus.MODID, "textures/models/mito_single_sword.png");
    }
}
