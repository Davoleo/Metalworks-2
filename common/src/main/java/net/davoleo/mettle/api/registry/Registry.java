package net.davoleo.mettle.api.registry;

import com.google.common.collect.Streams;
import net.davoleo.mettle.registry.InternalRegistry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

public class Registry<T> implements Iterable<T> {

    public interface Nameable {
        String name();
    }

    private final String modId;

    protected final Map<String, T> registryMap;

    public Registry(String modId)
    {
        this.modId = modId;
        registryMap = new HashMap<>();
    }

    public Collection<T> getEntries() {
        return registryMap.values();
    }

    @ApiStatus.Internal
    public Map<String, T> getRegistryMap(InternalRegistry.RegistryKey key) {
        if (key == null)
            throw new IllegalCallerException("getRegistryMap can only be called by InternalRegistry");
        return registryMap;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return getEntries().iterator();
    }

    public int entriesCount() {
        return registryMap.size();
    }

    public Stream<T> stream() {
        return Streams.stream(this);
    }

    public T register(T value)
    {
        if (!(value instanceof Nameable v)) {
            throw new IllegalArgumentException("Implicitly named register, only works with Nameable objects");
        }

        if (registryMap.containsKey(v.name())) {
            throw new IllegalStateException("Duplicate registration");
        }

        return this.registryMap.put(v.name(), value);
    }

    public T register(String name, @NotNull T object)
    {
        Objects.requireNonNull(object, "Object cannot be null");
        Objects.requireNonNull(name, "Registry Name cannot be null");

        if(registryMap.containsKey(name))
            throw new IllegalStateException("Object " + modId + ':' + name +  " is already registered!");

        return this.registryMap.put(name, object);
    }

    public T get(@NotNull String name)
    {
        Objects.requireNonNull(name,"Metal Name cannot be null");
        if (!registryMap.containsKey(name))
            throw new NoSuchElementException("Metal " + modId + ':' + name +  " not found");
        return this.registryMap.get(name);
    }

    public boolean has(@NotNull String name) {
        Objects.requireNonNull(name,"Metal Name cannot be null");
        return this.registryMap.containsKey(name);
    }

    public String getMod() {
        return modId;
    }

}
