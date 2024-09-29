package zzzank.mods.kube_jei.impl.helpers;

import lombok.AllArgsConstructor;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author ZZZank
 */
@AllArgsConstructor
public class WrappedIngredients implements IIngredients {
    public final IIngredients raw;

    public <T> IIngredientType<T> wrapIngredientType(Class<T> type) {
        return () -> type;
    }

    public IIngredientType<ItemStack> itemIngredientType() {
        return VanillaTypes.ITEM;
    }

    public IIngredientType<FluidStack> fluidIngredientType() {
        return VanillaTypes.FLUID;
    }

    //---proxy---

    @Override
    public void setInputIngredients(@NotNull List<Ingredient> inputs) {
        raw.setInputIngredients(inputs);
    }

    @Override
    public <T> void setInput(@NotNull IIngredientType<T> ingredientType, @NotNull T input) {
        raw.setInput(ingredientType, input);
    }

    @Override
    public <T> void setInputs(@NotNull IIngredientType<T> ingredientType, @NotNull List<T> input) {
        raw.setInputs(ingredientType, input);
    }

    @Override
    public <T> void setInputLists(@NotNull IIngredientType<T> ingredientType, @NotNull List<List<T>> inputs) {
        raw.setInputLists(ingredientType, inputs);
    }

    @Override
    public <T> void setOutput(@NotNull IIngredientType<T> ingredientType, @NotNull T output) {
        raw.setOutput(ingredientType, output);
    }

    @Override
    public <T> void setOutputs(@NotNull IIngredientType<T> ingredientType, @NotNull List<T> outputs) {
        raw.setOutputs(ingredientType, outputs);
    }

    @Override
    public <T> void setOutputLists(@NotNull IIngredientType<T> ingredientType, @NotNull List<List<T>> outputs) {
        raw.setOutputLists(ingredientType, outputs);
    }

    @Override
    public <T> @NotNull List<List<T>> getInputs(@NotNull IIngredientType<T> ingredientType) {
        return raw.getInputs(ingredientType);
    }

    @Override
    public <T> @NotNull List<List<T>> getOutputs(@NotNull IIngredientType<T> ingredientType) {
        return raw.getOutputs(ingredientType);
    }
}
