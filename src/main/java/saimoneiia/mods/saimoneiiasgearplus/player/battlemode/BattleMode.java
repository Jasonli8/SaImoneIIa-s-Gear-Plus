package saimoneiia.mods.saimoneiiasgearplus.player.battlemode;

import net.minecraft.nbt.CompoundTag;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;

public class BattleMode {
    private boolean isBattleMode = false;
    private final String PROP_NAME = SaimoneiiasGearPlus.MODID + "battleMode";

    public void set(boolean isSet) { isBattleMode = isSet; }

    public void toggle() { isBattleMode = !isBattleMode; }

    public boolean get()  { return isBattleMode; }

    public void copyFrom(BattleMode source) {
        this.isBattleMode = source.isBattleMode;
    }

    public void saveNBTData(CompoundTag nbt) { nbt.putBoolean(PROP_NAME, isBattleMode); }

    public void loadNBTData(CompoundTag nbt) {
        isBattleMode = nbt.getBoolean(PROP_NAME);
    }
}
