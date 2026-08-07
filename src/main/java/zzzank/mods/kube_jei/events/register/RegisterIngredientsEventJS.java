package zzzank.mods.kube_jei.events.register;

import mezz.jei.api.registration.IModIngredientRegistration;
import zzzank.mods.kube_jei.events.KubeJEIEvent;

public class RegisterIngredientsEventJS implements KubeJEIEvent {
    public final IModIngredientRegistration registration;

    public RegisterIngredientsEventJS(IModIngredientRegistration registration) {
        this.registration = registration;
    }
}
