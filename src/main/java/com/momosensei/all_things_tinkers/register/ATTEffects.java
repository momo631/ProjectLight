package com.momosensei.all_things_tinkers.register;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.momosensei.all_things_tinkers.AllThingsTinkers.MOD_ID;

public class ATTEffects {
    public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MOD_ID);


    //public static final RegistryObject<MobEffect> HongWen = EFFECT.register("hongwen", HongWen::new);

}
