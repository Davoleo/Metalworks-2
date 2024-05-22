package net.davoleo.mettle;

import com.mojang.logging.LogUtils;
import net.davoleo.mettle.block.MettleOreBlock;
import net.davoleo.mettle.init.ModRegistry;
import net.davoleo.mettle.registry.IMettleRegistry;
import net.davoleo.mettle.render.IBlockFluidRendering;
import net.davoleo.mettle.util.IPlatformUtils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;

public class Mettle {

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "mettle";
    public static final String MODNAME = MODID;

    public static IMettleRegistry registry;
    public static IPlatformUtils platformUtils;
    public static IBlockFluidRendering blockFluidRendering;

    public static void clientSetup() {
        ModRegistry.BLOCKS.forEach(entry -> {
            Block block = entry.entry().get();
            if (block instanceof MettleOreBlock) {
                blockFluidRendering.setRenderType(block, RenderType.cutout());
            }
        });
    }
}
