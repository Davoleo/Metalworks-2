package net.davoleo.mettle.api.metal;

import net.davoleo.mettle.api.block.OreVariant;
import net.davoleo.mettle.api.metal.attribute.MetalModifier;

import java.util.List;
import java.util.Set;

public record SimpleMetal(
        String name,
        int color,
        int durability,
        int enchantability,
        int meltingTemperature,
        ToolStats toolStats,
        ArmorStats armorStats,
        List<MetalModifier> modifiers,
        MetalComponentFlags components,
        Set<OreVariant> oreVariants
) implements IMetal {

    public static MetalBuilder wizard(String name) {
        return new MetalBuilder(name);
    }
}
