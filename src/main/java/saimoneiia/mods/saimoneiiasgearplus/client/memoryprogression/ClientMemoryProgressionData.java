package saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientMemoryProgressionData {
    private static int playerMemory = 0;

    public static void set(int memory) { playerMemory = memory; }
    public static int get() {
        return playerMemory;
    }
}
