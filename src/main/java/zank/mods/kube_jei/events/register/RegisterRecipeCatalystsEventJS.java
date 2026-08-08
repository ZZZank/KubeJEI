package zank.mods.kube_jei.events.register;

import dev.latvian.mods.kubejs.typings.Info;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.resources.ResourceLocation;
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
    public void addItemCatalyst(ItemStack[] stacks, ResourceLocation... categoryIds) {
        var recipeTypes = new RecipeType<?>[categoryIds.length];
        for (int i = 0; i < categoryIds.length; i++) {
            var id = categoryIds[i];
            recipeTypes[i] = registration.getJeiHelpers()
                .getRecipeType(id)
                .orElseThrow(() -> new IllegalArgumentException("No recipe type for id: " + id));
        }

        for (ItemStack stack : Objects.requireNonNull(stacks)) {
            registration.addRecipeCatalyst(stack, recipeTypes);
        }
    }
}
