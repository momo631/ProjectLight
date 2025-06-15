package com.momosensei.all_things_tinkers.register;

import com.momosensei.all_things_tinkers.AllThingsTinkers;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.FluidObject;

import static com.momosensei.all_things_tinkers.AllThingsTinkers.MOD_ID;
import static slimeknights.tconstruct.fluids.block.BurningLiquidBlock.createBurning;

public class ATTFluid {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MOD_ID);
    private static FluidType.Properties hot(String name,int temp,boolean gas) {
        return FluidType.Properties.create().density(gas?-2000:2000).viscosity(10000).temperature(temp)
                .descriptionId(AllThingsTinkers.makeDescriptionId("fluid", name))
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_FILL)
                .motionScale(0.0023333333333333335D)
                .canSwim(false).canDrown(false)
                .pathType(BlockPathTypes.LAVA).adjacentPathType(null);
    }

    private static FluidType.Properties cool(String name) {
        return cool().descriptionId(AllThingsTinkers.makeDescriptionId("fluid", name)).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_FILL);
    }

    private static FluidType.Properties cool() {
        return FluidType.Properties.create().sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_FILL);
    }

    private static FlowingFluidObject<ForgeFlowingFluid> register(String name, int temp,int lightLevel,boolean gas) {
        return FLUIDS.register(name).type(hot(name,temp,gas)).block(createBurning(MapColor.COLOR_GRAY, lightLevel,5,0)).bucket().flowing();
    }

    //public static final FluidObject<ForgeFlowingFluid> molten_laomo = register("molten_laomo", 5867,10,false);
    public static final FluidObject<ForgeFlowingFluid> molten_redstone = register("molten_redstone", 532,10,false);
    public static final FluidObject<ForgeFlowingFluid> molten_glowstone = register("molten_glowstone", 532,10,false);

}
