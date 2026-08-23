package zank.mods.kube_jei.impl;

import mezz.jei.api.recipe.types.IRecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomJSRecipe {
    public Object data;

    public CustomJSRecipe(Object data) {
        this.data = data;
    }

    public static class CustomRecipeListBuilder {
        @NotNull
        public final IRecipeType<CustomJSRecipe> type;
        public final List<CustomJSRecipe> recipes = new ArrayList<>();

        public CustomRecipeListBuilder(@NotNull IRecipeType<CustomJSRecipe> type) {
            this.type = type;
        }

        public CustomRecipeListBuilder add(Object recipeData) {
            recipes.add(new CustomJSRecipe(recipeData));
            return this;
        }

        public CustomRecipeListBuilder add(CustomJSRecipe recipe) {
            recipes.add(recipe);
            return this;
        }

        public CustomRecipeListBuilder addAll(Object... recipes) {
            Arrays.asList(recipes).forEach(this::add);
            return this;
        }
    }
}
