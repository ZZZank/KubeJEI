package zzzank.mods.kube_jei.events.register;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.events.JEIEventJS;

public class RegisterFluidSubtypeEventJS extends JEIEventJS {
    public final ISubtypeRegistration registration;

    public RegisterFluidSubtypeEventJS(ISubtypeRegistration registration) {
        this.registration = registration;
    }

	@JSInfo("""
		Add an interpreter to compare fluid subtypes.
		This interpreter should account for nbt and anything else that's relevant to differentiating the fluid's subtypes.
		@param fluid       the fluid that has subtypes.
		@param interpreter the interpreter for the fluid.""")
	public void registerSubtypeInterpreter(@NotNull Fluid fluid, @NotNull IIngredientSubtypeInterpreter<FluidStack> interpreter) {
		registration.registerSubtypeInterpreter(fluid, interpreter);
	}

	@JSInfo("""
		Tells JEI to treat all NBT as relevant to these fluids' subtypes.""")
	public void useNbtForSubtypes(Fluid @NotNull ... fluids) {
		registration.useNbtForSubtypes(fluids);
	}

	@JSInfo("""
		Returns whether any `IIngredientSubtypeInterpreter` has been registered for this fluid.""")
	public boolean hasSubtypeInterpreter(@NotNull FluidStack fluidStack) {
		return registration.hasSubtypeInterpreter(fluidStack);
	}
}
