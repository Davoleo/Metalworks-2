package net.davoleo.mettle.init;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.IMettleIntegration;
import net.davoleo.mettle.api.metal.Alloy;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.api.metal.MetalComponents;
import net.davoleo.mettle.api.registry.Registry;
import net.davoleo.mettle.api.registry.RegistryEntry;
import net.davoleo.mettle.registry.InternalRegistry;
import net.minecraft.world.item.BlockItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModRegistry {

    public static final InternalRegistry<IMetal> METALS = new InternalRegistry<>();
    protected static final InternalRegistry<Alloy> ALLOYS = new InternalRegistry<>();

    public static Map<String, MetalComponents> METAL_COMPONENTS;

    protected static final List<RegistryEntry<? extends BlockItem>> BLOCK_ITEMS = new ArrayList<>();

    public static void init() {
        Map<String, MetalComponents.Builder> components = Maps.toMap(
                METALS.stream().map(mentry -> mentry.value().name()).iterator(),
                metal -> MetalComponents.builder()
        );

        MettleBlocks.init(components);
        MettleItems.init(components);

        METAL_COMPONENTS = components.entrySet().stream()
                .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, builderEntry -> builderEntry.getValue().build()));

        for (MetalComponents metalComponents : METAL_COMPONENTS.values()) {
            metalComponents.forEachBlock(regEntry -> Mettle.registry.registerBlock(regEntry.name(), regEntry.entry()));
            metalComponents.forEachItem(regEntry -> Mettle.registry.registerItem(regEntry.name(), regEntry.entry()));
        }

        BLOCK_ITEMS.forEach(regObj -> Mettle.registry.registerItem(regObj.name(), regObj.entry()));
    }

    public static void registerPack(String modid, IMettleIntegration pack) {
        Registry<IMetal> metalRegistry = new Registry<>(modid);
        Registry<Alloy> alloyRegistry = new Registry<>(modid);
        pack.registerMetals(metalRegistry);
        pack.registerAlloys(alloyRegistry);
        METALS.merge(metalRegistry);
        ALLOYS.merge(alloyRegistry);
    }

    protected static <T> RegistryEntry<T> rentry(String name, Supplier<T> supplier) {
        return RegistryEntry.of(name, supplier);
    }
}
