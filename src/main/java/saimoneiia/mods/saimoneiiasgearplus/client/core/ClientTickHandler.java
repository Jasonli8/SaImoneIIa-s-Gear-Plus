package saimoneiia.mods.saimoneiiasgearplus.client.core;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public final class ClientTickHandler {
    private ClientTickHandler() {}
    public static int ticksInGame = 0;
    public static float partialTicks = 0;

    public static float total() {
        return ticksInGame + partialTicks;
    }

    public static void renderTick(float renderTickTime) {
        partialTicks = renderTickTime;
    }

    public static void clientTickEnd(Minecraft mc) {

        if (mc.level == null) {
            // clear level specific states
        }

        if (!mc.isPaused()) {
            ticksInGame++;
            partialTicks = 0;
        }
    }
}
