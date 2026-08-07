package zzzank.mods.kube_jei.events.register;

import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import zzzank.mods.kube_jei.events.KubeJEIEvent;

public class RegisterVanillaCategoryExtensionsEventJS implements KubeJEIEvent {
    public final IVanillaCategoryExtensionRegistration registration;

    public RegisterVanillaCategoryExtensionsEventJS(IVanillaCategoryExtensionRegistration registration) {
        this.registration = registration;
    }
}
