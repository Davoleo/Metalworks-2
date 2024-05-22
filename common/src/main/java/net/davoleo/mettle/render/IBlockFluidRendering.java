package net.davoleo.mettle.render;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public interface IBlockFluidRendering {

    void setRenderType(Block block, RenderType renderType);

    void setRenderType(Fluid fluid, RenderType renderType);

}
