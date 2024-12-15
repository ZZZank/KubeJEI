package zzzank.mods.kube_jei.impl;

import dev.latvian.kubejs.util.ConsoleJS;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.advanced.IRecipeManagerPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.impl.builder.CustomRecipeManagerPluginBuilder;

import java.util.Collections;
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
        if (builder.uidMatcher == null) {
            return Collections.emptyList();
        }
        try {
            return builder.uidMatcher.getRecipeCategoryUids(focus.getMode(), focus.getValue());
        } catch (Exception e) {
            ConsoleJS.CLIENT.error(String.format(
                "Exception happened on providing recipe categories when focusing %s '%s'",
                focus.getMode(),
                focus.getValue()
            ), e);
            return Collections.emptyList();
        }
    }

    @Override
    public <T, V> @NotNull List<T> getRecipes(
        @NotNull IRecipeCategory<T> recipeCategory, @NotNull IFocus<V> focus
    ) {
        if (builder.recipeMatcher == null) {
            return Collections.emptyList();
        }
        try {
            return builder.recipeMatcher.getRecipes(recipeCategory, focus.getMode(), focus.getValue());
        } catch (Exception e) {
            ConsoleJS.CLIENT.error(String.format(
                "Exception happened on providing recipe for category '%s' when focusing %s '%s'",
                recipeCategory.getUid(),
                focus.getMode(),
                focus.getValue()
            ), e);
            return Collections.emptyList();
        }
    }

    @Override
    public <T> @NotNull List<T> getRecipes(@NotNull IRecipeCategory<T> recipeCategory) {
        if (builder.noFocusRecipeMatcher == null) {
            return Collections.emptyList();
        }
        try {
            return builder.noFocusRecipeMatcher.getRecipes(recipeCategory);
        } catch (Exception e) {
            ConsoleJS.CLIENT.error(
                String.format(
                    "Exception happened on providing recipe for category '%s'",
                    recipeCategory.getUid()
                ),
                e
            );
            return Collections.emptyList();
        }
    }
}
