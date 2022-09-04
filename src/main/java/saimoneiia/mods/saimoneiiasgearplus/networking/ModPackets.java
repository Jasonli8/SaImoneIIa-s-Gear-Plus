package saimoneiia.mods.saimoneiiasgearplus.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.MemoryC2SPacket;

public class ModPackets {
    private static SimpleChannel INSTANCE;
    private static int packetID = 0;
    private static int id() {
        return packetID++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(SaimoneiiasGearPlus.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0") // change with version
                .clientAcceptedVersions((s) -> true)
                .serverAcceptedVersions((s) -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(MemoryC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MemoryC2SPacket::new)
                .encoder(MemoryC2SPacket::toBytes)
                .consumer(MemoryC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
