package net.davoleo.mettle.api.block;

import net.minecraft.resources.ResourceLocation;

public class OreVariant {
    public static OreVariant STONE = new OreVariant("stone",new ResourceLocation("minecraft", "block/stone"));
    public static OreVariant DEEPSLATE = new OreVariant("deepslate",new ResourceLocation("minecraft", "block/deepslate"));
    public static OreVariant NETHERRAK = new OreVariant("netherrack",new ResourceLocation("minecraft", "block/netherrack"));
    public static OreVariant END_STONE = new OreVariant("end_stone",new ResourceLocation("minecraft", "block/end_stone"));

    private final String name;
    private final ResourceLocation textureLocation;

    private String tool = "pickaxe";

    public OreVariant(String name, ResourceLocation texture) {
        this.name = name;
        this.textureLocation = texture;
    }

    public OreVariant(String name, ResourceLocation texture, String tool) {
        this.name = name;
        this.textureLocation = texture;
        this.tool = tool;
    }

    @Override
    public String toString() {
        return this.name.toLowerCase();
    }

    public String getTool() {
        return tool;
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }
}
