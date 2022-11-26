package saimoneiia.mods.saimoneiiasgearplus.util.handler;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ModifiedRenderableModels {
    public static final ModifiedRenderableModels INSTANCE = new ModifiedRenderableModels();
    public static final String[] ITEMS_WITHOUT_DIFFERING_MODELS = new String[] {"example_belt", "example_charm", "example_hand", "example_necklace", "example_ring"};
    public static final String[] ITEMS_WITH_DIFFERING_MODELS = new String[] {"mito_single_sword"}; // may have duplicates with lists below
    public static final String[] SINGLE_SWORDS = new String[] {"mito_single_sword"};
    public boolean registeredModels = false;

    private ModifiedRenderableModels() {}

    public Map<String, BakedModel> modelMap = new HashMap<>();


    public void onModelBake(ModelBakery loader, Map<ResourceLocation, BakedModel> map) {
        if (!registeredModels) {
            return;
        }
        for (String item : ITEMS_WITHOUT_DIFFERING_MODELS) {
            modelMap.put(item, map.get(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/" + item)));
        }
        for (String item : ITEMS_WITH_DIFFERING_MODELS) { // model rendered in world is different from model in item form
            modelMap.put(item + "_in_hand", map.get(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/" + item + "_in_hand")));
            modelMap.put(item, map.get(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/" + item)));
        }

        for (String item : ITEMS_WITH_DIFFERING_MODELS) { // model rendered in world is different from model in item form
            modelMap.put(item + "_blade", map.get(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/" + item + "_blade")));
            modelMap.put(item + "_sheath", map.get(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/" + item + "_sheath")));
        }

        // Manual model tracking for stuff with multiple models and other exceptions
        // ...
    }

    public void onModelRegister(ResourceManager rm, Consumer<ResourceLocation> consumer) {

        for (String item : ITEMS_WITHOUT_DIFFERING_MODELS) {
            consumer.accept(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/" + item));
        }
        for (String item : ITEMS_WITH_DIFFERING_MODELS) {
            consumer.accept(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/" + item + "_in_hand"));
            consumer.accept(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/" + item));
        }

        for (String item : SINGLE_SWORDS) {
            consumer.accept(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/" + item + "_blade"));
            consumer.accept(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/" + item + "_sheath"));
        }

        // Manual Registration for stuff with multiple models and other exceptions
        // ...

        if (!registeredModels) {
            registeredModels = true;
        }
    }
}
