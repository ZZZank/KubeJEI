package zzzank.mods.kube_jei.impl.helpers.layout;

import dev.latvian.mods.rhino.util.HideFromJS;
import lombok.AllArgsConstructor;
import lombok.val;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author ZZZank
 */
@AllArgsConstructor
public class WrappedLayout implements IRecipeLayout {
    public final IRecipeLayout raw;
    private final Map<IGuiIngredientGroup, GroupBuilder> groups = new IdentityHashMap<>();

    public <T> GroupBuilder<T, IGuiIngredientGroup<T>> getGroupBuilder(@NotNull IIngredientType<T> ingredientType) {
        return groups.computeIfAbsent(getIngredientsGroup(ingredientType), GroupBuilder::new);
    }

    public GroupBuilder<ItemStack, IGuiItemStackGroup> getItemGroupBuilder() {
        return groups.computeIfAbsent(getItemStacks(), GroupBuilder::new);
    }

    public GroupBuilder<FluidStack, IGuiFluidStackGroup> getFluidGroupBuilder() {
        return groups.computeIfAbsent(getFluidStacks(), GroupBuilder::new);
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

    @HideFromJS
    public void post() {
        for (val builder : this.groups.values()) {
            builder.post();
        }
    }
}
