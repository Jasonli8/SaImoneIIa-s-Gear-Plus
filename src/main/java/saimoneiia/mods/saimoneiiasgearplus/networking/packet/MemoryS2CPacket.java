package saimoneiia.mods.saimoneiiasgearplus.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression.ClientMemoryProgressionData;

import java.util.function.Supplier;

// sync player memory progression on server side to client side
// currently only used for displaying progression when using player card item
public class MemoryS2CPacket {
    private final int memory;

    public MemoryS2CPacket(int memory) {
        this.memory = memory;
    }

    public MemoryS2CPacket(FriendlyByteBuf buf) {
        this.memory = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(memory);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // code on client
            ClientMemoryProgressionData.set(memory);
        });
        return true;
    }
}
