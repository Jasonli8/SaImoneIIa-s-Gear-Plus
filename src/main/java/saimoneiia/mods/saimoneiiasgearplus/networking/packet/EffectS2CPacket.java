package saimoneiia.mods.saimoneiiasgearplus.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression.ClientMemoryProgressionData;
import saimoneiia.mods.saimoneiiasgearplus.client.particle.EffectType;
import saimoneiia.mods.saimoneiiasgearplus.proxy.Proxy;

import java.util.function.Supplier;

public class EffectS2CPacket {
    private EffectType type;
    private Vec3 origin;

    public EffectS2CPacket(EffectType type, Vec3 origin) {
        this.type = type;
        this.origin = origin;
    }

    public EffectS2CPacket(FriendlyByteBuf buf) {
        this.type = EffectType.values()[buf.readByte()];
        this.origin = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeByte(type.ordinal());
        buf.writeDouble(origin.x);
        buf.writeDouble(origin.y);
        buf.writeDouble(origin.z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        Minecraft mc = Minecraft.getInstance();
        mc.execute(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case MITO_RETRACE_EFFECT -> {
                        // randomize lines around player (source)
                        Proxy.INSTANCE.mitoRetraceFX(mc.level, origin, 1);
                    }
                }
            }
        });
//        context.enqueueWork(() -> {
//            System.out.println("type: " + type.toString());
//            switch (this.type) {
//                case MITO_RETRACE_EFFECT -> {
//                    // randomize lines around player (source)
//                    System.out.println("Calling proxy");
//                    Proxy.INSTANCE.mitoRetraceFX(context.getSender().level, this.origin, 1, 0x0179C4, 0xAADFFF); // debug change colours
//                }
//            }
//        });
        return true;
    }
}
