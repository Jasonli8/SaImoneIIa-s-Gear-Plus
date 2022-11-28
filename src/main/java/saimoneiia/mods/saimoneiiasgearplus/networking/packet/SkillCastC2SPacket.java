package saimoneiia.mods.saimoneiiasgearplus.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.MeleeWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.RangedWeaponItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.function.Supplier;

public class SkillCastC2SPacket {
    private final int skillCode;

    public SkillCastC2SPacket(int skillCode) { this.skillCode = skillCode; }

    public SkillCastC2SPacket(FriendlyByteBuf buf) { this.skillCode = buf.readInt(); }

    public void toBytes(FriendlyByteBuf buf) { buf.writeInt(skillCode); }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // TODO: skill casting on server side
            ServerPlayer player = context.getSender();
            CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> {
                ICurioStacksHandler stacksHandler = handler.getCurios().get("weapon");
                if (stacksHandler.getStacks().getStackInSlot(0).getItem() instanceof MeleeWeaponItem) {
                    MeleeWeaponItem weapon = (MeleeWeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem();
                    weapon.castSkill(player, skillCode);
                } else {
                    RangedWeaponItem weapon = (RangedWeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem();
                    weapon.castSkill(player, skillCode);
                }
            });
        });
        return true;
    }
}
