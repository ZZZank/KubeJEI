package zzzank.mods.kube_jei.events.register;

import mezz.jei.api.registration.IRecipeCategoryRegistration;
import zzzank.mods.kube_jei.events.KubeJEIEvent;

public class RegisterCategoriesEventJS implements KubeJEIEvent {
    public final IRecipeCategoryRegistration registration;

    public RegisterCategoriesEventJS(IRecipeCategoryRegistration registration) {
        this.registration = registration;
    }
}
