package net.davoleo.mettle.block.entity;

import net.davoleo.mettle.init.MettleBEs;
import net.davoleo.mettle.multiblock.MultiblockControllerBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;


public class ForgeBlockEntity extends MultiblockControllerBE {

    public ForgeBlockEntity(BlockPos pos, BlockState blockState) {
        super(MettleBEs.FORGE_BE_TYPE.get(), pos, blockState);
    }

    @Override
    protected boolean isValid() {
        return false;
    }

    @Override
    protected void assemble() {
    }

    @Override
    protected void disassemble() {
    }
}
