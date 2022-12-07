package saimoneiia.mods.saimoneiiasgearplus.networking.packet;

import ca.weblite.objc.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;

import java.util.function.Supplier;

public class BattleModeResourcesS2CPacket {
    private final boolean isBattleMode;
    private final int manaMax;
    private final int mana;
    private final int manaRate;
    private final int manaDelay;
    private final int ticksTillManaRecharge;
    private final int energyMax;
    private final int energy;
    private final int energyRate;
    private final int energyDelay;
    private final int ticksTillEnergyRecharge;

    public BattleModeResourcesS2CPacket(boolean isBattleMode, int manaMax, int mana, int manaRate, int manaDelay, int ticksTillManaRecharge, int energyMax, int energy, int energyRate, int energyDelay, int ticksTillEnergyRecharge) {
        this.isBattleMode = isBattleMode;
        this.manaMax = manaMax;
        this.mana = mana;
        this.manaDelay = manaDelay;
        this.manaRate = manaRate;
        this.ticksTillManaRecharge = ticksTillManaRecharge;
        this.energyMax = energyMax;
        this.energy = energy;
        this.energyRate = energyRate;
        this.energyDelay = energyDelay;
        this.ticksTillEnergyRecharge = ticksTillEnergyRecharge;
    }

    public BattleModeResourcesS2CPacket(FriendlyByteBuf buf) {
        this.isBattleMode = buf.readBoolean();
        this.manaMax = buf.readInt();
        this.mana = buf.readInt();
        this.manaRate = buf.readInt();
        this.manaDelay = buf.readInt();
        this.ticksTillManaRecharge = buf.readInt();
        this.energyMax = buf.readInt();
        this.energy = buf.readInt();
        this.energyRate = buf.readInt();
        this.energyDelay = buf.readInt();
        this.ticksTillEnergyRecharge = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(isBattleMode);
        buf.writeInt(manaMax);
        buf.writeInt(mana);
        buf.writeInt(manaRate);
        buf.writeInt(manaDelay);
        buf.writeInt(ticksTillManaRecharge);
        buf.writeInt(energyMax);
        buf.writeInt(energy);
        buf.writeInt(energyRate);
        buf.writeInt(energyDelay);
        buf.writeInt(ticksTillEnergyRecharge);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientBattleModeData.setAll(isBattleMode, manaMax, mana, manaRate, manaDelay, ticksTillManaRecharge, energyMax, energy, energyRate, energyDelay, ticksTillEnergyRecharge);
            Minecraft.getInstance().player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                battleMode.setAll(isBattleMode, manaMax, mana, manaRate, manaDelay, ticksTillManaRecharge, energyMax, energy, energyRate, energyDelay, ticksTillEnergyRecharge);
            });
        });
        return true;
    }
}
