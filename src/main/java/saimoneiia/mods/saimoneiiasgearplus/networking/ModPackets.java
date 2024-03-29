package saimoneiia.mods.saimoneiiasgearplus.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.*;

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

        net.messageBuilder(MemoryS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MemoryS2CPacket::new)
                .encoder(MemoryS2CPacket::toBytes)
                .consumerNetworkThread(MemoryS2CPacket::handle)
                .add();

        net.messageBuilder(BattleModeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BattleModeC2SPacket::new)
                .encoder(BattleModeC2SPacket::toBytes)
                .consumerNetworkThread(BattleModeC2SPacket::handle)
                .add();

        net.messageBuilder(SkillCastC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SkillCastC2SPacket::new)
                .encoder(SkillCastC2SPacket::toBytes)
                .consumerNetworkThread(SkillCastC2SPacket::handle)
                .add();

        net.messageBuilder(BattleModeResourcesS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BattleModeResourcesS2CPacket::new)
                .encoder(BattleModeResourcesS2CPacket::toBytes)
                .consumerNetworkThread(BattleModeResourcesS2CPacket::handle)
                .add();

        net.messageBuilder(MovementCastC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MovementCastC2SPacket::new)
                .encoder(MovementCastC2SPacket::toBytes)
                .consumerNetworkThread(MovementCastC2SPacket::handle)
                .add();

        net.messageBuilder(PlayerSpecialSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerSpecialSyncS2CPacket::new)
                .encoder(PlayerSpecialSyncS2CPacket::toBytes)
                .consumerNetworkThread(PlayerSpecialSyncS2CPacket::handle)
                .add();

        net.messageBuilder(BasicAttackC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BasicAttackC2SPacket::new)
                .encoder(BasicAttackC2SPacket::toBytes)
                .consumerNetworkThread(BasicAttackC2SPacket::handle)
                .add();

        net.messageBuilder(EffectS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EffectS2CPacket::new)
                .encoder(EffectS2CPacket::toBytes)
                .consumerNetworkThread(EffectS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
