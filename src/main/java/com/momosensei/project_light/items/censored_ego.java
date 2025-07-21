package com.momosensei.project_light.items;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.momosensei.project_light.register.PLItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.momosensei.project_light.util.AttackUtil.getCooldownFunctionFloat;

public class censored_ego extends Item{
    private final Multimap<Attribute, AttributeModifier> attributes;
    public censored_ego(Properties properties) {
        super(properties.rarity(Rarity.create("aleph",ChatFormatting.DARK_RED)).stacksTo(1).fireResistant());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 29D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -3.25F, AttributeModifier.Operation.ADDITION));
        this.attributes = builder.build();
        MinecraftForge.EVENT_BUS.addListener(this::livinghurtevent);
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributes : super.getDefaultAttributeModifiers(equipmentSlot);
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

    private void livinghurtevent(LivingHurtEvent event) {
        Entity a = event.getEntity();
        Entity b = event.getSource().getEntity();
        if (b instanceof Player player&&!player.level().isClientSide()&&a instanceof LivingEntity living) {
            ItemStack stack = player.getMainHandItem();
            if (!stack.isEmpty()&&stack.is(PLItem.censored_ego.get())) {
                float c = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                float d = getCooldownFunctionFloat(player, InteractionHand.MAIN_HAND);
                float e = c * (0.2f + d * d * 0.8f);
                event.setAmount(event.getAmount()*((float) 11 /30));
                //event.getSource().bypassArmor().bypassMagic();
                living.invulnerableTime = 0;
                living.hurt(player.level().damageSources().starve(),e*((float) 11 /30));
                living.invulnerableTime = 0;
            }
        }
        if (a instanceof Player player&&!player.level().isClientSide()) {
            ItemStack stack = player.getMainHandItem();
            if (!stack.isEmpty()&&stack.is(PLItem.censored_ego.get())&&!player.isDeadOrDying()) {
                player.heal(event.getAmount()*0.4f);
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("project_light.item.censored_ego1").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.censored_ego2").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.censored_ego3").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.censored_ego4"));
        list.add(Component.translatable("project_light.item.censored_ego5"));
    }
}