package zzzank.mods.kube_jei.events;

import dev.latvian.kubejs.event.EventJS;
import mezz.jei.api.registration.IRecipeTransferRegistration;

public class RegisterRecipeTransferHandlersEventJS extends EventJS {
    public final IRecipeTransferRegistration registration;

    public RegisterRecipeTransferHandlersEventJS(IRecipeTransferRegistration registration) {
        this.registration = registration;
    }
}
