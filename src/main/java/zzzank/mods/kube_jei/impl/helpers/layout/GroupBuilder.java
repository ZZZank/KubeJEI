package zzzank.mods.kube_jei.impl.helpers.layout;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import dev.latvian.mods.rhino.util.HideFromJS;
import lombok.RequiredArgsConstructor;
import lombok.val;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.gui.ingredient.ITooltipCallback;
import mezz.jei.api.recipe.IFocus;
import zzzank.mods.kube_jei.KubeJEI;
import zzzank.mods.kube_jei.mixins.AccessGuiIngredientGroup;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZZZank
 */
@RequiredArgsConstructor
public class GroupBuilder<T, G extends IGuiIngredientGroup<T>> {

    private final G group;
    final AccessGuiIngredientGroup<T> access = KubeJEI.duck(group);
    private final List<SlotBuilder<T>> slots = new ArrayList<>();

    public SlotBuilder<T> addSlot(int x, int y) {
        val builder = new SlotBuilder<>(this, slots.size(), x, y);
        slots.add(builder);
        return builder;
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
            this.group.set(slot.index, slot.ingredients);
        }
    }
}
