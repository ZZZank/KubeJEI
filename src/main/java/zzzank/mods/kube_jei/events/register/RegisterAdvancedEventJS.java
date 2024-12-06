package zzzank.mods.kube_jei.events.register;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import lombok.val;
import mezz.jei.api.recipe.advanced.IRecipeManagerPlugin;
import mezz.jei.api.registration.IAdvancedRegistration;
import zzzank.mods.kube_jei.events.JEIEventJS;
import zzzank.mods.kube_jei.impl.CustomRecipeManagerPlugin;
import zzzank.mods.kube_jei.impl.builder.CustomRecipeManagerPluginBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RegisterAdvancedEventJS extends JEIEventJS {
	public final IAdvancedRegistration registration;
	private final List<CustomRecipeManagerPluginBuilder> builders = new ArrayList<>();

	public RegisterAdvancedEventJS(IAdvancedRegistration registration) {
		this.registration = registration;
	}

	public void addRecipeManagerPlugin(IRecipeManagerPlugin recipeManagerPlugin) {
		registration.addRecipeManagerPlugin(recipeManagerPlugin);
	}

	public CustomRecipeManagerPluginBuilder custom() {
		val builder = new CustomRecipeManagerPluginBuilder();
		builders.add(builder);
		return builder;
	}

	public CustomRecipeManagerPlugin custom(Consumer<CustomRecipeManagerPluginBuilder> modifier) {
		val builder = new CustomRecipeManagerPluginBuilder();
		modifier.accept(builder);
		val plugin = new CustomRecipeManagerPlugin(builder);
		addRecipeManagerPlugin(plugin);
		return plugin;
	}

	@JSInfo("""
		use `custom()` instead""")
	@Deprecated
	public CustomRecipeManagerPlugin createCustomRecipeManagerPlugin(Consumer<CustomRecipeManagerPluginBuilder> modifier) {
		return custom(modifier);
	}

	@Override
	protected void afterPosted(boolean result) {
		for (val builder : builders) {
			addRecipeManagerPlugin(new CustomRecipeManagerPlugin(builder));
		}
	}
}
