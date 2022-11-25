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

    public void setBattleMode(boolean isSet) { isBattleMode = isSet; }

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
        ModPackets.sendToPlayer(new BattleModeS2CPacket(isBattleMode), player);
        ModPackets.sendToPlayer(new BattleModeResourcesS2CPacket(manaMax, mana, manaRate, manaDelay, energyMax, energy, energyRate, energyDelay), player);
    }

    public void copyFrom(BattleMode source) {
        this.isBattleMode = source.isBattleMode;
        this.mana = source.mana;
        this.energy = source.energy;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean(PROP_NAME, isBattleMode);
        nbt.putInt(PROP_NAME, mana);
        nbt.putInt(PROP_NAME, energy);
    }

    public void loadNBTData(CompoundTag nbt) {
        isBattleMode = nbt.getBoolean(PROP_NAME);
        mana = nbt.getInt(PROP_NAME);
        energy = nbt.getInt(PROP_NAME);
    }
}
