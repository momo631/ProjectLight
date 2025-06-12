package com.momosensei.all_things_tinkers.modifiers.modifiers;

import com.momosensei.all_things_tinkers.modifiers.ATTmodifier;
import com.momosensei.all_things_tinkers.register.ATTModifiers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class FluorescenceA extends ATTmodifier {
    public FluorescenceA() {
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }
    @Override
    public boolean isNoLevels() {
        return true;
    }
    public void livinghurtevent(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player&&event.getSource().getEntity() instanceof LivingEntity entity) {
            int a = getArmorModifierlevel(player, ATTModifiers.fluorescencea.getId());
            if (a>0) {
                entity.addEffect(new MobEffectInstance(MobEffects.GLOWING,200,0));
            }
        }
    }
}