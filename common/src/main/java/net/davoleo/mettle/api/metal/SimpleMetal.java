package net.davoleo.mettle.api.metal;

public record SimpleMetal(String name, int color, int durability, int enchantability, ToolStats toolStats,
                          ArmorStats armorStats, MetalComponents components) implements IMetal {

    public static MetalBuilder wizard(String name) {
        return new MetalBuilder(name);
    }
}
