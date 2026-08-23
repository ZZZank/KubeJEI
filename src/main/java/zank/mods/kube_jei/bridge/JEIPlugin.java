package zank.mods.kube_jei.bridge;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiFeatures;
import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.runtime.config.IJeiConfigManager;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;
import zank.mods.kube_jei.KubeJEI;
import zank.mods.kube_jei.events.misc.ConfigureJeiEventJS;
import zank.mods.kube_jei.events.KubeJEIEvents;
import zank.mods.kube_jei.events.misc.OnConfigManagerAvailableEventJS;
import zank.mods.kube_jei.events.misc.OnRuntimeAvailableEventJS;
import zank.mods.kube_jei.events.misc.OnRuntimeUnavailableEventJS;
import zank.mods.kube_jei.events.register.*;

@NullMarked
@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public Identifier getPluginUid() {
        return KubeJEI.rl("jei_plugin");
    }

    @Override
    public void configureJei(IJeiFeatures jeiFeatures) {
        KubeJEIEvents.CONFIGURE_JEI.post(new ConfigureJeiEventJS(jeiFeatures));
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        KubeJEIEvents.REGISTER_ITEM_SUBTYPES.post(new RegisterItemSubtypesEventJS(registration));
    }

    @Override
    public <T> void registerFluidSubtypes(
        ISubtypeRegistration registration,
        IPlatformFluidHelper<T> platformFluidHelper
    ) {
        KubeJEIEvents.REGISTER_FLUID_SUBTYPES.post(new RegisterFluidSubtypesEventJS(registration, platformFluidHelper));
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        KubeJEIEvents.REGISTER_INGREDIENTS.post(new RegisterIngredientsEventJS(registration));
    }

    @Override
    public void registerExtraIngredients(IExtraIngredientRegistration registration) {
        KubeJEIEvents.REGISTER_EXTRA_INGREDIENTS.post(new RegisterExtraIngredientsEventJS(registration));
    }

    @Override
    public void registerIngredientAliases(IIngredientAliasRegistration registration) {
        KubeJEIEvents.REGISTER_INGREDIENT_ALIASES.post(new RegisterIngredientAliasesEventJS(registration));
    }

    @Override
    public void registerAdvancedSearch(IAdvancedSearchRegistration registration) {
        KubeJEIEvents.REGISTER_ADVANCED_SEARCH.post(new RegisterAdvancedSearchEventJS(registration));
    }

    @Override
    public void registerModInfo(IModInfoRegistration modAliasRegistration) {
        KubeJEIEvents.REGISTER_MOD_INFO.post(new RegisterModInfoEventJS(modAliasRegistration));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        KubeJEIEvents.REGISTER_CATEGORIES.post(new RegisterCategoriesEventJS(registration));
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        KubeJEIEvents.REGISTER_VANILLA_CATEGORY_EXTENSIONS.post(new RegisterVanillaCategoryExtensionsEventJS(registration));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        KubeJEIEvents.REGISTER_RECIPES.post(new RegisterRecipesEventJS(registration));
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        KubeJEIEvents.REGISTER_RECIPE_TRANSFER_HANDLERS.post(new RegisterRecipeTransferHandlersEventJS(registration));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        KubeJEIEvents.REGISTER_RECIPE_CATALYSTS.post(new RegisterRecipeCatalystsEventJS(registration));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        KubeJEIEvents.REGISTER_GUI_HANDLERS.post(new RegisterGUIHandlersEventJS(registration));
    }

    @Override
    public void registerAdvanced(IAdvancedRegistration registration) {
        KubeJEIEvents.REGISTER_ADVANCED.post(new RegisterAdvancedEventJS(registration));
    }

    @Override
    public void registerRuntime(IRuntimeRegistration registration) {
        KubeJEIEvents.REGISTER_RUNTIME.post(new RegisterRuntimeEventJS(registration));
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        KubeJEIEvents.ON_RUNTIME_AVAILABLE.post(new OnRuntimeAvailableEventJS(jeiRuntime));
    }

    @Override
    public void onRuntimeUnavailable() {
        KubeJEIEvents.ON_RUNTIME_UNAVAILABLE.post(new OnRuntimeUnavailableEventJS());
    }

    @Override
    public void onConfigManagerAvailable(IJeiConfigManager configManager) {
        KubeJEIEvents.ON_CONFIG_MANAGER_AVAILABLE.post(new OnConfigManagerAvailableEventJS(configManager));
    }
}
