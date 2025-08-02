package com.momosensei.project_light.register;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
    public static PLDamageSource mobAttack(@NotNull LivingEntity living){
        return new PLDamageSource(living.damageSources().mobAttack(living));
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
    public PLDamageSource setExplosion(){
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.IS_EXPLOSION.location()));
        return this;
    }
    public PLDamageSource setFire(){
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.IS_FIRE.location()));
        return this;
    }
    public PLDamageSource setProjectile(){
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.IS_PROJECTILE.location()));
        return this;
    }
    public PLDamageSource setFreezing(){
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.IS_FREEZING.location()));
        return this;
    }
    public PLDamageSource setFall(){
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.IS_FALL.location()));
        return this;
    }
    public PLDamageSource setDrowning(){
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.IS_DROWNING.location()));
        return this;
    }
    public PLDamageSource setDamageHelmet(){
        this.damageTypes.add(ResourceKey.create(Registries.DAMAGE_TYPE,DamageTypeTags.DAMAGES_HELMET.location()));
        return this;
    }

    public PLDamageSource setMsgId(String s){
        this.msgId=s;
        return this;
    }
    public @NotNull Component getLocalizedDeathMessage(LivingEntity pLivingEntity) {
        String $$1 = "death.attack." + (this.msgId==null?this.type().msgId():this.msgId);
        if (this.getEntity() == null && this.getDirectEntity() == null) {
            LivingEntity $$5 = pLivingEntity.getKillCredit();
            String $$6 = $$1 + ".player";
            return $$5 != null ? Component.translatable($$6, pLivingEntity.getDisplayName(), $$5.getDisplayName()) : Component.translatable($$1, pLivingEntity.getDisplayName());
        } else {
            Component $$2 = this.getEntity() == null ? this.getDirectEntity().getDisplayName() : this.getEntity().getDisplayName();
            Entity var6 = this.getEntity();
            ItemStack var10000;
            if (var6 instanceof LivingEntity $$3) {
                var10000 = $$3.getMainHandItem();
            } else {
                var10000 = ItemStack.EMPTY;
            }
            ItemStack $$4 = var10000;
            return !$$4.isEmpty() && $$4.hasCustomHoverName() ? Component.translatable($$1 + ".item", pLivingEntity.getDisplayName(), $$2, $$4.getDisplayName()) : Component.translatable($$1, pLivingEntity.getDisplayName(), $$2);
        }
    }

    @Override
    public boolean is(TagKey<DamageType> key) {
        if (!damageTypes.isEmpty()){
            return damageTypes.contains(ResourceKey.create(Registries.DAMAGE_TYPE, key.location())) || super.is(key);
        }
        return super.is(key);
    }
    @Override
    public boolean is(ResourceKey<DamageType> key) {
        if (!damageTypes.isEmpty()){
            return damageTypes.contains(key) || super.is(key);
        }
        return super.is(key);
    }
}
