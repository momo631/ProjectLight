package com.momosensei.project_light.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.momosensei.project_light.ProjectLight.MOD_ID;

public class PLTables {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final RegistryObject<CreativeModeTab> tabTables = CREATIVE_TAB.register(
            "items", () -> CreativeModeTab.builder()
                    .title(Component.translatable("item.project_light"))
                    .icon(() -> new ItemStack(PLItem.twilight_ego_a.get()))
                    .displayItems(PLTables::addTabItems)
                    .build());

    public static Item.Properties items() {
        return new Item.Properties();
    }

    public static Item.Properties items(int stackSize) {
        return items().stacksTo(stackSize);
    }

    private static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        output.accept(PLItem.twilight_ego.get());
        output.accept(PLItem.justitia_ego.get());
        output.accept(PLItem.smile_ego.get());
        output.accept(PLItem.censored_ego.get());
        output.accept(PLItem.paradise_lost_ego.get());
        output.accept(PLItem.apostles_sickle.get());
        output.accept(PLItem.apostles_staff.get());
        output.accept(PLItem.apostles_spear.get());
    }
}