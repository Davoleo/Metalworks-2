package net.davoleo.mettle.registry;

import java.util.function.Supplier;

public record RegistryEntry<T>(String name, Supplier<T> entry) {
}
