package zzzank.mods.kube_jei.impl.helpers;

import dev.latvian.kubejs.fluid.FluidStackJS;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.item.ingredient.IngredientJS;
import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import lombok.AllArgsConstructor;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.util.KJeiUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZZZank
 */
@AllArgsConstructor
public class WrappedIngredients implements IIngredients {
    public final IIngredients raw;

    @JSInfo("""
        wrap param `type` into IIngredientType, where type should be a `$Class<Something>`,
        `typeof Something` also works because in Rhino/Rhizo they are basically the same""")
    public <T> IIngredientType<T> wrapIngredientType(Class<T> type) {
        return () -> type;
    }

    @JSInfo("""
        predefined vanilla item ingredient type""")
    public IIngredientType<ItemStack> itemIngredientType() {
        return VanillaTypes.ITEM;
    }

    @JSInfo("""
        predefined Forge fluid ingredient type
        
        note that this is FORGE fluid, not ARCH fluid that KubeJS is using.
        Use `fluidStackFromJS()` to wrap KJS one into Forge one""")
    public IIngredientType<FluidStack> fluidIngredientType() {
        return VanillaTypes.FLUID;
    }

    public void setItemInputs(IngredientJS... ingredients) {
        setInputLists(
            itemIngredientType(),
            Arrays.stream(ingredients)
                .map(IngredientJS::getStacks)
                .map(s -> KJeiUtils.mapToList(s, ItemStackJS::getItemStack))
                .collect(Collectors.toList())
        );
    }

    public void setItemOutputs(IngredientJS... outputs) {
        setOutputLists(itemIngredientType(),
            KJeiUtils.mapToList(
                outputs,
                js -> KJeiUtils.mapToList(js.getStacks(), ItemStackJS::getItemStack)
            )
        );
    }

    public void setFluidInputs(FluidStackJS... fluids) {
        setInputs(fluidIngredientType(), KJeiUtils.mapToList(fluids, this::fluidStackFromJS));
    }

    public void setFluidIngredientInputs(FluidStackJS[]... ingredients) {
        setInputLists(
            fluidIngredientType(),
            KJeiUtils.mapToList(
                ingredients,
                arr -> KJeiUtils.mapToList(arr, this::fluidStackFromJS)
            )
        );
    }

    public void setFluidOutputs(FluidStackJS... outputs) {
        setOutputs(fluidIngredientType(), KJeiUtils.mapToList(outputs, this::fluidStackFromJS));
    }

    @JSInfo("""
        create a new Forge FluidStack from FluidStackJS""")
    public FluidStack fluidStackFromJS(FluidStackJS fluidStackJS) {
        return new FluidStack(fluidStackJS.getFluid(), fluidStackJS.getAmount(), fluidStackJS.getNbt());
    }

    //---proxy---

    @JSInfo("""
        Sets the recipe's inputs. Each input ingredient represents one slot.""")
    @Override
    public void setInputIngredients(@NotNull List<Ingredient> inputs) {
        raw.setInputIngredients(inputs);
    }

    @JSInfo("""
        Sets a single recipe input. For recipes with only one input slot.""")
    @Override
    public <T> void setInput(
        @JSInfo("The type of ingredient: {@link itemIngredientType}, {@link fluidIngredientType}, etc")
        @NotNull IIngredientType<T> ingredientType,
        @JSInfo("The list of ingredients representing each input slot")
        @NotNull T input) {
        raw.setInput(ingredientType, input);
    }

    @JSInfo("""
        Sets the recipe's inputs. Each list element represents one slot.""")
    @Override
    public <T> void setInputs(
        @JSInfo("The type of ingredient: {@link itemIngredientType}, {@link fluidIngredientType}, etc")
        @NotNull IIngredientType<T> ingredientType,
        @JSInfo("The list of ingredients representing each input slot")
        @NotNull List<T> input) {
        raw.setInputs(ingredientType, input);
    }

    @JSInfo("""
        Sets the recipe's inputs. Each input list represents one slot.
        Accepts multiple ingredients per slot.""")
    @Override
    public <T> void setInputLists(
        @JSInfo("The type of ingredient: {@link itemIngredientType}, {@link fluidIngredientType}, etc")
        @NotNull IIngredientType<T> ingredientType,
        @JSInfo("The outer list represents the slot, the inner list is a rotating list of ingredients in that slot")
        @NotNull List<List<T>> inputs) {
        raw.setInputLists(ingredientType, inputs);
    }

    @JSInfo("""
        Sets a single recipe output.""")
    @Override
    public <T> void setOutput(
        @JSInfo("The type of ingredient: {@link itemIngredientType}, {@link fluidIngredientType}, etc")
        @NotNull IIngredientType<T> ingredientType,
        @JSInfo("The single ingredient representing the recipe output")
        @NotNull T output) {
        raw.setOutput(ingredientType, output);
    }

    @JSInfo("""
        Sets multiple recipe outputs. Each list element represents one slot.""")
    @Override
    public <T> void setOutputs(
        @JSInfo("The type of ingredient: {@link $VanillaTypes#ITEM}, {@link $VanillaTypes#FLUID}, etc")
        @NotNull IIngredientType<T> ingredientType,
        @JSInfo("The list of ingredients representing each output slot")
        @NotNull List<T> outputs) {
        raw.setOutputs(ingredientType, outputs);
    }

    @JSInfo("""
        Sets the recipe's outputs. Each output list represents one slot.
        Accepts multiple ingredients per slot.""")
    @Override
    public <T> void setOutputLists(
        @JSInfo("The type of ingredient: {@link $VanillaTypes#ITEM}, {@link $VanillaTypes#FLUID}, etc")
        @NotNull IIngredientType<T> ingredientType,
        @JSInfo("The outer list represents the slot, the inner list is a rotating list of ingredients in that slot")
        @NotNull List<List<T>> outputs) {
        raw.setOutputLists(ingredientType, outputs);
    }

    @JSInfo("""
        Get all the inputs that have been set for the ingredientClass. Each list element represents one slot. The inner list represents the ingredient(s) in the slot.""")
    @Override
    public <T> @NotNull List<List<T>> getInputs(@NotNull IIngredientType<T> ingredientType) {
        return raw.getInputs(ingredientType);
    }

    @JSInfo("""
        Get all the outputs that have been set for the ingredientClass. Each list element represents one slot.""")
    @Override
    public <T> @NotNull List<List<T>> getOutputs(@NotNull IIngredientType<T> ingredientType) {
        return raw.getOutputs(ingredientType);
    }
}
