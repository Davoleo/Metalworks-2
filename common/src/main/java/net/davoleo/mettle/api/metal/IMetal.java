package net.davoleo.mettle.api.metal;

import net.davoleo.mettle.api.block.OreVariant;
import net.davoleo.mettle.api.metal.attribute.MetalModifier;
import net.davoleo.mettle.api.registry.Registry;

import java.util.List;
import java.util.Set;

public interface IMetal extends Registry.Nameable {

    /**
     * The name: unique identifier of a metal
     */
    String name();

    /**
     * Color of the metal! :)
     */
    int color();

    /**
     * Durability, used both in:
     * <ul>
     * <li>tools & weapons</li>
     * <li>armor</li>
     * </ul>
     */
    int durability();

    /**
     * Metal Enchant Value, shared among tools and armor
     */
    int enchantability();

    /**
     * Metal Melting temperature, how much heat is necessary to turn this metal into molten form.<br>
     * <b>Unit: °C</b>
     */
    int meltingTemperature();

    /**
     * Stats concerning tools
     */
    ToolStats toolStats();

    /**
     * Stats concerning armor
     */
    ArmorStats armorStats();

    /**
     * List of metal modifiers applied to the final product in case it's a compound product made of >50% of this metal<br>
     * Can be an empty list in case no modifiers are needed.
     */
    List<MetalModifier> modifiers();

    MetalComponentFlags components();

    Set<OreVariant> oreVariants();

}
