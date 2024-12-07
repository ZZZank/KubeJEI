package zzzank.mods.kube_jei.impl.helpers;

import dev.latvian.kubejs.fluid.FluidStackJS;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import lombok.val;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.config.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidStack;
import zzzank.mods.kube_jei.events.JEIEventJS;
import zzzank.mods.kube_jei.util.DualDrawable;

import java.util.function.IntFunction;
import java.util.function.IntSupplier;

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
        Creates an animated texture for a gui, revealing the texture over time.""")
    public IDrawableAnimated animated(
        @JSInfo("the underlying texture to draw")
        IDrawableStatic drawable,
        @JSInfo("the number of ticks for the animation to run before starting over")
        int ticksPerCycle,
        @JSInfo("the direction that the animation starts drawing the texture")
        IDrawableAnimated.StartDirection startDirection,
        @JSInfo("when inverted is true, the texture will start fully drawn and be hidden over time")
        boolean inverted
    ) {
        return guiHelper().createAnimatedDrawable(drawable, ticksPerCycle, startDirection, inverted);
    }

    @JSInfo("""
        Returns a slot drawable for drawing extra slots on guis
        
        the size of this drawable is: width=18, height=18""")
    public IDrawableStatic slot() {
        return guiHelper().getSlotDrawable();
    }

    @JSInfo("""
        Returns a blank drawable for using as a blank recipe background.""")
    public IDrawableStatic blank(int width, int height) {
        return guiHelper().createBlankDrawable(width, height);
    }

    public IDrawable ingredientItem(ItemStackJS item) {
        return guiHelper().createDrawableIngredient(item.getItemStack());
    }

    public IDrawable ingredientFluid(FluidStackJS fluid) {
        return guiHelper().createDrawableIngredient(new FluidStack(fluid.getFluid(), fluid.getAmount(), fluid.getNbt()));
    }

    @JSInfo("""
        Returns a 16x16 drawable for the given ingredient, matching the one JEI draws in the ingredient list.
        
        if you're creating item ingredient drawables, use {@link ingredientItem} for better experience
        
        if you're creating fluid ingredient drawables, use {@link ingredientFluid} for better experience""")
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
        Create a timer to help with rendering things that normally depend on ticks.""")
    public ITickTimer tickTimer(
        @JSInfo("the number of ticks for timer to run before starting over at 0")
        int ticksPerCycle,
        @JSInfo("the number to count up to before starting over at 0")
        int maxValue,
        @JSInfo("if true, the tick timer will count backwards from maxValue")
        boolean countDown) {
        return guiHelper().createTickTimer(ticksPerCycle, maxValue, countDown);
    }

    @JSInfo("""
        Create a timer to help with rendering things that normally depend on ticks.""")
    public ITickTimer tickTimer(
        @JSInfo("an integer supplier that return the 'current' tick value")
        IntSupplier currentTick,
        @JSInfo("the number to count up to before starting over at 0")
        int maxTick) {
        return new CustomTickTimer(currentTick, maxTick);
    }

    @JSInfo("""
        creates a drawable builder that, when built, can be used for rendering a vanilla style arrow.
        
        the size of such drawable is: width=24, height=17""")
    public IDrawableBuilder arrowBuilder() {
        return builder(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17);
    }

    @JSInfo("""
        create a drawable that renders a static, vanilla style arrow
        
        the size of such drawable is: width=24, height=17""")
    public IDrawableStatic arrow() {
        return arrowBuilder().build();
    }

    public IntFunction<IDrawableAnimated> cacheAnimated(
        IDrawableBuilder builder,
        int maxTick,
        IDrawableAnimated.StartDirection startDirection,
        boolean inverted
    ) {
        val cache = new IDrawableAnimated[maxTick];
        return tick -> {
            val cached = cache[tick];
            return cached != null ? cached : (cache[tick] = builder.buildAnimated(tick, startDirection, inverted));
        };
    }
}
