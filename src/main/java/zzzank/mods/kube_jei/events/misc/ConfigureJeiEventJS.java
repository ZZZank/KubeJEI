package zzzank.mods.kube_jei.events.misc;

import mezz.jei.api.runtime.IJeiFeatures;
import zzzank.mods.kube_jei.events.KubeJEIEvent;

public class ConfigureJeiEventJS implements KubeJEIEvent {
    public final IJeiFeatures features;

    public ConfigureJeiEventJS(IJeiFeatures features) {
        this.features = features;
    }
}
