package saimoneiia.mods.saimoneiiasgearplus.proxy;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.function.Supplier;

public interface Proxy {
    Proxy INSTANCE = make();

    private static Proxy make() {
        if (FMLLoader.getDist() == Dist.CLIENT) {
            return new ClientProxy();
        } else {
            return new Proxy() {};
        }
    }

    default void runOnClient(Supplier<Runnable> s) {}

    default void mitoRetraceFX(Level level, Vec3 origin) {
        mitoRetraceFX(level, origin, System.nanoTime());;
    }

    default void mitoRetraceFX(Level level, Vec3 origin, long seed) {}
}
