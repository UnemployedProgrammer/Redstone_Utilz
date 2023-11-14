package net.unemployedgames.redstoneutilz.infrastructure.registry;

import java.util.function.Supplier;

public class RedstoneUtilsRegistryObject<T> implements Supplier<T> {
    private T o;
    public T get() {
        return o;
    }

    public RedstoneUtilsRegistryObject(T obj) {
        this.o = obj;
    }
}
