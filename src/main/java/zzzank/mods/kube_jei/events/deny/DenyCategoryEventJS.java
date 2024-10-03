package zzzank.mods.kube_jei.events.deny;

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
    a more thorough and complete version of `jei.remove.categories` event from KubeJS.
    
    Instead of removing categories after JEI runtime is initialized, categories will be denied at the earliest point possible
    registration, so no computation and redundant access for the category will happen.""")
public class DenyCategoryEventJS extends EventJS {
    public final Set<ResourceLocation> deniedIds = new HashSet<>();
    public final List<Predicate<IRecipeCategory<?>>> denyPredicates = new ArrayList<>();

    public DenyCategoryEventJS() {
        denyPredicates.add(category -> deniedIds.contains(category.getUid()));
    }

    @JSInfo("""
        deny a category by its id
        
        you can get a list of categories via `runtime.recipeManager.getRecipeCategories()`, where `runtime` is IJeiRuntime""")
    public void deny(ResourceLocation... ids) {
        deniedIds.addAll(Arrays.asList(ids));
    }

    @JSInfo("""
        deny a category by your custom predicate.
        The predicate should return `true` if you want to deny such category""")
    public void denyIf(Predicate<IRecipeCategory<?>> predicate) {
        denyPredicates.add(Objects.requireNonNull(predicate));
    }
}
