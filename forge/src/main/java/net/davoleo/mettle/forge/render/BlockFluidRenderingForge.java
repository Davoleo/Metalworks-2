package net.davoleo.mettle.forge.render;

import net.davoleo.mettle.render.IBlockFluidRendering;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class BlockFluidRenderingForge implements IBlockFluidRendering {

    @Override
    public void setRenderType(Block block, RenderType renderType) {
        ItemBlockRenderTypes.setRenderLayer(block, renderType);
    }

    @Override
    public void setRenderType(Fluid fluid, RenderType renderType) {
        ItemBlockRenderTypes.setRenderLayer(fluid, renderType);
    }
}
