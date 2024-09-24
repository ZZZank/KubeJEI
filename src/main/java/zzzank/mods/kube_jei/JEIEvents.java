package zzzank.mods.kube_jei;

public interface JEIEvents {

	String _PREFIX = "kube_jei.";
	String _REGISTER_PREFIX = _PREFIX + "register_";

    String ON_RUNTIME_AVAILABLE = _PREFIX + "on_runtime_available";
    String REGISTER_ADVANCED = _REGISTER_PREFIX + "advanced";
    String REGISTER_CATEGORIES = _REGISTER_PREFIX + "categories";
    String REGISTER_FLUID_SUBTYPES = _REGISTER_PREFIX + "fluid_subtypes";
    String REGISTER_GUI_HANDLERS = _REGISTER_PREFIX + "gui_handlers";
    String REGISTER_INGREDIENTS = _REGISTER_PREFIX + "ingredients";
    String REGISTER_ITEM_SUBTYPES = _REGISTER_PREFIX + "item_subtypes";
    String REGISTER_RECIPE_CATALYSTS = _REGISTER_PREFIX + "recipe_catalysts";
    String REGISTER_RECIPES = _REGISTER_PREFIX + "recipes";
    String REGISTER_RECIPE_TRANSFER_HANDLERS = _REGISTER_PREFIX + "recipe_transfer_handlers";
    String REGISTER_VANILLA_CATEGORY_EXTENSIONS = _REGISTER_PREFIX + "vanilla_category_extensions";
}
