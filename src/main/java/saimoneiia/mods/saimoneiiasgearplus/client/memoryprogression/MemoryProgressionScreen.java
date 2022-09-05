package saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentNames;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.player.MemoryProgressionProvider;

@OnlyIn(Dist.CLIENT)
public class MemoryProgressionScreen extends AbstractContainerScreen<MemoryProgressionContainer> {
    private static final ResourceLocation SCREEN_TEXTURE = new ResourceLocation(SaimoneiiasGearPlus.MODID, "textures/gui/memoryprogressionscreen.png"); // TODO
    private static final ResourceLocation FILLED_MEMORY =
            new ResourceLocation(SaimoneiiasGearPlus.MODID, "textures/memprog/memprogfilled.png");
    private static final ResourceLocation EMPTY_MEMORY =
            new ResourceLocation(SaimoneiiasGearPlus.MODID, "textures/memprog/memprogempty.png");

    private final int renderWidth;
    private final int renderHeight;

    public MemoryProgressionScreen(MemoryProgressionContainer memoryProgressionGUI,
                                   Inventory inventory,
                                   Component title) {
        super(memoryProgressionGUI, inventory, title);
        this.passEvents = true;
        this.titleLabelX = 97;
        this.titleLabelY = 97;

        // size of image file
        this.imageWidth = 50;
        this.imageHeight = 50;

        // size to be displayed as
        this.renderWidth = 150;
        this.renderHeight = 150;
    }

    @Override
    protected void renderBg(PoseStack stack, float mouseX, int mouseY, int partialTicks) {
        Lighting.setupForFlatItems();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SCREEN_TEXTURE);
        int i = (this.width - this.renderWidth) / 2;
        int j = (this.height - this.renderHeight) / 2;
        blit(stack, i, j, 0, 0, this.renderWidth, this.renderHeight, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        int i = ((this.width - this.renderWidth) / 2) + 8;
        int j = ((this.height - this.renderHeight) / 2) + 8;
        this.font.draw(stack, title, i, j, 0x404040);
        renderMemoryProgressionBar(stack, i, j + 22);
    }

    @Override
    protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
    }

    protected void renderMemoryProgressionBar(PoseStack stack, int x, int y) {
        int level = ClientMemoryProgressionData.getPlayerLevel();
        int prog = ClientMemoryProgressionData.getPlayerProg();
        int maxProg = ClientMemoryProgressionData.getPlayerRequiredProg();

        final int memory_fillable = 20;
        int memory_filled = (memory_fillable * prog) / maxProg;

//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, FILLED_MEMORY);
        for (int i = 0; i < memory_filled; i++) {
            blit(stack, x + i, y, 0, 0, 2, 8, 1, 4);
        }
        RenderSystem.setShaderTexture(0, EMPTY_MEMORY);
        for (int i = memory_filled; i < memory_fillable; i++) {
            blit(stack, x + i, y, 0, 0, 2, 8, 1, 4);
        }

        if (ClientMemoryProgressionData.isMax()) {
            this.font.draw(stack, Component.literal("Level " + level + " (MAX LEVEL)"), x + memory_fillable + 3, y, 0x404040);
        } else {
            this.font.draw(stack, Component.literal("Level " + level + " (" + prog + "/" + maxProg + ")"), x + memory_fillable + 3, y, 0x404040);
        }
    }
}
