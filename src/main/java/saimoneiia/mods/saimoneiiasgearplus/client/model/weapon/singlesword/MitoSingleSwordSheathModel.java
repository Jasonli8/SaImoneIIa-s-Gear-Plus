package saimoneiia.mods.saimoneiiasgearplus.client.model.weapon.singlesword;

import net.minecraft.resources.ResourceLocation;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword.MitoSingleSwordSheath;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MitoSingleSwordSheathModel extends AnimatedGeoModel<MitoSingleSwordSheath> {

    @Override
    public ResourceLocation getAnimationResource(MitoSingleSwordSheath animatable) {
        return new ResourceLocation(SaimoneiiasGearPlus.MODID, "animations/mito_single_sword_sheath.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(MitoSingleSwordSheath object) {
        return new ResourceLocation(SaimoneiiasGearPlus.MODID, "geo/mito_single_sword_sheath.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MitoSingleSwordSheath animatable) {
        return new ResourceLocation(SaimoneiiasGearPlus.MODID, "textures/models/mito_single_sword.png");
    }
}
