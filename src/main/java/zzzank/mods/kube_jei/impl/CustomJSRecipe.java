package zzzank.mods.kube_jei.impl;

import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.impl.recipe_type.RecipeType;

import java.util.ArrayList;
import java.util.List;

public class CustomJSRecipe {
    @NotNull
    public final RecipeType<CustomJSRecipe> type;
    public Object data;

    public CustomJSRecipe(Object data, @NotNull RecipeType<CustomJSRecipe> type) {
        this.data = data;
        this.type = type;
    }

    public static class CustomRecipeListBuilder {
        @NotNull
        public final RecipeType<CustomJSRecipe> type;
        public final List<CustomJSRecipe> recipes;

        public CustomRecipeListBuilder(@NotNull RecipeType<CustomJSRecipe> type) {
            this.type = type;
            this.recipes = new ArrayList<>();
        }

        public CustomJSRecipe custom(Object recipeData) {
            var recipe = new CustomJSRecipe(recipeData, type);
            recipes.add(recipe);
            return recipe;
        }

        public CustomRecipeListBuilder add(Object recipeData) {
            return add(new CustomJSRecipe(recipeData, type));
        }

        public CustomRecipeListBuilder add(CustomJSRecipe recipe) {
            recipes.add(recipe);
            return this;
        }

        public CustomRecipeListBuilder addAll(List<Object> recipeDatas) {
            recipeDatas.forEach(this::add);
            return this;
        }
    }
}
