package net.davoleo.mettle.block;

import net.davoleo.mettle.api.metal.SimpleMetal;
import net.davoleo.mettle.register.CoreMetals;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.OreBlock;
import net.minecraftforge.registries.RegistryObject;

/**
 * MOreBlock!1!11!!!! -PierKnight
 */
public class MettleOreBlock extends OreBlock {

    public MettleOreBlock(Properties props, RegistryObject<SimpleMetal> metal)
    {
        super(props.strength(CoreMetals.LOCAL_METAL_CACHE.get(metal.getId()).getProperties().hardness() / 2),
                UniformInt.of(0, 2) // TODO: 30/08/2022 Customize
        );
    }
}
