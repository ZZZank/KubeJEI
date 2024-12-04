package zzzank.mods.kube_jei.impl.helpers.layout;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import mezz.jei.api.ingredients.IIngredientRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ZZZank
 */
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class SlotBuilder<T> {

    private final GroupBuilder<T, ?> group;
    @JSInfo("the slot index of this ingredient")
    public final int index;
    @JSInfo("x position relative to the recipe background")
    public final int x;
    @JSInfo("y position relative to the recipe background")
    public final int y;
    @JSInfo("ingredients attached to this slot, will be displayed in round-robin")
    public final List<T> ingredients = new ArrayList<>();
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
    public IIngredientRenderer<T> ingredientRenderer = group.access.ingredientRenderer();

    public SlotBuilder<T> addIngredient(T ingredient) {
        ingredients.add(Objects.requireNonNull(ingredient));
        return this;
    }
}
