package zzzank.mods.kube_jei.events.register;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.registration.IModIngredientRegistration;
import zzzank.mods.kube_jei.events.JEIEventJS;

import java.util.Collection;

public class RegisterIngredientsEventJS extends JEIEventJS {
    public final IModIngredientRegistration registration;

    public RegisterIngredientsEventJS(IModIngredientRegistration registration) {
        this.registration = registration;
    }

    @JSInfo("""
        Register a new type of ingredient.
        @param ingredientType     The type of the ingredient.
        @param allIngredients     A collection of every to be displayed in the ingredient list.
        @param ingredientHelper   The ingredient helper to allows JEI to get information about ingredients for searching and other purposes.
        @param ingredientRenderer The ingredient render to allow JEI to render these ingredients in the ingredient list.
                                  This ingredient renderer must be configured to draw in a 16 by 16 pixel space.""")
    public <V> void register(
        IIngredientType<V> ingredientType,
        Collection<V> allIngredients,
        IIngredientHelper<V> ingredientHelper,
        IIngredientRenderer<V> ingredientRenderer
    ) {
        registration.register(ingredientType, allIngredients, ingredientHelper, ingredientRenderer);
    }
}
