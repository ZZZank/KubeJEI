package zzzank.mods.kube_jei.impl.helpers.layout;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import dev.latvian.mods.rhino.util.HideFromJS;
import lombok.val;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.gui.ingredient.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.plugins.vanilla.ingredients.fluid.FluidStackRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import zzzank.mods.kube_jei.KubeJEI;
import zzzank.mods.kube_jei.mixins.AccessGuiIngredientGroup;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ZZZank
 */
public class GroupBuilder<T, G extends IGuiIngredientGroup<T>> {

    final G group;
    final AccessGuiIngredientGroup<T> access;
    final List<SlotBuilder<T>> slots;

    public GroupBuilder(G group) {
        this.group = group;
        access = KubeJEI.duck(this.group);
        slots = new ArrayList<>();
    }

    public List<SlotBuilder<T>> getSlots() {
        return Collections.unmodifiableList(slots);
    }

    public SlotBuilder<T> addSlot(int x, int y) {
        val builder = new SlotBuilder<>(this, slots.size(), x, y);
        slots.add(builder);
        return builder;
    }

    @JSInfo("""
        apply ingredients to added slots, as replacement of `IGuiIngredientGroup#set(IIngredients)`
        
        note that slots added after this action will NOT be affected, you need to manually set ingredients for them""")
    public void applyIngredients(IIngredients ingredients) {
        val outputs = ingredients.getOutputs(access.kJei$ingredientType()).iterator();
        val inputs = ingredients.getInputs(access.kJei$ingredientType()).iterator();
        for (val slot : slots) {
            if (slot.isInput) {
                if (inputs.hasNext()) {
                    slot.ingredients.addAll(inputs.next());
                }
            } else {
                if (outputs.hasNext()) {
                    slot.ingredients.addAll(outputs.next());
                }
            }
        }
    }

    @JSInfo("""
        for each slot in this grid: y = yBegin + row * yStep, x = xBegin + column * xStep, where 0 <= row < rows, 0 <= column < columns""")
    public List<SlotBuilder<T>> addSlotGrid(int xBegin, int yBegin, int rows, int columns, int xStep, int yStep) {
        val grid = new ArrayList<SlotBuilder<T>>(rows * columns);
        for (int row = 0; row < rows; row++) {
            val y = yBegin + row * yStep;
            for (int col = 0; col < columns; col++) {
                val x = xBegin + col * xStep;
                val slot = addSlot(x, y);
                grid.add(slot);
            }
        }
        return grid;
    }

    @JSInfo("""
        for each slot in this grid: y = yBegin + row * 18, x = xBegin + column * 18, where 0 <= row < rows, 0 <= column < columns""")
    public List<SlotBuilder<T>> addSlotGrid(int xBegin, int yBegin, int rows, int columns) {
        return addSlotGrid(xBegin, yBegin, rows, columns, 18, 18);
    }

    @JSInfo("""
        Force this ingredient group to display a different focus.
        This must be set before any ingredients are set.
        
        Useful for recipes that display things in a custom way depending on what the overall recipe focus is.""")
    public void setOverrideDisplayFocus(@Nullable IFocus<T> focus) {
        group.setOverrideDisplayFocus(focus);
    }

    @JSInfo("""
        Set a background image to draw behind the ingredient.
        Some examples are slot background or tank background.""")
    void setBackground(int slotIndex, IDrawable background) {
        group.setBackground(slotIndex, background);
    }

    @JSInfo("""
        Add a callback to alter the tooltip for these ingredients.""")
    public void addTooltipCallback(ITooltipCallback<T> tooltipCallback) {
        group.addTooltipCallback(tooltipCallback);
    }

    @HideFromJS
    public void post() {
        for (val slot : this.slots) {
            postSingle(slot);
        }
    }

    protected void postSingle(SlotBuilder<T> slot) {
        this.group.init(
            slot.index,
            slot.isInput,
            slot.ingredientRenderer,
            slot.x,
            slot.y,
            slot.width,
            slot.height,
            slot.xPadding,
            slot.yPadding
        );
        if (!slot.ingredients.isEmpty()) {
            this.group.set(slot.index, slot.ingredients);
        }
    }

    public static class Item extends GroupBuilder<ItemStack, IGuiItemStackGroup> {

        public Item(IGuiIngredientGroup<ItemStack> group) {
            super((IGuiItemStackGroup) group);
        }

        @Override
        public SlotBuilder.Item addSlot(int x, int y) {
            val builder = new SlotBuilder.Item(this, slots.size(), x, y);
            slots.add(builder);
            return builder;
        }
    }

    public static class Fluid extends GroupBuilder<FluidStack, IGuiFluidStackGroup> {

        public Fluid(IGuiIngredientGroup<FluidStack> group) {
            super((IGuiFluidStackGroup) group);
        }

        @Override
        public SlotBuilder.Fluid addSlot(int x, int y) {
            val builder = new SlotBuilder.Fluid(this, slots.size(), x, y);
            slots.add(builder);
            return builder;
        }

        @Override
        protected final void postSingle(SlotBuilder<FluidStack> slot) {
            val casted = (SlotBuilder.Fluid) slot;
            if (casted.capacityMb != 0) {
                slot.ingredientRenderer = new FluidStackRenderer(
                    casted.capacityMb,
                    casted.showCapacity,
                    casted.width,
                    casted.height,
                    casted.overlay
                );
            }
            super.postSingle(slot);
        }
    }
}
