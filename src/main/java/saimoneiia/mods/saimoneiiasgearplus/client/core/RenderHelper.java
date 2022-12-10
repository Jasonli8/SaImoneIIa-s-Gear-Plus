package saimoneiia.mods.saimoneiiasgearplus.client.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.function.Function;

public class RenderHelper extends RenderType {
    public static final RenderType MITO_RETRACE;

    private static RenderType makeLayer(String name, VertexFormat format, VertexFormat.Mode mode,
                                        int bufSize, boolean hasCrumbling, boolean sortOnUpload, CompositeState glState) {
        return RenderType.create(name, format, mode, bufSize, hasCrumbling, sortOnUpload, glState);
    }

    private static RenderType makeLayer(String name, VertexFormat format, VertexFormat.Mode mode,
                                        int bufSize, CompositeState glState) {
        return makeLayer(name, format, mode, bufSize, false, false, glState);
    }

    static {
        RenderType.CompositeState glState = RenderType.CompositeState.builder()
                .setShaderState(POSITION_COLOR_SHADER)
                .setTransparencyState(LIGHTNING_TRANSPARENCY)
                .createCompositeState(false);
        MITO_RETRACE = makeLayer(SaimoneiiasGearPlus.MODID + "_mito_retrace", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.DEBUG_LINES, 256, false, true, glState);
    }

    private RenderHelper(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
        super(string, vertexFormat, mode, i, bl, bl2, runnable, runnable2);
        throw new UnsupportedOperationException("Should not be instantiated");
    }
}
