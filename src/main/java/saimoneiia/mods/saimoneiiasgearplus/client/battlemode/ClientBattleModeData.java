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
    public static int ticksTillManaRecharge = 0;
    public static int energyMax = 100;
    public static int energy = 100;
    public static int energyRate = 1;
    public static int energyDelay = 20;
    public static int ticksTillEnergyRecharge = 0;

    public static void setAll(
            boolean isBattleMode,
            int manaMax, int mana, int manaRate, int manaDelay, int ticksTillManaRecharge,
            int energyMax, int energy, int energyRate, int energyDelay, int ticksTillEnergyRecharge
    ) {
        ClientBattleModeData.isBattleMode = isBattleMode;
        ClientBattleModeData.manaMax = manaMax;
        ClientBattleModeData.mana = mana;
        ClientBattleModeData.manaRate = manaRate;
        ClientBattleModeData.manaDelay = manaDelay;
        ClientBattleModeData.ticksTillManaRecharge = ticksTillManaRecharge;
        ClientBattleModeData.energyMax = energyMax;
        ClientBattleModeData.energy = energy;
        ClientBattleModeData.energyRate = energyRate;
        ClientBattleModeData.energyDelay = energyDelay;
        ClientBattleModeData.ticksTillEnergyRecharge = ticksTillEnergyRecharge;
    }
}
