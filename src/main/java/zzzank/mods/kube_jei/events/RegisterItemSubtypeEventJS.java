package zzzank.mods.kube_jei.events;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RegisterItemSubtypeEventJS extends JEIEventJS {
    public final ISubtypeRegistration registration;

    public RegisterItemSubtypeEventJS(ISubtypeRegistration registration) {
        this.registration = registration;
    }

	public void registerSubtypeInterpreter(@NotNull Item item, @NotNull IIngredientSubtypeInterpreter<ItemStack> interpreter) {
		registration.registerSubtypeInterpreter(item, interpreter);
	}

	public void useNbtForSubtypes(Item @NotNull ... items) {
		registration.useNbtForSubtypes(items);
	}

	public boolean hasSubtypeInterpreter(@NotNull ItemStack itemStack) {
		return registration.hasSubtypeInterpreter(itemStack);
	}
}
