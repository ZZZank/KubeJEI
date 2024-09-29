package zzzank.mods.kube_jei.impl.helpers;

import lombok.AllArgsConstructor;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author ZZZank
 */
@AllArgsConstructor
public class WrappedLayout implements IRecipeLayout {
    public final IRecipeLayout raw;
    private int itemIndex = 0;

    public void addItem(ItemStack stack, boolean isInput, int xPosition, int yPosition) {
        getItemStacks().init(itemIndex, isInput, xPosition, yPosition);
        getItemStacks().set(itemIndex, stack);
        itemIndex++;
    }

    @Override
    @NotNull
    public IGuiItemStackGroup getItemStacks() {
        return raw.getItemStacks();
    }

    @Override
    @NotNull
    public IGuiFluidStackGroup getFluidStacks() {
        return raw.getFluidStacks();
    }

    @Override
    @NotNull
    public <T> IGuiIngredientGroup<T> getIngredientsGroup(@NotNull IIngredientType<T> ingredientType) {
        return raw.getIngredientsGroup(ingredientType);
    }

    @Override
    public @Nullable IFocus<?> getFocus() {
        return raw.getFocus();
    }

    @Override
    public @Nullable <V> IFocus<V> getFocus(@NotNull IIngredientType<V> ingredientType) {
        return raw.getFocus(ingredientType);
    }

    @Override
    @NotNull
    public IRecipeCategory<?> getRecipeCategory() {
        return raw.getRecipeCategory();
    }

    @Override
    public void moveRecipeTransferButton(int posX, int posY) {
        raw.moveRecipeTransferButton(posX, posY);
    }

    @Override
    public void setShapeless() {
        raw.setShapeless();
    }
}
