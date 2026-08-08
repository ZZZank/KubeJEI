package zzzank.mods.kube_jei.mixins;

import com.google.common.collect.ImmutableList;
import lombok.val;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.library.load.registration.RecipeCategoryRegistration;
import mezz.jei.library.runtime.JeiHelpers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zzzank.mods.kube_jei.KubeJEI;
import zzzank.mods.kube_jei.events.KubeJEIEvents;
import zzzank.mods.kube_jei.events.deny.DenyCategoryEventJS;

import java.util.Arrays;
import java.util.List;

/**
 * @author ZZZank
 */
@Mixin(value = RecipeCategoryRegistration.class, remap = false)
public abstract class MixinRecipeCategoryRegistration {

    @Unique
    private List<DenyCategoryEventJS.CategoryDenyPredicate> kJei$denyPredicates;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void kJei$init(JeiHelpers jeiHelpers, CallbackInfo ci) {
        val denyCategoryEvent = new DenyCategoryEventJS();
        KubeJEIEvents.DENY_CATEGORIES.post(denyCategoryEvent);
        KubeJEI.LOGGER.info(
            "KubeJEI collected {} directly denied categories, {} filters in total",
            denyCategoryEvent.deniedIds.size(),
            denyCategoryEvent.denyPredicates.size()
        );
        kJei$denyPredicates = ImmutableList.copyOf(denyCategoryEvent.denyPredicates);
    }

    @ModifyVariable(
        method = "addRecipeCategories",
        at = @At(
            value = "INVOKE",
            target = "Lmezz/jei/common/util/ErrorUtil;checkNotEmpty([Ljava/lang/Object;Ljava/lang/String;)V",
            shift = At.Shift.AFTER,
            ordinal = 0
        ),
        argsOnly = true
    )
    public IRecipeCategory<?>[] kJei$denyCategories(IRecipeCategory<?>[] value) {
        var denyPredicates = kJei$denyPredicates;
        return Arrays.stream(value)
            .filter(category -> denyPredicates.stream().noneMatch(predicate -> predicate.shouldDeny(category)))
            .toArray(IRecipeCategory[]::new);
    }
}
