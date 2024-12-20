package zzzank.mods.kube_jei.impl.builder;

import dev.latvian.kubejs.text.Text;
import lombok.Getter;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.IRecipeCategory;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.impl.recipe_type.RecipeType;

public class RecipeCategoryWrapperBuilder<T> extends RecipeCategoryBuilder<T> {
    public final IRecipeCategory<T> sourceCategory;

    public RecipeCategoryWrapperBuilder(
        @NotNull RecipeType<T> recipeType,
        @NotNull IJeiHelpers jeiHelpers,
        @NotNull IRecipeCategory<T> existingCategory
    ) {
        super(recipeType, jeiHelpers);
        this.sourceCategory = existingCategory;
        setDrawHandler(existingCategory::draw);
        setRecipeHandlePredicate(existingCategory::isHandled);
        setRecipeSetHandler(existingCategory::setRecipe);
        setTooltipHandler(existingCategory::getTooltipStrings);
        setInputHandler(existingCategory::handleClick);
        setTitle(Text.of(existingCategory.getTitleAsTextComponent()));
        setBackground(existingCategory.getBackground());
        setIcon(existingCategory.getIcon());
        fillIngredients(existingCategory::setIngredients);
    }
}

