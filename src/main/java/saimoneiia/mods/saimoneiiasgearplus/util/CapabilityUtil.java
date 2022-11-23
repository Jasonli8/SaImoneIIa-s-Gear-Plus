package saimoneiia.mods.saimoneiiasgearplus.util;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CapabilityUtil {
    public static <T, U extends T> ICapabilityProvider makeProvider(Capability<T> cap, U instance) {
        LazyOptional<T> lazyInstanceButNotReally = LazyOptional.of(() -> instance);
        return new CapProvider<>(cap, lazyInstanceButNotReally);
    }

    private CapabilityUtil() {}

    private static class CapProvider<T> implements ICapabilityProvider {
        protected final Capability<T> cap;
        protected final LazyOptional<T> lazyInstanceButNotReally;

        public CapProvider(Capability<T> cap, LazyOptional<T> instance) {
            this.cap = cap;
            this.lazyInstanceButNotReally = instance;
        }

        @NotNull
        @Override
        public <C> LazyOptional<C> getCapability(@NotNull Capability<C> queryCap, @Nullable Direction side) {
            return cap.orEmpty(queryCap, lazyInstanceButNotReally);
        }
    }
}
