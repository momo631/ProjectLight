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
            () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MOD_ID, "twilight_attack"),12));
    public static final Supplier<SoundEvent> ExtraTwilightAttack = register("extra_twilight_attack",
            () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MOD_ID, "extra_twilight_attack"),10));

    public static final Supplier<SoundEvent> JustitiaAttack = register("justitia_attack",
            () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MOD_ID, "justitia_attack"),10));
    public static final Supplier<SoundEvent> ExtraJustitiaAttack = register("extra_justitia_attack",
            () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MOD_ID, "extra_justitia_attack"),10));

    public static final Supplier<SoundEvent> SmileAttack = register("smile_attack",
            () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MOD_ID, "smile_attack"),10));
    public static final Supplier<SoundEvent> ExtraSmileAttack = register("extra_smile_attack",
            () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MOD_ID, "extra_smile_attack"),10));

    public static final Supplier<SoundEvent> CensoredAttack = register("censored_attack",
            () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MOD_ID, "censored_attack"),10));
    public static final Supplier<SoundEvent> ExtraCensoredAttack = register("extra_censored_attack",
            () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MOD_ID, "extra_censored_attack"),14));

}
