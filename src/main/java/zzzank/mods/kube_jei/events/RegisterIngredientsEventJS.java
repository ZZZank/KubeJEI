package zzzank.mods.kube_jei.events;

import mezz.jei.api.registration.IModIngredientRegistration;

public class RegisterIngredientsEventJS extends JEIEventJS {
    public final IModIngredientRegistration registration;

    public RegisterIngredientsEventJS(IModIngredientRegistration registration) {
        this.registration = registration;
    }
}
