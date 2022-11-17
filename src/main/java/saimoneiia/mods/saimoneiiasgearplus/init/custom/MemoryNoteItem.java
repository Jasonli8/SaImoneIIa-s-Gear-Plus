package saimoneiia.mods.saimoneiiasgearplus.init.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import saimoneiia.mods.saimoneiiasgearplus.init.BlockInit;
import saimoneiia.mods.saimoneiiasgearplus.player.memoryprogression.MemoryProgressionProvider;

public class MemoryNoteItem extends Item {
    public MemoryNoteItem(Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (blockState.is(BlockInit.MEMORY_CORE.get())) {
            if (level instanceof ServerLevel) {
                player.getCapability(MemoryProgressionProvider.PLAYER_MEM_PROG).ifPresent(memProg -> {
                    if (memProg.canAdd()) {
                        context.getItemInHand().shrink(1);
                        memProg.addMem(1);
                        player.sendSystemMessage(Component.literal("Successful use on memory core: " + memProg.getMem())); // debug
                    } else {
                        player.sendSystemMessage(Component.literal("Max level reached"));
                    }
                });
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            player.sendSystemMessage(Component.literal("Unsuccessful use on memory core")); // debug
            return InteractionResult.FAIL;
        }
    }
}
