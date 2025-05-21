package com.momosensei.all_things_tinkers.modifiers.modifiers;

import com.momosensei.all_things_tinkers.modifiers.ATTmodifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class SharpSword extends ATTmodifier {
    public SharpSword() {
    }
    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        if (builder.getStat(ToolStats.ATTACK_DAMAGE) *0.02f<1) {
            ToolStats.ATTACK_DAMAGE.add(builder, modifier.getLevel());
        }else if (builder.getStat(ToolStats.ATTACK_DAMAGE) *0.02f>1){
            ToolStats.ATTACK_DAMAGE.multiply(builder, modifier.getLevel()*1.02);
        }
    }
}