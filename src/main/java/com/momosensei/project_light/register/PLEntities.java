package com.momosensei.project_light.register;

import com.momosensei.project_light.entity.entity.ParadiseLostAttackEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.momosensei.project_light.ProjectLight.MOD_ID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PLEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<EntityType<ParadiseLostAttackEntity>> paradise_lost_attack_entity =
            ENTITIES.register("paradise_lost_attack_entity",
                    () -> EntityType.Builder.of(ParadiseLostAttackEntity::new, MobCategory.MISC)
                            .sized(1.6f, 2.2f) // 碰撞箱大小 (宽, 高)
                            .clientTrackingRange(20) // 客户端追踪距离
                            .build("project_light:paradise_lost_attack_entity"));
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(ParadiseLostAttackEntity::init);
    }

}