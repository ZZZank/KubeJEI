package zank.mods.kube_jei.events.register;

import mezz.jei.api.registration.IAdvancedSearchRegistration;
import zank.mods.kube_jei.events.KubeJEIEvent;

public class RegisterAdvancedSearchEventJS implements KubeJEIEvent {
    public final IAdvancedSearchRegistration registration;

    public RegisterAdvancedSearchEventJS(IAdvancedSearchRegistration registration) {
        this.registration = registration;
    }
}
