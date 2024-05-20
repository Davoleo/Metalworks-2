package net.davoleo.mettle.registry;

import java.util.function.Supplier;

public record RegistryEntry<T>(String name, CachingSupplier<T> entry) {

    public static <T> RegistryEntry<T> of(String name, Supplier<T> entry) {
        return new RegistryEntry<>(name, new CachingSupplier<>(entry));
    }

    public static class CachingSupplier<T> implements Supplier<T> {

        private final Supplier<T> delegate;
        private T value;

        public CachingSupplier(Supplier<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public T get() {
            if (value == null) {
                value = delegate.get();
            }
            return value;
        }
    }
}
