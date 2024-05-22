package net.davoleo.mettle.init;

import net.davoleo.mettle.api.block.OreVariant;
import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.block.MettleOreBlock;

public class MettleBlocks extends ModRegistry {

    public static void init() {

        //Register Ores
        METALS.forEach((metal) -> {
            if (metal.value().components().get(ComponentType.ORE)) {
                for (OreVariant oreVariant : metal.value().oreVariants()) {
                    var regEntry = rentry(
                            metal.value().name() + '_' + oreVariant.toString() + "_ore",
                            () -> new MettleOreBlock(metal.value(), oreVariant)
                    );
                    BLOCKS.add(regEntry);
                    MettleItems.registerSimpleBlockItem(regEntry);
                }
            }
        });
    }

}
