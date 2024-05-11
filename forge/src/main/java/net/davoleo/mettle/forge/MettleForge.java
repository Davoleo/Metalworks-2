package net.davoleo.mettle.forge;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.forge.init.MettlePackFinder;
import net.davoleo.mettle.forge.init.MettleRegistryForge;
import net.davoleo.mettle.forge.util.PlatformUtilsForge;
import net.davoleo.mettle.init.ModRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Mettle.MODID)
public class MettleForge {

    public static final String MODVERSION = ModList.get().getModFileById(Mettle.MODID).versionString();

    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(Mettle.MODID) {
        @Nonnull
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(Items.COPPER_INGOT);
        }
    };

    public MettleForge()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        Mettle.platformUtils = new PlatformUtilsForge();
        MettleRegistryForge.setup();
        MettlePackFinder.doTheStuff();
        ModRegistry.init();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // Some preinit code
        Mettle.LOGGER.info("HELLO FROM PREINIT");
        Mettle.LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("mettle", "helloworld", () -> {
            Mettle.LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
        Mettle.LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        Mettle.LOGGER.info("HELLO from server starting");
    }
}
