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

    public RecipeCategoryBuilder<CustomJSRecipe> custom(ResourceLocation id) {
        val builder = new RecipeCategoryBuilder<>(
            KubeJEIRecipeTypes.getOrCreateCustom(id),
            data.getJeiHelpers()
        );
        data.addRecipeCategories(builder.asCategory());
        return builder;
    }

    public CustomRecipeCategory<?> custom(
        ResourceLocation id,
        Consumer<RecipeCategoryBuilder<CustomJSRecipe>> modifier
    ) {
        val builder = custom(id);
        modifier.accept(builder);
        return builder.asCategory();
    }

    public <T> RecipeCategoryWrapperBuilder<T> wrap(
        RecipeType<T> type,
        IRecipeCategory<T> existingCategory
    ) {
        val builder = new RecipeCategoryWrapperBuilder<>(type, data.getJeiHelpers(), existingCategory);
        data.addRecipeCategories(builder.asCategory());
        return builder;
    }

    public <T> CustomRecipeCategory<T> wrap(
        RecipeType<T> type,
        IRecipeCategory<T> existingCategory,
        Consumer<RecipeCategoryWrapperBuilder<T>> modifier
    ) {
        val builder = wrap(type, existingCategory);
        modifier.accept(builder);
        return builder.asCategory();
    }

    public <T> RecipeCategoryBuilder<T> register(RecipeType<T> type) {
        val builder = new RecipeCategoryBuilder<>(type, data.getJeiHelpers());
        data.addRecipeCategories(builder.asCategory());
        return builder;
    }

    public <T> CustomRecipeCategory<T> register(
        RecipeType<T> type,
        Consumer<RecipeCategoryBuilder<T>> modifier
    ) {
        val builder = register(type);
        modifier.accept(builder);
        return builder.asCategory();
    }
}
