package saimoneiia.mods.saimoneiiasgearplus.player.memoryprogression;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.MemoryS2CPacket;
import saimoneiia.mods.saimoneiiasgearplus.util.MemoryLevelScaling;

public class MemoryProgression {
    private int memProg;

    private final String PROP_NAME = SaimoneiiasGearPlus.MODID + "_memProg";

    public int getMem() {
        return memProg;
    }

    public boolean canAdd() { return memProg < MemoryLevelScaling.MAX_MEM; }
    public void addMem(int add) {
        memProg = Math.max(memProg + add, MemoryLevelScaling.MIN_MEM);
        memProg = Math.min(memProg, MemoryLevelScaling.MAX_MEM);
    }

    public void setMem(int mem) {
        memProg = Math.max(mem, MemoryLevelScaling.MIN_MEM);
        memProg = Math.min(mem, MemoryLevelScaling.MAX_MEM);
    }

    public void addLevel(int add) {
        int resulting_level = MemoryLevelScaling.getMemLevel(memProg) + add;
        if (resulting_level < 0) {
            memProg = 0;
            return;
        }
        int levelAdd = MemoryLevelScaling.getRequiredMemProg(MemoryLevelScaling.getMemLevel(memProg) + add);
        memProg = Math.min(levelAdd + MemoryLevelScaling.getMemLevelProg(memProg), MemoryLevelScaling.MAX_MEM);
    }

    public void setLevel(int add) {
        memProg = MemoryLevelScaling.getRequiredMemProg(MemoryLevelScaling.getMemLevel(memProg) + add);
    }

    public static void syncPlayerMem(ServerPlayer player) {
        if (!Minecraft.getInstance().level.isClientSide) player.getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG).ifPresent(memProg -> ModPackets.sendToPlayer(new MemoryS2CPacket(memProg.getMem()), player));
    }

    public void copyFrom(MemoryProgression source) {
        this.memProg = source.memProg;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt(PROP_NAME, memProg);
    }

    public void loadNBTData(CompoundTag nbt) {
        memProg = nbt.getInt(PROP_NAME);
    }
}
