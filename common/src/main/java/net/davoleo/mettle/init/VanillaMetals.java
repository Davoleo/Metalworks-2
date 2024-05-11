package net.davoleo.mettle.init;

import net.davoleo.mettle.api.IMettleIntegration;
import net.davoleo.mettle.api.MettlePack;
import net.davoleo.mettle.api.metal.Alloy;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.api.metal.SimpleMetal;
import net.davoleo.mettle.api.registry.Registry;

@MettlePack(modid = "minecraft")
public class VanillaMetals implements IMettleIntegration {

    public static final IMetal IRON = SimpleMetal
            .wizard("iron")
            .build();

    public static final IMetal GOLD = SimpleMetal
            .wizard("gold")
            .build();

    public static final IMetal COPPER = SimpleMetal
            .wizard("copper")
            .build();

    public static final IMetal NETHERITE = SimpleMetal
            .wizard("netherite")
            .build();

    @Override
    public void registerMetals(Registry<IMetal> registry) {
        registry.register(IRON.name(), IRON);
        registry.register(GOLD.name(), GOLD);
        registry.register(COPPER.name(), COPPER);
        registry.register(NETHERITE.name(), NETHERITE);
    }

    @Override
    public void registerAlloys(Registry<Alloy> registry) {

    }
}
