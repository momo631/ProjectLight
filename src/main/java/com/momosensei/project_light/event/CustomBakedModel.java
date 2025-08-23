package com.momosensei.project_light.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.momosensei.project_light.register.PLItem;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.momosensei.project_light.ProjectLight.MOD_ID;

@Mod.EventBusSubscriber(modid =MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class CustomBakedModel {
    public static final String[] HAND_MODEL_ITEMS = getHandModelItemNames();
    public static String[] getHandModelItemNames() {
        List<String> itemNames = new ArrayList<>();
        // 遍历所有已注册的物品
        for (RegistryObject<Item> item : PLItem.ITEMS.getEntries()) {
            ResourceLocation registryName = item.getId();
            String path = null;
            if (registryName != null) {
                path = registryName.getPath();
            }
            itemNames.add(path);
        }
        return itemNames.toArray(new String[0]);
    }

    @SubscribeEvent
    public static void onModelBakeEvent(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> map = event.getModels();

        for (String item : HAND_MODEL_ITEMS) {
            ResourceLocation modelInventory = new ModelResourceLocation(new ResourceLocation(MOD_ID, item), "inventory");
            ResourceLocation modelHand = new ModelResourceLocation(new ResourceLocation(MOD_ID, item+ "_in_hand"), "inventory");

            BakedModel bakedModelDefault = map.get(modelInventory);
            BakedModel bakedModelHand = map.get(modelHand);
            BakedModel modelWrapper = new MultiModelBakedWrapper(bakedModelDefault, bakedModelHand);
            map.put(modelInventory, modelWrapper);
        }
    }
}
