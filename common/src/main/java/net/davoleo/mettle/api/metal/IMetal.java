package net.davoleo.mettle.api.metal;

public interface IMetal {

    /**
     * @return The name, unique identifier of a metal
     */
    String name();

    int color();

    int durability();

    int enchantability();

    ToolStats toolStats();

    ArmorStats armorStats();

    MetalComponents components();

}
