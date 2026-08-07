package zzzank.mods.kube_jei.events.register;

import mezz.jei.api.registration.IModInfoRegistration;
import zzzank.mods.kube_jei.events.KubeJEIEvent;

public class RegisterModInfoEventJS implements KubeJEIEvent {
    public final IModInfoRegistration registration;

    public RegisterModInfoEventJS(IModInfoRegistration registration) {
        this.registration = registration;
    }
}
