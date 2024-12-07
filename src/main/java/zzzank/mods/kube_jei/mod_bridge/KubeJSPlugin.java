package zzzank.mods.kube_jei.mod_bridge;

import dev.latvian.kubejs.script.BindingsEvent;
import dev.latvian.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.NativeJavaClass;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import mezz.jei.api.ingredients.IIngredientType;
import zzzank.mods.kube_jei.impl.recipe_type.KubeJEIRecipeTypes;

public class KubeJSPlugin extends dev.latvian.kubejs.KubeJSPlugin {

	public static IIngredientType<?> ingredientTypeOf(Object o) {
        if (o instanceof IIngredientType<?> ingredientType) {
            return ingredientType;
		} else if (o instanceof NativeJavaClass c) {
			return c::getClassObject;
        } else if (o instanceof Class<?> c) {
            return () -> c;
        }
        return null;
    }

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
			KubeJSPlugin::ingredientTypeOf
		);
	}
}
