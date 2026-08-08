package zank.mods.kube_jei.events.register;

import dev.latvian.mods.kubejs.event.EventResult;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.resources.ResourceLocation;
import zank.mods.kube_jei.events.KubeJEIEvent;
import zank.mods.kube_jei.impl.CustomJSRecipe;
import zank.mods.kube_jei.impl.CustomCategoryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RegisterCategoriesEventJS implements KubeJEIEvent {
    public final IRecipeCategoryRegistration registration;
    private final List<CustomCategoryBuilder<?>> builders = new ArrayList<>();
    private final Map<ResourceLocation, RecipeType<CustomJSRecipe>> customRecipeTypes = new HashMap<>();

    public RegisterCategoriesEventJS(IRecipeCategoryRegistration registration) {
        this.registration = registration;
    }

    public CustomCategoryBuilder<CustomJSRecipe> custom(ResourceLocation id) {
        var builder = new CustomCategoryBuilder<>(recipeType(id), registration.getJeiHelpers());
        builders.add(builder);
        return builder;
    }

    public CustomCategoryBuilder<CustomJSRecipe> custom(ResourceLocation id, Consumer<CustomCategoryBuilder<CustomJSRecipe>> modifier) {
        var builder = custom(id);
        modifier.accept(builder);
        return builder;
    }

    private RecipeType<CustomJSRecipe> recipeType(ResourceLocation id) {
        return customRecipeTypes.computeIfAbsent(id, uid -> new RecipeType<>(uid, CustomJSRecipe.class));
    }

    @Override
    public void afterPosted(EventResult result) {
        for (var builder : builders) {
            registration.addRecipeCategories(builder.asCategory());
        }
    }
}
