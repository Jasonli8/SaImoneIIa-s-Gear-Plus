package saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression;

// unique for each client, only used client side
public class ClientMemoryProgressionData {
    private static int playerMemory;

    public static void set(int memory) {
        ClientMemoryProgressionData.playerMemory = memory;
    }

    public static int getPlayerMemory() {
        return playerMemory;
    }


    // TODO: 3 below are to be changed with level scaling
    public static int getPlayerLevel() {
        return playerMemory / 10;
    }

    public static int getPlayerProg() {
        return playerMemory % getPlayerRequiredProg();
    }

    public static int getPlayerRequiredProg() {
        return 10;
    }

    public static boolean isMax() {
        return playerMemory == 100;
    }
}
