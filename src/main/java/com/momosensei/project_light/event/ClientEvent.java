package com.momosensei.project_light.event;

import com.momosensei.project_light.entity.render.ParadiseLostAttackRenderer;
import com.momosensei.project_light.register.PLEntities;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(PLEntities.paradise_lost_attack_entity.get(), ParadiseLostAttackRenderer::new);
	}
}
