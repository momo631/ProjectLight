package com.momosensei.project_light.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class MultiModelBakedWrapper implements BakedModel {
    private final BakedModel bakedModelDefault;
    private final BakedModel bakedModelHand;

    public MultiModelBakedWrapper(BakedModel bakedModelDefault, BakedModel bakedModelHand) {
        this.bakedModelDefault = bakedModelDefault;
        this.bakedModelHand = bakedModelHand;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand) {
        return bakedModelDefault.getQuads(state, side, rand);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return bakedModelDefault.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return bakedModelDefault.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return bakedModelDefault.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return bakedModelDefault.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return bakedModelDefault.getParticleIcon();
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return bakedModelDefault.getOverrides();
    }

    @Override
    public @NotNull BakedModel applyTransform(@NotNull ItemDisplayContext transformType, @NotNull PoseStack poseStack, boolean applyLeftHandTransform) {
        BakedModel modelToUse = bakedModelDefault;

        // 判断是否为手持场景
        if (isHandTransform(transformType)) {
            modelToUse = bakedModelHand;
        }

        return modelToUse.applyTransform(transformType, poseStack, applyLeftHandTransform);
    }

    @Override
    public @NotNull ItemTransforms getTransforms() {
        return bakedModelDefault.getTransforms();
    }

    // 判断是否为手持变换类型
    private boolean isHandTransform(ItemDisplayContext transformType) {
        return transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND ||
                transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND ||
                transformType == ItemDisplayContext.THIRD_PERSON_LEFT_HAND ||
                transformType == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }
}
