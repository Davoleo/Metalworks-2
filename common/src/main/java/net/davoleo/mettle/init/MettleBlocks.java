package net.davoleo.mettle.init;

import net.davoleo.mettle.api.block.OreVariant;
import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.api.metal.MetalComponents;
import net.davoleo.mettle.block.MettleOreBlock;

import java.util.Map;

public class MettleBlocks extends ModRegistry {

    public static void init(Map<String, MetalComponents.Builder> componentsMap) {

        //Register Ores
        METALS.forEach((metal) -> {
            if (metal.value().components().get(ComponentType.ORE)) {
                for (OreVariant oreVariant : metal.value().oreVariants()) {
                    var componentsObj = componentsMap.get(metal.value().name());
                    var regEntry = rentry(
                            metal.value().name() + '_' + oreVariant.toString() + "_ore",
                            () -> new MettleOreBlock(metal.value(), oreVariant)
                    );
                    componentsObj.addOre(oreVariant, regEntry);
                    MettleItems.registerSimpleBlockItem(regEntry);
                }
            }
        });
    }

}
