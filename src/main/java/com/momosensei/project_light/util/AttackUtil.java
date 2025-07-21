package com.momosensei.project_light.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.function.DoubleSupplier;

public class AttackUtil {
    public static void executeall(LevelAccessor world, double x, double y, double z, LivingEntity damager) {
        if (damager instanceof Player player) {
            if (!damager.getCommandSenderWorld().isClientSide) {
                Vec3 vec3 = new Vec3(x, y, z);
                List<LivingEntity> list = world.getEntitiesOfClass(LivingEntity.class, (new AABB(vec3, vec3)).inflate(200.0F), (e) -> true).stream().sorted(Comparator.comparingDouble((_entcnd) -> _entcnd.distanceToSqr(vec3))).toList();
                for (LivingEntity entity : list) {
                    PenetratingDamage.reflectionPenetratingDamage(entity,player, entity.getMaxHealth());
                    entity.onRemovedFromWorld();
                    entity.setPos(Double.NaN, Double.NaN, Double.NaN);
                }
            }
        }
    }

    public static float getCooldownFunctionFloat(Player player, InteractionHand hand){
        return (float) getCooldownFunction(player,hand).getAsDouble();
    }
    public static DoubleSupplier getCooldownFunction(Player player, InteractionHand hand) {
        return () -> player.getAttackStrengthScale(0.5f);
    }
}
