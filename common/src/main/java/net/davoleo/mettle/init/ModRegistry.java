package net.davoleo.mettle.init;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.IMettleIntegration;
import net.davoleo.mettle.api.metal.Alloy;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.api.registry.Registry;
import net.davoleo.mettle.registry.InternalRegistry;
import net.davoleo.mettle.registry.RegistryEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class ModRegistry {

    private static final InternalRegistry<IMetal> METALS = new InternalRegistry<>();
    private static final InternalRegistry<Alloy> ALLOYS = new InternalRegistry<>();

    private static final List<RegistryEntry<Block>> BLOCKS = Lists.newArrayList();
    private static final List<RegistryEntry<Item>> ITEMS = Lists.newArrayList();


    public static void init() {
        BLOCKS.forEach(regObj -> Mettle.registry.registerBlock(regObj.name(), regObj.entry()));
        ITEMS.forEach(regObj -> Mettle.registry.registerItem(regObj.name(), regObj.entry()));
        MettleBlocks.init();
    }

    public static void registerPack(String modid, IMettleIntegration pack) {
        Registry<IMetal> metalRegistry = new Registry<>(modid);
        Registry<Alloy> alloyRegistry = new Registry<>(modid);
        pack.registerMetals(metalRegistry);
        pack.registerAlloys(alloyRegistry);
        METALS.merge(metalRegistry);
        ALLOYS.merge(alloyRegistry);
    }
}
