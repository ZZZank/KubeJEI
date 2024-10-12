package zzzank.mods.kube_jei.events.deny;

import com.google.common.collect.*;
import dev.latvian.kubejs.event.EventJS;
import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import lombok.val;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author ZZZank
 */
@JSInfo("""
    A more complete and thorough version of `jei.remove.recipes` event from KubeJS.
    
    By denying recipes at the earliest point possible (, instead of simply hiding after initialized), almost all related computation for denied recipes can be avoided""")
public class DenyRecipeEventJS extends EventJS {

    private final SetMultimap<ResourceLocation, ResourceLocation> directDenied;
    private final ListMultimap<ResourceLocation, SimpleRecipeDenyPredicate> categoryDenied;
    public final List<RecipeDenyPredicate> denyPredicates;

    public DenyRecipeEventJS() {
        this.directDenied = HashMultimap.create();
        this.categoryDenied = ArrayListMultimap.create();
        this.denyPredicates = new ArrayList<>();

        //direct denied
        denyPredicates.add((categoryId, jeiRecipe) -> {
            val recipeIds = directDenied.get(categoryId);
            return recipeIds != null && jeiRecipe instanceof Recipe<?> recipe && recipeIds.contains(recipe.getId());
        });
        //defined category denied
        denyPredicates.add((categoryId, jeiRecipe) -> {
            val predicates = categoryDenied.get(categoryId);
            if (predicates == null) {
                return false;
            }
            for (val predicate : predicates) {
                if (predicate.shouldDeny(jeiRecipe)) {
                    return true;
                }
            }
            return false;
        });
    }

    @JSInfo("""
        deny recipe by its recipe id and the category the recipe belongs to""")
    public void denyById(ResourceLocation categoryId, ResourceLocation... recipeIds) {
        directDenied.putAll(Objects.requireNonNull(categoryId), Arrays.asList(recipeIds));
    }

    @JSInfo("""
        deny all recipes in such category""")
    public void denyAllInCategory(@NotNull ResourceLocation categoryId) {
        denyCustom(Objects.requireNonNull(categoryId), SimpleRecipeDenyPredicate.ALWAYS_DENY);
    }

    @JSInfo("""
        deny recipes in a category with custom filter. The `recipe` passed to your filter will be an instance whose type
        is restricted by the recipe category, or more accurately, restricted to be an instance of: `IRecipeCategory#getRecipeClass()`""")
    public void denyCustom(ResourceLocation categoryId, SimpleRecipeDenyPredicate filter) {
        categoryDenied.put(Objects.requireNonNull(categoryId), Objects.requireNonNull(filter));
    }

    @JSInfo("""
        deny recipes with custom filter. The `recipe` passed to your filter will be an instance whose type
        is restricted by the recipe category, or more accurately, restricted to be an instance of: `IRecipeCategory#getRecipeClass()`""")
    public void denyCustom(RecipeDenyPredicate filter) {
        denyPredicates.add(Objects.requireNonNull(filter));
    }
}
