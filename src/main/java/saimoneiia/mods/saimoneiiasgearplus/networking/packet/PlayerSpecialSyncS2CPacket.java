package saimoneiia.mods.saimoneiiasgearplus.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import saimoneiia.mods.saimoneiiasgearplus.player.playerspecial.PlayerSpecialProvider;

import java.util.function.Supplier;

public class PlayerSpecialSyncS2CPacket {
    private final boolean isArmorPierce;
    private final boolean isMagicPierce;
    private final boolean isMagicAttack;

    private final boolean isEasyStep;

    public PlayerSpecialSyncS2CPacket(boolean isArmorPierce, boolean isMagicPierce, boolean isMagicAttack, boolean isEasyStep) {
        this.isArmorPierce = isArmorPierce;
        this.isMagicPierce = isMagicPierce;
        this.isMagicAttack = isMagicAttack;

        this.isEasyStep = isEasyStep;
    }

    public PlayerSpecialSyncS2CPacket(FriendlyByteBuf buf) {
        this.isArmorPierce = buf.readBoolean();
        this.isMagicPierce = buf.readBoolean();
        this.isMagicAttack = buf.readBoolean();

        this.isEasyStep = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(isArmorPierce);
        buf.writeBoolean(isMagicPierce);
        buf.writeBoolean(isMagicAttack);

        buf.writeBoolean(isEasyStep);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            context.getSender().getCapability(PlayerSpecialProvider.PLAYER_PLAYER_SPECIAL).ifPresent(playerSpecialState -> {
                playerSpecialState.set(isArmorPierce, isMagicPierce, isMagicAttack, isEasyStep);
            });
        });
        return true;
    }
}
