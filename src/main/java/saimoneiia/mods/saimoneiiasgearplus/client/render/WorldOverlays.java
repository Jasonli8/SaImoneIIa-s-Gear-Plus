package saimoneiia.mods.saimoneiiasgearplus.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.world.level.Level;
import saimoneiia.mods.saimoneiiasgearplus.client.particle.MitoRetraceRenderer;

public final class WorldOverlays {
    public static void renderWorldLast(Camera camera, float tickDelta, PoseStack matrix, RenderBuffers buffers, Level level) {
        MitoRetraceRenderer.onWorldRenderLast(camera, tickDelta, matrix, buffers);
    }

    private WorldOverlays() {}
}
