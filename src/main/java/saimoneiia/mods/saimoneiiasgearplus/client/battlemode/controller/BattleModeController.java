package saimoneiia.mods.saimoneiiasgearplus.client.battlemode.controller;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.gui.SkillInputOverlay;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.MeleeWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.RangedWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.BasicAttackC2SPacket;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.SkillCastC2SPacket;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.concurrent.atomic.AtomicBoolean;

@OnlyIn(Dist.CLIENT)
public class BattleModeController {
    private static Minecraft minecraft = Minecraft.getInstance();

    private static MeleeWeaponItem weaponMelee;
    private static RangedWeaponItem weaponRanged;
    private static boolean isMelee = false;

    private static boolean initiatedBattle = false;

    public static int skillCode = 0;
    public static int skillInput = 0;


    private static boolean pressedA = false;
    private static boolean pressedB = false;
    private static boolean directionPrepped = false;

    public static int castCooldown = 0;
    public static int ticksSinceLastSkillInput = 0;
    public static boolean isSkillCasted = false;

    public static void combatTick(TickEvent.ClientTickEvent event) {
        // tick cooldowns and update states
        if (minecraft.player != null) {
            minecraft.player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                if (castCooldown > 0) {
                    castCooldown--;
                } else {
                    isSkillCasted = false;
                }

                if (battleMode.isBattleMode) {
                    // get weilded weapon at start of battle
                    if (!initiatedBattle) {
                        initiatedBattle = true;
                        skillReset(20, false);
                        CuriosApi.getCuriosHelper().getCuriosHandler(Minecraft.getInstance().player).ifPresent(handler -> {
                            ICurioStacksHandler stacksHandler = handler.getCurios().get("weapon");
                            if (stacksHandler.getStacks().getStackInSlot(0).getItem() instanceof MeleeWeaponItem) {
                                weaponMelee = (MeleeWeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem();
                                isMelee = true;
                            } else if (stacksHandler.getStacks().getStackInSlot(0).getItem() instanceof RangedWeaponItem) {
                                weaponRanged = (RangedWeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem();
                                isMelee = false;
                            } else {
                                battleMode.isBattleMode = false;
                                return;
                            }
                        });
                    }


                    if (castCooldown > 0) castCooldown--;
                    if (skillInput > 0) ticksSinceLastSkillInput++;

                    if (ticksSinceLastSkillInput == 60) {
                        skillReset(0, false);
                        ticksSinceLastSkillInput = 0;
                    }

                    // handle skill button clicks (1 click per hold on down)
                    if (!pressedA && minecraft.options.keyAttack.isDown()) {
                        pressA();
                    } else if (!minecraft.options.keyAttack.isDown()) {
                        pressedA = false;
                    }
                    if (castCooldown <= 0) {
                        if (!pressedB && minecraft.options.keyUse.isDown()) {
                            pressB();
                        } else if (!minecraft.options.keyUse.isDown()) {
                            pressedB = false;
                        }
                    }

                    // movement mechanics
                    if (directionPrepped && minecraft.options.keyShift.isDown()) {
                        dodge();
                    }
                    directionPrepped = (minecraft.options.keyUp.isDown() || minecraft.options.keyDown.isDown() || minecraft.options.keyLeft.isDown() || minecraft.options.keyRight.isDown()) && !minecraft.options.keyShift.isDown();
                    if (minecraft.options.keyShift.isDown()) {
                        if ( minecraft.options.keyJump.isDown()) {
                            powerJump();
                        }
                    }
                } else {
                    initiatedBattle = false;
                    SkillInputOverlay.setCirclesToRender();
                    skillReset(0, false);
                }
            });
        }
    }

    public static void skillReset(int cooldown, boolean wasCastSkill) {
        castCooldown = cooldown;
        isSkillCasted = wasCastSkill;
        skillCode = 0;
        skillInput = 0;

    }

    private static void pressA() {
        pressedA = true;
        skillCode *= 2;
        if (skillCode != 0) {
            skillInput++;
            ticksSinceLastSkillInput = 0;
        } else {
            ModPackets.sendToServer(new BasicAttackC2SPacket());
        }
        onSkillInput();
    }

    private static void pressB() {
        pressedB = true;
        AtomicBoolean skillLockout = new AtomicBoolean(false);
        CuriosApi.getCuriosHelper().getCuriosHandler(minecraft.player).ifPresent(handler -> {
            ICurioStacksHandler stacksHandler = handler.getCurios().get("weapon");
            if (stacksHandler.getStacks().getStackInSlot(0).getItem() instanceof MeleeWeaponItem) {
                skillLockout.set(((MeleeWeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem()).skillLockout);
            } else if (stacksHandler.getStacks().getStackInSlot(0).getItem() instanceof RangedWeaponItem) {
                skillLockout.set(((RangedWeaponItem) stacksHandler.getStacks().getStackInSlot(0).getItem()).skillLockout);
            }
        });
        if (!skillLockout.get()) {
            ticksSinceLastSkillInput = 0;
            skillCode = 2 * skillCode + 1;
            skillInput++;
        }
        onSkillInput();
    }


    private static void onSkillInput() {
            if (skillInput > 0) minecraft.player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
            SkillInputOverlay.setCirclesToRender();
            if (skillInput > 0) {
                minecraft.options.keyAttack.consumeClick();
                minecraft.options.keyUse.consumeClick();
            }
            attemptSkillCast();
    }

    private static void attemptSkillCast() {
        if (isMelee) {
            if (skillInput == weaponMelee.getRequiredSkillInputs()) {
                ModPackets.sendToServer(new SkillCastC2SPacket(skillCode));
                weaponMelee.castSkill(minecraft.player, skillCode);
                skillReset(10, true);
            }
        } else {
            if (skillInput == weaponRanged.getRequiredSkillInputs()) {
                ModPackets.sendToServer(new SkillCastC2SPacket(skillCode));
                weaponRanged.castSkill(minecraft.player, skillCode);
                skillReset(10, true);
            }
        }
    }

    // DEFAULT MOVEMENTS
    private static void dodge() {
        minecraft.player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
            if (minecraft.player.isOnGround() && battleMode.consumeEnergy(30)) {
                Vec3 directionVec = Vec3.ZERO;
                Vec3 moveVec = minecraft.player.getLookAngle().multiply(1,0,1).normalize();
                if (minecraft.options.keyLeft.isDown()) directionVec = directionVec.add(moveVec.yRot((float) Math.PI  / 2));
                if (minecraft.options.keyRight.isDown()) directionVec = directionVec.add(moveVec.yRot((float) Math.PI  / -2));
                if (minecraft.options.keyDown.isDown()) directionVec = directionVec.add(moveVec.yRot((float) Math.PI));
                if (minecraft.options.keyUp.isDown()) directionVec = directionVec.add(moveVec);

                if (isMelee) {
                    weaponMelee.dodge(minecraft.player, directionVec);
                } else {
                    weaponRanged.dodge(minecraft.player, directionVec);
                }
            }
        });
    }

    private static void powerJump() {
        minecraft.player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
            if (minecraft.player.isOnGround() && battleMode.consumeEnergy(30)) {
                if (isMelee) {
                    weaponMelee.powerJump(minecraft.player);
                } else {
                    weaponRanged.powerJump(minecraft.player);
                }

                minecraft.options.keyJump.setDown(false); // MINECRAFT DEFAULT JUMP WILL OVERRIDE OTHERWISE
            }
        });

    }
}
