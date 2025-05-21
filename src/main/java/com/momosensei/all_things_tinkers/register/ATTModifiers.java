package com.momosensei.all_things_tinkers.register;

import com.momosensei.all_things_tinkers.modifiers.modifiers.*;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.momosensei.all_things_tinkers.AllThingsTinkers.MOD_ID;

public class ATTModifiers {

    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MOD_ID);

    public static final StaticModifier<Test> test=MODIFIERS.register("test", Test::new);
    public static final StaticModifier<SharpSword> sharpsword=MODIFIERS.register("sharpsword", SharpSword::new);
    public static final StaticModifier<SharpArrows> sharparrows=MODIFIERS.register("sharparrows", SharpArrows::new);
    public static final StaticModifier<Brittleness> brittleness=MODIFIERS.register("brittleness", Brittleness::new);
    public static final StaticModifier<PotionExtension> potionextension=MODIFIERS.register("potionextension", PotionExtension::new);

}
