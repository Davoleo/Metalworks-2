package net.davoleo.mettle.api.block;

import net.davoleo.mettle.api.tool.ToolType;
import net.minecraft.resources.ResourceLocation;

public class OreVariant {
    public static final OreVariant STONE = new OreVariant("stone");
    public static final OreVariant DEEPSLATE = new OreVariant("deepslate");
    public static final OreVariant NETHERRACK = new OreVariant("netherrack");
    public static final OreVariant END_STONE = new OreVariant("end_stone");

    private final String name;
    private final ResourceLocation textureLocation;
    private final ToolType miningTool;

    public OreVariant(String name, ResourceLocation textureLocation, ToolType tool) {
        this.name = name;
        this.textureLocation = textureLocation;
        this.miningTool = tool;
    }

    public OreVariant(String name, ToolType tool) {
        this(name, new ResourceLocation("minecraft", "block/" + name), tool);
    }

    public OreVariant(String name) {
        this(name, ToolType.PICKAXE);
    }

    @Override
    public String toString() {
        return this.name.toLowerCase();
    }

    public ToolType getTool() {
        return miningTool;
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }
}
