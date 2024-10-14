package zzzank.mods.kube_jei.mod_bridge.kubejs;

import dev.latvian.kubejs.KubeJSPlugin;
import dev.latvian.kubejs.script.BindingsEvent;
import dev.latvian.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import mezz.jei.api.ingredients.IIngredientType;
import zzzank.mods.kube_jei.impl.recipe_type.KubeJEIRecipeTypes;
import zzzank.mods.kube_jei.mod_bridge.ModState;

public class KubeJEI_KJSPlugin extends KubeJSPlugin {

	@Override
	public void addBindings(BindingsEvent event) {
		if (!shouldEnable(event.type)) {
			return;
		}
		event.add("KubeJEIRecipeTypes", KubeJEIRecipeTypes.class);
	}

	private static boolean shouldEnable(ScriptType type) {
		return type == ScriptType.CLIENT && ModState.JEI;
	}

	@Override
	public void addTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
		if (!shouldEnable(type)) {
			return;
		}
		typeWrappers.register(
			IIngredientType.class,
			o -> o instanceof IIngredientType<?> || o instanceof Class<?>,
			o -> o instanceof IIngredientType<?> ingredientType ? ingredientType
				: o instanceof Class<?> c ? () -> c
					: null
		);
	}
}
