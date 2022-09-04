package saimoneiia.mods.saimoneiiasgearplus.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MemoryC2SPacket {
    public MemoryC2SPacket() {

    }

    public MemoryC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // code on server
            ServerPlayer player = context.getSender();
        });
        return true;
    }
}
