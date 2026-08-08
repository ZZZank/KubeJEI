package zank.mods.kube_jei.events.register;

import mezz.jei.api.registration.IRuntimeRegistration;
import zank.mods.kube_jei.events.KubeJEIEvent;

public class RegisterRuntimeEventJS implements KubeJEIEvent {
    public final IRuntimeRegistration registration;

    public RegisterRuntimeEventJS(IRuntimeRegistration registration) {
        this.registration = registration;
    }
}
