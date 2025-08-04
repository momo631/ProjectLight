package com.momosensei.project_light.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;

public class AttackUtil {
    public AttackUtil() {
    }

    public static float getCooldownFunctionFloat(Player player, InteractionHand hand){
        return (float) getCooldownFunction(player,hand).getAsDouble();
    }
    public static DoubleSupplier getCooldownFunction(Player player, InteractionHand hand) {
        return () -> player.getAttackStrengthScale(0.5f);
    }

    public static float getCriticalFloat(Player player,float damageModifier){
        float d = getCooldownFunctionFloat(player, InteractionHand.MAIN_HAND);
        boolean fullyCharged = (0.2f + d * d * 0.8f) > 0.9f;
        boolean isCritical = fullyCharged && player.fallDistance > 0.0F && !player.onGround() && !player.onClimbable() && !player.isInWater() && !player.hasEffect(MobEffects.BLINDNESS) && !player.isPassenger() && !player.isSprinting();
        if (isCritical){
            return damageModifier;
        }else return 1f;
    }
    public static void OtherDamageHurt(Entity target, Player player, DamageSource source, float amount) {
        if (!(target instanceof LivingEntity living)) return;
        living.invulnerableTime=0;
        living.hurt(source,amount);
        living.invulnerableTime=0;
        String s = "dead_by";
        if (living.getHealth() <= 0.0F&&!living.getPersistentData().hasUUID(s)) {
            living.die(player.level().damageSources().playerAttack(player));
            living.getPersistentData().putUUID(s,player.getUUID());
        }
    }

    /**
     * 获取玩家前方长条形区域内的生物
     * @param player 玩家实体
     * @param distance 检测距离
     * @param width 检测宽度
     * @param height 检测高度
     * @return 符合条件的生物列表
     */
    public static List<Entity> getEntitiesInFrontOfPlayer(Player player, double distance, double width, double height) {
        // 获取玩家位置和视角方向
        Vec3 playerPos = player.position();
        Vec3 lookVec = player.getLookAngle();

        // 计算检测区域的中心点（玩家前方distance/2处）
        Vec3 center = playerPos.add(lookVec.scale(distance / 2));

        // 计算检测区域的边界框
        double halfWidth = width / 2;
        double halfHeight = height / 2;
        AABB detectionBox = new AABB(
                center.x - halfWidth, center.y - halfHeight, center.z - halfWidth,
                center.x + halfWidth, center.y + halfHeight, center.z + halfWidth
        );

        // 获取世界中的所有实体
        Level world = player.level();
        List<Entity> entities = world.getEntities(player, detectionBox);

        // 进一步筛选：只保留在玩家前方的实体
        entities.removeIf(entity -> {
            Vec3 vecToEntity = entity.position().subtract(playerPos);
            return vecToEntity.dot(lookVec) <= 0; // 排除玩家后方的实体
        });
        return entities;
    }
    public static List<LivingEntity> getEntitiesInLookDirection(Player player, double distance, double radius) {
        List<LivingEntity> entities = new ArrayList<>();
        Level world = player.level();

        // 获取玩家视线方向的向量
        Vec3 lookVec = player.getLookAngle();

        // 计算起点和终点
        Vec3 startPos = player.getEyePosition(1.0F);
        Vec3 endPos = startPos.add(lookVec.scale(distance));

        // 计算检测区域的AABB（轴向对齐边界框）
        AABB detectionBox = calculateDetectionBox(startPos, endPos, radius);

        // 获取边界框内的所有实体
        List<Entity> allEntities = world.getEntities(player, detectionBox);

        // 筛选出生物实体
        for (Entity entity : allEntities) {
            if (entity instanceof LivingEntity && !entity.isSpectator()) {
                // 进一步检查实体是否在圆柱形区域内
                if (isInCylinder(startPos, endPos, radius, entity.getBoundingBox())) {
                    entities.add((LivingEntity) entity);
                }
            }
        }
        return entities;
    }

    /**
     * 计算检测区域的AABB
     */
    private static AABB calculateDetectionBox(Vec3 start, Vec3 end, double radius) {
        double minX = Math.min(start.x, end.x) - radius;
        double minY = Math.min(start.y, end.y) - radius;
        double minZ = Math.min(start.z, end.z) - radius;
        double maxX = Math.max(start.x, end.x) + radius;
        double maxY = Math.max(start.y, end.y) + radius;
        double maxZ = Math.max(start.z, end.z) + radius;
        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * 检查实体是否在圆柱形区域内
     */
    private static boolean isInCylinder(Vec3 start, Vec3 end, double radius, AABB entityBox) {
        Vec3 lineVec = end.subtract(start);
        double lineLength = lineVec.length();
        Vec3 lineDir = lineVec.normalize();

        // 检查实体包围盒的8个角点是否在圆柱内
        for (int x = 0; x <= 1; x++) {
            for (int y = 0; y <= 1; y++) {
                for (int z = 0; z <= 1; z++) {
                    Vec3 corner = new Vec3(
                            x == 0 ? entityBox.minX : entityBox.maxX,
                            y == 0 ? entityBox.minY : entityBox.maxY,
                            z == 0 ? entityBox.minZ : entityBox.maxZ
                    );

                    Vec3 cornerToStart = corner.subtract(start);
                    double projection = cornerToStart.dot(lineDir);

                    // 检查投影是否在线段范围内
                    if (projection < 0 || projection > lineLength) {
                        continue;
                    }

                    // 计算点到线的距离
                    Vec3 closestPoint = start.add(lineDir.scale(projection));
                    double distanceSq = closestPoint.distanceToSqr(corner);

                    if (distanceSq <= radius * radius) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}