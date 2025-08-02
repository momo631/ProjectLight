package com.momosensei.project_light.sounds;


import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.momosensei.project_light.ProjectLight.MOD_ID;


public class PLSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, MOD_ID);

    public static RegistryObject<SoundEvent> register(String name, Supplier<SoundEvent> supplier){
        return SOUNDS.register(name, supplier);
    }
    public static void register(IEventBus modBus){
        SOUNDS.register(modBus);
    }

    public static final Supplier<SoundEvent> TwilightAttack = register("twilight_attack",
            () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MOD_ID, "twilight_attack"),16));

    public static final Supplier<SoundEvent> ExtraTwilightAttack = register("extra_twilight_attack",
            () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MOD_ID, "extra_twilight_attack"),16));



}
