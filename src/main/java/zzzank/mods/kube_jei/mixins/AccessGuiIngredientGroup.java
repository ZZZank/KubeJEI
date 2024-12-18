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
    Map<Integer, GuiIngredient<T>> kJei$guiIngredients();

    @Accessor("inputSlots")
    Set<Integer> kJei$inputSlots();

    @Accessor("outputSlots")
    Set<Integer> kJei$outputSlots();

    @Accessor("ingredientHelper")
    IIngredientHelper<T> kJei$ingredientHelper();

    @Accessor("ingredientRenderer")
    IIngredientRenderer<T> kJei$ingredientRenderer();

    @Accessor("ingredientType")
    IIngredientType<T> kJei$ingredientType();

    @Accessor("cycleOffset")
    int kJei$cycleOffset();
}
