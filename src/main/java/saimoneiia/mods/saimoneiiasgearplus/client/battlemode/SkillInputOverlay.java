package saimoneiia.mods.saimoneiiasgearplus.client.battlemode;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.controller.BattleModeController;

import java.util.Stack;

public class SkillInputOverlay {
    private static Stack<Integer> circleToRender = new Stack<Integer>();
    private static final ResourceLocation[] circles = {
            new ResourceLocation(SaimoneiiasGearPlus.MODID, "textures/gui/skillinput1.png"),
            new ResourceLocation(SaimoneiiasGearPlus.MODID, "textures/gui/skillinput2a.png"),
            new ResourceLocation(SaimoneiiasGearPlus.MODID, "textures/gui/skillinput2b.png"),
            new ResourceLocation(SaimoneiiasGearPlus.MODID, "textures/gui/skillinput3a.png"),
            new ResourceLocation(SaimoneiiasGearPlus.MODID, "textures/gui/skillinput3b.png"),
            new ResourceLocation(SaimoneiiasGearPlus.MODID, "textures/gui/skillinput4a.png"),
            new ResourceLocation(SaimoneiiasGearPlus.MODID, "textures/gui/skillinput4b.png"),
    };
    private static float overlayAlpha = 0.2F;
    public static final IGuiOverlay HUD_SKILL_CIRCLES = (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth/2;
        int y = screenHeight/2;

        Stack<Integer> circleToRenderClone = (Stack<Integer>) circleToRender.clone();

        if (BattleModeController.isSkillCasted) {
            if (BattleModeController.ticksSinceLastSkillInput < 10) {
                overlayAlpha = Math.min(overlayAlpha + 0.1F, 1.0F);
            } else if (BattleModeController.ticksSinceLastSkillInput > 15) {
                overlayAlpha = Math.max(overlayAlpha - 0.1F, 0F);
            }
        } else if (BattleModeController.skillInput == 0 && !circleToRender.isEmpty()) {
            overlayAlpha = Math.max(overlayAlpha - 0.01F, 0F);
        } else {
            overlayAlpha = 0.2F;
        }

        if (overlayAlpha == 0F) circleToRender.clear();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, overlayAlpha);
        int size = 512;
        while (!circleToRenderClone.isEmpty()) {
            RenderSystem.setShaderTexture(0, circles[circleToRenderClone.pop()]);
            GuiComponent.blit(poseStack, x-(size/2)-2, y-(size/2), 0, 0, size, size, size, size);
//            size -= 120;
        }
    };

    public static void setCirclesToRender() {
        if (ClientBattleModeData.isBattleMode) {
            if (BattleModeController.skillInput > 0) {
                int circleIndexToRender = BattleModeController.skillInput;
                int parseSkillCode = BattleModeController.skillCode;
                if (circleIndexToRender != 0) circleToRender.clear();
                while (circleIndexToRender > 0) {
                    circleToRender.push(2 * (circleIndexToRender - 1) - ((parseSkillCode + 1) % 2));
                    parseSkillCode = parseSkillCode / 2;
                    circleIndexToRender--;
                }
            }
        }
    }
}
