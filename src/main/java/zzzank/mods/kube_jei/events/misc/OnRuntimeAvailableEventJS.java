package zzzank.mods.kube_jei.events.misc;

import mezz.jei.api.runtime.IJeiRuntime;
import zzzank.mods.kube_jei.events.KubeJEIEvent;

public class OnRuntimeAvailableEventJS implements KubeJEIEvent {
    public final IJeiRuntime runtime;

    public OnRuntimeAvailableEventJS(IJeiRuntime runtime) {
        this.runtime = runtime;
    }
}
