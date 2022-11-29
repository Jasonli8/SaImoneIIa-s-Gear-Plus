package saimoneiia.mods.saimoneiiasgearplus.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.controller.BattleModeController;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.SkillInputOverlay;
import saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression.MemoryProgressionScreen;
import saimoneiia.mods.saimoneiiasgearplus.init.ContainerInit;
import saimoneiia.mods.saimoneiiasgearplus.util.handler.ModifiedRenderableModels;
import saimoneiia.mods.saimoneiiasgearplus.client.keybindings.KeyBinding;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber(modid = SaimoneiiasGearPlus.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

//    @SubscribeEvent
//    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
//        event.registerEntityRenderer(EntityRegistry.GEO_EXAMPLE_ENTITY.get(), ExampleGeoRenderer::new);
//        event.registerEntityRenderer(EntityRegistry.BIKE_ENTITY.get(), BikeGeoRenderer::new);
//        event.registerEntityRenderer(EntityRegistry.CAR_ENTITY.get(), CarGeoRenderer::new);
//        event.registerEntityRenderer(EntityRegistry.TEST_ENTITY.get(), TestGeoRenderer::new);
//        event.registerEntityRenderer(EntityRegistry.GEOLAYERENTITY.get(), LERenderer::new);
//        event.registerEntityRenderer(EntityRegistry.EXTENDED_RENDERER_EXAMPLE.get(),
//                ExampleExtendedRendererEntityRenderer::new);
//        event.registerEntityRenderer(EntityRegistry.TEXTURE_PER_BONE_EXAMPLE.get(),
//                TexturePerBoneTestEntityRenderer::new);
//
//        event.registerBlockEntityRenderer(TileRegistry.HABITAT_TILE.get(), HabitatTileRenderer::new);
//        event.registerBlockEntityRenderer(TileRegistry.FERTILIZER.get(), FertilizerTileRenderer::new);
//
//        event.registerEntityRenderer(EntityType.CREEPER, ReplacedCreeperRenderer::new);
//    }
    @SubscribeEvent
    public static void onModelRegister(ModelEvent.RegisterAdditional evt) {
        var resourceManager = Minecraft.getInstance().getResourceManager();
        ModifiedRenderableModels.INSTANCE.onModelRegister(resourceManager, evt::register);
    }


    @SubscribeEvent
    public static void onModelBake(ModelEvent.BakingCompleted evt) {
        ModifiedRenderableModels.INSTANCE.onModelBake(evt.getModelBakery(), evt.getModels());
        Map<ResourceLocation, BakedModel> map = Minecraft.getInstance().getModelManager().getModelBakery().getBakedTopLevelModels();

        for (String item : ModifiedRenderableModels.ITEMS_WITH_DIFFERING_MODELS) {
            ResourceLocation modelInventory = new ModelResourceLocation("saimoneiiasgearplus:" + item, "inventory");
            String modelHand = item + "_in_hand";

            BakedModel bakedModelDefault = map.get(modelInventory);
            BakedModel bakedModelHand = ModifiedRenderableModels.INSTANCE.modelMap.get(modelHand);
            BakedModel modelWrapper = new BakedModel() {
                @Override
                public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType) {
                    return BakedModel.super.getQuads(state, side, rand, data, renderType);
                }

                public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
                    return bakedModelDefault.getQuads(state, side, (RandomSource) rand);
                }

                @Override
                public List<BakedQuad> getQuads(@Nullable BlockState p_235039_, @Nullable Direction p_235040_, RandomSource p_235041_) {
                    return bakedModelDefault.getQuads(p_235039_, p_235040_, p_235041_);
                }

                @Override
                public boolean useAmbientOcclusion() {
                    return bakedModelDefault.useAmbientOcclusion();
                }

                @Override
                public boolean isGui3d() {
                    return bakedModelDefault.isGui3d();
                }

                @Override
                public boolean usesBlockLight() {
                    return false;
                }

                @Override
                public boolean isCustomRenderer() {
                    return bakedModelDefault.isCustomRenderer();
                }

                @Override
                public TextureAtlasSprite getParticleIcon() {
                    return bakedModelDefault.getParticleIcon();
                }

                @Override
                public ItemOverrides getOverrides() {
                    return bakedModelDefault.getOverrides();
                }

                @Override
                public BakedModel applyTransform(ItemTransforms.TransformType transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
                    BakedModel modelToUse = bakedModelDefault;
                    if (transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND
                            || transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND) {
                        modelToUse = bakedModelHand;
                    }
                    return ForgeHooksClient.handleCameraTransforms(poseStack, modelToUse, transformType, applyLeftHandTransform);
                }
            };
            map.put(modelInventory, modelWrapper);
        }
    }

    @Mod.EventBusSubscriber(modid=SaimoneiiasGearPlus.MODID, value=Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.TOGGLE_BATTLE_MODE_KEY.consumeClick()) {
                ClientBattleModeData.toggle();
            }
        }

        @SubscribeEvent
        public static void preProcessKeyBindings(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.START) {
                BattleModeController.combatTick(event);
            }
        }
    }

    @Mod.EventBusSubscriber(modid=SaimoneiiasGearPlus.MODID, value=Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.TOGGLE_BATTLE_MODE_KEY);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ContainerInit.MEMORY_PROGRESSION.get(), MemoryProgressionScreen::new);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("skill", SkillInputOverlay.HUD_SKILL_CIRCLES);
        }
    }
}
