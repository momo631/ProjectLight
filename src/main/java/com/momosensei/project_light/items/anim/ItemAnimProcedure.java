package com.momosensei.project_light.items.anim;

import com.momosensei.project_light.items.censored_ego;
import net.minecraft.world.item.ItemStack;



public class ItemAnimProcedure {
	public static void AttackAnim(ItemStack itemstack) {
		//if (itemstack.getItem() instanceof censored_ego)itemstack.getOrCreateTag().putString("geckoAnim", "animation.attack");
	}

	public static void ExtraAttackAnim(ItemStack itemstack) {
		if (itemstack.getItem() instanceof censored_ego)
			itemstack.getOrCreateTag().putString("geckoAnim", "animation.extra_attack");
	}

}
