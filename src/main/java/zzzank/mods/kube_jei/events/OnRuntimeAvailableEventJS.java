package zzzank.mods.kube_jei.events;

import mezz.jei.api.runtime.IJeiRuntime;

public class OnRuntimeAvailableEventJS extends JEIEventJS {
    public final IJeiRuntime runtime;

    public OnRuntimeAvailableEventJS(IJeiRuntime runtime) {
        this.runtime = runtime;
    }
}
