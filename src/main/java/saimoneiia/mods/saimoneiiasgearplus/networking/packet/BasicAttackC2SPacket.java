package saimoneiia.mods.saimoneiiasgearplus.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.MeleeWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.RangedWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.function.Supplier;

public class BasicAttackC2SPacket {

    public BasicAttackC2SPacket() {}

    public BasicAttackC2SPacket(FriendlyByteBuf buf) {}

    public void toBytes(FriendlyByteBuf buf) {}

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> {
                ICurioStacksHandler stacksHandler = handler.getCurios().get("weapon");
                if (stacksHandler.getStacks().getStackInSlot(0).getItem() instanceof MeleeWeaponItem) {
                    MeleeWeaponItem weapon = (MeleeWeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem();
                    weapon.serverBasicAttack(player);
                } else {
                    RangedWeaponItem weapon = (RangedWeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem();
                    weapon.serverBasicAttack(player);
                }
            });

        });
        return true;
    }
}
