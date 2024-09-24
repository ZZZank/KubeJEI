package zzzank.mods.kube_jei;

import dev.latvian.kubejs.KubeJSPlugin;
import dev.latvian.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import me.shedaniel.architectury.platform.Platform;
import mezz.jei.api.gui.drawable.IDrawable;

public class JEIKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void addTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
		if (type != ScriptType.CLIENT || !Platform.isModLoaded("zzzank/mods/kube_jei")) {
			return;
		}
		typeWrappers.register(IDrawable.class, JEIDrawableWrapper::of);
    }
}
