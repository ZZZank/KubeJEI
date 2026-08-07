package zzzank.mods.kube_jei.mod_bridge;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiFeatures;
import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.runtime.config.IJeiConfigManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.KubeJEI;
import zzzank.mods.kube_jei.events.misc.ConfigureJeiEventJS;
import zzzank.mods.kube_jei.events.KubeJEIEvents;
import zzzank.mods.kube_jei.events.misc.OnConfigManagerAvailableEventJS;
import zzzank.mods.kube_jei.events.misc.OnRuntimeAvailableEventJS;
import zzzank.mods.kube_jei.events.misc.OnRuntimeUnavailableEventJS;
import zzzank.mods.kube_jei.events.register.*;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return KubeJEI.rl("jei_plugin");
    }

    @Override
    public void configureJei(@NotNull IJeiFeatures jeiFeatures) {
        KubeJEIEvents.CONFIGURE_JEI.post(new ConfigureJeiEventJS(jeiFeatures));
    }

    @Override
    public void registerItemSubtypes(@NotNull ISubtypeRegistration registration) {
        KubeJEIEvents.REGISTER_ITEM_SUBTYPES.post(new RegisterItemSubtypesEventJS(registration));
    }

    @Override
    public <T> void registerFluidSubtypes(
        @NotNull ISubtypeRegistration registration,
        @NotNull IPlatformFluidHelper<T> platformFluidHelper
    ) {
        KubeJEIEvents.REGISTER_FLUID_SUBTYPES.post(new RegisterFluidSubtypesEventJS(registration, platformFluidHelper));
    }

    @Override
    public void registerIngredients(@NotNull IModIngredientRegistration registration) {
        KubeJEIEvents.REGISTER_INGREDIENTS.post(new RegisterIngredientsEventJS(registration));
    }

    @Override
    public void registerExtraIngredients(@NotNull IExtraIngredientRegistration registration) {
        KubeJEIEvents.REGISTER_EXTRA_INGREDIENTS.post(new RegisterExtraIngredientsEventJS(registration));
    }

    @Override
    public void registerIngredientAliases(@NotNull IIngredientAliasRegistration registration) {
        KubeJEIEvents.REGISTER_INGREDIENT_ALIASES.post(new RegisterIngredientAliasesEventJS(registration));
    }

    @Override
    public void registerAdvancedSearch(@NotNull IAdvancedSearchRegistration registration) {
        KubeJEIEvents.REGISTER_ADVANCED_SEARCH.post(new RegisterAdvancedSearchEventJS(registration));
    }

    @Override
    public void registerModInfo(@NotNull IModInfoRegistration modAliasRegistration) {
        KubeJEIEvents.REGISTER_MOD_INFO.post(new RegisterModInfoEventJS(modAliasRegistration));
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        KubeJEIEvents.REGISTER_CATEGORIES.post(new RegisterCategoriesEventJS(registration));
    }

    @Override
    public void registerVanillaCategoryExtensions(@NotNull IVanillaCategoryExtensionRegistration registration) {
        KubeJEIEvents.REGISTER_VANILLA_CATEGORY_EXTENSIONS.post(new RegisterVanillaCategoryExtensionsEventJS(registration));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        KubeJEIEvents.REGISTER_RECIPES.post(new RegisterRecipesEventJS(registration));
    }

    @Override
    public void registerRecipeTransferHandlers(@NotNull IRecipeTransferRegistration registration) {
        KubeJEIEvents.REGISTER_RECIPE_TRANSFER_HANDLERS.post(new RegisterRecipeTransferHandlersEventJS(registration));
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        KubeJEIEvents.REGISTER_RECIPE_CATALYSTS.post(new RegisterRecipeCatalystsEventJS(registration));
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        KubeJEIEvents.REGISTER_GUI_HANDLERS.post(new RegisterGUIHandlersEventJS(registration));
    }

    @Override
    public void registerAdvanced(@NotNull IAdvancedRegistration registration) {
        KubeJEIEvents.REGISTER_ADVANCED.post(new RegisterAdvancedEventJS(registration));
    }

    @Override
    public void registerRuntime(@NotNull IRuntimeRegistration registration) {
        KubeJEIEvents.REGISTER_RUNTIME.post(new RegisterRuntimeEventJS(registration));
    }

    @Override
    public void onRuntimeAvailable(@NotNull IJeiRuntime jeiRuntime) {
        KubeJEIEvents.ON_RUNTIME_AVAILABLE.post(new OnRuntimeAvailableEventJS(jeiRuntime));
    }

    @Override
    public void onRuntimeUnavailable() {
        KubeJEIEvents.ON_RUNTIME_UNAVAILABLE.post(new OnRuntimeUnavailableEventJS());
    }

    @Override
    public void onConfigManagerAvailable(@NotNull IJeiConfigManager configManager) {
        KubeJEIEvents.ON_CONFIG_MANAGER_AVAILABLE.post(new OnConfigManagerAvailableEventJS(configManager));
    }
}
