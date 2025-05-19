package com.momosensei.all_things_tinkers;


import com.momosensei.all_things_tinkers.register.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

import static slimeknights.tconstruct.TConstruct.makeTranslationKey;

@Mod(AllThingsTinkers.MOD_ID)
@Mod.EventBusSubscriber(
        bus = Mod.EventBusSubscriber.Bus.MOD
)

public class AllThingsTinkers {
    public static final String MOD_ID = "all_things_tinkers"; //是你的模组名，需要英文
    public AllThingsTinkers() {
        FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
        IEventBus eventBus = context.getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ATTItem.ITEMS.register(eventBus);
        ATTModifiers.MODIFIERS.register(eventBus);
        ATTFluid.FLUIDS.register(eventBus);
        ATTBlock.BLOCK.register(eventBus);
        ATTEffects.EFFECT.register(eventBus);
        ATTEntities.ENTITIES.register(eventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ATTConfig.spec);
        ATTTables.initRegisters();
    }
    //Resourcelocation
    public static ResourceLocation getResource(String id) {
        return new ResourceLocation("all_things_tinkers", id);
    }

    public static ResourceLocation getResourceLocation(String id) {
        return new ResourceLocation(id);
    }

    public static <T> TinkerDataCapability.TinkerDataKey<T> createKey(String name) {
        return TinkerDataCapability.TinkerDataKey.of(getResource(name));
    }

    //生成键名用的
    public static String makeDescriptionId(String type, String name) {
        return type + ".all_things_tinkers." + name;
    }

    public static MutableComponent makeTranslation(String base, String name) {
        return Component.translatable(makeTranslationKey(base, name));
    }
}
