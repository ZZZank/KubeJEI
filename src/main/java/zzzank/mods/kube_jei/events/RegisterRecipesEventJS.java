package zzzank.mods.kube_jei.events;

import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import zzzank.mods.kube_jei.RecipeType;
import zzzank.mods.kube_jei.impl.CustomJSRecipe;

import java.util.ArrayList;
import java.util.List;

public class RegisterRecipesEventJS extends JEIEventJS {
    public final IRecipeRegistration data;
    public final List<CustomJSRecipe.CustomRecipeListBuilder> customRecipeListBuilders = new ArrayList<>();

    public RegisterRecipesEventJS(IRecipeRegistration data) {
        this.data = data;
    }

    public <T> void register(RecipeType<T> recipeType, List<T> recipes) {
        data.addRecipes(recipes, recipeType.uid());
    }

    public CustomJSRecipe.CustomRecipeListBuilder custom(ResourceLocation recipeType) {
        var recipeListBuilder = new CustomJSRecipe.CustomRecipeListBuilder(getOrCreateCustomRecipeType(recipeType));
        customRecipeListBuilders.add(recipeListBuilder);
        return recipeListBuilder;
    }
}
