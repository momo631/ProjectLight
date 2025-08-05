package com.momosensei.project_light.entity.model;


import com.momosensei.project_light.entity.entity.ParadiseLostAttackEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.momosensei.project_light.ProjectLight.MOD_ID;


public class ParadiseLostAttackModel extends GeoModel<ParadiseLostAttackEntity> {
	private final ResourceLocation model = new ResourceLocation(MOD_ID, "geo/paradise_lost_attack_entity.geo.json");
	private final ResourceLocation texture = new ResourceLocation(MOD_ID, "textures/entity/paradise_lost_attack_entity.png");
	private final ResourceLocation animations = new ResourceLocation(MOD_ID, "animations/paradise_lost_attack_entity.animation.json");

	
	@Override
	public ResourceLocation getModelResource(ParadiseLostAttackEntity paradise_lost_attack_entity) {
		return model;
	}

	@Override
	public ResourceLocation getTextureResource(ParadiseLostAttackEntity paradise_lost_attack_entity) {
		return texture;
	}

	@Override
	public ResourceLocation getAnimationResource(ParadiseLostAttackEntity paradise_lost_attack_entity) {
		return animations;
	}

}