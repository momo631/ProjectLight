package com.momosensei.project_light.register;

import net.minecraftforge.common.ForgeConfigSpec;

public class PLConfig {
    public static final ForgeConfigSpec.Builder builder=new ForgeConfigSpec.Builder().comment("Acquisition Setting")
            .push("Item");



    public static final ForgeConfigSpec spec=builder.pop().build();
}
