package zzzank.mods.kube_jei.events;

import dev.latvian.kubejs.event.EventJS;
import mezz.jei.api.helpers.IJeiHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import zzzank.mods.kube_jei.impl.RecipeType;
import zzzank.mods.kube_jei.impl.CustomJSRecipe;

import java.util.Map;
import java.util.TreeMap;

public class JEIEventJS extends EventJS {
    public static final Map<ResourceLocation, RecipeType<CustomJSRecipe>> customRecipeTypes = new TreeMap<>();
    public static final Map<ResourceLocation, RecipeType> overriddenRecipeTypes = new TreeMap<>();
    public static IJeiHelpers JEI_HELPERS;

    public static void removeCustomRecipeType(ResourceLocation recipeType) {
        customRecipeTypes.remove(recipeType);
    }

    public static void removeOverriddenRecipeType(ResourceLocation recipeType) {
        overriddenRecipeTypes.remove(recipeType);
    }

    public static RecipeType<CustomJSRecipe> getCustomRecipeType(ResourceLocation recipeType) {
        return customRecipeTypes.get(recipeType);
    }

    public static RecipeType<?> getOverriddenRecipeType(ResourceLocation recipeType) {
        return overriddenRecipeTypes.get(recipeType);
    }

    public static RecipeType<CustomJSRecipe> getOrCreateCustomRecipeType(ResourceLocation recipeType) {
        customRecipeTypes.computeIfAbsent(
            recipeType,
            (key) -> RecipeType.create(key, CustomJSRecipe.class)
        );
        return customRecipeTypes.get(recipeType);
    }

    public static <T> RecipeType<T> getOrCreateCustomOverriddenRecipeType(
        ResourceLocation recipeType,
        RecipeType<T> existingRecipeType
    ) {
        return overriddenRecipeTypes.computeIfAbsent(
            recipeType,
            (key) -> RecipeType.create(key, existingRecipeType.type())
        );
    }

    public IJeiHelpers getJeiHelpers() {
        return JEI_HELPERS;
    }

    public Minecraft getMc() {
        return Minecraft.getInstance();
    }
}
