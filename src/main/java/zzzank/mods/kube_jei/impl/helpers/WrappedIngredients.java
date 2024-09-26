package zzzank.mods.kube_jei.impl.helpers;

import lombok.AllArgsConstructor;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 * @author ZZZank
 */
@AllArgsConstructor
public class WrappedIngredients implements IIngredients {
    public final IIngredients raw;

    public IIngredientType<ItemStack> getItemType() {
        return VanillaTypes.ITEM;
    }

    public IIngredientType<FluidStack> getFluidType() {
        return VanillaTypes.FLUID;
    }

    //---proxy---

    @Override
    public void setInputIngredients(List<Ingredient> inputs) {
        raw.setInputIngredients(inputs);
    }

    @Override
    public <T> void setInput(IIngredientType<T> ingredientType, T input) {
        raw.setInput(ingredientType, input);
    }

    @Override
    public <T> void setInputs(IIngredientType<T> ingredientType, List<T> input) {
        raw.setInputs(ingredientType, input);
    }

    @Override
    public <T> void setInputLists(IIngredientType<T> ingredientType, List<List<T>> inputs) {
        raw.setInputLists(ingredientType, inputs);
    }

    @Override
    public <T> void setOutput(IIngredientType<T> ingredientType, T output) {
        raw.setOutput(ingredientType, output);
    }

    @Override
    public <T> void setOutputs(IIngredientType<T> ingredientType, List<T> outputs) {
        raw.setOutputs(ingredientType, outputs);
    }

    @Override
    public <T> void setOutputLists(IIngredientType<T> ingredientType, List<List<T>> outputs) {
        raw.setOutputLists(ingredientType, outputs);
    }

    @Override
    public <T> List<List<T>> getInputs(IIngredientType<T> ingredientType) {
        return raw.getInputs(ingredientType);
    }

    @Override
    public <T> List<List<T>> getOutputs(IIngredientType<T> ingredientType) {
        return raw.getOutputs(ingredientType);
    }
}
