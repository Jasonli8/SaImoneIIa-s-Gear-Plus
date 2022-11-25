package saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.init.ContainerInit;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;
import saimoneiia.mods.saimoneiiasgearplus.player.memoryprogression.MemoryProgressionProvider;
import saimoneiia.mods.saimoneiiasgearplus.util.MemoryLevelScaling;

public class MemoryProgressionContainer extends AbstractContainerMenu {
    private final Player player;

    // client constructor
    public MemoryProgressionContainer(int id, Inventory playerInv) {
        this(id, playerInv, playerInv.player);
    }

    // server constructor
    public MemoryProgressionContainer(int id, Inventory playerInv, Player player) {
        super(ContainerInit.MEMORY_PROGRESSION.get(), id);
        this.player = player;
        player.getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG)
                .ifPresent(memProg -> {
                    this.addDataSlot(DataSlot.standalone()).set(MemoryLevelScaling.getMemLevel(memProg.getMem()));
                    this.addDataSlot(DataSlot.standalone()).set(MemoryLevelScaling.getMemLevelProg(memProg.getMem()));
                });
        player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE)
                .ifPresent(battleMode -> {
                    this.addDataSlot(DataSlot.standalone()).set(battleMode.manaMax);
                    this.addDataSlot(DataSlot.standalone()).set(battleMode.manaRate);
                    this.addDataSlot(DataSlot.standalone()).set(battleMode.manaDelay);
                    this.addDataSlot(DataSlot.standalone()).set(battleMode.energyMax);
                    this.addDataSlot(DataSlot.standalone()).set(battleMode.energyRate);
                    this.addDataSlot(DataSlot.standalone()).set(battleMode.energyDelay);
                });
    }

    @Override
    public ItemStack quickMoveStack(Player player, int id) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public static MenuConstructor getServerContainer() {
        return (id, playerInv, player) -> new MemoryProgressionContainer(id, playerInv, player);
    }
}
