package net.davoleo.mettle.init;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.registry.IMettleRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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
}
