package saimoneiia.mods.saimoneiiasgearplus.proxy;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import saimoneiia.mods.saimoneiiasgearplus.client.core.ClientTickHandler;
import saimoneiia.mods.saimoneiiasgearplus.client.particle.MitoRetraceOptions;
import saimoneiia.mods.saimoneiiasgearplus.client.particle.MitoRetraceRenderer;

import java.util.function.Supplier;

public class ClientProxy implements Proxy {
    @Override
    public void runOnClient(Supplier<Runnable> s) {
        s.get().run();
    }

    @Override
    public void mitoRetraceFX(Level level, Vec3 origin, long seed) {
        MitoRetraceRenderer.INSTANCE.add(level, seed, origin, ClientTickHandler.partialTicks);
    }
}
