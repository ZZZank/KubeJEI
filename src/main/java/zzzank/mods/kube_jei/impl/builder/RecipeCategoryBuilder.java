package zzzank.mods.kube_jei.impl.builder;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.latvian.kubejs.text.Text;
import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import lombok.Setter;
import lombok.experimental.Accessors;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IJeiHelpers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.impl.CustomRecipeCategory;
import zzzank.mods.kube_jei.impl.helpers.WrappedIngredients;
import zzzank.mods.kube_jei.impl.helpers.layout.WrappedLayout;
import zzzank.mods.kube_jei.impl.recipe_type.RecipeType;

import java.util.List;

@Setter
@Accessors(chain = true)
public class RecipeCategoryBuilder<T> {

    private CustomRecipeCategory<T> category;

    public CustomRecipeCategory<T> asCategory() {
        return category == null ? (category = new CustomRecipeCategory<>(this)) : category;
    }

    @NotNull
    public final RecipeType<T> type;
    @NotNull
    public Text title;
    @NotNull
    public IDrawable background;
    @NotNull
    public IDrawable icon;
    @JSInfo("""
        Set the {@link $IRecipeLayout} properties from the recipe.
        
        see its class declaration for parameter document""")
    public RecipeSetHandler<T> recipeSetHandler;
    @JSInfo("""
        Draw extras or additional info about the recipe.
        Use the mouse position for things like button highlights.
        Tooltips are handled by {@link $IRecipeCategory#getTooltipStrings(Object, double, double)}
        
        see its class declaration for parameter document""")
    public DrawHandler<T> drawHandler;
    @JSInfo("""
        Get the tooltip for whatever under the mouse.
        Ingredient tooltips are already handled by JEI, this is for anything else.
    
        To add to ingredient tooltips, see {@link $IGuiIngredientGroup#addTooltipCallback(ITooltipCallback)}
        
        see its class declaration for parameter document""")
    public TooltipHandler<T> tooltipHandler;
    @JSInfo("""
        Called when a player clicks the recipe.
        Useful for implementing buttons, hyperlinks, and other interactions to your recipe.
        
        see its class declaration for parameter document""")
    public InputHandler<T> inputHandler;
    @JSInfo("return true if the given recipe can be handled by this category.")
    public IsRecipeHandledByCategory<T> recipeHandlePredicate;
    @JSInfo("""
        Sets all the recipe's ingredients by filling out an instance of {@link $IIngredients}.
        This is used by JEI for lookups, to figure out what ingredients are inputs and outputs for a recipe.""")
    public FillIngredientsHandler<T> fillIngredientsHandler;

    public RecipeCategoryBuilder(@NotNull RecipeType<T> recipeType, @NotNull IJeiHelpers jeiHelpers) {
        this.type = recipeType;
        this.title = Text.of("KubeJEI Custom Category");
        this.background = jeiHelpers.getGuiHelper().createDrawableIngredient(new ItemStack(Items.CREEPER_HEAD));
        this.icon = jeiHelpers.getGuiHelper().createDrawableIngredient(new ItemStack(Items.TNT));
    }

    public RecipeCategoryBuilder<T> isRecipeHandled(IsRecipeHandledByCategory<T> isRecipeHandledByCategory) {
        return setRecipeHandlePredicate(isRecipeHandledByCategory);
    }

    public RecipeCategoryBuilder<T> onInput(InputHandler<T> inputHandler) {
        return setInputHandler(inputHandler);
    }

    public RecipeCategoryBuilder<T> withTooltip(TooltipHandler<T> tooltipHandler) {
        return setTooltipHandler(tooltipHandler);
    }

    public RecipeCategoryBuilder<T> handleLookup(RecipeSetHandler<T> recipeHandler) {
        return setRecipeSetHandler(recipeHandler);
    }

    public RecipeCategoryBuilder<T> fillIngredients(FillIngredientsHandler<T> handler) {
        return setFillIngredientsHandler(handler);
    }

    @FunctionalInterface
    public interface RecipeSetHandler<T> {
        void setRecipe(
            @JSInfo("the layout that needs its properties set")
            WrappedLayout layout,
            @JSInfo("the recipe, for extra information")
            T recipe,
            @JSInfo("the ingredients, already set earlier by {@link $IRecipeCategory#setIngredients}")
            WrappedIngredients ingredients);
    }

    @FunctionalInterface
    public interface DrawHandler<T> {
        @JSInfo("""
            @see event.drawables for a simple class for drawing things.
            @see event.jeiHelpers for useful functions.""")
        void draw(T recipe, PoseStack matrixStack,
            @JSInfo("the X position of the mouse, relative to the recipe")
            double mouseX,
            @JSInfo("the Y position of the mouse, relative to the recipe")
            double mouseY);
    }

    @FunctionalInterface
    public interface TooltipHandler<T> {
        @JSInfo("""
            @return tooltip strings. If there is no tooltip at this position, return an empty list.""")
        @NotNull
        List<Component> getTooltipStrings(T recipe,
            @JSInfo("mouseX the X position of the mouse, relative to the recipe")
            double mouseX,
            @JSInfo("mouseY the Y position of the mouse, relative to the recipe")
            double mouseY);
    }

    @FunctionalInterface
    public interface InputHandler<T> {
        @JSInfo("""
            @return true if the input was handled, false otherwise""")
        boolean handleInput(
            @JSInfo("the currently hovered recipe")
            T recipe,
            @JSInfo("the X position of the mouse, relative to the recipe")
            double mouseX,
            @JSInfo("the Y position of the mouse, relative to the recipe")
            double mouseY,
            @JSInfo("the current input")
            int input);
    }

    @FunctionalInterface
    public interface IsRecipeHandledByCategory<T> {
        boolean isHandled(T recipe);
    }

    public interface FillIngredientsHandler<T> {
        void setIngredients(T recipe, WrappedIngredients ingredients);
    }
}
