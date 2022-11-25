package saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.util.MemoryLevelScaling;

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
        renderStats(stack, i, j + 37);
    }

    @Override
    protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
    }

    protected void renderMemoryProgressionBar(PoseStack stack, int x, int y) {
        int memProg = ClientMemoryProgressionData.get();
        int level = MemoryLevelScaling.getMemLevel(memProg);
        int prog = MemoryLevelScaling.getMemLevelProg(memProg);
        int maxProg = MemoryLevelScaling.getRequiredMemProg(memProg);

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

        if (MemoryLevelScaling.isMaxLevel(memProg)) {
            this.font.draw(stack, Component.literal("Level " + level + " (MAX LEVEL)"), x + memory_fillable + 3, y, 0x404040);
        } else {
            this.font.draw(stack, Component.literal("Level " + level + " (" + prog + "/" + maxProg + ")"), x + memory_fillable + 3, y, 0x404040);
        }
    }

    protected void renderStats(PoseStack stack, int x, int y) {
        int manaMax = ClientBattleModeData.manaMax;
        int manaRate = ClientBattleModeData.manaRate;
        int manaDelay = ClientBattleModeData.manaDelay;
        int energyMax = ClientBattleModeData.energyMax;
        int energyRate = ClientBattleModeData.energyRate;
        int energyDelay = ClientBattleModeData.energyDelay;

        this.font.draw(stack, Component.literal("Max Mana: " + manaMax + "pts"), x, y, 0x404040);
        this.font.draw(stack, Component.literal("Mana RR: " + manaRate + "pts/tick"), x, y + 10, 0x404040);
        this.font.draw(stack, Component.literal("Mana RD: " + manaDelay + "ticks"), x, y + 20, 0x404040);
        this.font.draw(stack, Component.literal("Max Energy: " + energyMax + "pts/tick"), x, y + 35, 0x404040);
        this.font.draw(stack, Component.literal("Energy RR: " + energyRate + "pts/tick"), x, y + 45, 0x404040);
        this.font.draw(stack, Component.literal("Energy RD: " + energyDelay + "ticks"), x, y + 55, 0x404040);
    }
}
