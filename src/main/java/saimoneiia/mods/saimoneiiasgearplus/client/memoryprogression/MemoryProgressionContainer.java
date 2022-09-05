package saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.ItemStack;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.init.ContainerInit;
import saimoneiia.mods.saimoneiiasgearplus.player.MemoryProgressionProvider;

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
                    this.addDataSlot(DataSlot.standalone()).set(memProg.getLevel());
                    this.addDataSlot(DataSlot.standalone()).set(memProg.getProg());
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
