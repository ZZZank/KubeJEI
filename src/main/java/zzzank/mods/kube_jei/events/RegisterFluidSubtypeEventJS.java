package zzzank.mods.kube_jei.events;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class RegisterFluidSubtypeEventJS extends JEIEventJS {
    public final ISubtypeRegistration registration;

    public RegisterFluidSubtypeEventJS(ISubtypeRegistration registration) {
        this.registration = registration;
    }

	public void registerSubtypeInterpreter(@NotNull Fluid fluid, @NotNull IIngredientSubtypeInterpreter<FluidStack> interpreter) {
		registration.registerSubtypeInterpreter(fluid, interpreter);
	}

	public void useNbtForSubtypes(Fluid @NotNull ... fluids) {
		registration.useNbtForSubtypes(fluids);
	}

	public boolean hasSubtypeInterpreter(@NotNull FluidStack fluidStack) {
		return registration.hasSubtypeInterpreter(fluidStack);
	}
}
