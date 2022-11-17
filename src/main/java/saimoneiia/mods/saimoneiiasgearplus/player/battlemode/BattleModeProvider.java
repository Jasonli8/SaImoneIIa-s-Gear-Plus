package saimoneiia.mods.saimoneiiasgearplus.player.battlemode;

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

public class BattleModeProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<BattleMode> PLAYER_BATTLE_MODE = CapabilityManager.get(new CapabilityToken<BattleMode>() { });

    private BattleMode battleMode = null;
    private final LazyOptional<BattleMode> optional = LazyOptional.of(this::createBattleMode);

    private BattleMode createBattleMode() {
        if (this.battleMode == null) {
            this.battleMode = new BattleMode();
        }
        return this.battleMode;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_BATTLE_MODE) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createBattleMode().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createBattleMode().loadNBTData(nbt);
    }
}