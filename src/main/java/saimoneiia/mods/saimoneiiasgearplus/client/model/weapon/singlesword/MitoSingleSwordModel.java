package saimoneiia.mods.saimoneiiasgearplus.client.model.weapon.singlesword;

import net.minecraft.resources.ResourceLocation;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword.MitoSingleSword;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MitoSingleSwordModel extends AnimatedGeoModel<MitoSingleSword> {

    @Override
    public ResourceLocation getAnimationResource(MitoSingleSword animatable) {
        return new ResourceLocation(SaimoneiiasGearPlus.MODID, "animations/mito_single_sword.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(MitoSingleSword object) {
        return new ResourceLocation(SaimoneiiasGearPlus.MODID, "geo/mito_single_sword.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MitoSingleSword animatable) {
        return new ResourceLocation(SaimoneiiasGearPlus.MODID, "textures/models/mito_single_sword.png");
    }
}
