package zank.mods.kube_jei.bridge;

import mezz.jei.api.recipe.RecipeType;
import mezz.jei.common.Internal;
import moe.wolfgirl.probejs.snippet.SnippetRegisterer;
import net.minecraft.resources.ResourceLocation;

/**
 * @author ZZZank
 */
public class ProbeJSPlugin extends moe.wolfgirl.probejs.plugin.ProbeJSPlugin {

    @Override
    public void addSnippets(SnippetRegisterer registerer) {
        registerer.snippet("jei_recipe_types")
            .prefix("@jei_recipe_types")
            .literal("\"")
            .choices(Internal.getJeiRuntime()
                .getJeiHelpers()
                .getAllRecipeTypes()
                .map(RecipeType::getUid)
                .map(ResourceLocation::toString)
                .toList())
            .literal("\"");
    }
}
