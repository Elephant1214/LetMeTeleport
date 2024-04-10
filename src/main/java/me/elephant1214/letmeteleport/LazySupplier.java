package me.elephant1214.letmeteleport;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public final class LazySupplier<T> implements Supplier<T> {
    private final @NotNull Supplier<T> supplier;
    private @Nullable T value;

    public LazySupplier(@NotNull Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public @NotNull T get() {
        if (this.value == null) {
            this.value = supplier.get();
        }
        return this.value;
    }
}
