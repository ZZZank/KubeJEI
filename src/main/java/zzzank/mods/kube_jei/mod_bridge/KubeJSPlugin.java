package zzzank.mods.kube_jei.mod_bridge;

import dev.latvian.mods.kubejs.script.BindingRegistry;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;

public class KubeJSPlugin implements dev.latvian.mods.kubejs.plugin.KubeJSPlugin {

	@Override
	public void registerBindings(BindingRegistry registry) {
	}

	@Override
	public void registerTypeWrappers(TypeWrapperRegistry registry) {
		// Rhino provides builtin string wrapping for enum class
		// registry.register(RecipeIngredientRole.class, ...);
	}
}
