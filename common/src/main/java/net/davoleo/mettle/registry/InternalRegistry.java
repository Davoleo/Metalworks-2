package net.davoleo.mettle.registry;

import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.api.registry.Registry;

public class InternalRegistry<T> extends Registry<InternalRegistry.Entry<T>> {

    public InternalRegistry() {
        super(Mettle.MODID);
    }

    public void merge(Registry<T> other) {
        String mod = other.getMod();
        other.forEach((name, value) -> this.register(name, new Entry<>(mod, value)));
    }

    public record Entry<T>(String mod, T value) { }

}
