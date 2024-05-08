package net.davoleo.mettle.api;

import net.davoleo.mettle.api.metal.Alloy;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.api.registry.AbstractRegistry;

public interface IMettleIntegration {

    void registerMetals(AbstractRegistry<IMetal> registry);

    void registerAlloys(AbstractRegistry<Alloy> registry);

}
