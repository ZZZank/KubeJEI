package zzzank.mods.kube_jei.mixins;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import dev.latvian.kubejs.script.ScriptType;
import lombok.val;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.load.registration.RecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzzank.mods.kube_jei.KubeJEIEvents;
import zzzank.mods.kube_jei.events.deny.DenyRecipeEventJS;
import zzzank.mods.kube_jei.events.deny.RecipeDenyPredicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author ZZZank
 */
@Mixin(value = RecipeRegistration.class, remap = false)
public abstract class MixinRecipeRegistration {

    @Unique
    private List<RecipeDenyPredicate> kJei$denyPredicates;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void kJei$init(
        ImmutableMap<ResourceLocation, IRecipeCategory<?>> recipeCategoriesByUid,
        IJeiHelpers jeiHelpers,
        IIngredientManager ingredientManager,
        IVanillaRecipeFactory vanillaRecipeFactory,
        CallbackInfo ci
    ) {
        val denyEvent = new DenyRecipeEventJS();
        denyEvent.post(ScriptType.CLIENT, KubeJEIEvents.DENY_RECIPES);
        kJei$denyPredicates = ImmutableList.copyOf(denyEvent.denyPredicates);
    }

    /**
     * make recipes always a mutable list, so that our modification can be easier
     */
    @ModifyVariable(method = "addRecipes", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    public Collection<?> kJei$preSetType(Collection<?> recipes) {
        return recipes == null ? null : new ArrayList<>(recipes);
    }

    @Inject(
        method = "addRecipes",
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lcom/google/common/collect/ImmutableMap;get(Ljava/lang/Object;)Ljava/lang/Object;",
            ordinal = 0
        ),
        cancellable = true
    )
    public void kJei$filterRecipes(Collection<Object> recipes, ResourceLocation recipeCategoryUid, CallbackInfo ci) {
        val filtered = new ArrayList<>();
        for (val recipe : recipes) {
            if (!kJei$shouldDeny(recipe, recipeCategoryUid)) {
                filtered.add(recipe);
            }
        }
        if (recipes.isEmpty() || filtered.isEmpty()) {
            ci.cancel();
        }
        recipes.clear();
        recipes.addAll(filtered);
    }

    @Unique
    private boolean kJei$shouldDeny(Object recipe, ResourceLocation categoryId) {
        for (val denyPredicate : kJei$denyPredicates) {
            if (denyPredicate.shouldDeny(categoryId, recipe)) {
                return false;
            }
        }
        return true;
    }
}
