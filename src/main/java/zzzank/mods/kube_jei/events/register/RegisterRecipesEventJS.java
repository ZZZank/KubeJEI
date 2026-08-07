package zzzank.mods.kube_jei.events.register;

import dev.latvian.mods.kubejs.event.EventResult;
import mezz.jei.api.registration.IRecipeRegistration;
import zzzank.mods.kube_jei.events.KubeJEIEvent;
import mezz.jei.api.recipe.RecipeType;

import java.util.List;

public class RegisterRecipesEventJS implements KubeJEIEvent {
    public final IRecipeRegistration registration;

    public RegisterRecipesEventJS(IRecipeRegistration registration) {
        this.registration = registration;
    }

    public <T> void register(RecipeType<T> recipeType, List<T> recipes) {
        registration.addRecipes(recipeType, recipes);
    }

    @Override
    public void afterPosted(EventResult result) {
    }
}
