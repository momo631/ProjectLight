package com.momosensei.project_light.items;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.momosensei.project_light.register.PLItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.momosensei.project_light.event.UseitemAnimatonEvent.UseitemAnimaton;

public class paradise_lost_ego extends Item{
    private final Multimap<Attribute, AttributeModifier> attributes;
    public paradise_lost_ego(Properties properties) {
        super(properties.rarity(Rarity.create("aleph",ChatFormatting.DARK_RED)).stacksTo(1).fireResistant());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 21D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -3.5F, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier( "Tool modifier", 37F, AttributeModifier.Operation.ADDITION));
        this.attributes = builder.build();
        MinecraftForge.EVENT_BUS.addListener(this::onLivingHeal);
    }
    private void onLivingHeal(LivingHealEvent event) {
        LivingEntity a = event.getEntity();
        if (a instanceof Player player) {
            for (int j = 0; j < player.getInventory().items.size(); j++) {
                ItemStack stack = player.getInventory().getItem(j);
                if (stack.getItem() == PLItem.paradise_lost_ego.get()){
                    event.setAmount(0);
                }
            }
        }
    }
    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        boolean retval = super.onEntitySwing(stack, entity);
        if (entity instanceof Player player&&player.getMainHandItem().is(stack.getItem())) {
            CompoundTag c = stack.getOrCreateTag();
            String s = "paradise_lost_attack";
            if (stack.is(PLItem.paradise_lost_ego.get())) {
                if (c.getInt(s)==0) {
                    UseitemAnimaton(player.level(), "animation.paradise_lost.attack", player);
                    c.putInt(s, 60);
                }
            }
        }
        return retval;
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

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.translatable("project_light.item.paradise_lost_ego1").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.paradise_lost_ego2").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.paradise_lost_ego3").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.paradise_lost_ego4").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.paradise_lost_ego5").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.paradise_lost_ego6"));
        list.add(Component.translatable("project_light.item.paradise_lost_ego7"));
        list.add(Component.translatable("project_light.item.paradise_lost_ego8"));
        list.add(Component.translatable("project_light.item.paradise_lost_ego9"));
    }
}