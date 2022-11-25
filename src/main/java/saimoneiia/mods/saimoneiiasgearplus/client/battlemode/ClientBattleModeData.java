package saimoneiia.mods.saimoneiiasgearplus.client.battlemode;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.controller.BattleModeController;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.BattleModeC2SPacket;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

@OnlyIn(Dist.CLIENT)
public class ClientBattleModeData {
    public static boolean isBattleMode = false;
    public static int manaMax = 100;
    public static int mana = 100;
    public static int manaRate = 1;
    public static int manaDelay = 20;
    public static int energyMax = 100;
    public static int energy = 100;
    public static int energyRate = 1;
    public static int energyDelay = 20;
    private static final Minecraft minecraft = Minecraft.getInstance();

    public static void setBattleMode(boolean isSet) { isBattleMode = isSet; }

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
        ModPackets.sendToServer(new BattleModeC2SPacket(isBattleMode));
        onToggle();
    }

    private static void onToggle() {
        if (isBattleMode) {
            minecraft.player.sendSystemMessage(Component.literal("Activating Battle Mode!"));
//            minecraft.options.setCameraType(CameraType.THIRD_PERSON_BACK);
            minecraft.player.removeVehicle();
        } else {
            minecraft.player.sendSystemMessage(Component.literal("De-activating Battle Mode!"));
//            minecraft.options.setCameraType(CameraType.FIRST_PERSON);
            BattleModeController.skillReset();
        }
    }

    public static boolean consumeMana(int points) {
        if (points > mana) return false;
        mana -= points;
        return true;
    }

    public static void restoreMana(int points) { mana = Math.max(manaMax, mana + points); }

    public static void setMana(int manaMaxSet, int manaSet) {
        manaMax = manaMaxSet;
        mana = manaSet;
    }

    public static void setManaRate(int rate, int delay) {
        manaRate = rate;
        manaDelay = delay;
    }

    public static boolean consumeEnergy(int points) {
        if (points > energy) return false;
        energy -= points;
        return true;
    }

    public static void restoreEnergy(int points) { energy = Math.max(energyMax, energy + points); }

    public static void setEnergy(int energyMaxSet, int energySet) {
        energyMax = energyMaxSet;
        energy = energySet;
    }

    public static void setEnergyRate(int rate, int delay) {
        energyRate = rate;
        energyDelay = delay;
    }
}
