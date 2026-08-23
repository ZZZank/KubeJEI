package zank.mods.kube_jei.mixins;

import com.google.common.collect.ImmutableList;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.library.load.registration.RecipeRegistration;
import mezz.jei.library.recipes.RecipeManagerInternal;
import net.minecraft.util.context.ContextMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zank.mods.kube_jei.events.KubeJEIEvents;
import zank.mods.kube_jei.events.deny.DenyRecipeEventJS;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZZZank
 */
@Mixin(value = RecipeRegistration.class, remap = false)
public abstract class MixinRecipeRegistration {

    @Shadow
    @Final
    private RecipeManagerInternal recipeManager;
    @Unique
    private List<DenyRecipeEventJS.RecipeDenyPredicate> kJei$denyPredicates;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void kJei$init(
        IJeiHelpers jeiHelpers,
        IIngredientManager ingredientManager,
        RecipeManagerInternal recipeManager,
        ContextMap contextMap,
        CallbackInfo ci
    ) {
        var denyEvent = new DenyRecipeEventJS();
        KubeJEIEvents.DENY_RECIPES.post(denyEvent);
        kJei$denyPredicates = ImmutableList.copyOf(denyEvent.denyPredicates);
    }

    @Redirect(method = "addRecipes", at = @At(value = "INVOKE", target = "Lmezz/jei/library/recipes/RecipeManagerInternal;addRecipes(Lmezz/jei/api/recipe/types/IRecipeType;Ljava/util/List;Lnet/minecraft/util/context/ContextMap;)V"))
    public <T> void kJei$filterBeforeAddingRecipes(RecipeManagerInternal instance, IRecipeType<T> recipeType, List<T> recipes, ContextMap contextMap) {
        var filtered = new ArrayList<T>();
        for (var recipe : recipes) {
            if (kJei$filterRecipe(recipe, recipeType)) {
                filtered.add(recipe);
            }
        }
        if (!filtered.isEmpty()) {
            recipeManager.addRecipes(recipeType, recipes, contextMap);
        }
    }

    @Unique
    private <T> boolean kJei$filterRecipe(T recipe, IRecipeType<T> recipeType) {
        for (var denyPredicate : kJei$denyPredicates) {
            if (denyPredicate.shouldDeny(recipeType, recipe)) {
                return false;
            }
        }
        return true;
    }
}
