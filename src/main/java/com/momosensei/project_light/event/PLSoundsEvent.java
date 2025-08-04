package com.momosensei.project_light.event;

import com.momosensei.project_light.register.PLItem;
import com.momosensei.project_light.sounds.PLSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

public class PLSoundsEvent {
    public PLSoundsEvent() {
        MinecraftForge.EVENT_BUS.addListener(this::OnPlayerTick);
    }
    private void OnPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ItemStack stack = player.getMainHandItem();
        CompoundTag tag = stack.getOrCreateTag();
        if (stack.isEmpty()) return;
        if (!player.level().isClientSide)return;
        if (stack.is(PLItem.justitia_ego.get())){
            String s = "justitia_extra_attack";
            if (tag.getInt(s) == 58) player.playSound(PLSounds.JustitiaAttack.get(),0.6F,1);
            if (tag.getInt(s) == 48) player.playSound(PLSounds.JustitiaAttack.get(),0.6F,1);
            if (tag.getInt(s) == 38) player.playSound(PLSounds.JustitiaAttack.get(),0.6F,1);
            if (tag.getInt(s) == 22) player.playSound(PLSounds.ExtraJustitiaAttack.get(),0.6F,1);
        }else
        if (stack.is(PLItem.smile_ego.get())){
            String s = "smile_extra_attack";
            if (tag.getInt(s) == 19) player.playSound(PLSounds.ExtraSmileAttack.get());
        }else
        if (stack.is(PLItem.censored_ego.get())){
            String s = "censored_extra_attack";
            if (tag.getInt(s) == 13) player.playSound(PLSounds.ExtraCensoredAttack.get(),0.8f,1);

        }
    }
}
