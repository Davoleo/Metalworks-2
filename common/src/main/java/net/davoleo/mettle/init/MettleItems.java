package net.davoleo.mettle.init;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.registry.RegistryEntry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class MettleItems extends ModRegistry {

    public static void init() {
        METALS.forEach((metal) -> {
            if (metal.value().components().get(ComponentType.ORE)) {
                ITEMS.add(rentry("raw_" + metal.value().name(), () -> new Item(defTab)));
            }
        });
    }

    public static void registerSimpleBlockItem(RegistryEntry<? extends Block> block) {
        ITEMS.add(rentry(block.name(), () -> new BlockItem(block.entry().get(), defTab)));
    }

    public static Item.Properties defTab = new Item.Properties().tab(Mettle.platformUtils.getTab());

}
