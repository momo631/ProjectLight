package com.momosensei.project_light.event;


import com.momosensei.project_light.network.Channel;
import com.momosensei.project_light.register.PLItem;
import com.momosensei.project_light.util.SetupAnimation;
import dev.kosmx.playerAnim.api.AnimUtils;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;

import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber
public class UseitemAnimatonEvent {
	@SubscribeEvent
	public static void onUseItemStart(LivingEntityUseItemEvent.Finish event) {
		if (event != null && event.getEntity() != null) {
			ItemStack stack= event.getEntity().getItemBySlot(EquipmentSlot.MAINHAND);

			if (stack.is(PLItem.twilight_ego.get())) {
				UseitemAnimaton(event.getEntity().level(), "animation.twilight", event.getEntity());
			}else
			if (stack.is(PLItem.justitia_ego.get())) {
				UseitemAnimaton(event.getEntity().level(), "animation.justitia", event.getEntity());
			}else
			if (stack.is(PLItem.smile_ego.get())) {
				UseitemAnimaton(event.getEntity().level(), "animation.smile", event.getEntity());
			}else
			if (stack.is(PLItem.censored_ego.get())) {
				UseitemAnimaton(event.getEntity().level(), "animation.censored", event.getEntity());
			}


		}
	}

	public static void UseitemAnimaton(LevelAccessor world, String anim, Entity entity) {
		if (entity == null)
			return;
		if (world.isClientSide()) {
			SetupAnimation.setAnimationClientside((Player) entity, anim, true);
		}
		if (!world.isClientSide()) {
			if (entity instanceof Player && world instanceof ServerLevel srvLvl_) {
				List<Connection> connections = srvLvl_.getServer().getConnection().getConnections();
				synchronized (connections) {
					Iterator<Connection> iterator = connections.iterator();
					while (iterator.hasNext()) {
						Connection connection = iterator.next();
						if (!connection.isConnecting() && connection.isConnected())
							Channel.PACKET_HANDLER.sendTo(new SetupAnimation.AnimationMessage(Component.literal(anim), entity.getId(), true), connection, NetworkDirection.PLAY_TO_CLIENT);
					}
				}
			}
		}

	}
}
