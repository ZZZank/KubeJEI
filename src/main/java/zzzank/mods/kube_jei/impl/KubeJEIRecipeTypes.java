package zzzank.mods.kube_jei.impl;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author ZZZank
 */
public interface KubeJEIRecipeTypes {
    Map<ResourceLocation, RecipeType<CustomJSRecipe>> CUSTOM = new TreeMap<>();
    Map<ResourceLocation, RecipeType> OVERRIDDEN = new TreeMap<>();

    static void removeCustomRecipeType(ResourceLocation recipeType) {
        CUSTOM.remove(recipeType);
    }

    static void removeOverriddenRecipeType(ResourceLocation recipeType) {
        OVERRIDDEN.remove(recipeType);
    }

    static RecipeType<CustomJSRecipe> getCustomRecipeType(ResourceLocation recipeType) {
        return CUSTOM.get(recipeType);
    }

    static RecipeType<?> getOverriddenRecipeType(ResourceLocation recipeType) {
        return OVERRIDDEN.get(recipeType);
    }

    static RecipeType<CustomJSRecipe> getOrCreateCustomRecipeType(ResourceLocation recipeType) {
        return CUSTOM.computeIfAbsent(
            recipeType,
            (key) -> RecipeType.create(key, CustomJSRecipe.class)
        );
    }

    static <T> RecipeType<T> getOrCreateCustomOverriddenRecipeType(
        ResourceLocation recipeType,
        RecipeType<T> existingRecipeType
    ) {
        return OVERRIDDEN.computeIfAbsent(
            recipeType,
            (key) -> RecipeType.create(key, existingRecipeType.type())
        );
    }
}
