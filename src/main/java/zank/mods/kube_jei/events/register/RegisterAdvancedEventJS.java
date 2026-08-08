package zank.mods.kube_jei.events.register;

import dev.latvian.mods.kubejs.event.EventResult;
import mezz.jei.api.recipe.advanced.IRecipeManagerPlugin;
import mezz.jei.api.registration.IAdvancedRegistration;
import zank.mods.kube_jei.events.KubeJEIEvent;

public class RegisterAdvancedEventJS implements KubeJEIEvent {
	public final IAdvancedRegistration registration;

	public RegisterAdvancedEventJS(IAdvancedRegistration registration) {
		this.registration = registration;
	}

	public void addRecipeManagerPlugin(IRecipeManagerPlugin recipeManagerPlugin) {
		registration.addRecipeManagerPlugin(recipeManagerPlugin);
	}

	@Override
	public void afterPosted(EventResult result) {

	}
}
