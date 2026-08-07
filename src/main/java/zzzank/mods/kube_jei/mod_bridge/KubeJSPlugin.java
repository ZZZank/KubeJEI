package zzzank.mods.kube_jei.mod_bridge;

import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;
import dev.latvian.mods.rhino.NativeJavaClass;
import mezz.jei.api.ingredients.IIngredientType;

public class KubeJSPlugin implements dev.latvian.mods.kubejs.plugin.KubeJSPlugin {

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
	public void registerBindings(BindingRegistry registry) {
		if (shouldEnable(registry.type())) {
		}
	}

	@Override
	public void registerTypeWrappers(TypeWrapperRegistry registry) {
		if (!shouldEnable(registry.scriptType())) {
			return;
		}
		registry.register(
			IIngredientType.class,
			(o, type) -> o instanceof IIngredientType<?> || o instanceof Class<?>,
			KubeJSPlugin::ingredientTypeOf
		);
	}

	private static boolean shouldEnable(ScriptType type) {
		return type == ScriptType.CLIENT && ModState.JEI;
	}
}
