package net.davoleo.mettle.init;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.IMettleIntegration;
import net.davoleo.mettle.api.MettlePack;
import net.davoleo.mettle.api.block.OreVariant;
import net.davoleo.mettle.api.metal.Alloy;
import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.api.metal.SimpleMetal;
import net.davoleo.mettle.api.registry.Registry;
import net.minecraft.resources.ResourceLocation;

@MettlePack(modid = Mettle.MODID)
public class CoreMetals implements IMettleIntegration {

    public static final IMetal TIN = SimpleMetal
            .wizard("tin")
            .enchantability(2)
            .toolStats(it -> it
                    .attackSpeed(4)
                    .harvestLevel(4)
                    .damage(1)
                    .efficiency(1)
            )
            .armorStats(b -> b
                    .movementSpeed(2)
                    .maxHealth(4)
                    .knockbackResistance(2)
                    .damageReduction(1,1,1,1)
            )
            .oreVariants(OreVariant.DEEPSLATE,OreVariant.NETHERRAK,OreVariant.END_STONE, new OreVariant("aboba", new ResourceLocation("block/dirt"), "shovel"))
            .component(ComponentType.ORE)
            .build();

    @Override
    public void registerMetals(Registry<IMetal> registry) {
        registry.register(TIN.name(), TIN);
    }

    @Override
    public void registerAlloys(Registry<Alloy> registry) {

    }
}
