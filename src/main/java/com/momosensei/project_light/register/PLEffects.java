package com.momosensei.project_light.register;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.momosensei.project_light.ProjectLight.MOD_ID;

public class PLEffects {
    public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MOD_ID);


    //public static final RegistryObject<MobEffect> HongWen = EFFECT.register("hongwen", HongWen::new);

}
