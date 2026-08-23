package zank.mods.kube_jei.events.register;

import mezz.jei.api.registration.ISlotDisplayInterpreterRegistration;
import zank.mods.kube_jei.events.KubeJEIEvent;

/**
 * @author ZZZank
 */
public class RegisterSlotDisplayInterpretersEventJS implements KubeJEIEvent {
    public final ISlotDisplayInterpreterRegistration registration;

    public RegisterSlotDisplayInterpretersEventJS(ISlotDisplayInterpreterRegistration registration) {
        this.registration = registration;
    }
}
