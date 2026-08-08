package zank.mods.kube_jei.events.register;

import mezz.jei.api.registration.IExtraIngredientRegistration;
import zank.mods.kube_jei.events.KubeJEIEvent;

public class RegisterExtraIngredientsEventJS implements KubeJEIEvent {
    public final IExtraIngredientRegistration registration;

    public RegisterExtraIngredientsEventJS(IExtraIngredientRegistration registration) {
        this.registration = registration;
    }
}
