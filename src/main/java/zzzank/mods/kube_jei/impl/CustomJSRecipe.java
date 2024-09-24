package zzzank.mods.kube_jei.impl;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.impl.recipe_type.RecipeType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CustomJSRecipe {
    @NotNull
    private final RecipeType<CustomJSRecipe> type;
    @Setter
    private Object data;

    public CustomJSRecipe(Object data, @NotNull RecipeType<CustomJSRecipe> type) {
        this.data = data;
        this.type = type;
    }

    @Getter
    public static class CustomRecipeListBuilder {
        @NotNull
        private final RecipeType<CustomJSRecipe> type;
        private final List<CustomJSRecipe> recipes;

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
            recipes.add(new CustomJSRecipe(recipeData, type));
            return this;
        }

        public CustomRecipeListBuilder add(CustomJSRecipe recipe) {
            recipes.add(recipe);
            return this;
        }

        public CustomRecipeListBuilder addAll(List<Object> recipeData) {
            this.recipes.addAll(
                recipeData.stream()
                    .map(data -> new CustomJSRecipe(data, type))
                    .collect(Collectors.toList())
            );
            return this;
        }
    }
}
