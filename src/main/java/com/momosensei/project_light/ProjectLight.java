package com.momosensei.project_light;


import com.momosensei.project_light.event.PLHurtEvent;
import com.momosensei.project_light.register.*;
import com.momosensei.project_light.sounds.PLSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib.GeckoLib;

import static com.momosensei.project_light.ProjectLight.MOD_ID;

@Mod(MOD_ID)
@Mod.EventBusSubscriber(modid =MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD
)

public class ProjectLight {
    public static final String MOD_ID = "project_light"; //是你的模组名，需要英文
    public ProjectLight() {
        FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
        IEventBus eventBus = context.getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        PLItem.ITEMS.register(eventBus);
        PLTables.CREATIVE_TAB.register(eventBus);
        PLBlock.BLOCK.register(eventBus);
        PLEffects.EFFECT.register(eventBus);
        PLEntities.ENTITIES.register(eventBus);
        PLSounds.SOUNDS.register(eventBus);

        MinecraftForge.EVENT_BUS.register(new PLHurtEvent());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PLConfig.spec);

        GeckoLib.initialize();
    }

    //Resourcelocation
    public static ResourceLocation getResource(String id) {
        return new ResourceLocation("project_light", id);
    }

    public static ResourceLocation getResourceLocation(String id) {
        return new ResourceLocation(id);
    }

    //生成键名用的
    public static String makeDescriptionId(String type, String name) {
        return type + ".project_light." + name;
    }

}
