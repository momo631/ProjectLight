package com.momosensei.project_light.event;

import com.momosensei.project_light.register.PLItem;
import com.momosensei.project_light.sounds.PLSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSwapItemsEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Random;

import static com.momosensei.project_light.util.AttackUtil.*;
import static com.momosensei.project_light.util.PenetratingDamage.reflectionPenetratingDamage;

public class PLHurtEvent {
    public PLHurtEvent() {
        MinecraftForge.EVENT_BUS.addListener(this::OnLivingHurt);
        MinecraftForge.EVENT_BUS.addListener(this::OnCriticalHit);
        MinecraftForge.EVENT_BUS.addListener(this::OnPlayerTick);

        MinecraftForge.EVENT_BUS.addListener(this::onPlayerInteract);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerAttack);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerLeftClick);
        MinecraftForge.EVENT_BUS.addListener(this::onLeftClickEmpty);
        MinecraftForge.EVENT_BUS.addListener(this::onLeftClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::onMouseScrolling);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingSwapItems);
        MinecraftForge.EVENT_BUS.addListener(this::onMovementInputUpdate);
        MinecraftForge.EVENT_BUS.addListener(this::onKeyInput);
    }
    private float damageModifier;
    private boolean ExtraAttack;
    private void OnCriticalHit(CriticalHitEvent event) {
        if (event.getTarget() != null) {
            damageModifier = event.getDamageModifier();
        }
    }
    public void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.isCancelable() && getExtraAttack()) {
            event.setCanceled(true);
        }
    }
    public void onPlayerAttack(AttackEntityEvent event) {
        if (event.isCancelable() && getExtraAttack()) {
            event.setCanceled(true);
        }
    }
    public void onPlayerLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        if (event.isCancelable() && getExtraAttack()) {
            event.setCanceled(true);
        }
    }
    public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.isCancelable() && getExtraAttack()) {
            event.setCanceled(true);
        }
    }
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.isCancelable() && getExtraAttack()) {
            event.setCanceled(true);
        }
    }
    public void onMouseScrolling(InputEvent.MouseScrollingEvent event) {
        if (event.isCancelable() &&getExtraAttack()){
            event.setCanceled(true);
        }
    }
    public void onLivingSwapItems(LivingSwapItemsEvent event) {
        if (event.isCancelable()&&getExtraAttack()) {
            event.setCanceled(true);
        }
    }
    public void onMovementInputUpdate(MovementInputUpdateEvent event) {
        if (getExtraAttack()) {
            event.getInput().forwardImpulse = 0;
            event.getInput().leftImpulse = 0;
            event.getInput().jumping = false;
            event.getInput().shiftKeyDown = false;
        }
    }
    private int MainHand;
    public void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        if (getExtraAttack()){
            int key=event.getAction();
            if (key >= GLFW.GLFW_KEY_1 && key <= GLFW.GLFW_KEY_9) {
                event.setCanceled(true);
            }
            mc.player.getInventory().selected = getMainHand();
        }
    }

    public int getMainHand() {
        return this.MainHand;
    }
    public void setMainHand(int MainHand) {
        this.MainHand = MainHand;
    }
    public float getDamageModifier() {
        return damageModifier;
    }
    public boolean getExtraAttack() {
        return this.ExtraAttack;
    }
    public void setExtraAttack(boolean extraAttack) {
        this.ExtraAttack = extraAttack;
    }


    private void OnLivingHurt(LivingHurtEvent event) {
        Entity a = event.getEntity();
        Entity b = event.getSource().getEntity();
        if (b instanceof Player player  && a instanceof LivingEntity living&&!event.isCanceled()) {
            ItemStack stack = player.getMainHandItem();
            if (stack.isEmpty()) return;
            if (getExtraAttack())return;
            float c = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float d = getCooldownFunctionFloat(player, InteractionHand.MAIN_HAND);
            float e = c * (0.2f + d * d * 0.8f) * getCriticalFloat(player, getDamageModifier());
            float f = e * living.getMaxHealth() * 0.01f;
            if (living.getMaxHealth() < 100) {
                f = e;
            }
            if (stack.is(PLItem.twilight_ego.get())) {
                living.playSound(PLSounds.TwilightAttack.get());

                living.invulnerableTime = 0;
                living.hurt(player.level().damageSources().magic(), e);
                living.invulnerableTime = 0;
                living.hurt(player.level().damageSources().starve(), e);
                living.invulnerableTime = 0;
                reflectionPenetratingDamage(a, player, f);
                living.invulnerableTime = 0;
            }
            if (stack.is(PLItem.justitia_ego.get())) {
                living.playSound(PLSounds.JustitiaAttack.get());

                living.invulnerableTime = 0;
                reflectionPenetratingDamage(a, player, f);
                living.invulnerableTime = 0;
                reflectionPenetratingDamage(a, player, f);
                living.invulnerableTime = 0;
                reflectionPenetratingDamage(a, player, f);
                living.invulnerableTime = 0;
                reflectionPenetratingDamage(a, player, f);
                living.invulnerableTime = 0;
                reflectionPenetratingDamage(a, player, f);
                living.invulnerableTime = 0;
                event.setAmount(0);
                living.invulnerableTime = 0;
            }
            if (stack.is(PLItem.smile_ego.get())) {
                living.invulnerableTime = 0;
                event.setAmount(0);
                living.invulnerableTime = 0;
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 4));
                List<LivingEntity> ls0 = player.level().getEntitiesOfClass(LivingEntity.class, living.getBoundingBox().inflate(1, 0.5, 1));
                for (LivingEntity targets : ls0) {
                    if (targets != player && targets != null) {
                        living.invulnerableTime = 0;
                        targets.hurt(player.level().damageSources().starve(), e);
                        living.invulnerableTime = 0;
                        targets.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 4));
                    }
                }
            }
            if (stack.is(PLItem.censored_ego.get())) {
                living.invulnerableTime = 0;
                event.setAmount(0);
                living.invulnerableTime = 0;
                living.hurt(player.level().damageSources().starve(), (e / c) * (c - 8));
                living.invulnerableTime = 0;
                living.hurt(player.level().damageSources().starve(), (e / c) * (c - 8));
                living.invulnerableTime = 0;
            }
            if (stack.is(PLItem.paradise_lost_ego.get())) {
                living.invulnerableTime = 0;
                event.setAmount(0);
                living.invulnerableTime = 0;
            }
        }
        if (a instanceof Player player && !player.level().isClientSide()) {
            ItemStack stack = player.getMainHandItem();
            if (!stack.isEmpty() && stack.is(PLItem.censored_ego.get()) && !player.isDeadOrDying()) {
                player.heal(event.getAmount() * 0.4f);
            }
        }
    }

    private void OnPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player=event.player;
        ItemStack stack = player.getMainHandItem();
        CompoundTag tag = stack.getOrCreateTag();
        Random random = new Random();
        if (player.level().isClientSide)return;
        if (stack.isEmpty()) return;
        float c = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);

        if (stack.is(PLItem.twilight_ego.get())){
            String s = "twilight_extra_attack";
            if (tag.getInt(s)<0) {
                tag.putInt(s, 0);
            }else
            if (tag.getInt(s)==0&&getExtraAttack()) {
                setExtraAttack(false);
            }else
            if (tag.getInt(s)>0) {
                if (getMainHand()!=player.getInventory().selected) {
                    setMainHand(player.getInventory().selected);
                }
                setExtraAttack(true);
                tag.putInt(s, tag.getInt(s) - 1);
                List<LivingEntity> ls0 = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().expandTowards(player.getLookAngle().x()*4, player.getLookAngle().y(), player.getLookAngle().z()*4).inflate(1, 2, 1));
                for (LivingEntity a : ls0) {
                    if (a != player && a != null) {
                        float e = (c+ 28+random.nextInt(20));
                        float f = e * a.getMaxHealth() * 0.01f;
                        if (a.getMaxHealth() < 100) {
                            f = e;
                        }
                        if (tag.getInt(s) == 47){
                            a.invulnerableTime = 0;
                            a.hurt(player.level().damageSources().lightningBolt(),e);
                            a.invulnerableTime = 0;
                        }else
                        if (tag.getInt(s) == 35){
                            a.invulnerableTime = 0;
                            a.hurt(player.level().damageSources().magic(),e);
                            a.invulnerableTime = 0;
                        }else
                        if (tag.getInt(s) == 23){
                            a.invulnerableTime = 0;
                            a.hurt(player.level().damageSources().starve(),e);
                            a.invulnerableTime = 0;
                        }else
                        if (tag.getInt(s) == 9) {
                            float e1 = (c+ 8+random.nextInt(10));
                            float f1 = e * a.getMaxHealth() * 0.01f;
                            if (a.getMaxHealth() < 100) {
                                f1 = e;
                            }
                            a.invulnerableTime = 0;
                            reflectionPenetratingDamage(a, player, f);
                            a.invulnerableTime = 0;
                            a.hurt(player.level().damageSources().lightningBolt(),e1);
                            a.invulnerableTime = 0;
                            a.hurt(player.level().damageSources().magic(),e1);
                            a.invulnerableTime = 0;
                            a.hurt(player.level().damageSources().starve(),e1);
                            a.invulnerableTime = 0;
                            reflectionPenetratingDamage(a, player, f1);
                        }
                    }
                }
            }
        }
        if (stack.is(PLItem.justitia_ego.get())){
            String s = "justitia_extra_attack";
            if (tag.getInt(s)<0) {
                tag.putInt(s, 0);
            }else
            if (tag.getInt(s)==0&&getExtraAttack()) {
                setExtraAttack(false);
            }else
            if (tag.getInt(s)>0) {
                if (getMainHand()!=player.getInventory().selected) {
                    setMainHand(player.getInventory().selected);
                }
                setExtraAttack(true);
                tag.putInt(s, tag.getInt(s) - 1);

                List<LivingEntity> ls0 = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().expandTowards(player.getLookAngle().x()*3, player.getLookAngle().y(), player.getLookAngle().z()*3).inflate(1, 2, 1));
                for (LivingEntity a : ls0) {
                    if (a != player && a != null) {
                        float e = (c+ 5+random.nextInt(3));
                        float f = e * a.getMaxHealth() * 0.01f;
                        if (a.getMaxHealth() < 100) {
                            f = e;
                        }
                        float e1 = (c+ random.nextInt(2));
                        float f1 = e1 * a.getMaxHealth() * 0.01f;
                        if (a.getMaxHealth() < 100) {
                            f1 = e;
                        }
                        if (tag.getInt(s) == 57){
                            a.invulnerableTime = 0;
                            reflectionPenetratingDamage(a, player, f);
                        }else
                        if (tag.getInt(s) == 47){
                            a.invulnerableTime = 0;
                            reflectionPenetratingDamage(a, player, f);
                        }else
                        if (tag.getInt(s) == 37){
                            a.invulnerableTime = 0;
                            reflectionPenetratingDamage(a, player, f);
                        }else
                        if (tag.getInt(s) == 14) {
                            a.invulnerableTime = 0;
                            reflectionPenetratingDamage(a, player, f1);
                        }
                        if (tag.getInt(s) == 12) {
                            a.invulnerableTime = 0;
                            reflectionPenetratingDamage(a, player, f1);
                        }
                        if (tag.getInt(s) == 10) {
                            a.invulnerableTime = 0;
                            reflectionPenetratingDamage(a, player, f1);
                        }
                        if (tag.getInt(s) == 8) {
                            a.invulnerableTime = 0;
                            reflectionPenetratingDamage(a, player, f1);
                        }
                        if (tag.getInt(s) == 6) {
                            a.invulnerableTime = 0;
                            reflectionPenetratingDamage(a, player, f1);
                        }
                        if (tag.getInt(s) == 4) {
                            a.invulnerableTime = 0;
                            reflectionPenetratingDamage(a, player, f1);
                        }
                        if (tag.getInt(s) == 2) {
                            a.invulnerableTime = 0;
                            reflectionPenetratingDamage(a, player, f1);
                        }
                    }
                }

            }
        }

    }
}
