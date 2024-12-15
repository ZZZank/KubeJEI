package zzzank.mods.kube_jei.impl.helpers.layout;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import lombok.Setter;
import lombok.experimental.Accessors;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ZZZank
 */
@Setter
@Accessors(chain = true)
public class SlotBuilder<T> {

    @JSInfo("the slot index of this ingredient")
    public final int index;
    @JSInfo("x position relative to the recipe background")
    public final int x;
    @JSInfo("y position relative to the recipe background")
    public final int y;
    @JSInfo("ingredients attached to this slot, will be displayed in round-robin")
    public final List<T> ingredients;
    @JSInfo("whether this slot is an input, default to true")
    public boolean isInput = true;
    @JSInfo("width of this ingredient, default to 16")
    public int width = 16;
    @JSInfo("height of this ingredient, default to 16")
    public int height = 16;
    @JSInfo("the extra x padding added to each side when drawing the ingredient, default to 0")
    public int xPadding = 0;
    @JSInfo("the extra y padding added to each side when drawing the ingredient, default to 0")
    public int yPadding = 0;
    @JSInfo("the ingredient renderer for this ingredient, default to the default renderer of its belonging group")
    public IIngredientRenderer<T> ingredientRenderer;

    public SlotBuilder(GroupBuilder<T, ?> group, int index, int x, int y) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.ingredientRenderer = group.access.ingredientRenderer();
        this.ingredients = new ArrayList<>();
    }

    @SafeVarargs
    public final SlotBuilder<T> addIngredient(T... ingredients) {
        this.ingredients.addAll(Arrays.asList(ingredients));
        return this;
    }

    public static class Item extends SlotBuilder<ItemStack> {

        public Item(GroupBuilder<ItemStack, ?> group, int index, int x, int y) {
            super(group, index, x, y);
        }

        public Item addIngredients(Ingredient ingredient) {
            return (Item) addIngredient(ingredient.getItems());
        }
    }

    @Setter
    @Accessors(chain = true)
    public static class Fluid extends SlotBuilder<FluidStack> {
        @JSInfo("""
            maximum amount of fluid that this `tank` can hold in milli-buckets
            
            default to 0. Setting this field to any non-zero value will make KubeJEI replace fluid ingredient renderer with a renderer that can display `amount`.""")
        public int capacityMb = 0;
        @JSInfo("show the capacity in the tooltip")
        public boolean showCapacity;
        @JSInfo("""
            optional overlay to display over the tank.

            Typically the overlay is fluid level lines, but it could also be a mask to shape the tank.""")
        public IDrawable overlay;

        public Fluid(GroupBuilder<FluidStack, ?> group, int index, int x, int y) {
            super(group, index, x, y);
        }
    }
}
