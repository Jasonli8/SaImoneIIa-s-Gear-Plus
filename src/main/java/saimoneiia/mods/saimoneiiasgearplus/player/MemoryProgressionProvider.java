package saimoneiia.mods.saimoneiiasgearplus.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MemoryProgressionProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<MemoryProgression> PLAYER_MEM_PROG = CapabilityManager.get(new CapabilityToken<MemoryProgression>() { });

    private MemoryProgression memProg = null;
    private final LazyOptional<MemoryProgression> optional = LazyOptional.of(this::createMemoryProgression);

    private MemoryProgression createMemoryProgression() {
        if (this.memProg == null) {
            this.memProg = new MemoryProgression();
        }
        return this.memProg;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_MEM_PROG) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createMemoryProgression().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createMemoryProgression().loadNBTData(nbt);
    }
}
