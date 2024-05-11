package net.davoleo.mettle.init;

import net.davoleo.mettle.api.metal.ComponentType;
import net.minecraft.world.item.Item;

public class MettleItems extends ModRegistry {

    public static void init() {
        METALS.forEach((name, metal) -> {
            if (metal.value().components().get(ComponentType.ORE)) {
                ITEMS.add(rentry("raw_" + name, () -> new Item(new Item.Properties())));
            }
        });
    }

}
