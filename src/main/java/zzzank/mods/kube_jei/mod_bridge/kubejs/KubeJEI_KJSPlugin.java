package zzzank.mods.kube_jei.mod_bridge.kubejs;

import dev.latvian.kubejs.KubeJSPlugin;
import dev.latvian.kubejs.script.BindingsEvent;
import dev.latvian.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import me.shedaniel.architectury.platform.Platform;
import mezz.jei.api.gui.drawable.IDrawable;
import zzzank.mods.kube_jei.impl.helpers.JEIDrawableWrapper;
import zzzank.mods.kube_jei.impl.recipe_type.KubeJEIRecipeTypes;

public class KubeJEI_KJSPlugin extends KubeJSPlugin {
    @Override
    public void addTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
		if (type != ScriptType.CLIENT || !Platform.isModLoaded("jei")) {
			return;
		}
        typeWrappers.register(IDrawable.class, JEIDrawableWrapper.INSTANCE::of);
    }

	@Override
	public void addBindings(BindingsEvent event) {
		if (event.type != ScriptType.CLIENT || !Platform.isModLoaded("jei")) {
			return;
		}
		event.add("KubeJEIRecipeTypes", KubeJEIRecipeTypes.class);
	}
}
