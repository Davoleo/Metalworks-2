package net.davoleo.mettle.init;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.metal.ComponentType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class MettleItems extends ModRegistry {

    public static void init() {
        METALS.forEach((name, metal) -> {
            if (metal.value().components().get(ComponentType.ORE)) {
                ITEMS.add(rentry("raw_" + name, () -> new Item(defTab)));
            }
        });
    }

    public static void registerSimpleBlockItem(String name, Supplier<? extends Block> block) {
        ITEMS.add(rentry(name, () -> new BlockItem(block.get(), defTab)));
    }

    public static Item.Properties defTab = new Item.Properties().tab(Mettle.platformUtils.getTab());

}
