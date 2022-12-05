package saimoneiia.mods.saimoneiiasgearplus.player.battlemode;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.BattleModeResourcesS2CPacket;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.BattleModeS2CPacket;

public class BattleMode {
    public boolean isBattleMode = false;
    public int manaMax = 100;
    public int mana = 100;
    public int manaRate = 1;
    public int manaDelay = 20;
    public int energyMax = 100;
    public int energy = 100;
    public int energyRate = 1;
    public int energyDelay = 20;
    private final String PROP_NAME = SaimoneiiasGearPlus.MODID + "battleMode";

    public void setBattleMode(boolean isSet) {
        System.out.println("Was: " + isBattleMode);
        isBattleMode = isSet;
        System.out.println("Now: " + isBattleMode);
    }

    public void toggle() { isBattleMode = !isBattleMode; }

    public boolean consumeMana(int points) {
        if (points > mana) return false;
        mana -= points;
        return true;
    }

    public void restoreMana(int points) { mana = Math.max(manaMax, mana + points); }

    public void setMana(int manaMax, int mana) {
        this.manaMax = manaMax;
        this.mana = mana;
    }

    public void setManaRate(int rate, int delay) {
        manaRate = rate;
        manaDelay = delay;
    }

    public boolean consumeEnergy(int points) {
        if (points > energy) return false;
        energy -= points;
        return true;
    }

    public void restoreEnergy(int points) { energy = Math.max(energyMax, energy + points); }

    public void setEnergy(int energyMax, int energy) {
        this.energyMax = energyMax;
        this.energy = energy;
    }

    public void setEnergyRate(int rate, int delay) {
        energyRate = rate;
        energyDelay = delay;
    }

    public void syncClient(ServerPlayer player) {
        System.out.println("Sync client from "+ player.toString() + " with " + isBattleMode);
        ModPackets.sendToPlayer(new BattleModeS2CPacket(isBattleMode), player);
        ModPackets.sendToPlayer(new BattleModeResourcesS2CPacket(manaMax, mana, manaRate, manaDelay, energyMax, energy, energyRate, energyDelay), player);
    }

    public void copyFrom(BattleMode source) {
        System.out.println("copyFrom " + isBattleMode);
        this.isBattleMode = source.isBattleMode;
        this.mana = source.mana;
        this.manaMax = source.manaMax;
        this.manaRate = source.manaRate;
        this.manaDelay = source.manaDelay;
        this.energy = source.energy;
        this.energyRate = source.energyRate;
        this.energyMax = source.energyMax;
        this.energyDelay = source.energyDelay;
    }

    public void saveNBTData(CompoundTag nbt) {
        System.out.println("saveNBTDate " + isBattleMode);
        nbt.putBoolean(PROP_NAME + "_toggle", isBattleMode);
        nbt.putInt(PROP_NAME + "_mana", mana);
        nbt.putInt(PROP_NAME + "_mana_max", manaMax);
        nbt.putInt(PROP_NAME + "_mana_rate", manaRate);
        nbt.putInt(PROP_NAME + "_mana_delay", manaDelay);
        nbt.putInt(PROP_NAME + "_energy", energy);
        nbt.putInt(PROP_NAME + "_energy_max", energyMax);
        nbt.putInt(PROP_NAME + "_energy_rate", energyRate);
        nbt.putInt(PROP_NAME + "_energy_delay", energyDelay);
    }

    public void loadNBTData(CompoundTag nbt) {
        System.out.println("loadNBTData " + isBattleMode);
        isBattleMode = nbt.getBoolean(PROP_NAME + "_toggle");
        mana = nbt.getInt(PROP_NAME + "_mana");
        manaMax = nbt.getInt(PROP_NAME + "_mana_max");
        manaRate = nbt.getInt(PROP_NAME + "_mana_rate");
        manaDelay = nbt.getInt(PROP_NAME + "_mana_delay");
        energy = nbt.getInt(PROP_NAME + "_energy");
        energyMax = nbt.getInt(PROP_NAME + "_energy_max");
        energyRate = nbt.getInt(PROP_NAME + "_energy_rate");
        energyDelay = nbt.getInt(PROP_NAME + "_energy_delay");
        System.out.println("loadNBTData2 " + isBattleMode);
    }
}
