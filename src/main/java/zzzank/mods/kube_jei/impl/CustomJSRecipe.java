package zzzank.mods.kube_jei.impl;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.impl.recipe_type.RecipeType;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomJSRecipe {
    @NotNull
    private final RecipeType<CustomJSRecipe> recipeType;
	@Setter
    private Object recipeData;

    public CustomJSRecipe(Object recipeData, @NotNull RecipeType<CustomJSRecipe> recipeType) {
        this.recipeData = recipeData;
        this.recipeType = recipeType;
    }

	@Getter
    public static class CustomRecipeListBuilder {
        @NotNull
        private final RecipeType<CustomJSRecipe> recipeType;
        private final List<CustomJSRecipe> recipes;

        public CustomRecipeListBuilder(@NotNull RecipeType<CustomJSRecipe> recipeType) {
            this.recipeType = recipeType;
            this.recipes = new ArrayList<>();
        }

        public CustomJSRecipe custom(Object recipeData) {
            var recipe = new CustomJSRecipe(recipeData, recipeType);
            recipes.add(recipe);
            return recipe;
        }

        public CustomRecipeListBuilder add(Object recipeData) {
            recipes.add(new CustomJSRecipe(recipeData, recipeType));
            return this;
        }

        public CustomRecipeListBuilder add(CustomJSRecipe recipe) {
            recipes.add(recipe);
            return this;
        }

        public CustomRecipeListBuilder addAll(List<Object> recipeData) {
            this.recipes.addAll(recipeData.stream().map(data -> new CustomJSRecipe(data, recipeType)).toList());
            return this;
        }
    }
}
