package zzzank.mods.kube_jei.events.register;

import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.resources.ResourceLocation;
import zzzank.mods.kube_jei.events.JEIEventJS;

import java.util.Objects;

public class RegisterRecipeCatalystsEventJS extends JEIEventJS {
    public final IRecipeCatalystRegistration registration;

    public RegisterRecipeCatalystsEventJS(IRecipeCatalystRegistration registration) {
        this.registration = registration;
    }

    @JSInfo("""
        Add an association between an ingredient and what it can craft. (i.e. Furnace ItemStack -> Smelting and Fuel Recipes)
        Allows players to see what ingredient they need to craft in order to make recipes from a recipe category.
        @param catalystIngredient the ingredient that can craft recipes (like a furnace or crafting table)
        @param recipeCategoryUids the recipe categories handled by the ingredient""")
    public void addRecipeCatalyst(Object catalystIngredient, ResourceLocation... recipeCategoryUids) {
        registration.addRecipeCatalyst(catalystIngredient, recipeCategoryUids);
    }

    @JSInfo("""
        a specialized version of {@link addRecipeCatalyst} to make the most frequent catalyst action easier""")
    public void addItemCatalyst(ItemStackJS[] stacks, ResourceLocation... categoryIds) {
        for (ItemStackJS stack : Objects.requireNonNull(stacks)) {
            addRecipeCatalyst(stack.getItemStack(), categoryIds);
        }
    }
}
