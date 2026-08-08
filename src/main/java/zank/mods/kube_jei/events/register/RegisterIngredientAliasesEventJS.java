package zank.mods.kube_jei.events.register;

import mezz.jei.api.registration.IIngredientAliasRegistration;
import zank.mods.kube_jei.events.KubeJEIEvent;

public class RegisterIngredientAliasesEventJS implements KubeJEIEvent {
    public final IIngredientAliasRegistration registration;

    public RegisterIngredientAliasesEventJS(IIngredientAliasRegistration registration) {
        this.registration = registration;
    }
}
