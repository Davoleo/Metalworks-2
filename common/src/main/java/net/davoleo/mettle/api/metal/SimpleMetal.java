package net.davoleo.mettle.api.metal;

import net.davoleo.mettle.api.metal.attribute.MetalModifier;
import net.minecraft.resources.ResourceLocation;

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
        MetalComponents components,
        ResourceLocation[] oreVariantsTextures
) implements IMetal {

    public static MetalBuilder wizard(String name) {
        return new MetalBuilder(name);
    }
}
