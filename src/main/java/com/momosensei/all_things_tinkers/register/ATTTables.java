package com.momosensei.all_things_tinkers.register;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public class ATTTables extends ATTMod {
    public static final RegistryObject<CreativeModeTab> tabTables = CREATIVE_TABS.register(
            "materials", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.all_things_tinkers.materials"))
                    .icon(() -> new ItemStack(ATTItem.laomo.get()))
                    .displayItems(ATTTables::addTabItems)
                    .build());

    public static Item.Properties material() {
        return new Item.Properties();
    }

    public static Item.Properties material(int stackSize) {
        return material().stacksTo(stackSize);
    }

    private static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        output.accept(ATTItem.laomo.get());

    }
}