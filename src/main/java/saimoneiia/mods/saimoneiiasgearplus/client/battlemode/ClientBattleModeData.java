package saimoneiia.mods.saimoneiiasgearplus.client.battlemode;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.BattleModeC2SPacket;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

@OnlyIn(Dist.CLIENT)
public class ClientBattleModeData {
    private static boolean isBattleMode = false;
    private static final Minecraft minecraft = Minecraft.getInstance();

    public static void set(boolean isSet) { isBattleMode = isSet; }
    public static boolean get()  { return isBattleMode; }

    public static void toggle() {
        isBattleMode = !isBattleMode;
        refreshToggle();
    }

    public static void refreshToggle() {
        CuriosApi.getCuriosHelper().getCuriosHandler(minecraft.player).ifPresent(handler -> {
            ICurioStacksHandler stacksHandler = handler.getCurios().get("weapon");
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();
            ItemStack stack = stackHandler.getStackInSlot(0);
            if (stack.isEmpty()) {
                isBattleMode = false;
            }
        });
        ModPackets.sendToServer(new BattleModeC2SPacket(ClientBattleModeData.get()));
        onToggle();
    }

    private static void onToggle() {
        if (isBattleMode) {
            minecraft.player.sendSystemMessage(Component.literal("Activating Battle Mode!"));
            minecraft.options.setCameraType(CameraType.THIRD_PERSON_BACK);
            minecraft.player.removeVehicle();
        } else {
            minecraft.player.sendSystemMessage(Component.literal("De-activating Battle Mode!"));
            minecraft.options.setCameraType(CameraType.FIRST_PERSON);
        }
    }
}
