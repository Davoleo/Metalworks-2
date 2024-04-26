package net.davoleo.mettle.api.metal;

import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IMetal extends IForgeRegistryEntry<IMetal> {

    int getColor();

    MetalProperties getProperties();

    MetalComponents getComponents();

}
