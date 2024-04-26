package net.davoleo.mettle.api.metal;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

/**
 * If methods' implementation return null,
 * items and blocks won't be added by Mettle,
 * otherwise the mod's features will be used
 */
public class MetalComponents {

    @Nullable
    Block getBlock() {
        return null;
    }

    @Nullable
    Item getIngot() {
        return null;
    }

    @Nullable
    Item getNugget() {
        return null;
    }

    @Nullable
    Item getRod() {
        return null;
    }

    @Nullable
    Item getPlate() {
        return null;
    }

}
