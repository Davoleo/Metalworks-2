package net.davoleo.mettle.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public interface IMettleRegistry {

    void registerBlock(String name, Supplier<? extends Block> block);

    void registerItem(String name, Supplier<? extends Item> item);

}
