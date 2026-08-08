package zank.mods.kube_jei.events.register;

import mezz.jei.api.registration.IGuiHandlerRegistration;
import zank.mods.kube_jei.events.KubeJEIEvent;

public class RegisterGUIHandlersEventJS implements KubeJEIEvent {
    public final IGuiHandlerRegistration registration;

    public RegisterGUIHandlersEventJS(IGuiHandlerRegistration registration) {
        this.registration = registration;
    }
}
