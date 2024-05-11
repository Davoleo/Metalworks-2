package net.davoleo.mettle.init;

import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.block.MettleOreBlock;

public class MettleBlocks extends ModRegistry {

    public static void init() {

        //Register Ores
        METALS.forEach((name, metal) -> {
            if (metal.value().components().get(ComponentType.ORE)) {
                BLOCKS.add(rentry(name + "_ore", () -> new MettleOreBlock(metal.value())));
            }
        });
    }

}
