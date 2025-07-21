package com.momosensei.project_light.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class PLDamageSource extends DamageSource {
    public String msgId=null;
    public ArrayList<ResourceKey<DamageType>> damageTypes =new ArrayList<>();
    public PLDamageSource(Holder<DamageType> holder, @Nullable Entity entity, @Nullable Entity attacker, @Nullable Vec3 sourcePos) {
        super(holder, entity, attacker, sourcePos);
    }

    public PLDamageSource(DamageSource source){
        this(source.typeHolder(),source.getDirectEntity(),source.getEntity(),source.sourcePositionRaw());
    }

    public static PLDamageSource playerAttack(@NotNull Player player){
        return new PLDamageSource(player.damageSources().playerAttack(player));
    }
    public static PLDamageSource Attack(@NotNull DamageSource damageSource){
        return new PLDamageSource(damageSource);
    }

    public PLDamageSource setBypassInvulnerableTime(){
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.BYPASSES_COOLDOWN.location()));
        return this;
    }
    public PLDamageSource setBypassArmor(){
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.BYPASSES_ARMOR.location()));
        return this;
    }
    public PLDamageSource setBypassInvul(){
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.BYPASSES_INVULNERABILITY.location()));
        return this;
    }
    public PLDamageSource setBypassMagic(){
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.BYPASSES_RESISTANCE.location()));
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.BYPASSES_EFFECTS.location()));
        return this;
    }
    public PLDamageSource setBypassEnchantment(){
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.BYPASSES_ENCHANTMENTS.location()));
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.AVOIDS_GUARDIAN_THORNS.location()));
        return this;
    }
    public PLDamageSource setBypassShield(){
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.BYPASSES_SHIELD.location()));
        return this;
    }

}
