package zzzank.mods.kube_jei.impl;

import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.advanced.IRecipeManagerPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.impl.builder.CustomRecipeManagerPluginBuilder;

import java.util.List;

/**
 * @author ZZZank
 */
public class CustomRecipeManagerPlugin implements IRecipeManagerPlugin {

    private final CustomRecipeManagerPluginBuilder builder;

    public CustomRecipeManagerPlugin(CustomRecipeManagerPluginBuilder builder) {
        this.builder = builder;
    }

    public static CustomRecipeManagerPluginBuilder builder() {
        return new CustomRecipeManagerPluginBuilder();
    }

    @Override
    public <V> @NotNull List<ResourceLocation> getRecipeCategoryUids(@NotNull IFocus<V> focus) {
        return builder.getUidMatcher().getRecipeCategoryUids(focus.getMode(), focus.getValue());
    }

    @Override
    public <T, V> @NotNull List<T> getRecipes(
        @NotNull IRecipeCategory<T> recipeCategory,
        @NotNull IFocus<V> focus
    ) {
        return builder.getRecipeMatcher().getRecipes(recipeCategory, focus.getMode(), focus.getValue());
    }

    @Override
    public <T> @NotNull List<T> getRecipes(@NotNull IRecipeCategory<T> recipeCategory) {
        return builder.getNoFocusRecipeMatcher().getRecipes(recipeCategory);
    }

    @FunctionalInterface
    public interface UidMatcher {
        @NotNull List<ResourceLocation> getRecipeCategoryUids(IFocus.Mode mode, Object value);
    }

    @FunctionalInterface
    public interface RecipeMatcher {
        <T> List<T> getRecipes(IRecipeCategory<T> category, IFocus.Mode mode, Object value);
    }

    @FunctionalInterface
    public interface NoFocusRecipeMatcher {
        <T> List<T> getRecipes(IRecipeCategory<T> category);
    }
}
