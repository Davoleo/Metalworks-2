package net.davoleo.mettle.api.metal.attribute;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public record MetalModifier(
        MetalAttribute attribute,
        AttributeModifier.Operation operation,
        double value
) {
}
