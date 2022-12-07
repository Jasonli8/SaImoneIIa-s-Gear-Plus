package saimoneiia.mods.saimoneiiasgearplus.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import saimoneiia.mods.saimoneiiasgearplus.client.battlemode.ClientBattleModeData;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;

@Mixin(value = LocalPlayer.class)
public abstract class MixinLocalPlayer {
    @Inject(at = @At(value = "HEAD"), method = "drop", cancellable = true)
    private void saimoneiiasgearplus_drop(boolean p_108701_, CallbackInfoReturnable<Boolean> cir) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                if (battleMode.isBattleMode) {
                    cir.setReturnValue(false);
                    cir.cancel();
                }
            });
        }
    }
}
