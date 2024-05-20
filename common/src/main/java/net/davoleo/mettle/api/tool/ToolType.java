package net.davoleo.mettle.api.tool;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public enum ToolType {
    //Vanilla
    AXE,
    HOE,
    PICKAXE,
    SHOVEL,
    //Mettle
    ;

    public TagKey<Block> getMineableTag() {
        return switch (this) {
            case AXE -> BlockTags.MINEABLE_WITH_AXE;
            case HOE -> BlockTags.MINEABLE_WITH_HOE;
            case PICKAXE -> BlockTags.MINEABLE_WITH_PICKAXE;
            case SHOVEL -> BlockTags.MINEABLE_WITH_SHOVEL;
        };
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
