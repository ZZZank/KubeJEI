package zzzank.mods.kube_jei.mixins;

import com.google.common.collect.ImmutableList;
import dev.latvian.kubejs.script.ScriptType;
import lombok.val;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.load.registration.RecipeCategoryRegistration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzzank.mods.kube_jei.KubeJEI;
import zzzank.mods.kube_jei.KubeJEIEvents;
import zzzank.mods.kube_jei.events.deny.DenyCategoryEventJS;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author ZZZank
 */
@Mixin(value = RecipeCategoryRegistration.class, remap = false)
public abstract class MixinRecipeCategoryRegistration {

    @Unique
    private List<Predicate<IRecipeCategory<?>>> kJei$denyPredicates;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void kJei$init(IJeiHelpers jeiHelpers, CallbackInfo ci) {
        val denyCategoryEvent = new DenyCategoryEventJS();
        denyCategoryEvent.post(ScriptType.CLIENT, KubeJEIEvents.DENY_CATEGORIES);
        KubeJEI.LOGGER.info(
            "KubeJEI collected {} directly denied categories, {} filters in total",
            denyCategoryEvent.deniedIds.size(),
            denyCategoryEvent.denyPredicates.size()
        );
        kJei$denyPredicates = ImmutableList.copyOf(denyCategoryEvent.denyPredicates);
    }

    @Unique
    private boolean kJei$shouldDeny(IRecipeCategory<?> category) {
        for (val predicate : kJei$denyPredicates) {
            if (predicate.test(category)) {
                return true;
            }
        }
        return false;
    }

    @ModifyVariable(method = "addRecipeCategories", at = @At("HEAD"), index = 1, argsOnly = true)
    public IRecipeCategory<?>[] kJei$denyCategories(IRecipeCategory<?>[] value) {
        val filtered = new ArrayList<IRecipeCategory<?>>();
        for (val category : value) {
            if (!kJei$shouldDeny(category)) {
                filtered.add(category);
            }
        }
        return filtered.toArray(new IRecipeCategory[0]);
    }
}
