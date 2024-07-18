package net.davoleo.mettle;

import com.mojang.logging.LogUtils;
import net.davoleo.mettle.init.ModRegistry;
import net.davoleo.mettle.registry.IMettleRegistry;
import net.davoleo.mettle.render.IBlockFluidRendering;
import net.davoleo.mettle.util.IPlatformUtils;
import net.minecraft.client.renderer.RenderType;
import org.slf4j.Logger;

import java.util.ServiceLoader;

public class Mettle {

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "mettle";
    public static final String MODNAME = MODID;

    //Services
    public final static IMettleRegistry REGISTRY = ServiceLoader.load(IMettleRegistry.class).findFirst().orElseThrow();
    public final static IPlatformUtils PLATFORM_UTILS = ServiceLoader.load(IPlatformUtils.class).findFirst().orElseThrow();
    public final static IBlockFluidRendering BLOCK_FLUID_RENDERING = ServiceLoader.load(IBlockFluidRendering.class).findFirst().orElseThrow();

    public static void clientSetup() {
        ModRegistry.METAL_COMPONENTS.values().forEach(
                components -> components.ores().values().forEach(
                        ore -> BLOCK_FLUID_RENDERING.setRenderType(ore.get(), RenderType.cutout())
                )
        );
    }
}
