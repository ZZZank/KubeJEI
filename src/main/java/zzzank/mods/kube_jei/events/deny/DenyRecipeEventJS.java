package zzzank.mods.kube_jei.events.deny;

import com.google.common.collect.*;
import dev.latvian.kubejs.event.EventJS;
import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import lombok.val;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author ZZZank
 */
@JSInfo("""
    A more complete and thorough version of `jei.remove.recipes` event from KubeJS.
    
    By denying recipes at the earliest point possible (, instead of simply hiding after initialized), almost all related computation for denied recipes can be avoided""")
public class DenyRecipeEventJS extends EventJS {

    private final SetMultimap<ResourceLocation, ResourceLocation> deniedRecipeIds;
    public final Multimap<ResourceLocation, DenyPredicate> denyPredicates;

    public DenyRecipeEventJS() {
        this.deniedRecipeIds = HashMultimap.create();
        this.denyPredicates = ArrayListMultimap.create();
    }

    @JSInfo("""
        deny recipe by its recipe id and the category the recipe belongs to""")
    public void denyById(ResourceLocation categoryId, ResourceLocation... recipeIds) {
        deniedRecipeIds.putAll(Objects.requireNonNull(categoryId), Arrays.asList(recipeIds));
    }

    @JSInfo("""
        deny recipes in a category with custom filter. The `recipe` passed to your filter will be an instance whose type
        is restricted by the recipe category, or more accurately, restricted to be an instance of: `IRecipeCategory#getRecipeClass()`""")
    public void denyCustom(ResourceLocation categoryId, DenyPredicate filter) {
        denyPredicates.put(Objects.requireNonNull(categoryId), Objects.requireNonNull(filter));
    }

    public interface DenyPredicate {
        @JSInfo("""
            @param recipe recipe instance. It's usually (but not guaranteed to be) an instance of {@link net.minecraft.world.item.crafting.Recipe}.
            It's actual type is restricted by its recipe category, or more accurately, restricted to be an instance of: `IRecipeCategory#getRecipeClass()`
            @return true if you want to deny the `recipe`""")
        boolean shouldDeny(Object recipe);
    }

    @Override
    protected void afterPosted(boolean result) {
        for (val entry : deniedRecipeIds.asMap().entrySet()) {
            val categoryId = entry.getKey();
            val ids = ImmutableSet.copyOf(entry.getValue());
            denyPredicates.put(categoryId, (r) -> r instanceof Recipe<?> recipe && ids.contains(recipe.getId()));
        }
    }
}
