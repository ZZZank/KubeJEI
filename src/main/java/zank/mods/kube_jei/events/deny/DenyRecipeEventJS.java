package zank.mods.kube_jei.events.deny;

import com.google.common.collect.*;
import dev.latvian.mods.kubejs.typings.Info;
import lombok.val;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import zank.mods.kube_jei.events.KubeJEIEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author ZZZank
 */
@Info("""
    A more complete and thorough version of `jei.remove.recipes` event from KubeJS.
    
    By denying recipes at the earliest point possible (, instead of simply hiding after initialized), almost all related computation for denied recipes can be avoided""")
public class DenyRecipeEventJS implements KubeJEIEvent {

    private final SetMultimap<ResourceLocation, ResourceLocation> directDenied;
    private final ListMultimap<ResourceLocation, SimpleRecipeDenyPredicate> categoryDenied;
    public final List<RecipeDenyPredicate> denyPredicates;

    public DenyRecipeEventJS() {
        this.directDenied = HashMultimap.create();
        this.categoryDenied = ArrayListMultimap.create();
        this.denyPredicates = new ArrayList<>();

        //direct denied
        denyPredicates.add((recipeType, jeiRecipe) -> {
            val recipeIds = directDenied.get(recipeType.getUid());
            return jeiRecipe instanceof RecipeHolder<?> holder && recipeIds.contains(holder.id());
        });
        //defined category denied
        denyPredicates.add((recipeType, jeiRecipe) -> {
            val predicates = categoryDenied.get(recipeType.getUid());
            for (val predicate : predicates) {
                if (predicate.shouldDeny(jeiRecipe)) {
                    return true;
                }
            }
            return false;
        });
    }

    @Info("""
        deny recipe by its recipe id and the category the recipe belongs to""")
    public void denyById(@NotNull ResourceLocation categoryId, @NotNull ResourceLocation... recipeIds) {
        directDenied.putAll(Objects.requireNonNull(categoryId), Arrays.asList(recipeIds));
    }

    @Info("""
        deny all recipes in such category""")
    public void denyAllInCategory(@NotNull ResourceLocation categoryId) {
        denyCustom(Objects.requireNonNull(categoryId), (r) -> true);
    }

    @Info("""
        deny recipes in a category with custom filter. The `recipe` passed to your filter will be an instance whose type
        is restricted by the recipe category, or more accurately, restricted to be an instance of: `IRecipeCategory#getRecipeClass()`""")
    public void denyCustom(@NotNull ResourceLocation categoryId, @NotNull SimpleRecipeDenyPredicate filter) {
        categoryDenied.put(Objects.requireNonNull(categoryId), Objects.requireNonNull(filter));
    }

    @Info("""
        deny recipes with custom filter. The `recipe` passed to your filter will be an instance whose type
        is restricted by the recipe category, or more accurately, restricted to be an instance of: `IRecipeCategory#getRecipeClass()`""")
    public void denyCustom(@NotNull RecipeDenyPredicate filter) {
        denyPredicates.add(Objects.requireNonNull(filter));
    }

    /**
     * @author ZZZank
     */
    public interface RecipeDenyPredicate {
        boolean shouldDeny(RecipeType<?> recipeType, Object recipe);
    }

    /**
     * @author ZZZank
     */
    public interface SimpleRecipeDenyPredicate {
        boolean shouldDeny(Object recipe);
    }
}
