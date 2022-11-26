package saimoneiia.mods.saimoneiiasgearplus.client.render.item;

import saimoneiia.mods.saimoneiiasgearplus.client.model.weapon.singlesword.MitoSingleSwordSheathModel;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword.MitoSingleSwordSheath;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class MitoSingleSwordSheathRenderer extends GeoItemRenderer<MitoSingleSwordSheath> {
    public MitoSingleSwordSheathRenderer() {
        super(new MitoSingleSwordSheathModel());
    }
}
