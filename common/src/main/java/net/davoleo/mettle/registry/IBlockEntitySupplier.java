package net.davoleo.mettle.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface IBlockEntitySupplier<T extends BlockEntity> {
    @NotNull
    T create(BlockPos pos, BlockState state);
}
