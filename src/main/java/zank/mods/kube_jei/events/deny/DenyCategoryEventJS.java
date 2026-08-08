package zank.mods.kube_jei.events.deny;

import dev.latvian.mods.kubejs.typings.Info;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import zank.mods.kube_jei.events.KubeJEIEvent;
import zank.mods.kube_jei.impl.CustomRecipeCategory;

import java.util.*;

/**
 * @author ZZZank
 */
@Info("""
    a more thorough and complete version of `removeCategories` event from KubeJS.
    
    Instead of removing categories after JEI runtime is initialized, categories will be denied at the earliest point possible
    , so no computation and redundant access for the category will happen.""")
public class DenyCategoryEventJS implements KubeJEIEvent {

    public final Set<ResourceLocation> deniedIds = new HashSet<>();
    public final Set<ResourceLocation> deniedNonCustom = new HashSet<>();
    public final List<CategoryDenyPredicate> denyPredicates = new ArrayList<>();

    public DenyCategoryEventJS() {
        denyPredicates.add(category -> deniedIds.contains(category.getRecipeType().getUid()));
        denyPredicates.add(category ->
            !(category instanceof CustomRecipeCategory) && deniedNonCustom.contains(category.getRecipeType().getUid())
        );
    }

    @Info("""
        deny category by its id

        keep in mind that recipes for this category will still ba passed to JEI, you might need to deny these recipes as
        well in `denyRecipes` event (`event.denyAllInCategory(...)`).

        you can get a list of categories via `runtime.recipeManager.getRecipeCategories()`, where `runtime` is IJeiRuntime""")
    public void deny(@NotNull ResourceLocation... ids) {
        deniedIds.addAll(Arrays.asList(ids));
    }

    @Info("""
        deny a category by your custom predicate.

        The predicate should return `true` if you want to deny such category""")
    public void denyIf(@NotNull CategoryDenyPredicate predicate) {
        denyPredicates.add(Objects.requireNonNull(predicate));
    }

    @Info("""
        deny a category with such id, BUT allowing custom recipe category with such id to register itself
        
        usually useful for replacing recipe category with custom implementation""")
    public void denyNonCustom(@NotNull ResourceLocation id) {
        deniedNonCustom.add(Objects.requireNonNull(id));
    }

    /**
     * @author ZZZank
     */
    public interface CategoryDenyPredicate {
        CategoryDenyPredicate ALWAYS_DENY = (recipeCategory) -> true;
        CategoryDenyPredicate ALWAYS_ALLOW = (recipeCategory) -> false;

        @Info("""
            @param recipeCategory recipe category passed to JEI, invalid categories(null category or category with null id
            or null recipe class) have been filtered automatically
            @return true if you want to deny this category""")
        boolean shouldDeny(IRecipeCategory<?> recipeCategory);
    }
}
