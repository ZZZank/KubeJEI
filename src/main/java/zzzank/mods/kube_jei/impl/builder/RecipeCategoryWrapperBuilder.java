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
        this.setDrawHandler(recipeCategory::draw);
        this.setIsRecipeHandledByCategory(recipeCategory::isHandled);
        this.setRecipeSetHandler(recipeCategory::setRecipe);
        this.setTooltipHandler(recipeCategory::getTooltipStrings);
        this.setInputHandler(recipeCategory::handleClick);
        this.setTitle(Text.of(recipeCategory.getTitleAsTextComponent()));
        this.setBackground(recipeCategory.getBackground());
        this.setIcon(recipeCategory.getIcon());
        this.handleLookup(recipeCategory::setRecipe);
    }
}

