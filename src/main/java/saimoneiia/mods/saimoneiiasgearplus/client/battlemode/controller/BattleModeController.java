package saimoneiia.mods.saimoneiiasgearplus.client.battlemode.controller;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.WeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.SkillCastC2SPacket;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

@OnlyIn(Dist.CLIENT)
public class BattleModeController {
    private static Minecraft minecraft = Minecraft.getInstance();

    private static WeaponItem weapon;

    private static boolean initiatedBattle = false;

    private static int skillCode = 0;
    private static int skillInput = 0;


    private static boolean pressedA = false;
    private static boolean pressedB = false;

    public static void combatTick(TickEvent.ClientTickEvent event) {
        if (ClientBattleModeData.get()) {
            // get weilded weapon at start of battle
            if (!initiatedBattle) {
                initiatedBattle = true;
                CuriosApi.getCuriosHelper().getCuriosHandler(Minecraft.getInstance().player).ifPresent(handler -> {
                    ICurioStacksHandler stacksHandler = handler.getCurios().get("weapon");
                    weapon = (WeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem();
                });
            }

            // handle skill button clicks (1 click per hold on down)
            if (!pressedA && minecraft.options.keyAttack.isDown()) {
                pressA();
            } else if (!minecraft.options.keyAttack.isDown()) {
                pressedA = false;
            }
            if (!pressedB && minecraft.options.keyUse.isDown()) {
                pressB();
            } else if (!minecraft.options.keyUse.isDown()) {
                pressedB = false;
            }
            onSkillInput();
        } else {
            initiatedBattle = false;
        }

    }

    public static void skillReset() {
        skillCode = 0;
        skillInput = 0;
    }

    private static void pressA() {
        pressedA = true;
        skillCode *= 2;
        if (skillCode != 0) {
            skillInput++;

        }
        System.out.println(skillCode);
        System.out.println(skillInput);
    }

    private static void pressB() {
        pressedB = true;
        skillCode = 2 * skillCode + 1;
        skillInput++;
        System.out.println(skillCode);
        System.out.println(skillInput);
    }

    private static void onSkillInput() {
        if (skillInput == weapon.getRequiredSkillInputs()) {
            ModPackets.sendToServer(new SkillCastC2SPacket(skillCode));
            weapon.castSkill(minecraft.player, skillCode);
            skillReset();
        }
    }
}
