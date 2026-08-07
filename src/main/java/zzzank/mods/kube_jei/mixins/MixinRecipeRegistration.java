package zzzank.mods.kube_jei.mixins;

import com.google.common.collect.ImmutableList;
import lombok.val;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.library.load.registration.RecipeRegistration;
import mezz.jei.library.recipes.RecipeManagerInternal;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzzank.mods.kube_jei.events.KubeJEIEvents;
import zzzank.mods.kube_jei.events.deny.DenyRecipeEventJS;

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
    public void kJei$init(IJeiHelpers jeiHelpers, IIngredientManager ingredientManager, RecipeManagerInternal recipeManager, CallbackInfo ci) {
        val denyEvent = new DenyRecipeEventJS();
        KubeJEIEvents.DENY_RECIPES.post(denyEvent);
        kJei$denyPredicates = ImmutableList.copyOf(denyEvent.denyPredicates);
    }

    @Redirect(method = "addRecipes", at = @At(value = "INVOKE", target = "Lmezz/jei/library/recipes/RecipeManagerInternal;addRecipes(Lmezz/jei/api/recipe/RecipeType;Ljava/util/List;)V"))
    public <T> void kJei$filterBeforeAddingRecipes(
        RecipeManagerInternal instance,
        RecipeType<T> recipeType,
        List<T> recipes
    ) {
        var filtered = new ArrayList<T>();
        for (var recipe : recipes) {
            if (kJei$filterRecipe(recipe, recipeType.getUid())) {
                filtered.add(recipe);
            }
        }
        if (!filtered.isEmpty()) {
            recipeManager.addRecipes(recipeType, recipes);
        }
    }

    @Unique
    private boolean kJei$filterRecipe(Object recipe, ResourceLocation categoryId) {
        for (val denyPredicate : kJei$denyPredicates) {
            if (denyPredicate.shouldDeny(categoryId, recipe)) {
                return false;
            }
        }
        return true;
    }
}
