package com.momosensei.all_things_tinkers.register;

import net.minecraftforge.common.ForgeConfigSpec;

public class ATTConfig {
    public static final ForgeConfigSpec.Builder builder=new ForgeConfigSpec.Builder().comment("Acquisition Setting")
            .push("Item");



    public static final ForgeConfigSpec spec=builder.pop().build();
}
