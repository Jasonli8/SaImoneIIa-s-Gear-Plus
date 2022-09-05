package saimoneiia.mods.saimoneiiasgearplus.player;

import net.minecraft.nbt.CompoundTag;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;

public class MemoryProgression {
    private int memProg;
    public static final int maxMemProg = 100; // change with xp scaling
    public static final int minMemProg = 0;
    private final String PROP_NAME = SaimoneiiasGearPlus.MODID + "memProg";

    public int getMem() {
        return memProg;
    }

    public boolean canAdd() { return memProg < maxMemProg; }
    public void addMem(int add) {
        memProg = Math.min(memProg + add, maxMemProg);
    }

    public void setMem(int mem) {
        memProg = Math.min(mem, maxMemProg);
    }

    public void addLevel(int add) {
        memProg = Math.min(memProg + (add * 10), maxMemProg);
    } // change with xp scaling

    public void setLevel(int mem) {
        memProg = Math.min(mem * 10, maxMemProg);
    } // change with xp scaling

    public int getLevel() {
        return memProg / 10; // change with xp scaling
    }

    public int getRequiredProg() {
        return 10; // change with xp scaling
    }

    public int getProg() {
        return memProg % this.getRequiredProg(); // change with xp scaling
    }

    public void copyFrom(MemoryProgression source) {
        this.memProg = source.memProg;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt(PROP_NAME, memProg);
    }

    public void loadNBTData(CompoundTag nbt) {
        memProg = nbt.getInt(PROP_NAME);
    }
}
