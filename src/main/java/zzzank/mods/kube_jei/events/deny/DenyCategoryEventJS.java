package zzzank.mods.kube_jei.events.deny;

import dev.latvian.kubejs.event.EventJS;
import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.KubeJEIEvents;

import java.util.*;

/**
 * @author ZZZank
 */
@JSInfo("""
    a more thorough and complete version of `jei.remove.categories` event from KubeJS.
    
    Instead of removing categories after JEI runtime is initialized, categories will be denied at the earliest point possible
    , so no computation and redundant access for the category will happen.""")
public class DenyCategoryEventJS extends EventJS {

    public final Set<ResourceLocation> deniedIds = new HashSet<>();
    public final List<CategoryDenyPredicate> denyPredicates = new ArrayList<>();

    public DenyCategoryEventJS() {
        denyPredicates.add(category -> deniedIds.contains(category.getUid()));
    }

    @JSInfo("""
        deny category by its id
        
        keep in mind that recipes for this category will still ba passed to JEI, you might need to deny these recipes as
        well in `""" + KubeJEIEvents.DENY_RECIPES + """
        ` event.

        you can get a list of categories via `runtime.recipeManager.getRecipeCategories()`, where `runtime` is IJeiRuntime""")
    public void deny(@NotNull ResourceLocation... ids) {
        deniedIds.addAll(Arrays.asList(ids));
    }

    @JSInfo("""
        deny a category by your custom predicate.

        The predicate should return `true` if you want to deny such category""")
    public void denyIf(@NotNull CategoryDenyPredicate predicate) {
        denyPredicates.add(Objects.requireNonNull(predicate));
    }
}
