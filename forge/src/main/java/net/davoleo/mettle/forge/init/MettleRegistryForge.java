package net.davoleo.mettle.forge.init;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.registry.IMettleRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class MettleRegistryForge implements IMettleRegistry {

    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Mettle.MODID);
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Mettle.MODID);

    public static void registerOnBus(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }

    @Override
    public void registerBlock(String name, Supplier<? extends Block> block) {
        BLOCKS.register(name, block);
    }

    @Override
    public void registerItem(String name, Supplier<? extends Item> item) {
        ITEMS.register(name, item);
    }
}
