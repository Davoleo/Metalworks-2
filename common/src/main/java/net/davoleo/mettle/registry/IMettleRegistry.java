package net.davoleo.mettle.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public interface IMettleRegistry {

    void registerBlock(String name, Supplier<? extends Block> block);

    void registerItem(String name, Supplier<? extends Item> item);

    <T extends BlockEntity> BlockEntityType<T> newBlockEntityType(IBlockEntitySupplier<T> be, Block... validBlocks);

    <T extends BlockEntity> void registerBlockEntity(String name, Supplier<BlockEntityType<T>> blockEntityType);
}
