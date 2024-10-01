package zzzank.mods.kube_jei.events;

import dev.latvian.kubejs.event.EventJS;
import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.resources.ResourceLocation;

import java.util.*;
import java.util.function.Predicate;

/**
 * @author ZZZank
 */
@JSInfo("""
    a more thorough and complete version of 'jei.remove.categories'.
    
    Instead of removing categories after JEI runtime is initialized, banned categories will be denied at the earliest
    registration, so no computation and redundant access for the category will happen.""")
public class DenyCategoryEventJS extends EventJS {
    public final Set<ResourceLocation> DIRECT_DENIED = new HashSet<>();

    @JSInfo("""
        for every predicate in this list, returns true means this recipe category should be denied""")
    public final List<Predicate<IRecipeCategory<?>>> denys = new ArrayList<>();

    public DenyCategoryEventJS() {
        denys.add(category -> DIRECT_DENIED.contains(category.getUid()));
    }

    @JSInfo("""
        deny a category whose id is one of provided ids""")
    public void deny(ResourceLocation... ids) {
        DIRECT_DENIED.addAll(Arrays.asList(ids));
    }

    @JSInfo("""
        deny a category by your custom predicate.
        The predicate should return `true` if you want to deny such category""")
    public void denyIf(Predicate<IRecipeCategory<?>> predicate) {
        denys.add(Objects.requireNonNull(predicate));
    }
}
