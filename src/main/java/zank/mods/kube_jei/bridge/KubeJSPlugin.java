package zank.mods.kube_jei.bridge;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import zank.mods.kube_jei.events.KubeJEIEvents;

public class KubeJSPlugin implements dev.latvian.mods.kubejs.plugin.KubeJSPlugin {

	@Override
	public void registerEvents(EventGroupRegistry registry) {
		registry.register(KubeJEIEvents.GROUP);
	}
}
