package net.davoleo.mettle.init;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.IMettleIntegration;
import net.davoleo.mettle.api.MettlePack;
import net.davoleo.mettle.api.metal.Alloy;
import net.davoleo.mettle.api.metal.ComponentType;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.api.metal.SimpleMetal;
import net.davoleo.mettle.api.registry.Registry;

@MettlePack(modid = Mettle.MODID)
public class CoreMetals implements IMettleIntegration {

    public static final IMetal TIN = SimpleMetal
            .wizard("tin")
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
