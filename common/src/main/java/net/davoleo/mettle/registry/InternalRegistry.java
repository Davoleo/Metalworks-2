package net.davoleo.mettle.registry;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.registry.Registry;

public class InternalRegistry<T> extends Registry<InternalRegistry.Entry<T>> {

    public InternalRegistry() {
        super(Mettle.MODID);
    }

    public static class RegistryKey {
        private RegistryKey(){}
    }
    private static final RegistryKey TO_REGISTRY = new RegistryKey();


    public void merge(Registry<T> other) {

        String mod = other.getMod();
        var map = other.getRegistryMap(TO_REGISTRY);

        map.forEach((k, v) -> {
            if (!this.has(k)) {
                this.register(k, new Entry<>(mod, v));
            }
            else {
                Mettle.LOGGER.warn("Skipping registration of metal: {}:{} because it was already registered", mod, k);
            }
        });
    }

    public record Entry<T>(String mod, T value) { }

}
