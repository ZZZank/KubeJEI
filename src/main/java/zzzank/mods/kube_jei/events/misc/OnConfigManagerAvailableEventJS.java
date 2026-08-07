package zzzank.mods.kube_jei.events.misc;

import mezz.jei.api.runtime.config.IJeiConfigManager;
import zzzank.mods.kube_jei.events.KubeJEIEvent;

public class OnConfigManagerAvailableEventJS implements KubeJEIEvent {
    public final IJeiConfigManager configManager;

    public OnConfigManagerAvailableEventJS(IJeiConfigManager configManager) {
        this.configManager = configManager;
    }
}
