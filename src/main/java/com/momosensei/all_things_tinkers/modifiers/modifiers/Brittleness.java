package com.momosensei.all_things_tinkers.modifiers.modifiers;

import com.momosensei.all_things_tinkers.modifiers.ATTmodifier;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class Brittleness extends ATTmodifier {
    public Brittleness() {
    }
    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolStats.ARMOR.add(builder, modifier.getLevel());
    }
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity livingEntity) {
        if (modifier.getLevel()>0){
            return (int) (amount * (1F+0.1F*modifier.getLevel()));
        }
        return amount;
    }
}