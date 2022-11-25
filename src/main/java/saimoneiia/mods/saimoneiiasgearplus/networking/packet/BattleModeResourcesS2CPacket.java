package saimoneiia.mods.saimoneiiasgearplus.networking.packet;

import ca.weblite.objc.Client;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;

import java.util.function.Supplier;

public class BattleModeResourcesS2CPacket {
    private final int manaMax;
    private final int mana;
    private final int manaRate;
    private final int manaDelay;
    private final int energyMax;
    private final int energy;
    private final int energyRate;
    private final int energyDelay;

    public BattleModeResourcesS2CPacket(int manaMax, int mana, int manaRate, int manaDelay, int energyMax, int energy, int energyRate, int energyDelay) {
        this.manaMax = manaMax;
        this.mana = mana;
        this.manaDelay = manaDelay;
        this.manaRate = manaRate;
        this.energyMax = energyMax;
        this.energy = energy;
        this.energyRate = energyRate;
        this.energyDelay = energyDelay;
    }

    public BattleModeResourcesS2CPacket(FriendlyByteBuf buf) {
        this.manaMax = buf.readInt();
        this.mana = buf.readInt();
        this.manaRate = buf.readInt();
        this.manaDelay = buf.readInt();
        this.energyMax = buf.readInt();
        this.energy = buf.readInt();
        this.energyRate = buf.readInt();
        this.energyDelay = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(manaMax);
        buf.writeInt(mana);
        buf.writeInt(manaRate);
        buf.writeInt(manaDelay);
        buf.writeInt(energyMax);
        buf.writeInt(energy);
        buf.writeInt(energyRate);
        buf.writeInt(energyDelay);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientBattleModeData.setMana(manaMax, mana);
            ClientBattleModeData.setManaRate(manaRate, manaDelay);
            ClientBattleModeData.setEnergy(energyMax, energy);
            ClientBattleModeData.setEnergyRate(energyRate, energyDelay);
        });
        return true;
    }
}
