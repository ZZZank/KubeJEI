package zank.mods.kube_jei.events.register;

import mezz.jei.api.registration.IRecipeTransferRegistration;
import zank.mods.kube_jei.events.KubeJEIEvent;

public class RegisterRecipeTransferHandlersEventJS implements KubeJEIEvent {
    public final IRecipeTransferRegistration registration;

    public RegisterRecipeTransferHandlersEventJS(IRecipeTransferRegistration registration) {
        this.registration = registration;
    }
}
