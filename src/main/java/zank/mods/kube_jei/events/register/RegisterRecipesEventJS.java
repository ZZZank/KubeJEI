package zank.mods.kube_jei.events.register;

import dev.latvian.mods.kubejs.event.EventResult;
import dev.latvian.mods.kubejs.script.ConsoleJS;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import zank.mods.kube_jei.events.KubeJEIEvent;
import zank.mods.kube_jei.impl.CustomJSRecipe;

import java.util.ArrayList;
import java.util.List;

public class RegisterRecipesEventJS implements KubeJEIEvent {
    public final IRecipeRegistration registration;
    private final List<CustomJSRecipe.CustomRecipeListBuilder> builders = new ArrayList<>();

    public RegisterRecipesEventJS(IRecipeRegistration registration) {
        this.registration = registration;
    }

    public <T> void register(RecipeType<T> recipeType, List<T> recipes) {
        registration.addRecipes(recipeType, recipes);
    }

    public CustomJSRecipe.CustomRecipeListBuilder custom(ResourceLocation recipeType) {
        var type = registration.getJeiHelpers()
            .getRecipeType(recipeType, CustomJSRecipe.class)
            .orElseGet(() -> new RecipeType<>(recipeType, CustomJSRecipe.class));

        var builder = new CustomJSRecipe.CustomRecipeListBuilder(type);
        builders.add(builder);
        return builder;
    }

    @Override
    public void afterPosted(EventResult result) {
        for (var builder : builders) {
            if (registration.getJeiHelpers().getRecipeType(builder.type.getUid()).isEmpty()) {
                ConsoleJS.CLIENT.error(
                    "Custom recipe type " + builder.type.getUid() + " has no registered category, skipping "
                        + builder.recipes.size() + " recipes"
                );
                continue;
            }
            registration.addRecipes(builder.type, builder.recipes);
        }
    }
}
