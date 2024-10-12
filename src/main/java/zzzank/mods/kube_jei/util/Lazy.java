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
        return provided ? cached : refresh();
    }

    /**
     * @return newly created value
     */
    public T refresh() {
        provided = true;
        return cached = supplier.get();
    }

    public void forget() {
        cached = null;
        provided = false;
    }

    /**
     * @return last value
     */
    public T pop() {
        val old = cached;
        forget();
        return old;
    }
}
