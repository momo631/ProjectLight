package com.momosensei.project_light.register;

import com.momosensei.project_light.items.*;
import com.momosensei.project_light.items.censored_ego;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.momosensei.project_light.ProjectLight.MOD_ID;

public class PLItem {
    public PLItem() {
    }
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static final RegistryObject<Item> laomo = ITEMS.register("laomo", () -> new Item(PLTables.items()));

    public static final RegistryObject<twilight_ego> twilight_ego = ITEMS.register("twilight_ego", () -> new twilight_ego(PLTables.items()));

    public static final RegistryObject<Item> justitia_ego = ITEMS.register("justitia_ego", () -> new justitia_ego(PLTables.items()));
    public static final RegistryObject<Item> smile_ego = ITEMS.register("smile_ego", () -> new smile_ego(PLTables.items()));
    public static final RegistryObject<Item> paradise_lost_ego = ITEMS.register("paradise_lost_ego", () -> new paradise_lost_ego(PLTables.items()));
    public static final RegistryObject<Item> censored_ego = ITEMS.register("censored_ego", () -> new censored_ego(PLTables.items()));
    public static final RegistryObject<Item> apostles_sickle = ITEMS.register("apostles_sickle", () -> new apostles_sickle(PLTables.items()));
    public static final RegistryObject<Item> apostles_staff = ITEMS.register("apostles_staff", () -> new apostles_staff(PLTables.items()));
    public static final RegistryObject<Item> apostles_spear = ITEMS.register("apostles_spear", () -> new apostles_spear(PLTables.items()));

    //public static final RegistryObject<BlockItem> Laomo_block = ITEMS.register("laomo_block", () -> new BlockItem(PLBlock.Laomo_block.get(), new Item.Properties()));

}