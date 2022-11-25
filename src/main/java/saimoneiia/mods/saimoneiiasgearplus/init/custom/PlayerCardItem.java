package saimoneiia.mods.saimoneiiasgearplus.init.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression.MemoryProgressionContainer;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.BattleModeResourcesS2CPacket;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.MemoryS2CPacket;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;
import saimoneiia.mods.saimoneiiasgearplus.player.memoryprogression.MemoryProgressionProvider;

public class PlayerCardItem extends Item{
    public PlayerCardItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand hand){
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            player.getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG).ifPresent(memProg -> {
                ModPackets.sendToPlayer(new MemoryS2CPacket(memProg.getMem()), (ServerPlayer) player);
            });
            player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                battleMode.syncClient((ServerPlayer) player);
            });
            MenuProvider container = new SimpleMenuProvider(MemoryProgressionContainer.getServerContainer(), Component.literal("Memory Field"));
            NetworkHooks.openScreen((ServerPlayer) player, container);
        }

        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }
}
