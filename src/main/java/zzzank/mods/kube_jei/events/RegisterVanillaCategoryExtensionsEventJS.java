package zzzank.mods.kube_jei.events;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import mezz.jei.api.recipe.category.extensions.IExtendableRecipeCategory;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.world.item.crafting.CraftingRecipe;

public class RegisterVanillaCategoryExtensionsEventJS extends JEIEventJS {
    public final IVanillaCategoryExtensionRegistration registration;

    public RegisterVanillaCategoryExtensionsEventJS(IVanillaCategoryExtensionRegistration registration) {
        this.registration = registration;
    }

    @JSInfo("""
        Get the vanilla crafting category, to extend it with your own mod's crafting category extensions.""")
    public IExtendableRecipeCategory<CraftingRecipe, ICraftingCategoryExtension> getCraftingCategory() {
        return registration.getCraftingCategory();
    }
}
