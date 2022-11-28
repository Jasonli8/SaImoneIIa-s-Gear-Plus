package saimoneiia.mods.saimoneiiasgearplus.client.battlemode.controller;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.MeleeWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.RangedWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.SkillCastC2SPacket;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

@OnlyIn(Dist.CLIENT)
public class BattleModeController {
    private static Minecraft minecraft = Minecraft.getInstance();

    private static MeleeWeaponItem weaponMelee;
    private static RangedWeaponItem weaponRanged;
    private static boolean isMelee = false;

    private static boolean initiatedBattle = false;

    private static int skillCode = 0;
    private static int skillInput = 0;


    private static boolean pressedA = false;
    private static boolean pressedB = false;

    public static void combatTick(TickEvent.ClientTickEvent event) {
        if (ClientBattleModeData.isBattleMode) {
            // get weilded weapon at start of battle
            if (!initiatedBattle) {
                initiatedBattle = true;
                CuriosApi.getCuriosHelper().getCuriosHandler(Minecraft.getInstance().player).ifPresent(handler -> {
                    ICurioStacksHandler stacksHandler = handler.getCurios().get("weapon");
                    if (stacksHandler.getStacks().getStackInSlot(0).getItem() instanceof MeleeWeaponItem) {
                        weaponMelee = (MeleeWeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem();
                        isMelee = true;
                    } else {
                        weaponRanged = (RangedWeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem();
                        isMelee = false;
                    }
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
        if (isMelee) {
            if (skillInput == weaponMelee.getRequiredSkillInputs()) {
                ModPackets.sendToServer(new SkillCastC2SPacket(skillCode));
                weaponMelee.castSkill(minecraft.player, skillCode);
                skillReset();
            }
        } else {
            if (skillInput == weaponRanged.getRequiredSkillInputs()) {
                ModPackets.sendToServer(new SkillCastC2SPacket(skillCode));
                weaponRanged.castSkill(minecraft.player, skillCode);
                skillReset();
            }
        }

    }
}
