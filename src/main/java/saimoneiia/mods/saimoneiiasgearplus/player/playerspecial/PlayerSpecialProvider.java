package saimoneiia.mods.saimoneiiasgearplus.player.playerspecial;

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

public class PlayerSpecialProvider  implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerSpecialState> PLAYER_PLAYER_SPECIAL = CapabilityManager.get(new CapabilityToken<PlayerSpecialState>() { });

    private PlayerSpecialState playerSpecialState = null;
    private final LazyOptional<PlayerSpecialState> optional = LazyOptional.of(this::createPlayerSpecial);

    private PlayerSpecialState createPlayerSpecial() {
        if (this.playerSpecialState == null) {
            this.playerSpecialState = new PlayerSpecialState();
        }
        return this.playerSpecialState;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_PLAYER_SPECIAL) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerSpecial().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerSpecial().loadNBTData(nbt);
    }
}
