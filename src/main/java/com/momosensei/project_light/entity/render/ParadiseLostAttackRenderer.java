package com.momosensei.project_light.entity.render;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.momosensei.project_light.entity.entity.ParadiseLostAttackEntity;
import com.momosensei.project_light.entity.model.ParadiseLostAttackModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Random;

import static net.minecraft.client.gui.components.toasts.Toast.TEXTURE;

public class ParadiseLostAttackRenderer extends GeoEntityRenderer<ParadiseLostAttackEntity> {
	public ParadiseLostAttackRenderer(EntityRendererProvider.Context context) {
		super(context, new ParadiseLostAttackModel());
	}

	@Override
	public void preRender(PoseStack poseStack, ParadiseLostAttackEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

		poseStack.mulPose(Axis.YP.rotationDegrees(entity.getRandomRotation()));
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

	}

}
