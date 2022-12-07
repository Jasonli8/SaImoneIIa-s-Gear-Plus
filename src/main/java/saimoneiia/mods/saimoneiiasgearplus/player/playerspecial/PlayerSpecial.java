package saimoneiia.mods.saimoneiiasgearplus.player.playerspecial;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;

import java.util.UUID;

public class PlayerSpecial {
    private static final UUID STEP_BOOST_UUID = UUID.fromString("649db56a-dca8-4d35-b773-1a0571fd1938");
    private static final AttributeModifier STEP_BOOST = new AttributeModifier(
            STEP_BOOST_UUID,
            "saimoneiiasgearplus:mito_single_sword_step_boost",
            0.65, AttributeModifier.Operation.ADDITION);

    public static void handleEasyStep(Player player) {
        player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
            AttributeInstance attrib = player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
            if (battleMode.isBattleMode && !attrib.hasModifier(STEP_BOOST)) {
                attrib.addTransientModifier(STEP_BOOST);
            } else if (!battleMode.isBattleMode) {
                attrib.removeModifier(STEP_BOOST);
            }
        });
    }

    public static float handleBasicAttackAttributes(Player player, DamageSource source, float amount) {
        player.getCapability(PlayerSpecialProvider.PLAYER_PLAYER_SPECIAL).ifPresent(playerSpecial -> {
            if (playerSpecial.isArmorPierce) {
                source.bypassArmor();
            }
            if (playerSpecial.isMagicPierce) {
                source.bypassMagic();
            }
            if (playerSpecial.isMagicAttack) {
                source.setMagic();
            }
        });

        return amount;
    }
}
