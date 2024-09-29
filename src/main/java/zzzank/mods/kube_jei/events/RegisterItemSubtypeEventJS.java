package zzzank.mods.kube_jei.events;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
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

	@JSInfo("""
		Add an interpreter to compare item subtypes.
		This interpreter should account for nbt and anything else that's relevant to differentiating the item's subtypes.
		
		@param item        the item that has subtypes.
		@param interpreter the interpreter for the item.""")
	public void registerSubtypeInterpreter(@NotNull Item item, @NotNull IIngredientSubtypeInterpreter<ItemStack> interpreter) {
		registration.registerSubtypeInterpreter(item, interpreter);
	}

	@JSInfo("""
		Tells JEI to treat all NBT as relevant to these items' subtypes.""")
	public void useNbtForSubtypes(Item @NotNull ... items) {
		registration.useNbtForSubtypes(items);
	}

	@JSInfo("""
		Returns whether any `IIngredientSubtypeInterpreter` has been registered for this item.""")
	public boolean hasSubtypeInterpreter(@NotNull ItemStack itemStack) {
		return registration.hasSubtypeInterpreter(itemStack);
	}
}
