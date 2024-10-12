package zzzank.mods.kube_jei.events.deny;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import mezz.jei.api.recipe.category.IRecipeCategory;

/**
 * @author ZZZank
 */
public interface CategoryDenyPredicate {

    @JSInfo("""
        @param recipeCategory recipe category passed to JEI, invalid categories(null category or category with null id
        or null recipe class) have been filtered automatically
        @return true if you want to deny this category""")
    boolean shouldDeny(IRecipeCategory<?> recipeCategory);
}
