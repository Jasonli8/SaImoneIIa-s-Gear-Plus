package saimoneiia.mods.saimoneiiasgearplus.util.handler;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MiscellaneousModels {
    public static final MiscellaneousModels INSTANCE = new MiscellaneousModels();
    public boolean registeredModels = false;

    private MiscellaneousModels() {}

    public Map<String, BakedModel> modelMap = new HashMap<>();


    public void onModelBake(ModelBakery loader, Map<ResourceLocation, BakedModel> map) {
        if (!registeredModels) {
            return;
        }
        modelMap.put("example_charm", map.get(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/example_charm")));
        modelMap.put("example_necklace", map.get(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/example_necklace")));
        modelMap.put("mito_single_sword", map.get(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/mito_single_sword")));
        modelMap.put("mito_single_sword_blade", map.get(new ResourceLocation(SaimoneiiasGearPlus.MODID, "equip/mito_single_sword_blade")));
        modelMap.put("mito_single_sword_sheath", map.get(new ResourceLocation(SaimoneiiasGearPlus.MODID, "equip/mito_single_sword_sheath")));
    }

    public void onModelRegister(ResourceManager rm, Consumer<ResourceLocation> consumer) {

        consumer.accept(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/example_charm"));
        consumer.accept(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/example_necklace"));
        consumer.accept(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/mito_single_sword"));
        consumer.accept(new ResourceLocation(SaimoneiiasGearPlus.MODID, "equip/mito_single_sword_blade"));
        consumer.accept(new ResourceLocation(SaimoneiiasGearPlus.MODID, "equip/mito_single_sword_sheath"));

        if (!registeredModels) {
            registeredModels = true;
        }
    }
}
