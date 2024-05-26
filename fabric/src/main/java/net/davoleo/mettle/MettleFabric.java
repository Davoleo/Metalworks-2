package net.davoleo.mettle;

import net.davoleo.mettle.init.MettlePackFinderFabric;
import net.davoleo.mettle.init.ModRegistry;
import net.davoleo.mettle.world.MettleOreGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MettleFabric implements ModInitializer {

    public static final CreativeModeTab CREATIVE_TAB = FabricItemGroupBuilder
            .create(new ResourceLocation(Mettle.MODID, "main"))
            .icon(() -> new ItemStack(Items.COPPER_INGOT))
            .build();

    public MettleFabric() {
        MettlePackFinderFabric.findPacks();
    }

    @Override
    public void onInitialize() {
        Mettle.LOGGER.info("HELLO FROM {}", Mettle.MODNAME);
        ModRegistry.init();

        MettleOreGeneration.generate();
    }
}
