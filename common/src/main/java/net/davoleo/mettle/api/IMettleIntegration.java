package net.davoleo.mettle.api;

import net.davoleo.mettle.api.metal.Alloy;
import net.davoleo.mettle.api.metal.IMetal;
import net.davoleo.mettle.api.registry.Registry;

public interface IMettleIntegration {

    void registerMetals(Registry<IMetal> registry);

    void registerAlloys(Registry<Alloy> registry);

}
