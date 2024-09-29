package zzzank.mods.kube_jei.events;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import mezz.jei.api.runtime.IJeiRuntime;

public class OnRuntimeAvailableEventJS extends JEIEventJS {
    @JSInfo("""
        Gives access to JEI functions that are available once everything has loaded.""")
    public final IJeiRuntime runtime;

    public OnRuntimeAvailableEventJS(IJeiRuntime runtime) {
        this.runtime = runtime;
    }
}
