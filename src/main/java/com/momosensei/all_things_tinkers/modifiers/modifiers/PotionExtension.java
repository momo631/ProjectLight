package com.momosensei.all_things_tinkers.modifiers.modifiers;

import com.momosensei.all_things_tinkers.modifiers.ATTmodifier;
import com.momosensei.all_things_tinkers.register.ATTModifiers;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;

import java.util.List;

public class PotionExtension extends ATTmodifier {
    public PotionExtension() {
        MinecraftForge.EVENT_BUS.addListener(this::AddMobEffect);
    }

    private void AddMobEffect(MobEffectEvent.Expired event) {
        if (event.getEntity() instanceof Player player) {
            int a= getAllModifierlevel(player, ATTModifiers.potionextension.getId());
            if (a>0){
                //event.getEffectInstance().mapDuration();
            }
        }
    }
}