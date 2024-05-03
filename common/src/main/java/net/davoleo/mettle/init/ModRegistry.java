package net.davoleo.mettle.init;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.registry.RegistryEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class ModRegistry {

    private static final List<RegistryEntry<Block>> BLOCKS = Lists.newArrayList();
    private static final List<RegistryEntry<Item>> ITEMS = Lists.newArrayList();

    public static void init() {
        BLOCKS.forEach(regObj -> Mettle.registry.registerBlock(regObj.name(), regObj.entry()));
        ITEMS.forEach(regObj -> Mettle.registry);
        MettleBlocks.init();
    }
}
