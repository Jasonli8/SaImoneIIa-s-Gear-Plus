package saimoneiia.mods.saimoneiiasgearplus.player.battlemode;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.BattleModeC2SPacket;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.BattleModeResourcesS2CPacket;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class BattleMode {
    public boolean isBattleMode = false;
    public int manaMax = 100;
    public int mana = 100;
    public int manaRate = 1;
    public int manaDelay = 20;
    public int ticksTillManaRecharge = 0;
    public int energyMax = 100;
    public int energy = 100;
    public int energyRate = 1;
    public int energyDelay = 20;
    public int ticksTillEnergyRecharge = 0;
    private final Minecraft minecraft = Minecraft.getInstance();
    private String PROP_NAME = SaimoneiiasGearPlus.MODID + "battleMode";

    public void setBattleMode(boolean isSet) { isBattleMode = isSet; }

    public void setAll(
            boolean isBattleMode,
            int manaMax, int mana, int manaRate, int manaDelay, int ticksTillManaRecharge,
            int energyMax, int energy, int energyRate, int energyDelay, int ticksTillEnergyRecharge
    ) {
        this.isBattleMode = isBattleMode;
        this.manaMax = manaMax;
        this.mana = mana;
        this.manaRate = manaRate;
        this.manaDelay = manaDelay;
        this.ticksTillManaRecharge = ticksTillManaRecharge;
        this.energyMax = energyMax;
        this.energy = energy;
        this.energyRate = energyRate;
        this.energyDelay = energyDelay;
        this.ticksTillEnergyRecharge = ticksTillEnergyRecharge;
    }

    public void toggle() {
        isBattleMode = !isBattleMode;
        refreshToggle();
    }

    public void refreshToggle() {
        CuriosApi.getCuriosHelper().getCuriosHandler(minecraft.player).ifPresent(handler -> {
            ICurioStacksHandler stacksHandler = handler.getCurios().get("weapon");
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();
            ItemStack stack = stackHandler.getStackInSlot(0);
            if (stack.isEmpty()) {
                isBattleMode = false;
            }
        });
        ModPackets.sendToServer(new BattleModeC2SPacket(isBattleMode));
        onToggle();
    }

    private void onToggle() {
        if (isBattleMode) {
            minecraft.player.sendSystemMessage(Component.literal("Activating Battle Mode!"));
//            minecraft.options.setCameraType(CameraType.THIRD_PERSON_BACK);
            minecraft.player.removeVehicle();
        } else {
            minecraft.player.sendSystemMessage(Component.literal("De-activating Battle Mode!"));
//            minecraft.options.setCameraType(CameraType.FIRST_PERSON);
        }
    }

    public boolean consumeMana(int points) {
        if (points > mana) return false;
        mana -= points;
        ticksTillManaRecharge = manaDelay;
        return true;
    }

    public void restoreMana(int points) { mana = Math.min(manaMax, mana + points); }

    public void setMana(int manaMaxSet, int manaSet) {
        manaMax = manaMaxSet;
        mana = manaSet;
    }

    public void setManaRate(int rate, int delay) {
        manaRate = rate;
        manaDelay = delay;
    }

    public boolean consumeEnergy(int points) {
        if (points > energy) return false;
        energy -= points;
        ticksTillEnergyRecharge = energyDelay;
        return true;
    }

    public void restoreEnergy(int points) { energy = Math.min(energyMax, energy + points); }

    public void setEnergy(int energyMaxSet, int energySet) {
        energyMax = energyMaxSet;
        energy = energySet;
    }

    public void setEnergyRate(int rate, int delay) {
        energyRate = rate;
        energyDelay = delay;
    }

    public void tickResources() {
        ticksTillManaRecharge = Math.max(ticksTillManaRecharge - 1, 0);
        ticksTillEnergyRecharge = Math.max(ticksTillEnergyRecharge - 1, 0);
        if (ticksTillManaRecharge == 0) {
            restoreMana(manaRate);
        }
        if (ticksTillEnergyRecharge == 0) {
            restoreEnergy(energyRate);
        }
    }

    public void syncClient(ServerPlayer player) {
        ModPackets.sendToPlayer(new BattleModeResourcesS2CPacket(isBattleMode, manaMax, mana, manaRate, manaDelay, ticksTillManaRecharge, energyMax, energy, energyRate, energyDelay, ticksTillEnergyRecharge), player);
    }

    public void copyFrom(BattleMode source) {
        this.isBattleMode = source.isBattleMode;
        this.mana = source.mana;
        this.manaMax = source.manaMax;
        this.manaRate = source.manaRate;
        this.manaDelay = source.manaDelay;
        this.ticksTillManaRecharge = source.ticksTillManaRecharge;
        this.energy = source.energy;
        this.energyRate = source.energyRate;
        this.energyMax = source.energyMax;
        this.energyDelay = source.energyDelay;
        this.ticksTillEnergyRecharge = source.ticksTillEnergyRecharge;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean(PROP_NAME + "_toggle", isBattleMode);
        nbt.putInt(PROP_NAME + "_mana", mana);
        nbt.putInt(PROP_NAME + "_mana_max", manaMax);
        nbt.putInt(PROP_NAME + "_mana_rate", manaRate);
        nbt.putInt(PROP_NAME + "_mana_delay", manaDelay);
        nbt.putInt(PROP_NAME + "_mana_ticks_remaining", ticksTillManaRecharge);
        nbt.putInt(PROP_NAME + "_energy", energy);
        nbt.putInt(PROP_NAME + "_energy_max", energyMax);
        nbt.putInt(PROP_NAME + "_energy_rate", energyRate);
        nbt.putInt(PROP_NAME + "_energy_delay", energyDelay);
        nbt.putInt(PROP_NAME + "_energy_ticks_remaining", ticksTillEnergyRecharge);
    }

    public void loadNBTData(CompoundTag nbt) {
        isBattleMode = nbt.getBoolean(PROP_NAME + "_toggle");
        mana = nbt.getInt(PROP_NAME + "_mana");
        manaMax = nbt.getInt(PROP_NAME + "_mana_max");
        manaRate = nbt.getInt(PROP_NAME + "_mana_rate");
        manaDelay = nbt.getInt(PROP_NAME + "_mana_delay");
        ticksTillManaRecharge = nbt.getInt(PROP_NAME + "_mana_ticks_remaining");
        energy = nbt.getInt(PROP_NAME + "_energy");
        energyMax = nbt.getInt(PROP_NAME + "_energy_max");
        energyRate = nbt.getInt(PROP_NAME + "_energy_rate");
        energyDelay = nbt.getInt(PROP_NAME + "_energy_delay");
        ticksTillEnergyRecharge = nbt.getInt(PROP_NAME + "_energy_ticks_remaining");
    }
}
