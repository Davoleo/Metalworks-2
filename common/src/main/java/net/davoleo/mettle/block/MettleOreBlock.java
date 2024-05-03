package net.davoleo.mettle.block;

import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.util.PropertiesStore;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.OreBlock;

/**
 * MOreBlock!1!11!!!! -PierKnight
 */
public class MettleOreBlock extends OreBlock {

    public MettleOreBlock(IMetal metal)
    {
        // TODO: 30/08/2022 Customize
        super(PropertiesStore.oreProperties(metal), UniformInt.of(0, 2));
    }
}
