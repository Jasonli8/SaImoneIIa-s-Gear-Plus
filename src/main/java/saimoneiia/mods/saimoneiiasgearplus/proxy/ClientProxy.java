package saimoneiia.mods.saimoneiiasgearplus.proxy;

import java.util.function.Supplier;

public class ClientProxy implements Proxy {
    @Override
    public void runOnClient(Supplier<Runnable> s) {
        s.get().run();
    }
}
