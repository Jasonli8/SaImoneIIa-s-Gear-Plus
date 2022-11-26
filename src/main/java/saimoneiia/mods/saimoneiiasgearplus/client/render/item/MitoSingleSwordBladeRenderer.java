package saimoneiia.mods.saimoneiiasgearplus.client.render.item;

import saimoneiia.mods.saimoneiiasgearplus.client.model.weapon.singlesword.MitoSingleSwordBladeModel;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword.MitoSingleSwordBlade;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class MitoSingleSwordBladeRenderer extends GeoItemRenderer<MitoSingleSwordBlade> {
    public MitoSingleSwordBladeRenderer() {
        super(new MitoSingleSwordBladeModel());
    }
}
