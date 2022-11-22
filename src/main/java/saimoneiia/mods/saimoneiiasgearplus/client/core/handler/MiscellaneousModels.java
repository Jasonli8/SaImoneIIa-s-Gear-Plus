package saimoneiia.mods.saimoneiiasgearplus.client.core.handler;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.DyeColor;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class MiscellaneousModels {
    public static final MiscellaneousModels INSTANCE = new MiscellaneousModels();
    public boolean registeredModels = false;

    private MiscellaneousModels() {}

    public BakedModel exampleCharm, exampleNecklace, mitoSingleSword;

    public void onModelBake(ModelBakery loader, Map<ResourceLocation, BakedModel> map) {
        if (!registeredModels) {
            return;
        }
        exampleCharm = map.get(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/example_charm"));
        exampleNecklace = map.get(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/example_necklace"));
        mitoSingleSword = map.get(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/mito_single_sword"));
    }

    public void onModelRegister(ResourceManager rm, Consumer<ResourceLocation> consumer) {

        consumer.accept(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/example_charm"));
        consumer.accept(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/example_necklace"));
        consumer.accept(new ResourceLocation(SaimoneiiasGearPlus.MODID, "item/mito_single_sword"));

        if (!registeredModels) {
            registeredModels = true;
        }
    }
}
