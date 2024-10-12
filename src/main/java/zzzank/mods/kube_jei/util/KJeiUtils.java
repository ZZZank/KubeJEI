package zzzank.mods.kube_jei.util;

import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

/**
 * @author ZZZank
 */
public interface KJeiUtils {

    static <I, O> ArrayList<O> mapToList(@NotNull I[] elements, @NotNull Function<I, O> elementMapper) {
        val mapped = new ArrayList<O>(elements.length);
        for (val element : elements) {
            mapped.add(elementMapper.apply(element));
        }
        return mapped;
    }

    static <I, O> ArrayList<O> mapToList(@NotNull Collection<I> elements, @NotNull Function<I, O> elementMapper) {
        val mapped = new ArrayList<O>(elements.size());
        for (val element : elements) {
            mapped.add(elementMapper.apply(element));
        }
        return mapped;
    }
}
