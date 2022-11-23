package saimoneiia.mods.saimoneiiasgearplus.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;

import java.util.function.Supplier;

public class BattleModeC2SPacket {
    private final boolean isBattleMode;

    public BattleModeC2SPacket(boolean isBattleMode) { this.isBattleMode = isBattleMode; }

    public BattleModeC2SPacket(FriendlyByteBuf buf) { this.isBattleMode = buf.readBoolean(); }

    public void toBytes(FriendlyByteBuf buf) { buf.writeBoolean(isBattleMode); }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                battleMode.set(isBattleMode);
            });

        });
        return true;
    }
}
