package zzzank.mods.kube_jei.events;

import lombok.val;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.resources.ResourceLocation;
import zzzank.mods.kube_jei.impl.recipe_type.RecipeType;
import zzzank.mods.kube_jei.impl.recipe_type.KubeJEIRecipeTypes;
import zzzank.mods.kube_jei.impl.builder.RecipeCategoryBuilder;
import zzzank.mods.kube_jei.impl.builder.RecipeCategoryWrapperBuilder;
import zzzank.mods.kube_jei.impl.CustomJSRecipe;
import zzzank.mods.kube_jei.impl.CustomRecipeCategory;

import java.util.function.Consumer;

public class RegisterCategoriesEventJS extends JEIEventJS {
    public final IRecipeCategoryRegistration data;

    public RegisterCategoriesEventJS(IRecipeCategoryRegistration data) {
        this.data = data;
    }

    public CustomRecipeCategory<?> custom(
        ResourceLocation recipeType,
        Consumer<RecipeCategoryBuilder<CustomJSRecipe>> categoryConsumer
    ) {
        return register(KubeJEIRecipeTypes.CUSTOM.getOrCreateCustom(recipeType), categoryConsumer);
    }

    public <T> CustomRecipeCategory<T> wrap(
        RecipeType<T> recipeType,
        IRecipeCategory<T> existingCategory,
        Consumer<RecipeCategoryWrapperBuilder<T>> categoryConsumer
    ) {
        val builder = new RecipeCategoryWrapperBuilder<>(
            recipeType,
            data.getJeiHelpers(),
            existingCategory
        );
        categoryConsumer.accept(builder);
        val customRecipeCategory = new CustomRecipeCategory<>(builder);
        data.addRecipeCategories(customRecipeCategory);
        return customRecipeCategory;
    }

    public <T> CustomRecipeCategory<T> register(
        RecipeType<T> recipeType,
        Consumer<RecipeCategoryBuilder<T>> categoryModifier
    ) {
        val category = new RecipeCategoryBuilder<>(recipeType, data.getJeiHelpers());
        categoryModifier.accept(category);
        val customRecipeCategory = new CustomRecipeCategory<>(category);
        data.addRecipeCategories(customRecipeCategory);
        return customRecipeCategory;
    }
}
