package net.davoleo.mettle.init;

import com.google.common.base.Suppliers;
import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.block.MettleOreBlock;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class MettleBlocks extends ModRegistry {

    public static void init() {

        //Register Ores
        METALS.forEach((name, metal) -> {
            if (metal.value().components().get(ComponentType.ORE)) {
                for (ResourceLocation oreVariant : metal.value().oreVariantsTextures()) {
                    Supplier<MettleOreBlock> oreBlock = Suppliers.memoize(() -> new MettleOreBlock(metal.value(), new ResourceLocation("minecraft", "stone")));
                    String oreName = name + '_' + oreVariant.getPath() + "_ore";
                    BLOCKS.add(rentry(oreName, oreBlock));
                    MettleItems.registerSimpleBlockItem(oreName, oreBlock);
                }
            }
        });
    }

}
