package zzzank.mods.kube_jei.events;

import mezz.jei.api.recipe.category.extensions.IExtendableRecipeCategory;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.world.item.crafting.CraftingRecipe;

public class RegisterVanillaCategoryExtensionsEventJS extends JEIEventJS {
    public final IVanillaCategoryExtensionRegistration registration;

    public RegisterVanillaCategoryExtensionsEventJS(IVanillaCategoryExtensionRegistration registration) {
        this.registration = registration;
    }

    public IExtendableRecipeCategory<CraftingRecipe, ICraftingCategoryExtension> getCraftingCategory() {
        return registration.getCraftingCategory();
    }
}
