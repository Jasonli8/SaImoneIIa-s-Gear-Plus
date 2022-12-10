package saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.singlesword;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.client.model.DynamicFluidContainerModel;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import saimoneiia.mods.saimoneiiasgearplus.SaimoneiiasGearPlus;
import saimoneiia.mods.saimoneiiasgearplus.client.particle.EffectType;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderRegistry;
import saimoneiia.mods.saimoneiiasgearplus.client.render.EquipmentRenderer;
import saimoneiia.mods.saimoneiiasgearplus.client.render.item.MitoSingleSwordRenderer;
import saimoneiia.mods.saimoneiiasgearplus.client.render.item.MitoSingleSwordSheathRenderer;
import saimoneiia.mods.saimoneiiasgearplus.init.ItemInit;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.BaseWeaponTeir;
import saimoneiia.mods.saimoneiiasgearplus.init.gear.weapons.MeleeWeaponItem;
import saimoneiia.mods.saimoneiiasgearplus.networking.ModPackets;
import saimoneiia.mods.saimoneiiasgearplus.networking.packet.EffectS2CPacket;
import saimoneiia.mods.saimoneiiasgearplus.player.battlemode.BattleModeProvider;
import saimoneiia.mods.saimoneiiasgearplus.player.playerspecial.PlayerSpecial;
import saimoneiia.mods.saimoneiiasgearplus.player.playerspecial.PlayerSpecialProvider;
import saimoneiia.mods.saimoneiiasgearplus.proxy.Proxy;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MitoSingleSword extends MeleeWeaponItem {

    private boolean isChannelled = false;
    private boolean isFlowed = false;

    private int skill4Ticks = -1;

    float tempX = 0;
    float tempY = 0;
    float tempZ = 0;

    Vec3 temp = Vec3.ZERO;


    public MitoSingleSword() {
        super("mito_single_sword", 4, new BaseWeaponTeir(), 20, -1, (new Item.Properties()).tab(SaimoneiiasGearPlus.TAB).stacksTo(1).fireResistant());
        Proxy.INSTANCE.runOnClient(() -> () -> EquipmentRenderRegistry.register(this, new MitoSingleSword.Renderer()));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new MitoSingleSwordRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public void itemTick(ItemStack stack, LivingEntity livingEntity) {

        livingEntity.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
            livingEntity.getCapability(PlayerSpecialProvider.PLAYER_PLAYER_SPECIAL).ifPresent(playerSpecial -> {

                // consume mana
                if (isChannelled) {
                    isChannelled = battleMode.consumeMana(1);
                }
                if (isFlowed) {
                    isFlowed = battleMode.consumeMana(1);
                }

                // add some functionality here
                if (iTicks == 0) ((Player) livingEntity).setInvulnerable(false);
                if (isFlowed) {
                    playerSpecial.isArmorPierce = true;
                    playerSpecial.isMagicPierce = true;
                    playerSpecial.isMagicAttack = true;
                } else {
                    playerSpecial.isArmorPierce = false;
                    playerSpecial.isMagicPierce = false;
                    playerSpecial.isMagicAttack = false;
                }

                if (livingEntity instanceof Player player && !player.level.isClientSide) {
                    PlayerSpecial.handleEasyStep(player);

                    if (attackSurroundingTicks > 0) {
                        double range = 1;
                        Predicate<Entity> selector = e -> e instanceof LivingEntity && !e.is(player) && !entitiesHitInCurrentSKill.contains(e.getId());
                        List<Entity> targets = player.level.getEntities(player, new AABB(player.getX() - range, player.getY() - range, player.getZ() - range, player.getX() + range, player.getY() + range, player.getZ() + range), selector);
                        for (Entity target : targets) {
                            target.hurt(DamageSource.playerAttack(player), Math.max(((float) player.getDeltaMovement().length()) * 10 , attackSurroundingAmount));
                            entitiesHitInCurrentSKill.add(target.getId());
                        }
                    } else if (!entitiesHitInCurrentSKill.isEmpty()) {
                        entitiesHitInCurrentSKill.clear();
                    }

                    if (skill3Ticks == 30 || skill3Ticks == 20 || skill3Ticks == 10) {
                        if (hitResults != null) {
                            for (EntityHitResult entityHitResult : hitResults) {
                                Entity target = entityHitResult.getEntity();
                                target.invulnerableTime = 0;
                                target.hurt(DamageSource.playerAttack(player), 5F);
                                if (skill3Ticks != 10) target.setDeltaMovement(Vec3.ZERO);
                            }
                        }
                    }
                    if (skill3Ticks >= 0) {
                        if (player.getEffect(MobEffects.MOVEMENT_SLOWDOWN) != null) {
                            player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                        }
                        if (skill3Ticks > 0) player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 9, false, false, false));
                        if (skill3Ticks == 0) {
                            skillLockout = false;
                            hitResults.clear();
                        }
                    }

                    if (skill4Ticks >= 0) {
                        if (hitResults != null) {
                            for (EntityHitResult entityHitResult : hitResults) {
                                Entity target = entityHitResult.getEntity();
                                target.invulnerableTime = 0;
                                target.hurt(DamageSource.playerAttack(player), 0.5F);
                                target.setDeltaMovement(Vec3.ZERO);
                            }
                        }
                        if (player.getEffect(MobEffects.MOVEMENT_SLOWDOWN) != null) {
                            player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                        }
                        if (skill4Ticks > 0) player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 9, false, false, false));
                        if (skill4Ticks == 0) {
                            skillLockout = false;
                            hitResults.clear();
                        }
                    }

                    if (battleMode.isBattleMode) {
                        if (isChannelled) {
                            if (player.getEffect(MobEffects.ABSORPTION) != null) {
                                player.removeEffect(MobEffects.ABSORPTION);
                            }
                            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 10, 4, false, false, false));
                        } else {
                            if (player.getEffect(MobEffects.ABSORPTION) != null) {
                                player.removeEffect(MobEffects.ABSORPTION);
                            }
                        }

                        if (isFlowed) {
                            if (player.getEffect(MobEffects.DIG_SPEED) != null) {
                                player.removeEffect(MobEffects.DIG_SPEED);
                            }
                            if (player.getEffect(MobEffects.MOVEMENT_SPEED) != null) {
                                player.removeEffect(MobEffects.MOVEMENT_SPEED);
                            }
                            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 10, 3, false, false, false));
                            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 2, false, false, false));
                        }
                    } else {
                        isChannelled = false;
                        isFlowed = false;
                    }

                    // update tick durations
                    iTicks = Math.max(iTicks - 1, 0);
                    attackSurroundingTicks = Math.max(attackSurroundingTicks - 1, 0);
                    skill3Ticks = Math.max(skill3Ticks - 1, -1);
                    skill4Ticks = Math.max(skill4Ticks - 1, -1);
                }
            });
        });
    }

    @Override
    public InteractionResult attackEntity(Player player, Level world, InteractionHand hand, Entity target, @Nullable EntityHitResult hit) {
        if (!player.level.isClientSide && !player.isSpectator() && isFlowed) {
            if (!player.getMainHandItem().isEmpty()
                    && player.getMainHandItem().is(ItemInit.MITO_SWORD_SINGLE.get())
                    && player.getAttackStrengthScale(0F) == 1) {
                double range = 3;
                Predicate<Entity> selector = e -> e instanceof LivingEntity && e instanceof Enemy && !(e instanceof Player);
                List<Entity> targets = player.level.getEntities(target, new AABB(target.getX() - range, target.getY() - range, target.getZ() - range, target.getX() + range, target.getY() + range, target.getZ() + range), selector);
                for (Entity chainedTargets : targets) {
                    target.hurt(DamageSource.playerAttack(player), 4);
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void serverBasicAttack(ServerPlayer player) {
        if (isFlowed) {
            double range = 1.5;
            Predicate<Entity> selector = e -> e instanceof LivingEntity && !e.is(player);
            List<Entity> targets = player.level.getEntities(player, new AABB(player.getX() - range, player.getY() - range, player.getZ() - range, player.getX() + range, player.getY() + range, player.getZ() + range), selector);
            for (Entity target : targets) {
                target.hurt(DamageSource.playerAttack(player), 4);
            }
        }
    }

    @Override
    public void dodge(Player player, Vec3 directionVec) {
        float moveAmount = isChannelled ? 2F : 1F;
        Vec3 moveVec = directionVec.normalize().scale(moveAmount);
        player.setDeltaMovement(moveVec);
    }

    @Override
    public void powerJump(Player player) {
        float jumpHeight = isChannelled ? 1.5F : 0.8F;
        Vec3 moveVec = Vec3.ZERO.add(0,1,0).normalize().scale(jumpHeight).add(player.getDeltaMovement());
        player.setDeltaMovement(moveVec);
    }

    @Override
    public void castSkill(Player player, int skillCode) {
        int skillId = skillCode - (int) Math.pow(2, skillInputs - 1);
        switch (skillId) {
            case 0 -> skill0(player);
            case 1 -> skill1(player);
            case 2 -> skill2(player);
            case 3 -> skill3(player);
            case 4 -> skill4(player);
            case 5 -> skill5(player);
            case 6 -> skill6(player);
            case 7 -> skill7(player);
            default -> System.out.println("Shouldn't get here when casting skills");
        }
    }

    @Override
    protected void skill0(Player player) {
        // Ebb & Flow, water attacks
//        player.sweepAttack(); FOR REFERENCE
        if (!player.level.isClientSide) isFlowed = !isFlowed;
    }

    @Override
    protected void skill1(Player player) {
        // Channel, mana armour and enhanced movement
        if (!player.level.isClientSide) isChannelled = !isChannelled;
    }

    @Override
    protected void skill2(Player player) {
        // Rushing Tide, dash and slash
        player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
            if (battleMode.consumeMana(40)) {
                if (Minecraft.getInstance().level.isClientSide) {
                    Vec3 moveVec = player.getLookAngle();
                    float moveAmount = 3F;
                    if (moveVec.y > 0)  moveVec = moveVec.multiply(1,0,1);
                    moveVec = moveVec.normalize().scale(moveAmount).add(player.getDeltaMovement());
                    player.setDeltaMovement(moveVec);
                }
                player.setInvulnerable(true);
                iTicks = Math.max(iTicks, 5);
                attackSurroundingTicks = Math.max(attackSurroundingTicks, 10);
            }
        });
    }

    @Override
    protected void skill3(Player player) {
        // Cleave, 3 glaive slashes w large aoe
        player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
            if (!player.level.isClientSide && battleMode.consumeMana(30)) {
                hitResults = getPlayerPOVHitResult(player, 5.0F, 3.0F);
                skill3Ticks = 40;
                skillLockout = true;
            }
        });
    }

    private void skill4(Player player) {
        // Rapids, hitcount barrage
        player.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
            if (!player.level.isClientSide && battleMode.consumeMana(20)) {
                hitResults = getPlayerPOVHitResult(player, 5.0F, 1.0F);
                skill4Ticks = 40;
                skillLockout = true;
            }
        });
    }

    private void skill5(Player player) {
        // Retrace
    }

    private void skill6(Player player) {
        // Hydrolance
    }

    private void skill7(Player player) {
        // Judgement of altair, hitcount mark
        if (!player.level.isClientSide) {
            ModPackets.sendToPlayer(new EffectS2CPacket(EffectType.MITO_RETRACE_EFFECT, player.getPosition(0)), (ServerPlayer) player);
        }
    }

    @Override
    public void onAnimationSync(int id, int state) {
        if (state == ANIM_OPEN) {
            final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
            controller.clearAnimationCache();
        }
    }

    public class Renderer implements EquipmentRenderer {
        public void doRender(HumanoidModel<?> bipedModel, ItemStack stack, LivingEntity living, PoseStack ms, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            boolean armor = !living.getItemBySlot(EquipmentSlot.LEGS).isEmpty();
            ms.pushPose();
            bipedModel.body.translateAndRotate(ms);
            ms.scale(0.6F, 0.6F, 0.6F);
            ms.mulPose(Vector3f.XN.rotationDegrees(130.0F));
            ms.translate(-0.05, -3.5F, 0.4F);

            new MitoSingleSwordSheathRenderer().render(ItemInit.MITO_SWORD_SINGLE_SHEATH.get(), ms, buffers, light, stack);

            living.getCapability(BattleModeProvider.PLAYER_BATTLE_MODE).ifPresent(battleMode -> {
                if (!battleMode.isBattleMode) {
                    new MitoSingleSwordRenderer().render((MitoSingleSword) stack.getItem(), ms, buffers, light, stack);
                }
            });
            ms.popPose();
        }
    }

    public static List<EntityHitResult> getPlayerPOVHitResult(Player player, double range, double leniency) {
        List<EntityHitResult> entitiesHit = new ArrayList<>();
        Vec3 startPos = player.getEyePosition();
        Vec3 lookDir = player.getLookAngle().normalize();
        Vec3 lookLineVec = Vec3.ZERO.add(lookDir.scale(range));

        Predicate<Entity> selector = e -> e instanceof LivingEntity && !e.is(player);
        List<Entity> targets = player.level.getEntities(player, new AABB(player.getX() - range, player.getY() - range, player.getZ() - range, player.getX() + range, player.getY() + range, player.getZ() + range), selector);
        for (Entity e : targets) {
            Vec3 entityLineVec = e.getBoundingBox().getCenter().subtract(startPos);

            double distForward = entityLineVec.dot(lookLineVec) / range;
            Vec3 lookEntityCastVec = Vec3.ZERO.add(lookDir.scale(distForward));
            Vec3 distVec = lookEntityCastVec.subtract(entityLineVec);
            if (distForward < 0) {
                // the entity's center is behind us
                if (e.getBoundingBox().contains(startPos)) {
                    // the entity is in our camera
                    entitiesHit.add(new EntityHitResult(e));
                }
                continue;
            } else if (distForward > range) {
                // the entity is too far forward
                distVec = entityLineVec.subtract(lookEntityCastVec);
            }

            // the entity is in front of us

            if (distVec.distanceTo(Vec3.ZERO) < leniency) {
                entitiesHit.add(new EntityHitResult(e));
            } else {
                double xMargin = e.getBoundingBox().getXsize();
                double yMargin = e.getBoundingBox().getYsize();
                double zMargin = e.getBoundingBox().getZsize();
                double xDis = Math.abs(distVec.x);
                double yDis = Math.abs(distVec.y);
                double zDis = Math.abs(distVec.z);

                if ((xDis - xMargin <= 0) || (yDis - yMargin <= 0) || (zDis - zMargin <= 0)) {
                    entitiesHit.add(new EntityHitResult(e));
                }
            }
        }

        return entitiesHit;
    }
}