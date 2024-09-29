package zzzank.mods.kube_jei.events;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import zzzank.mods.kube_jei.impl.recipe_type.RecipeType;
import zzzank.mods.kube_jei.impl.CustomJSRecipe;
import zzzank.mods.kube_jei.impl.recipe_type.KubeJEIRecipeTypes;

import java.util.ArrayList;
import java.util.List;

public class RegisterRecipesEventJS extends JEIEventJS {
    public final IRecipeRegistration data;
    public final List<CustomJSRecipe.CustomRecipeListBuilder> customRecipeListBuilders = new ArrayList<>();

    public RegisterRecipesEventJS(IRecipeRegistration data) {
        this.data = data;
    }

    @JSInfo("""
        Add the recipes provided by your plugin.""")
    public <T> void register(RecipeType<T> recipeType, List<T> recipes) {
        data.addRecipes(recipes, recipeType.uid());
    }

    public CustomJSRecipe.CustomRecipeListBuilder custom(ResourceLocation recipeType) {
        var recipeListBuilder = new CustomJSRecipe.CustomRecipeListBuilder(KubeJEIRecipeTypes.getOrCreateCustom(recipeType));
        customRecipeListBuilders.add(recipeListBuilder);
        return recipeListBuilder;
    }
}
