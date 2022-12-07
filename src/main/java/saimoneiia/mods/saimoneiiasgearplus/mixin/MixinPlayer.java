package saimoneiia.mods.saimoneiiasgearplus.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;
import saimoneiia.mods.saimoneiiasgearplus.player.playerspecial.PlayerSpecial;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(value = Player.class)
public class MixinPlayer {

    @Inject(at = @At(value = "HEAD"), method = "blockActionRestricted", cancellable = true)
    public void saimoneiiasgearplus_blockActionRestricted(Level p_36188_, BlockPos p_36189_, GameType p_36190_, CallbackInfoReturnable<Boolean> cir) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                if (battleMode.isBattleMode) {
                    cir.setReturnValue(true);
                    cir.cancel();
                }
            });
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "interactOn", cancellable = true)
    public void saimoneiiasgearplus_interactOn(Entity p_36158_, InteractionHand p_36159_, CallbackInfoReturnable<InteractionResult> cir) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                if (battleMode.isBattleMode) {
                    cir.setReturnValue(InteractionResult.FAIL);
                    cir.cancel();
                }
            });
        }
    }


//    @Inject(at = @At(value = "HEAD"), method = "attack", cancellable = true)
//    public void saimoneiiasgearplus_attack(Entity p_36347_, CallbackInfo ci) {
//
//    }
//
//    @Inject(at = @At(value = "HEAD"), method = "crit", cancellable = true)
//    public void saimoneiiasgearplus_crit(Entity p_36347_, CallbackInfo ci) {
//
//    }
//
//    @Inject(at = @At(value = "HEAD"), method = "magicCrit", cancellable = true)
//    public void saimoneiiasgearplus_magicCrit(Entity p_36347_, CallbackInfo ci) {
//
//    }
//
//    @Inject(at = @At(value = "HEAD"), method = "isScoping", cancellable = true)
//    public void saimoneiiasgearplus_isScoping(CallbackInfoReturnable<Boolean> cir) {
//
//    }

    @ModifyArg(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z")
    )
    private DamageSource onDamageTarget(DamageSource source, float amount) {
        amount = PlayerSpecial.handleBasicAttackAttributes((Player) (Object) this, source, amount);
        return source;
    }

    @Inject(at = @At(value = "HEAD"), method = "canEat", cancellable = true)
    public void saimoneiiasgearplus_canEat(boolean p_36392_, CallbackInfoReturnable<Boolean> cir) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                if (battleMode.isBattleMode) {
                    cir.setReturnValue(false);
                    cir.cancel();
                }
            });
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "eat", cancellable = true)
    public void saimoneiiasgearplus_eat(Level p_36185_, ItemStack p_36186_, CallbackInfoReturnable<ItemStack> cir) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                if (battleMode.isBattleMode) {
                    cir.setReturnValue(ItemStack.EMPTY);
                    cir.cancel();
                }
            });
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "mayBuild", cancellable = true)
    public void saimoneiiasgearplus_mayBuild(CallbackInfoReturnable<Boolean> cir) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                if (battleMode.isBattleMode) {
                    cir.setReturnValue(false);
                    cir.cancel();
                }
            });
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "mayUseItemAt", cancellable = true)
    public void saimoneiiasgearplus_mayUseItemAt(BlockPos p_36205_, Direction p_36206_, ItemStack p_36207_, CallbackInfoReturnable<Boolean> cir) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                if (battleMode.isBattleMode) {
                    cir.setReturnValue(false);
                    cir.cancel();
                }
            });
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "getItemBySlot", cancellable = true)
    public void saimoneiiasgearplus_getItemBySlot(EquipmentSlot p_36257_, CallbackInfoReturnable<ItemStack> cir) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                if (battleMode.isBattleMode) {
                    if (p_36257_ == EquipmentSlot.MAINHAND) {
                        cir.setReturnValue(ItemStack.EMPTY);
                        CuriosApi.getCuriosHelper().getCuriosHandler(Minecraft.getInstance().player).ifPresent(handler -> {
                            if (!handler.getCurios().isEmpty()) {
                                cir.setReturnValue(handler.getCurios().get("weapon").getStacks().getStackInSlot(0));
                            }
                        });
                        cir.cancel();
                    } else if (p_36257_ == EquipmentSlot.OFFHAND) {
                        cir.setReturnValue(ItemStack.EMPTY);
                        cir.cancel();
                    }
                }
            });
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "setItemSlot", cancellable = true)
    public void saimoneiiasgearplus_setItemSlot(EquipmentSlot p_36161_, ItemStack p_36162_, CallbackInfo cir) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                if (battleMode.isBattleMode) {
                    cir.cancel();
                }
            });
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "getHandSlots", cancellable = true)
    public void saimoneiiasgearplus_getHandSlots(CallbackInfoReturnable<Iterable<ItemStack>> cir) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                if (battleMode.isBattleMode) {
                    CuriosApi.getCuriosHelper().getCuriosHandler(Minecraft.getInstance().player).ifPresent(handler -> {
                        cir.setReturnValue(Lists.newArrayList(handler.getCurios().get("weapon").getStacks().getStackInSlot(0), ItemStack.EMPTY));
                    });
                    cir.cancel();
                }
            });
        }
    }
}
