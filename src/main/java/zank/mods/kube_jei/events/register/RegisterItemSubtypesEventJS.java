package zank.mods.kube_jei.events.register;

import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import zank.mods.kube_jei.events.KubeJEIEvent;

public class RegisterItemSubtypesEventJS implements KubeJEIEvent {
    public final ISubtypeRegistration registration;

    public RegisterItemSubtypesEventJS(ISubtypeRegistration registration) {
        this.registration = registration;
    }

	public void registerItemSubtypeInterpreter(@NotNull Item item, @NotNull ISubtypeInterpreter<ItemStack> interpreter) {
		registration.registerSubtypeInterpreter(item, interpreter);
	}
}
