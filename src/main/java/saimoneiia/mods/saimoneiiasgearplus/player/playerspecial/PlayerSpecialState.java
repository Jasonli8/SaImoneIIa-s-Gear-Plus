package saimoneiia.mods.saimoneiiasgearplus.player.playerspecial;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.PlayerSpecialSyncS2CPacket;

public class PlayerSpecialState {
    private final String PROP_NAME = SaimoneiiasGearPlus.MODID + "_playerSpecial";

    public boolean isArmorPierce = false;
    public boolean isMagicPierce = false;
    public boolean isMagicAttack = false;

    public boolean isEasyStep = false;

    public void set(boolean isArmorPierce, boolean isMagicPierce, boolean isMagicAttack, boolean isEasyStep) {
        this.isArmorPierce = isArmorPierce;
        this.isMagicPierce = isMagicPierce;
        this.isMagicAttack = isMagicAttack;

        this.isEasyStep = isEasyStep;
    }

    public void syncClient(ServerPlayer player) {
        ModPackets.sendToPlayer(new PlayerSpecialSyncS2CPacket(isArmorPierce, isMagicPierce, isMagicAttack, isEasyStep), player);
    }

    public void resetFromClone() {
        isArmorPierce = false;
        isMagicPierce = false;
        isMagicAttack = false;

        isEasyStep = false;
    }

    public void copyFrom(PlayerSpecialState source) {
        this.isArmorPierce = source.isArmorPierce;
        this.isMagicPierce = source.isMagicPierce;
        this.isMagicAttack = source.isMagicAttack;

        this.isEasyStep = source.isEasyStep;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean(PROP_NAME + "_isArmorPierce", isArmorPierce);
        nbt.putBoolean(PROP_NAME + "_isMagicPierce", isMagicPierce);
        nbt.putBoolean(PROP_NAME + "_isStance", isMagicAttack);

        nbt.putBoolean(PROP_NAME + "_isEasyStep", isEasyStep);
    }

    public void loadNBTData(CompoundTag nbt) {
        isArmorPierce = nbt.getBoolean(PROP_NAME + "_isArmorPierce");
        isMagicPierce = nbt.getBoolean(PROP_NAME + "_isMagicPierce");
        isMagicAttack = nbt.getBoolean(PROP_NAME + "_isStance");

        isEasyStep = nbt.getBoolean(PROP_NAME + "_isEasyStep");
    }
}
