package zzzank.mods.kube_jei.impl.recipe_type;

import net.minecraft.resources.ResourceLocation;
import zzzank.mods.kube_jei.impl.CustomJSRecipe;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author ZZZank
 */
public class KubeJEIRecipeTypes<T> {
    public static final KubeJEIRecipeTypes<RecipeType<CustomJSRecipe>> CUSTOM = new KubeJEIRecipeTypes<>();
    public static final KubeJEIRecipeTypes<RecipeType> OVERRIDDEN = new KubeJEIRecipeTypes<>();

    public final Map<ResourceLocation, T> raw = new TreeMap<>();

    public static RecipeType<CustomJSRecipe> getOrCreateCustom(ResourceLocation recipeType) {
        return CUSTOM.raw.computeIfAbsent(
            recipeType,
            (key) -> new RecipeType<>(key, CustomJSRecipe.class)
        );
    }

    public static <T> RecipeType<T> getOrCreateOverride(
        ResourceLocation recipeType,
        RecipeType<T> existingRecipeType
    ) {
        return OVERRIDDEN.raw.computeIfAbsent(
            recipeType,
            (key) -> new RecipeType<>(key, existingRecipeType.type())
        );
    }

    public void remove(ResourceLocation id) {
        raw.remove(id);
    }

    public T get(ResourceLocation id) {
        return raw.get(id);
    }
}
