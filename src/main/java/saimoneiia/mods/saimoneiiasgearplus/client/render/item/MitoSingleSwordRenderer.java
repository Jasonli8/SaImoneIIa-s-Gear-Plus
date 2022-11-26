package saimoneiia.mods.saimoneiiasgearplus.client.render.item;

import saimoneiia.mods.saimoneiiasgearplus.client.model.weapon.singlesword.MitoSingleSwordModel;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword.MitoSingleSword;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class MitoSingleSwordRenderer extends GeoItemRenderer<MitoSingleSword> {
    public MitoSingleSwordRenderer() {
        super(new MitoSingleSwordModel());
    }
}
