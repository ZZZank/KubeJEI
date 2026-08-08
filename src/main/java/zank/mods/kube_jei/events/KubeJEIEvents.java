package zank.mods.kube_jei.events;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import zank.mods.kube_jei.events.deny.DenyCategoryEventJS;
import zank.mods.kube_jei.events.deny.DenyRecipeEventJS;
import zank.mods.kube_jei.events.misc.ConfigureJeiEventJS;
import zank.mods.kube_jei.events.misc.OnConfigManagerAvailableEventJS;
import zank.mods.kube_jei.events.misc.OnRuntimeAvailableEventJS;
import zank.mods.kube_jei.events.misc.OnRuntimeUnavailableEventJS;
import zank.mods.kube_jei.events.register.*;

/**
 * @author ZZZank
 */
public interface KubeJEIEvents {
    EventGroup GROUP = EventGroup.of("KubeJEIEvents");

    //region redirected JEI native event

    EventHandler CONFIGURE_JEI = GROUP.client("configureJei", () -> ConfigureJeiEventJS.class);

    EventHandler ON_RUNTIME_AVAILABLE = GROUP.client("onRuntimeAvailable", () -> OnRuntimeAvailableEventJS.class);
    EventHandler ON_RUNTIME_UNAVAILABLE = GROUP.client("onRuntimeUnavailable", () -> OnRuntimeUnavailableEventJS.class);
    EventHandler ON_CONFIG_MANAGER_AVAILABLE =
        GROUP.client("onConfigManagerAvailable", () -> OnConfigManagerAvailableEventJS.class);

    EventHandler REGISTER_ADVANCED = GROUP.client("registerAdvanced", () -> RegisterAdvancedEventJS.class);
    EventHandler REGISTER_ADVANCED_SEARCH =
        GROUP.client("registerAdvancedSearch", () -> RegisterAdvancedSearchEventJS.class);
    EventHandler REGISTER_CATEGORIES = GROUP.client("registerCategories", () -> RegisterCategoriesEventJS.class);
    EventHandler REGISTER_EXTRA_INGREDIENTS =
        GROUP.client("registerExtraIngredients", () -> RegisterExtraIngredientsEventJS.class);
    EventHandler REGISTER_FLUID_SUBTYPES =
        GROUP.client("registerFluidSubtypes", () -> RegisterFluidSubtypesEventJS.class);
    EventHandler REGISTER_GUI_HANDLERS = GROUP.client("registerGuiHandlers", () -> RegisterGUIHandlersEventJS.class);
    EventHandler REGISTER_INGREDIENT_ALIASES =
        GROUP.client("registerIngredientAliases", () -> RegisterIngredientAliasesEventJS.class);
    EventHandler REGISTER_INGREDIENTS = GROUP.client("registerIngredients", () -> RegisterIngredientsEventJS.class);
    EventHandler REGISTER_ITEM_SUBTYPES = GROUP.client("registerItemSubtypes", () -> RegisterItemSubtypesEventJS.class);
    EventHandler REGISTER_MOD_INFO = GROUP.client("registerModInfo", () -> RegisterModInfoEventJS.class);
    EventHandler REGISTER_RECIPE_CATALYSTS =
        GROUP.client("registerRecipeCatalysts", () -> RegisterRecipeCatalystsEventJS.class);
    EventHandler REGISTER_RECIPES = GROUP.client("registerRecipes", () -> RegisterRecipesEventJS.class);
    EventHandler REGISTER_RECIPE_TRANSFER_HANDLERS =
        GROUP.client("registerRecipeTransferHandlers", () -> RegisterRecipeTransferHandlersEventJS.class);
    EventHandler REGISTER_RUNTIME = GROUP.client("registerRuntime", () -> RegisterRuntimeEventJS.class);
    EventHandler REGISTER_VANILLA_CATEGORY_EXTENSIONS =
        GROUP.client("registerVanillaCategoryExtensions", () -> RegisterVanillaCategoryExtensionsEventJS.class);

    //endregion
    //region extended function

    EventHandler DENY_CATEGORIES = GROUP.client("denyCategory", () -> DenyCategoryEventJS.class);
    EventHandler DENY_RECIPES = GROUP.client("denyRecipe", () -> DenyRecipeEventJS.class);

    //endregion
}
