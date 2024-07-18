package net.davoleo.mettle.init;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.registry.IBlockEntitySupplier;
import net.davoleo.mettle.registry.IMettleRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class MettleRegistryFabric implements IMettleRegistry {

    @Override
    public void registerBlock(String name, Supplier<? extends Block> block) {
        Registry.register(Registry.BLOCK, new ResourceLocation(Mettle.MODID, name), block.get());
    }

    @Override
    public void registerItem(String name, Supplier<? extends Item> item) {
        Registry.register(Registry.ITEM, new ResourceLocation(Mettle.MODID, name), item.get());
    }

    @Override
    public <T extends BlockEntity> BlockEntityType<T> newBlockEntityType(IBlockEntitySupplier<T> be, Block... validBlocks) {
        return FabricBlockEntityTypeBuilder.create(be::create, validBlocks).build();
    }

    @Override
    public <T extends BlockEntity> void registerBlockEntity(String name, Supplier<BlockEntityType<T>> blockEntityType) {
        Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new ResourceLocation(Mettle.MODID, name),
                blockEntityType.get()
        );
    }
}
