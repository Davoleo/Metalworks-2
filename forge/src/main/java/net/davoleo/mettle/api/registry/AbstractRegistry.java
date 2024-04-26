package net.davoleo.mettle.api.registry;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

public abstract class AbstractRegistry<T> {

    private final Map<ResourceLocation, T> registryMap;

    AbstractRegistry()
    {
        registryMap = new HashMap<>();
    }

    public Collection<T> getEntries() {
        return registryMap.values();
    }

    public void forEach(BiConsumer<ResourceLocation, T> consumer) {
        registryMap.forEach(consumer);
    }

    public int entriesCount() {
        return registryMap.size();
    }

    public T register(ResourceLocation id, @Nonnull T object)
    {

        Objects.requireNonNull(object, "Object cannot be null");
        Objects.requireNonNull(id, "Object ID cannot be null");

        if(registryMap.containsKey(id))
            throw new IllegalStateException("Object is already registered!");

        return this.registryMap.put(id, object);
    }

    @Nullable
    public T getMetal(@Nonnull ResourceLocation name)
    {
        Objects.requireNonNull(name,"Metal Name cannot be null");
        return this.registryMap.get(name);
    }

}
