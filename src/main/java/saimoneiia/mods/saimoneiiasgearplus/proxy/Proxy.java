package saimoneiia.mods.saimoneiiasgearplus.proxy;

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
}
