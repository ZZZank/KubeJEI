package zzzank.mods.kube_jei.mixins;

import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.gui.ingredients.GuiIngredient;
import mezz.jei.gui.ingredients.GuiIngredientGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.Set;

/**
 * @author ZZZank
 */
@Mixin(value = GuiIngredientGroup.class, remap = false)
public interface AccessGuiIngredientGroup<T> {
    @Accessor("guiIngredients")
    Map<Integer, GuiIngredient<T>> guiIngredients();

    @Accessor("inputSlots")
    Set<Integer> inputSlots();

    @Accessor("outputSlots")
    Set<Integer> outputSlots();

    @Accessor("ingredientHelper")
    IIngredientHelper<T> ingredientHelper();

    @Accessor("ingredientRenderer")
    IIngredientRenderer<T> ingredientRenderer();

    @Accessor("ingredientType")
    IIngredientType<T> ingredientType();

    @Accessor("cycleOffset")
    int cycleOffset();
}
