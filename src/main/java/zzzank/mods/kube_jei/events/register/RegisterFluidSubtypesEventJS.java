package zzzank.mods.kube_jei.events.register;

import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.events.KubeJEIEvent;

public class RegisterFluidSubtypesEventJS implements KubeJEIEvent {
    public final ISubtypeRegistration registration;
    public final IPlatformFluidHelper<?> platformHelper;

    public <T> RegisterFluidSubtypesEventJS(
        ISubtypeRegistration registration,
        @NotNull IPlatformFluidHelper<T> platformHelper
    ) {
        this.registration = registration;
        this.platformHelper = platformHelper;
    }

	public void registerFluidSubtypeInterpreter(@NotNull Fluid fluid, @NotNull ISubtypeInterpreter<FluidStack> interpreter) {
		registration.registerSubtypeInterpreter(NeoForgeTypes.FLUID_STACK, fluid, interpreter);
	}
}
