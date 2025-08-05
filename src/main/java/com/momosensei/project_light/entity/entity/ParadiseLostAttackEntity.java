package com.momosensei.project_light.entity.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Random;


public class ParadiseLostAttackEntity extends Projectile implements GeoEntity {
	private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");

	private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	private final float randomRotation = new Random().nextFloat() * 360f;

	public float getRandomRotation() {
		return randomRotation;
	}
	public ParadiseLostAttackEntity(EntityType<? extends Projectile> p_37248_, Level p_37249_) {
		super(p_37248_, p_37249_);
	}

	public static void init() {
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return geoCache;
	}

	@Override
	public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "idle", 0, this::idleAnimController));
	}

	private PlayState idleAnimController(AnimationState<ParadiseLostAttackEntity> state) {
		return state.setAndContinue(IDLE_ANIM);
	}

	@Override
	public void defineSynchedData() {
    }
	@Override
	public void tick() {
		super.tick();
		if (this.tickCount >= 21) {
			this.discard();
		}
		List<Entity> ls0 = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox());
		for (Entity entity1 : ls0) {
			if (entity1!=this&&entity1 instanceof ParadiseLostAttackEntity){
				this.discard();
			}
		}
	}
}
