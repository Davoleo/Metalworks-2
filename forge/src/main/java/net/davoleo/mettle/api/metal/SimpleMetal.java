package net.davoleo.mettle.api.metal;

import net.minecraftforge.registries.ForgeRegistryEntry;

public class SimpleMetal extends ForgeRegistryEntry<IMetal> implements IMetal {

    private int color;

    private MetalProperties properties;

    private MetalComponents components;

    public SimpleMetal(int color, MetalProperties properties, MetalComponents components)
    {
        this.color = color;
        this.properties = properties;
        this.components = components;
    }

    public String getName()
    {
        return "mettle.metal." + getRegistryName().getPath();
    }

    @Override
    public int getColor()
    {
        return color;
    }

    @Override
    public MetalProperties getProperties()
    {
        return properties;
    }

    @Override
    public MetalComponents getComponents()
    {
        return components;
    }
}
