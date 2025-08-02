package com.momosensei.project_light.event;

import com.momosensei.project_light.register.PLDamageSource;
import com.momosensei.project_light.register.PLEntities;
import com.momosensei.project_light.register.PLItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

import java.util.List;

import static com.momosensei.project_light.util.AttackUtil.*;
import static com.momosensei.project_light.util.PenetratingDamage.reflectionPenetratingDamage;

public class PLHurtEvent {
    public PLHurtEvent() {
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
        MinecraftForge.EVENT_BUS.addListener(this::livingcriticalhitevent);
    }

    private float damageModifier;
    private void livingcriticalhitevent(CriticalHitEvent event) {
        if (event.getTarget() != null) {
            damageModifier=event.getDamageModifier();
        }
    }
    public float getDamageModifier() {
        return damageModifier;
    }

    private void livinghurtevent(LivingHurtEvent event) {
        Entity a = event.getEntity();
        Entity b = event.getSource().getEntity();
        if (b instanceof Player player&&!player.level().isClientSide()&&a instanceof LivingEntity living) {
            ItemStack stack = player.getMainHandItem();
            if (stack.isEmpty())return;
            float c = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float d = getCooldownFunctionFloat(player, InteractionHand.MAIN_HAND);
            float e = c * (0.2f + d * d * 0.8f) * getCriticalFloat(player,getDamageModifier());
            float f = e * living.getMaxHealth() * 0.01f;
            if (living.getMaxHealth() < 100) {
                f = e;
            }
            if (stack.is(PLItem.twilight_ego.get())) {
                living.invulnerableTime=0;
                living.hurt(player.level().damageSources().magic(), e);
                living.invulnerableTime=0;
                living.hurt(player.level().damageSources().starve(),e);
                living.invulnerableTime=0;
                reflectionPenetratingDamage(a, player, f);
                living.invulnerableTime=0;
            }
            if (stack.is(PLItem.justitia_ego.get())) {
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
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20,4));
                List<LivingEntity> ls0 = player.level().getEntitiesOfClass(LivingEntity.class, living.getBoundingBox().inflate(1, 0.5, 1));
                for (LivingEntity targets : ls0) {
                    if (targets != player&&targets!=null) {
                        living.invulnerableTime = 0;
                        targets.hurt(player.level().damageSources().starve(),e);
                        living.invulnerableTime = 0;
                        targets.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20,4));
                    }
                }
            }
            if (stack.is(PLItem.censored_ego.get())) {
                living.invulnerableTime = 0;
                event.setAmount(0);
                living.invulnerableTime = 0;
                living.hurt(player.level().damageSources().starve(),(e/c)*(c-8));
                living.invulnerableTime = 0;
                living.hurt(player.level().damageSources().starve(),(e/c)*(c-8));
                living.invulnerableTime = 0;
            }
            if (stack.is(PLItem.paradise_lost_ego.get())) {
                living.invulnerableTime = 0;
                event.setAmount(0);
                living.invulnerableTime = 0;
            }
        }
        if (a instanceof Player player&&!player.level().isClientSide()) {
            ItemStack stack = player.getMainHandItem();
            if (!stack.isEmpty()&&stack.is(PLItem.censored_ego.get())&&!player.isDeadOrDying()) {
                player.heal(event.getAmount()*0.4f);
            }
        }
    }
}
