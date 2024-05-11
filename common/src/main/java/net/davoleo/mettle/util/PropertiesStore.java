package net.davoleo.mettle.util;

import net.davoleo.mettle.api.metal.IMetal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class PropertiesStore {

    public static BlockBehaviour.Properties oreProperties(IMetal metal) {
        return Block.Properties.of(Material.STONE)
                .destroyTime(1.5F); //TODO: maybe customize
    }

}
