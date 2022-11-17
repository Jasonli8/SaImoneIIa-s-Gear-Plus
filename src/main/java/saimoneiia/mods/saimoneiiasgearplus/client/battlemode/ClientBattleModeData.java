package saimoneiia.mods.saimoneiiasgearplus.client.battlemode;

public class ClientBattleModeData {
    private static boolean isBattleMode = false;

    public static void set(boolean isSet) { isBattleMode = isSet; }

    public static void toggle() { isBattleMode = !isBattleMode; }

    public static boolean get()  { return isBattleMode; }
}
