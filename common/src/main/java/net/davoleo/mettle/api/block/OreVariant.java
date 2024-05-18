package net.davoleo.mettle.api.block;

import net.minecraft.resources.ResourceLocation;

public enum OreVariant {
    STONE(new ResourceLocation("minecraft", "block/stone")),
    DEEPSLATE(new ResourceLocation("minecraft", "block/deepslate")),
    NETHERRACK(new ResourceLocation("minecraft", "block/netherrack")),
    END_STONE(new ResourceLocation("minecraft", "block/end_stone")),
    ;

    private final ResourceLocation textureLocation;

    OreVariant(ResourceLocation texture) {
        this.textureLocation = texture;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }
}
