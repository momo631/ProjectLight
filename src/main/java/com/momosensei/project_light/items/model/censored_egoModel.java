package com.momosensei.project_light.items.model;

import com.momosensei.project_light.items.censored_ego;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.momosensei.project_light.ProjectLight.MOD_ID;


public class censored_egoModel extends GeoModel<censored_ego> {
	@Override
	public ResourceLocation getAnimationResource(censored_ego animatable) {
		return new ResourceLocation(MOD_ID, "animations/censored_ego.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(censored_ego animatable) {
		return new ResourceLocation(MOD_ID, "geo/censored_ego.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(censored_ego animatable) {
		return new ResourceLocation(MOD_ID, "textures/item/ego/censored_ego/censored_ego.png");
	}
}
