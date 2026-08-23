package zank.mods.kube_jei.events.register;

import dev.latvian.mods.kubejs.event.EventResult;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.resources.Identifier;
import zank.mods.kube_jei.events.KubeJEIEvent;
import zank.mods.kube_jei.impl.CustomCategoryBuilder;
import zank.mods.kube_jei.impl.CustomJSRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RegisterCategoriesEventJS implements KubeJEIEvent {
    public final IRecipeCategoryRegistration registration;
    private final List<CustomCategoryBuilder<?>> builders = new ArrayList<>();

    public RegisterCategoriesEventJS(IRecipeCategoryRegistration registration) {
        this.registration = registration;
    }

    public <T> CustomCategoryBuilder<T> custom(IRecipeType<T> recipeType) {
        var builder = new CustomCategoryBuilder<>(recipeType, registration.getJeiHelpers());
        builders.add(builder);
        return builder;
    }

    public <T> CustomCategoryBuilder<T> custom(IRecipeType<T> recipeType, Consumer<CustomCategoryBuilder<T>> modifier) {
        var builder = custom(recipeType);
        modifier.accept(builder);
        return builder;
    }

    public CustomCategoryBuilder<CustomJSRecipe> custom(Identifier id) {
        return custom(IRecipeType.create(id, CustomJSRecipe.class));
    }

    public CustomCategoryBuilder<CustomJSRecipe> custom(Identifier id, Consumer<CustomCategoryBuilder<CustomJSRecipe>> modifier) {
        return custom(IRecipeType.create(id, CustomJSRecipe.class), modifier);
    }

    @Override
    public void afterPosted(EventResult result) {
        for (var builder : builders) {
            registration.addRecipeCategories(builder.asCategory());
        }
    }
}
