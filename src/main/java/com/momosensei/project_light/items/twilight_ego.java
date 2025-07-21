package com.momosensei.project_light.items;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.momosensei.project_light.register.PLItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.momosensei.project_light.util.AttackUtil.*;
import static com.momosensei.project_light.util.PenetratingDamage.reflectionPenetratingDamage;


public class twilight_ego extends Item /*implements IAnimatable*/ {
    //public AnimationFactory factory = new SingletonAnimationFactory(this);
    private final Multimap<Attribute, AttributeModifier> attributes;
    public twilight_ego(Properties properties) {
        super(properties.rarity(Rarity.create("aleph",ChatFormatting.DARK_RED)).stacksTo(1).fireResistant());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 11D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -3.5F, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier( "Tool modifier", 3F, AttributeModifier.Operation.ADDITION));
        this.attributes = builder.build();
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
        list.add(Component.translatable("project_light.item.twilight_ego1").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.twilight_ego2").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.twilight_ego3").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.twilight_ego4").withStyle(ChatFormatting.GOLD));
        list.add(Component.translatable("project_light.item.twilight_ego5"));
        list.add(Component.translatable("project_light.item.twilight_ego6"));
    }
    /*
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new twilight_ego_renderer();
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this,"controller",0,this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate (AnimationEvent<E> event){
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
        return PlayState.CONTINUE;
    }
    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }*/
}