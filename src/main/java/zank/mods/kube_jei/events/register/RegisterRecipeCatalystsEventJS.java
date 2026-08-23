package zank.mods.kube_jei.events.register;

import dev.latvian.mods.kubejs.typings.Info;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import zank.mods.kube_jei.events.KubeJEIEvent;

import java.util.Objects;

public class RegisterRecipeCatalystsEventJS implements KubeJEIEvent {
    public final IRecipeCatalystRegistration registration;

    public RegisterRecipeCatalystsEventJS(IRecipeCatalystRegistration registration) {
        this.registration = registration;
    }

    @Info("""
        a specialized version of {@link addRecipeCatalyst} to make the most frequent catalyst action easier""")
    public void addItemCatalyst(ItemStack[] stacks, Identifier... categoryIds) {
        for (var id : categoryIds) {
            var recipeType = registration.getJeiHelpers()
                .getRecipeType(id)
                .orElseThrow(() -> new IllegalArgumentException("No recipe type for id: " + id));

            registration.addCraftingStation(recipeType, stacks);
        }
    }
}
