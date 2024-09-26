package zzzank.mods.kube_jei.util;

import lombok.val;

import java.util.function.Supplier;

/**
 *
 * @author ZZZank
 */
public class Lazy<T> implements Supplier<T> {

    private final Supplier<T> supplier;
    private T cached = null;
    private boolean provided = false;
    private Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<T>(supplier);
    }

    @Override
    public T get() {
        return provided ? cached : forceRefresh();
    }

    /**
     * @return newly created value
     */
    public T forceRefresh() {
        provided = true;
        return cached = supplier.get();
    }

    /**
     * @return last value
     */
    public T forget() {
        val old = cached;
        cached = null;
        provided = false;
        return old;
    }
}
