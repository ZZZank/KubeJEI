package zzzank.mods.kube_jei.plugin;

import dev.latvian.kubejs.script.ScriptType;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IAdvancedRegistration;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.KubeJEI;
import zzzank.mods.kube_jei.events.*;

import static zzzank.mods.kube_jei.KubeJEIEvents.*;

@JeiPlugin
public class KubeJEI_JEIPlugin implements IModPlugin {
    /**
     * The unique ID for this mod plugin.
     * The namespace should be your mod's modId.
     */
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return KubeJEI.rl("jei_plugin");
    }

    /**
     * If your item has subtypes that depend on NBT or capabilities, use this to help JEI identify those subtypes correctly.
     */
    @Override
    public void registerItemSubtypes(@NotNull ISubtypeRegistration registration) {
		new RegisterItemSubtypeEventJS(registration).post(ScriptType.CLIENT, REGISTER_ITEM_SUBTYPES);
    }

    /**
     * If your fluid has subtypes that depend on NBT or capabilities, use this to help JEI identify those subtypes correctly.
     */
    @Override
    public void registerFluidSubtypes(@NotNull ISubtypeRegistration registration) {
		new RegisterFluidSubtypeEventJS(registration).post(ScriptType.CLIENT, REGISTER_FLUID_SUBTYPES);
    }

    /**
     * Register special ingredients, beyond the basic ItemStack and FluidStack.
     */
    @Override
    public void registerIngredients(@NotNull IModIngredientRegistration registration) {
		new RegisterIngredientsEventJS(registration).post(ScriptType.CLIENT, REGISTER_INGREDIENTS);
    }

    /**
     * Register the categories handled by this plugin.
     * These are registered before recipes, so they can be checked for validity.
     */
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        JEIEventJS.JEI_HELPERS = registration.getJeiHelpers();
		new RegisterCategoriesEventJS(registration).post(ScriptType.CLIENT, REGISTER_CATEGORIES);
    }

    /**
     * Register modded extensions to the vanilla crafting recipe category.
     * Custom crafting recipes for your mod should use this to tell JEI how they work.
     */
    @Override
    public void registerVanillaCategoryExtensions(@NotNull IVanillaCategoryExtensionRegistration registration) {
        new RegisterVanillaCategoryExtensionsEventJS(registration).post(ScriptType.CLIENT, REGISTER_VANILLA_CATEGORY_EXTENSIONS);
    }

    /**
     * Register modded recipes.
     */
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        JEIEventJS.JEI_HELPERS = registration.getJeiHelpers();
        RegisterRecipesEventJS event = new RegisterRecipesEventJS(registration);
		event.post(ScriptType.CLIENT, REGISTER_RECIPES);
        for (var builder : event.customRecipeListBuilders) {
            registration.addRecipes(builder.getRecipes(), builder.getRecipeType().uid());
        }
    }

    /**
     * Register recipe transfer handlers (move ingredients from the inventory into crafting GUIs).
     */
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        JEIEventJS.JEI_HELPERS = registration.getJeiHelpers();
		new RegisterRecipeTransferHandlersEventJS(registration).post(ScriptType.CLIENT, REGISTER_RECIPE_TRANSFER_HANDLERS);
    }

    /**
     * Register recipe catalysts.
     * Recipe Catalysts are ingredients that are needed in order to craft other things.
     * Vanilla examples of Recipe Catalysts are the Crafting Table and Furnace.
     */
    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        new RegisterRecipeCatalystsEventJS(registration).post(ScriptType.CLIENT, REGISTER_RECIPE_CATALYSTS);
    }

    /**
     * Register various GUI-related things for your mod.
     * This includes adding clickable areas in your guis to open JEI,
     * and adding areas on the screen that JEI should avoid drawing.
     */
    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        new RegisterGUIHandlersEventJS(registration).post(ScriptType.CLIENT, REGISTER_GUI_HANDLERS);
    }

    /**
     * Register advanced features for your mod plugin.
     */
    @Override
    public void registerAdvanced(IAdvancedRegistration registration) {
        JEIEventJS.JEI_HELPERS = registration.getJeiHelpers();
		new RegisterAdvancedEventJS(registration).post(ScriptType.CLIENT, REGISTER_ADVANCED);
    }

    /**
     * Called when jei's runtime features are available, after all mods have registered.
     */
    @Override
    public void onRuntimeAvailable(@NotNull IJeiRuntime jeiRuntime) {
		new OnRuntimeAvailableEventJS(jeiRuntime).post(ScriptType.CLIENT, ON_RUNTIME_AVAILABLE);
    }
}
