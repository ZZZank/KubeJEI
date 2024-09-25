package zzzank.mods.kube_jei.impl;

import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.resources.ResourceLocation;
import zzzank.mods.kube_jei.events.JEIEventJS;

/**
 * @author ZZZank
 */
public final class JEIDrawables {
    private static final JEIDrawables INSTANCE = new JEIDrawables();

    private JEIDrawables() {}

    public static JEIDrawables get() {
        return JEIEventJS.JEI_HELPERS == null ? null : INSTANCE;
    }

    /**
     * Create a {@link IDrawableBuilder} which gives more control over drawable creation.
     *
     * @return a new {@link IDrawableBuilder} with the given resource location
     */
    public IDrawableBuilder builder(ResourceLocation resourceLocation, int u, int v, int width, int height) {
        return guiHelper().drawableBuilder(resourceLocation, u, v, width, height);
    }

    private static IGuiHelper guiHelper() {
        if (JEIEventJS.JEI_HELPERS == null) {
            throw new IllegalStateException("Jei helpers used before initialization!");
        }
        return JEIEventJS.JEI_HELPERS.getGuiHelper();
    }

    /**
     * Create a drawable from part of a standard 256x256 gui texture.
     */
    public IDrawableStatic standard(ResourceLocation resourceLocation, int u, int v, int width, int height) {
        return builder(resourceLocation, u, v, width, height).build();
    }

    /**
     * Creates an animated texture for a gui, revealing the texture over time.
     *
     * @param drawable       the underlying texture to draw
     * @param ticksPerCycle  the number of ticks for the animation to run before starting over
     * @param startDirection the direction that the animation starts drawing the texture
     * @param inverted       when inverted is true, the texture will start fully drawn and be hidden over time
     */
    public IDrawableAnimated animated(
        IDrawableStatic drawable,
        int ticksPerCycle,
        IDrawableAnimated.StartDirection startDirection,
        boolean inverted
    ) {
        return guiHelper().createAnimatedDrawable(drawable, ticksPerCycle, startDirection, inverted);
    }

    /**
     * Returns a slot drawable for drawing extra slots on guis
     */
    public IDrawableStatic slot() {
        return guiHelper().getSlotDrawable();
    }

    /**
     * Returns a blank drawable for using as a blank recipe background.
     */
    public IDrawableStatic blank(int width, int height) {
        return guiHelper().createBlankDrawable(width, height);
    }

    /**
     * Returns a 16x16 drawable for the given ingredient, matching the one JEI draws in the ingredient list.
     */
    public <V> IDrawable ingredient(V ingredient) {
        return guiHelper().createDrawableIngredient(ingredient);
    }

    /**
     * Create a crafting grid helper.
     * Helps set crafting-grid-style GuiItemStackGroup.
     */
    public ICraftingGridHelper gridHelper(int baseSlotIndex) {
        return guiHelper().createCraftingGridHelper(baseSlotIndex);
    }

    /**
     * Create a timer to help with rendering things that normally depend on ticks.
     *
     * @param ticksPerCycle the number of ticks for timer to run before starting over at 0
     * @param maxValue      the number to count up to before starting over at 0
     * @param countDown     if true, the tick timer will count backwards from maxValue
     */
    public ITickTimer tickTimer(int ticksPerCycle, int maxValue, boolean countDown) {
        return guiHelper().createTickTimer(ticksPerCycle, maxValue, countDown);
    }
}
