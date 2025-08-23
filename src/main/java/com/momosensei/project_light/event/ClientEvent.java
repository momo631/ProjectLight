package com.momosensei.project_light.event;

import com.momosensei.project_light.entity.render.ParadiseLostAttackRenderer;
import com.momosensei.project_light.register.PLEntities;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import static com.momosensei.project_light.ProjectLight.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(PLEntities.paradise_lost_attack_entity.get(), ParadiseLostAttackRenderer::new);
	}
	@SubscribeEvent
	public static void onRegisterModels(ModelEvent.RegisterAdditional modelRegistryEvent) {
		for (String item : CustomBakedModel.HAND_MODEL_ITEMS) {
			modelRegistryEvent.register(new ModelResourceLocation(new ResourceLocation(MOD_ID, item+ "_in_hand"), "inventory"));
		}
	}
}
