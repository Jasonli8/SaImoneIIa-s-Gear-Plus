package saimoneiia.mods.saimoneiiasgearplus.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression.ClientMemoryProgressionData;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;

import java.util.function.Supplier;

public class BattleModeS2CPacket {
    private final boolean isBattleMode;

    public BattleModeS2CPacket(boolean isBattleMode) { this.isBattleMode = isBattleMode; }

    public BattleModeS2CPacket(FriendlyByteBuf buf) { this.isBattleMode = buf.readBoolean(); }

    public void toBytes(FriendlyByteBuf buf) { buf.writeBoolean(isBattleMode); }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ModPackets.register();
            // code on client
            ClientBattleModeData.set(isBattleMode);
        });
        return true;
    }
}