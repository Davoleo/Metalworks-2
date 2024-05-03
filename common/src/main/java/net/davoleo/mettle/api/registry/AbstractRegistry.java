package net.davoleo.mettle.api.registry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

public class AbstractRegistry<T> {

    private String modId;

    private final Map<String, T> registryMap;

    AbstractRegistry(String modId)
    {
        this.modId = modId;
        registryMap = new HashMap<>();
    }

    public Collection<T> getEntries() {
        return registryMap.values();
    }

    public void forEach(BiConsumer<String, T> consumer) {
        registryMap.forEach(consumer);
    }

    public int entriesCount() {
        return registryMap.size();
    }

    public T register(String name, @NotNull T object)
    {
        Objects.requireNonNull(object, "Object cannot be null");
        Objects.requireNonNull(name, "Registry Name cannot be null");

        if(registryMap.containsKey(name))
            throw new IllegalStateException("Object " + modId + ':' + name +  " is already registered!");

        return this.registryMap.put(name, object);
    }

    @Nullable
    public T get(@NotNull String name)
    {
        Objects.requireNonNull(name,"Metal Name cannot be null");
        return this.registryMap.get(name);
    }

    public String getMod() {
        return modId;
    }

}
