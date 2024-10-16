package zzzank.mods.kube_jei.impl.helpers;

import dev.latvian.kubejs.fluid.FluidStackJS;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidStack;
import zzzank.mods.kube_jei.events.JEIEventJS;
import zzzank.mods.kube_jei.util.DualDrawable;

/**
 * @author ZZZank
 */
public final class JEIDrawableWrapper {
    public static final JEIDrawableWrapper INSTANCE = new JEIDrawableWrapper();

    private JEIDrawableWrapper() {}

    @JSInfo("""
        @return true if this class is ready to go, otherwise false, where most exposed methods will fail due to NullPointerException""")
    public boolean available() {
        return JEIEventJS.JEI_HELPERS != null;
    }

    @JSInfo("""
        Create a {@link $IDrawableBuilder} which gives more control over drawable creation
        @return a new {@link $IDrawableBuilder} with the given resource location""")
    public IDrawableBuilder builder(ResourceLocation resourceLocation, int u, int v, int width, int height) {
        return guiHelper().drawableBuilder(resourceLocation, u, v, width, height);
    }

    private static IGuiHelper guiHelper() {
        if (JEIEventJS.JEI_HELPERS == null) {
            throw new IllegalStateException("Jei helpers used before initialization!");
        }
        return JEIEventJS.JEI_HELPERS.getGuiHelper();
    }

    @JSInfo("""
        Create a drawable from part of a standard 256x256 gui texture.""")
    public IDrawableStatic standard(ResourceLocation texturePath, int u, int v, int width, int height) {
        return guiHelper().createDrawable(texturePath, u, v, width, height);
    }

    @JSInfo("""
        Creates an animated texture for a gui, revealing the texture over time.
        
        @param drawable       the underlying texture to draw
        @param ticksPerCycle  the number of ticks for the animation to run before starting over
        @param startDirection the direction that the animation starts drawing the texture
        @param inverted       when inverted is true, the texture will start fully drawn and be hidden over time""")
    public IDrawableAnimated animated(
        IDrawableStatic drawable,
        int ticksPerCycle,
        IDrawableAnimated.StartDirection startDirection,
        boolean inverted
    ) {
        return guiHelper().createAnimatedDrawable(drawable, ticksPerCycle, startDirection, inverted);
    }

    @JSInfo("""
        Returns a slot drawable for drawing extra slots on guis""")
    public IDrawableStatic slot() {
        return guiHelper().getSlotDrawable();
    }

    @JSInfo("""
        Returns a blank drawable for using as a blank recipe background.""")
    public IDrawableStatic blank(int width, int height) {
        return guiHelper().createBlankDrawable(width, height);
    }

    @JSInfo("""
        Returns a 16x16 drawable for the given ingredient, matching the one JEI draws in the ingredient list.""")
    public IDrawable ingredient(Object ingredient) {
        if (ingredient instanceof ItemStackJS itemStackJS) {
            ingredient = itemStackJS.getItemStack();
        } else if (ingredient instanceof FluidStackJS fluidStackJS) {
            ingredient = new FluidStack(fluidStackJS.getFluid(), fluidStackJS.getAmount(), fluidStackJS.getNbt());
        } else if (ingredient instanceof ItemLike itemLike) {
            ingredient = itemLike.asItem().getDefaultInstance();
        }
        return guiHelper().createDrawableIngredient(ingredient);
    }

    @JSInfo("""
        Creates a 18x18 drawable that will draw two drawables together,
        where the secondary drawable is at half the size and drawn at the bottom-right corner""")
    public DualDrawable dual(IDrawable primary, IDrawable secondary) {
        return new DualDrawable(primary, secondary);
    }

    @JSInfo("""
        Create a crafting grid helper.
        Helps set crafting-grid-style GuiItemStackGroup.""")
    public ICraftingGridHelper gridHelper(int baseSlotIndex) {
        return guiHelper().createCraftingGridHelper(baseSlotIndex);
    }

    @JSInfo("""
        Create a timer to help with rendering things that normally depend on ticks.
        
        @param ticksPerCycle the number of ticks for timer to run before starting over at 0
        @param maxValue      the number to count up to before starting over at 0
        @param countDown     if true, the tick timer will count backwards from maxValue""")
    public ITickTimer tickTimer(int ticksPerCycle, int maxValue, boolean countDown) {
        return guiHelper().createTickTimer(ticksPerCycle, maxValue, countDown);
    }
}
