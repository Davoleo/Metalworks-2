package net.davoleo.mettle.api.metal;

import net.davoleo.mettle.api.metal.attribute.MetalModifier;

import java.util.List;

public record SimpleMetal(
        String name,
        int color,
        int durability,
        int enchantability,
        int meltingTemperature,
        ToolStats toolStats,
        ArmorStats armorStats,
        List<MetalModifier> modifiers,
        MetalComponents components
) implements IMetal {

    public static MetalBuilder wizard(String name) {
        return new MetalBuilder(name);
    }
}
