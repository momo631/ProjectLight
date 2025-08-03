package com.momosensei.project_light.items;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.momosensei.project_light.register.PLItem;
import com.momosensei.project_light.sounds.PLSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class smile_ego extends Item{
    private final Multimap<Attribute, AttributeModifier> attributes;
    public smile_ego(Properties properties) {
        super(properties.rarity(Rarity.create("aleph",ChatFormatting.DARK_RED)).stacksTo(1).fireResistant());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 11D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -3.25F, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier( "Tool modifier", 2F, AttributeModifier.Operation.ADDITION));
        this.attributes = builder.build();
        MinecraftForge.EVENT_BUS.addListener(this::onEntityDeath);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributes : ImmutableMultimap.of();
    }
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        if (player.getCooldowns().isOnCooldown(stack.getItem()))return InteractionResultHolder.fail(stack);
        return InteractionResultHolder.consume(stack);
    }

    public int getUseDuration(ItemStack stack) {
        return 1;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
        CompoundTag c = stack.getOrCreateTag();
        String s = "smile_extra_attack";
        if (living instanceof Player player&&!player.getCooldowns().isOnCooldown(stack.getItem())) {
            c.putInt(s, 24);
            player.getCooldowns().addCooldown(stack.getItem(),1800);
        }
        return stack;
    }
    public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 24;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return Items.DIAMOND_SWORD.isBookEnchantable(new ItemStack(Items.DIAMOND_SWORD), book);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return Items.DIAMOND_SWORD.canApplyAtEnchantingTable(new ItemStack(Items.DIAMOND_SWORD), enchantment);
    }

    private void onEntityDeath(LivingDeathEvent event) {
        LivingEntity a = event.getEntity();
        Entity b = event.getSource().getEntity();
        if (b instanceof Player player&&a!=null){
            ItemStack stack = player.getMainHandItem();
            String s = "dead_by";
            if (!stack.isEmpty()&&stack.is(PLItem.smile_ego.get())) {
                CompoundTag c = stack.getOrCreateTag();
                if (c.getInt("smile_ego") < 30&&!a.getPersistentData().hasUUID(s)) {
                    c.putInt("smile_ego", c.getInt("smile_ego") + 1);
                    a.getPersistentData().putUUID(s,player.getUUID());
                }
            }
        }
        if (a instanceof Player player){
            for (int j = 0; j < player.getInventory().items.size(); j++) {
                ItemStack stack = player.getInventory().getItem(j);
                CompoundTag c = stack.getTag();
                if (stack.is(PLItem.smile_ego.get())&&c != null && c.getInt("smile_ego") != 0) {
                    c.putInt("smile_ego", 0);
                }
            }
        }
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (slot != EquipmentSlot.MAINHAND) return ImmutableMultimap.of();
        CompoundTag c = stack.getOrCreateTag();
        if ( c.getInt("smile_ego") != 0) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 11D, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, Attributes.ATTACK_SPEED.getDescriptionId(), c.getInt("smile_ego") *0.01-3.25F, AttributeModifier.Operation.ADDITION));
            builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("59CE02B7-FD71-0387-872F-17B95CC5841F"),"Tool modifier", 2F, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("F7321EE0-DBD9-C6B5-BF29-01BB2AF8EB96"),Attributes.MAX_HEALTH.getDescriptionId(), +c.getInt("smile_ego"), AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("73D70290-7E71-CFD6-8697-43CC1149D153"),Attributes.MOVEMENT_SPEED.getDescriptionId(), c.getInt("smile_ego") *0.01, AttributeModifier.Operation.ADDITION));
        }else return super.getAttributeModifiers(slot, stack);
        return builder.build();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("project_light.item.smile_ego1").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.smile_ego2").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.smile_ego3").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.smile_ego4").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.smile_ego5").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.smile_ego6"));
        list.add(Component.translatable("project_light.item.smile_ego7"));
        list.add(Component.translatable("project_light.item.smile_ego8"));
    }
}