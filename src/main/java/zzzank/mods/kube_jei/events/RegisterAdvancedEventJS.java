package zzzank.mods.kube_jei.events;

import lombok.val;
import mezz.jei.api.recipe.advanced.IRecipeManagerPlugin;
import mezz.jei.api.registration.IAdvancedRegistration;
import zzzank.mods.kube_jei.impl.CustomRecipeManagerPlugin;
import zzzank.mods.kube_jei.impl.builder.CustomRecipeManagerPluginBuilder;

import java.util.function.Consumer;

public class RegisterAdvancedEventJS extends JEIEventJS {
	public final IAdvancedRegistration registration;

	public RegisterAdvancedEventJS(IAdvancedRegistration registration) {
		this.registration = registration;
	}

	public void addRecipeManagerPlugin(IRecipeManagerPlugin recipeManagerPlugin) {
		registration.addRecipeManagerPlugin(recipeManagerPlugin);
	}

	public CustomRecipeManagerPlugin createCustomRecipeManagerPlugin(Consumer<CustomRecipeManagerPluginBuilder> modifier) {
		val builder = new CustomRecipeManagerPluginBuilder();
		modifier.accept(builder);
		val plugin = new CustomRecipeManagerPlugin(builder);
		addRecipeManagerPlugin(plugin);
		return plugin;
	}
}
