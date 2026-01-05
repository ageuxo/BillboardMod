package org.ageuxo.billboardmodels.util;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SimpleRegistry<V> {

    private final ResourceLocation id;
    private final Map<ResourceLocation, V> entries = new HashMap<>();
    private final Map<V, ResourceLocation> reversed = new HashMap<>();

    public SimpleRegistry(ResourceLocation id) {
        this.id = id;
    }

    public V register(ResourceLocation location, V entry) {
        entries.put(Objects.requireNonNull(location, "Location must be non-null"), Objects.requireNonNull(entry, "Entry value must be non-null"));
        reversed.put(entry, location); // Add to reverse lookup as well
        return entry;
    }

    public V get(ResourceLocation location) {
        return entries.get(location);
    }

    public V getOrDefault(ResourceLocation location, V fallback) {
        return entries.getOrDefault(location, fallback);
    }

    public V getOrThrow(ResourceLocation location) {
        var entry = get(location);
        if (entry == null) {
            throw new IllegalStateException("Attempted to get non-registered value from %s: %s".formatted(this, location));
        }

        return entry;
    }

    public ResourceLocation getLocation(V entry) {
        return reversed.get(entry);
    }

    public boolean containsKey(ResourceLocation location) {
        return entries.containsKey(location);
    }

    public boolean containsEntry(V entry) {
        return entries.containsValue(entry);
    }


    @Override
    public String toString() {
        return "SimpleRegistry{" +
                "id=" + id +
                ", entries=" + entries +
                '}';
    }
}
