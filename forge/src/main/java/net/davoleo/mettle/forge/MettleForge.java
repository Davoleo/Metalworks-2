package net.davoleo.mettle.forge;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.forge.init.ClientSetup;
import net.davoleo.mettle.forge.init.MettlePackFinder;
import net.davoleo.mettle.forge.init.MettleRegistryForge;
import net.davoleo.mettle.init.ModRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;

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
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        modBus.addListener(this::setup);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modBus.addListener(ClientSetup::setup));

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        MettlePackFinder.doTheStuff();
        ModRegistry.init();
        MettleRegistryForge.registerOnBus(modBus);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        Mettle.LOGGER.info("HELLO FROM {}", Mettle.MODNAME);
    }
}
