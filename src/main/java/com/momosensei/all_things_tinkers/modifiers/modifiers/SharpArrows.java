package com.momosensei.all_things_tinkers.modifiers.modifiers;

import com.momosensei.all_things_tinkers.modifiers.ATTmodifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class SharpArrows extends ATTmodifier {
    public SharpArrows() {
    }
    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        if (builder.getStat(ToolStats.PROJECTILE_DAMAGE) *0.01f<0.2) {
            ToolStats.PROJECTILE_DAMAGE.add(builder, modifier.getLevel()*0.2);
        }else if (builder.getStat(ToolStats.PROJECTILE_DAMAGE) *0.01f>0.2){
            ToolStats.PROJECTILE_DAMAGE.multiply(builder, modifier.getLevel()*1.01);
        }
    }
}