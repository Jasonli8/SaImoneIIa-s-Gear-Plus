package saimoneiia.mods.saimoneiiasgearplus.init.custom;


import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.controller.BattleModeController;
import saimoneiia.mods.saimoneiiasgearplus.client.memoryprogression.MemoryProgressionContainer;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.WeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.MemoryS2CPacket;
import saimoneiia.mods.saimoneiiasgearplus.player.memoryprogression.MemoryProgressionProvider;

// THIS ITEM IS USED FOR CAPTURING INPUTS DURING BATTLEMODE
public class SkillMasterItem extends Item {
    WeaponItem weapon;

    public SkillMasterItem() { super(new Item.Properties().stacksTo(1)); }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand hand){
        if (level.isClientSide() && hand == InteractionHand.MAIN_HAND) {

        }

        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    @Override
    public boolean canAttackBlock(BlockState p_41441_, Level p_41442_, BlockPos p_41443_, Player p_41444_) {
        return false;
    }
}
