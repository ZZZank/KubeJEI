package zzzank.mods.kube_jei.mixins;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import dev.latvian.kubejs.script.ScriptType;
import lombok.val;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.collect.ListMultiMap;
import mezz.jei.load.registration.RecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzzank.mods.kube_jei.KubeJEIEvents;
import zzzank.mods.kube_jei.events.deny.DenyRecipeEventJS;
import zzzank.mods.kube_jei.events.deny.RecipeDenyPredicate;

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
     * @param instance original map
     * @param key category id
     * @param value recipe
     * @return true if this collection changed as a result of the call
     */
    @Redirect(
        method = "addRecipes",
        at = @At(
            value = "INVOKE",
            target = "Lmezz/jei/collect/ListMultiMap;put(Ljava/lang/Object;Ljava/lang/Object;)Z"
        )
    )
    public boolean kJei$filterRecipes(ListMultiMap<ResourceLocation, Object> instance, Object key, Object value) {
        val categoryId = (ResourceLocation) key;
        for (val denyPredicate : kJei$denyPredicates) {
            if (denyPredicate.shouldDeny(categoryId, value)) {
                return false;
            }
        }
        return instance.put(categoryId, value);
    }
}
