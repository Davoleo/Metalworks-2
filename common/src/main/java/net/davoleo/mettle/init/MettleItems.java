package net.davoleo.mettle.init;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.metal.ComponentType;
import net.minecraft.world.item.Item;

public class MettleItems extends ModRegistry {

    public static void init() {
        METALS.forEach((name, metal) -> {
            if (metal.value().components().get(ComponentType.ORE)) {
                ITEMS.add(rentry("raw_" + name, () -> new Item(defTab)));
            }
        });
    }

    public static Item.Properties defTab = new Item.Properties().tab(Mettle.platformUtils.getTab());

}
