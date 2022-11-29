package saimoneiia.mods.saimoneiiasgearplus.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.MeleeWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.RangedWeaponItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.function.Supplier;

public class MovementCastC2SPacket {
    private final String action;
    private final Vec3 directionVec;

    public MovementCastC2SPacket(String action) {
        this.action = action;
        this.directionVec = Vec3.ZERO;
    }

    public MovementCastC2SPacket(String action, Vec3 directionVec) {
        this.action = action;
        this.directionVec = directionVec;
    }

    public MovementCastC2SPacket(FriendlyByteBuf buf) {
        this.action = buf.readUtf();
        this.directionVec = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(action);
        buf.writeDouble(directionVec.x);
        buf.writeDouble(directionVec.y);
        buf.writeDouble(directionVec.z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            System.out.println(player.toString());
            CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> {
                System.out.println(action);
                ICurioStacksHandler stacksHandler = handler.getCurios().get("weapon");
                if (stacksHandler.getStacks().getStackInSlot(0).getItem() instanceof MeleeWeaponItem) {
                    System.out.println("melee weapon");
                    MeleeWeaponItem weapon = (MeleeWeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem();
                    if (action.equals("dodge")) {
                        System.out.println("packet dodge");
                        weapon.dodge(player, directionVec);
                    } else if (action.equals("powerJump")) {
                        System.out.println("packet jump");
                        weapon.powerJump(player);
                    }
                } else {
                    System.out.println("ranged weapon");
                    RangedWeaponItem weapon = (RangedWeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem();
                    if (action.equals("dodge")) {
                        System.out.println("packet dodge");
                        weapon.dodge(player, directionVec);
                    } else if (action.equals("powerJump")) {
                        System.out.println("packet jump");
                        weapon.powerJump(player);
                    }
                }
            });
        });
        return true;
    }
}
