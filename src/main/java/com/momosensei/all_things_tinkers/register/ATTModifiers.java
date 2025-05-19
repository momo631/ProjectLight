package com.momosensei.all_things_tinkers.register;

import com.momosensei.all_things_tinkers.modifiers.modifiers.Test;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.momosensei.all_things_tinkers.AllThingsTinkers.MOD_ID;

public class ATTModifiers {

    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MOD_ID);

//  public static final StaticModifier<LaoMoChuJi> laomochuji=MODIFIERS.register("laomochuji", LaoMoChuJi::new);   //测试
    public static final StaticModifier<Test> test=MODIFIERS.register("test", Test::new);

}
