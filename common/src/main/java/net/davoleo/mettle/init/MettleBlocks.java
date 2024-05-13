package net.davoleo.mettle.init;

import com.google.common.base.Suppliers;
import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.block.MettleOreBlock;

import java.util.function.Supplier;

public class MettleBlocks extends ModRegistry {

    public static void init() {

        //Register Ores
        METALS.forEach((name, metal) -> {
            if (metal.value().components().get(ComponentType.ORE)) {
                Supplier<MettleOreBlock> oreBlock = Suppliers.memoize(() -> new MettleOreBlock(metal.value()));
                BLOCKS.add(rentry(name + "_ore", oreBlock));
                MettleItems.registerSimpleBlockItem(name + "_ore", oreBlock);
            }
        });
    }

}
