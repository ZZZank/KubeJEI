package zzzank.mods.kube_jei.impl.builder;

import dev.latvian.kubejs.text.Text;
import lombok.Getter;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.IRecipeCategory;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.impl.recipe_type.RecipeType;

@Getter
public class RecipeCategoryWrapperBuilder<T> extends RecipeCategoryBuilder<T> {
    private final IRecipeCategory<T> sourceCategory;

    public RecipeCategoryWrapperBuilder(
        @NotNull RecipeType<T> recipeType,
        @NotNull IJeiHelpers jeiHelpers,
        @NotNull IRecipeCategory<T> recipeCategory
    ) {
        super(recipeType, jeiHelpers);
        this.sourceCategory = recipeCategory;
        this.setDrawHandler(recipeCategory::draw)
            .setIsRecipeHandledByCategory(recipeCategory::isHandled)
            .setRecipeSetHandler(recipeCategory::setRecipe)
            .setTooltipHandler(recipeCategory::getTooltipStrings)
            .setInputHandler(recipeCategory::handleClick)
            .setTitle(Text.of(recipeCategory.getTitleAsTextComponent()))
            .setBackground(recipeCategory.getBackground())
            .setIcon(recipeCategory.getIcon())
            .handleLookup(recipeCategory::setRecipe)
            .fillIngredients(recipeCategory::setIngredients);
    }
}

