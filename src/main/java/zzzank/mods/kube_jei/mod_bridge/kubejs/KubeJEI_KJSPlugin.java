package zzzank.mods.kube_jei.mod_bridge.kubejs;

import dev.latvian.kubejs.KubeJSPlugin;
import dev.latvian.kubejs.script.BindingsEvent;
import dev.latvian.kubejs.script.ScriptType;
import me.shedaniel.architectury.platform.Platform;
import zzzank.mods.kube_jei.impl.recipe_type.KubeJEIRecipeTypes;

public class KubeJEI_KJSPlugin extends KubeJSPlugin {

	@Override
	public void addBindings(BindingsEvent event) {
		if (event.type != ScriptType.CLIENT || !Platform.isModLoaded("jei")) {
			return;
		}
		event.add("KubeJEIRecipeTypes", KubeJEIRecipeTypes.class);
	}
}
