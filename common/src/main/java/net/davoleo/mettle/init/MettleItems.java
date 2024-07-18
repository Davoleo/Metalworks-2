package net.davoleo.mettle.init;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.api.metal.MetalComponents;
import net.davoleo.mettle.api.registry.RegistryEntry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public class MettleItems extends ModRegistry {

    public static void init(Map<String, MetalComponents.Builder> componentsMap) {
        METALS.forEach((metal) -> {
            if (metal.value().components().get(ComponentType.ORE)) {
                var componentsObj = componentsMap.get(metal.value().name());
                componentsObj.setRaw(rentry("raw_" + metal.value().name(), () -> new Item(defTab)));
            }
        });
    }

    public static void registerSimpleBlockItem(RegistryEntry<? extends Block> block) {
        BLOCK_ITEMS.add(rentry(block.name(), () -> new BlockItem(block.entry().get(), defTab)));
    }

    public static Item.Properties defTab = new Item.Properties().tab(Mettle.PLATFORM_UTILS.getTab());

}
