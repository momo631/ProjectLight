package com.momosensei.project_light.event;

import com.momosensei.project_light.register.PLDamageSource;
import com.momosensei.project_light.register.PLItem;
import com.momosensei.project_light.sounds.PLSounds;
import com.momosensei.project_light.util.AttackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
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

import java.util.*;

import static com.momosensei.project_light.event.UseitemAnimatonEvent.UseitemAnimaton;
import static com.momosensei.project_light.util.AttackUtil.*;
import static com.momosensei.project_light.util.PenetratingDamage.reflectionPenetratingDamage;

public class PLHurtEvent {
    public PLHurtEvent() {
        MinecraftForge.EVENT_BUS.addListener(this::OnLivingHurt);
        MinecraftForge.EVENT_BUS.addListener(this::OnCriticalHit);
        MinecraftForge.EVENT_BUS.addListener(this::OnPlayerTick);

        MinecraftForge.EVENT_BUS.addListener(this::onPlayerInteract);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerAttack);
        MinecraftForge.EVENT_BUS.addListener(this::onLeftClickEmpty);
        MinecraftForge.EVENT_BUS.addListener(this::onLeftClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::onMouseScrolling);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingSwapItems);
        MinecraftForge.EVENT_BUS.addListener(this::onMovementInputUpdate);
        MinecraftForge.EVENT_BUS.addListener(this::onKeyInput);
    }
    private float damageModifier;
    private boolean ExtraAttack;
    private boolean SpecialAttack;
    private void OnCriticalHit(CriticalHitEvent event) {
        if (event.getTarget() != null) {
            damageModifier = event.getDamageModifier();
        }
    }
    public void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.isCancelable() && (getExtraAttack()||getSpecialAttack())) {
            event.setCanceled(true);
        }
    }
    public void onPlayerAttack(AttackEntityEvent event) {
        if (event.isCancelable() && (getExtraAttack()||getSpecialAttack())) {
            event.setCanceled(true);
        }
    }
    public void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.isCancelable() && (getExtraAttack()||getSpecialAttack())) {
            event.setCanceled(true);
        }
        Player player=event.getEntity();
        ItemStack stack=player.getMainHandItem();
        if (stack.is(PLItem.paradise_lost_ego.get())) {
            CompoundTag c = stack.getOrCreateTag();
            String s = "paradise_lost_attack";
            if (stack.is(PLItem.paradise_lost_ego.get())) {
                if (c.getInt(s)==0) {
                    UseitemAnimaton(player.level(), "animation.paradise_lost.attack", player);
                    c.putInt(s, 60);
                }
            }
        }
    }
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.isCancelable() && (getExtraAttack()||getSpecialAttack())) {
            event.setCanceled(true);
        }
        Player player=event.getEntity();
        ItemStack stack=player.getMainHandItem();
        if (stack.is(PLItem.paradise_lost_ego.get())) {
            CompoundTag c = stack.getOrCreateTag();
            String s = "paradise_lost_attack";
            if (stack.is(PLItem.paradise_lost_ego.get())) {
                if (c.getInt(s)==0) {
                    UseitemAnimaton(player.level(), "animation.paradise_lost.attack", player);
                    c.putInt(s, 60);
                }
            }
        }
    }
    public void onMouseScrolling(InputEvent.MouseScrollingEvent event) {
        if (event.isCancelable() &&(getExtraAttack()||getSpecialAttack())){
            event.setCanceled(true);
        }
    }
    public void onLivingSwapItems(LivingSwapItemsEvent event) {
        if (event.isCancelable()&&(getExtraAttack()||getSpecialAttack())) {
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
        if ((getExtraAttack()||getSpecialAttack())){
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
    public boolean getSpecialAttack() {
        return this.SpecialAttack;
    }
    public void setSpecialAttack(boolean specialAttack) {
        this.SpecialAttack = specialAttack;
    }

    private void OnLivingHurt(LivingHurtEvent event) {
        Entity a = event.getEntity();
        Entity b = event.getSource().getEntity();
        Random random = new Random();
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
                OtherDamageHurt(living,player,player.level().damageSources().magic(),e);
                OtherDamageHurt(living,player,player.level().damageSources().starve(),e);
                reflectionPenetratingDamage(a, player, f);
            }
            if (stack.is(PLItem.justitia_ego.get())) {
                living.playSound(PLSounds.JustitiaAttack.get());
                reflectionPenetratingDamage(a, player, f);
                reflectionPenetratingDamage(a, player, f);
                reflectionPenetratingDamage(a, player, f);
                reflectionPenetratingDamage(a, player, f);
                reflectionPenetratingDamage(a, player, f);
                event.setAmount(0);
                if (d>0.9&&player.getCooldowns().isOnCooldown(stack.getItem())&&random.nextInt(10)<=3){
                    player.getCooldowns().removeCooldown(stack.getItem());
                }
            }
            if (stack.is(PLItem.smile_ego.get())) {
                living.playSound(PLSounds.SmileAttack.get());
                living.invulnerableTime = 0;
                event.setAmount(0);
                if (player.level() instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(ParticleTypes.EXPLOSION, living.getX(), living.getY(), living.getZ(), 0, 0, 0, 0, 1);
                }
                List<LivingEntity> ls0 = player.level().getEntitiesOfClass(LivingEntity.class, living.getBoundingBox().inflate(1, 0.5, 1));
                for (LivingEntity targets : ls0) {
                    if (targets != player && targets != null) {
                        OtherDamageHurt(targets,player,player.level().damageSources().starve(),e);
                        targets.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 4));
                    }
                }
                if (d>0.9&&player.getCooldowns().isOnCooldown(stack.getItem())&&random.nextInt(10)<=2){
                    player.getCooldowns().removeCooldown(stack.getItem());
                }
            }
            if (stack.is(PLItem.censored_ego.get())) {
                living.playSound(PLSounds.CensoredAttack.get());
                living.invulnerableTime = 0;
                event.setAmount(0);
                OtherDamageHurt(living,player,player.level().damageSources().starve(),(e / c) * (c - 8));
                OtherDamageHurt(living,player,player.level().damageSources().starve(),(e / c) * (c - 8));
                if (d>0.9&&player.getCooldowns().isOnCooldown(stack.getItem())&&random.nextInt(10)==0){
                    player.getCooldowns().removeCooldown(stack.getItem());
                }
            }
            if (stack.is(PLItem.paradise_lost_ego.get())) {
                living.invulnerableTime = 0;
                event.setAmount(0);

            }
        }
        if (a instanceof Player player) {
            ItemStack stack = player.getMainHandItem();
            if (!stack.isEmpty() && stack.is(PLItem.censored_ego.get()) && !player.isDeadOrDying()) {
                float h =player.getHealth() + event.getAmount() * 0.4f;
                if (h > player.getMaxHealth()) h = player.getMaxHealth();
                player.setHealth(h);
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
                            OtherDamageHurt(a,player,PLDamageSource.playerAttack(player),e);
                        }else
                        if (tag.getInt(s) == 35){
                            OtherDamageHurt(a,player,PLDamageSource.playerAttack(player).setDamageType(DamageTypes.MAGIC),e);
                        }else
                        if (tag.getInt(s) == 23){
                            OtherDamageHurt(a,player,PLDamageSource.playerAttack(player).setDamageType(DamageTypes.STARVE),e);
                        }else
                        if (tag.getInt(s) == 9) {
                            float e1 = (c+ 8+random.nextInt(10));
                            float f1 = e * a.getMaxHealth() * 0.01f;
                            if (a.getMaxHealth() < 100) {
                                f1 = e;
                            }
                            reflectionPenetratingDamage(a, player, f);
                            OtherDamageHurt(a,player,PLDamageSource.playerAttack(player),e1);
                            OtherDamageHurt(a,player,PLDamageSource.playerAttack(player).setDamageType(DamageTypes.MAGIC),e1);
                            OtherDamageHurt(a,player,PLDamageSource.playerAttack(player).setDamageType(DamageTypes.STARVE),e1);
                            reflectionPenetratingDamage(a, player, f1);
                        }
                    }
                }
            }
        }else
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
                            reflectionPenetratingDamage(a, player, f);
                        }else
                        if (tag.getInt(s) == 47){
                            reflectionPenetratingDamage(a, player, f);
                        }else
                        if (tag.getInt(s) == 37){
                            reflectionPenetratingDamage(a, player, f);
                        }else
                        if (tag.getInt(s) == 14) {
                            reflectionPenetratingDamage(a, player, f1);
                        }
                        if (tag.getInt(s) == 12) {
                            reflectionPenetratingDamage(a, player, f1);
                        }
                        if (tag.getInt(s) == 10) {
                            reflectionPenetratingDamage(a, player, f1);
                        }
                        if (tag.getInt(s) == 8) {
                            reflectionPenetratingDamage(a, player, f1);
                        }
                        if (tag.getInt(s) == 6) {
                            reflectionPenetratingDamage(a, player, f1);
                        }
                        if (tag.getInt(s) == 4) {
                            reflectionPenetratingDamage(a, player, f1);
                        }
                        if (tag.getInt(s) == 2) {
                            reflectionPenetratingDamage(a, player, f1);
                        }
                    }
                }

            }
        }else
        if (stack.is(PLItem.smile_ego.get())){
            String s = "smile_extra_attack";
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
                Vec3 vec3 = new Vec3(player.getLookAngle().x(),0,player.getLookAngle().z()).scale(2f);
                List<LivingEntity> ls0 = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().expandTowards(player.getLookAngle().x(), player.getLookAngle().y(), player.getLookAngle().z()).inflate(2, 1, 2).move(vec3));
                for (LivingEntity a : ls0) {
                    if (a != player && a != null) {
                        float e = (c+ 23+random.nextInt(10));
                        float e1 = (c-8);
                        if (tag.getInt(s) == 15){
                            a.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,60,3));
                            OtherDamageHurt(a,player,PLDamageSource.playerAttack(player).setDamageType(DamageTypes.STARVE),e);
                        }else
                        if (tag.getInt(s) == 13) {
                            OtherDamageHurt(a,player,PLDamageSource.playerAttack(player).setDamageType(DamageTypes.STARVE),e1);
                        }else
                        if (tag.getInt(s) == 11) {OtherDamageHurt(a,player,PLDamageSource.playerAttack(player).setDamageType(DamageTypes.STARVE),e1);
                        }else
                        if (tag.getInt(s) == 9) {OtherDamageHurt(a,player,PLDamageSource.playerAttack(player).setDamageType(DamageTypes.STARVE),e1);
                        }else
                        if (tag.getInt(s) == 7) {OtherDamageHurt(a,player,PLDamageSource.playerAttack(player).setDamageType(DamageTypes.STARVE),e1);
                        }else
                        if (tag.getInt(s) == 5) {OtherDamageHurt(a,player,PLDamageSource.playerAttack(player).setDamageType(DamageTypes.STARVE),e1);
                        }
                    }
                }
                if ((tag.getInt(s)==15||tag.getInt(s)==13||tag.getInt(s)==11||tag.getInt(s)==9||tag.getInt(s)==7||tag.getInt(s)==5)&&player.level() instanceof ServerLevel serverLevel){
                    Vec3 vec = new Vec3(player.getLookAngle().x(),0,player.getLookAngle().z()).scale(2f);
                    serverLevel.sendParticles(ParticleTypes.EXPLOSION, player.getX()+vec.x, player.getY(), player.getZ()+vec.z, 1, 0, 0, 0, 1);
                }
            }
        }else
        if (stack.is(PLItem.censored_ego.get())){
            String s = "censored_extra_attack";
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
                List<LivingEntity> ls0 = AttackUtil.getEntitiesInLookDirection(player, 10, 1.2);
                for (LivingEntity a : ls0) {
                    if (a != player && a != null) {
                        float e = (c+ random.nextInt(15));
                        if (tag.getInt(s) == 10){
                            OtherDamageHurt(a,player,PLDamageSource.playerAttack(player).setDamageType(DamageTypes.STARVE),e);
                        }
                        if (tag.getInt(s) == 8){
                            OtherDamageHurt(a,player,PLDamageSource.playerAttack(player).setDamageType(DamageTypes.STARVE),e);
                        }
                        if (tag.getInt(s) == 6){
                            OtherDamageHurt(a,player,PLDamageSource.playerAttack(player).setDamageType(DamageTypes.STARVE),e);
                        }
                    }
                }
            }
        }
        if (stack.is(PLItem.paradise_lost_ego.get())) {
            String s = "paradise_lost_attack";
            if (tag.getInt(s)<0) {
                tag.putInt(s, 0);
            }else
            if (tag.getInt(s)==0&&getSpecialAttack()) {
                setSpecialAttack(false);
            }else
            if (tag.getInt(s)>0) {
                if (getMainHand()!=player.getInventory().selected) {
                    setMainHand(player.getInventory().selected);
                }
                setSpecialAttack(true);
                tag.putInt(s, tag.getInt(s) - 1);
                if (tag.getInt(s) == 38) {
                    handleAttackEffect(player, 40);
                    List<LivingEntity> ls0 = getTrueHostileAndNeutralMobs(player.level(),player,40);
                    for (LivingEntity a : ls0) {
                        if (a!=player &&a!=null) {
                            float e = (c + random.nextInt(6));
                            if (ls0.size() >= 3 && ls0.size() <= 6) e = (c - 3 + random.nextInt(4));
                            else if (ls0.size() >= 7) e = (c - 6 + random.nextInt(4));
                            float f = e * a.getMaxHealth() * 0.01f;
                            if (a.getMaxHealth() < 100) {
                                f = e;
                            }

                            OtherDamageHurt(a, player, player.level().damageSources().playerAttack(player), 1);
                            reflectionPenetratingDamage(a, player, f);
                            a.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 4));
                            float h = player.getHealth() + 2 + random.nextInt(2);
                            if (h > player.getMaxHealth()) h = player.getMaxHealth();
                            player.setHealth(h);
                        }
                    }
                }
            }
        }
    }

}
