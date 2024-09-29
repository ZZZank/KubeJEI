package zzzank.mods.kube_jei.impl.builder;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.latvian.kubejs.text.Text;
import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import lombok.Setter;
import lombok.experimental.Accessors;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.gui.ingredient.ITooltipCallback;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.impl.CustomRecipeCategory;
import zzzank.mods.kube_jei.impl.helpers.WrappedIngredients;
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
    public final IJeiHelpers jeiHelpers;
    @NotNull
    public Text title;
    @NotNull
    public IDrawable background;
    @NotNull
    public IDrawable icon;
    public RecipeSetHandler<T> recipeSetHandler;
    public DrawHandler<T> drawHandler;
    public TooltipHandler<T> tooltipHandler;
    public InputHandler<T> inputHandler;
    public IsRecipeHandledByCategory<T> recipeHandlePredicate;
    public FillIngredientsHandler<T> fillIngredientsHandler;

    public RecipeCategoryBuilder(@NotNull RecipeType<T> recipeType, @NotNull IJeiHelpers jeiHelpers) {
        this.type = recipeType;
        this.jeiHelpers = jeiHelpers;
        this.title = Text.of("KubeJEI Custom Category");
        this.background = this.jeiHelpers.getGuiHelper().createDrawableIngredient(new ItemStack(Items.CREEPER_HEAD));
        this.icon = this.jeiHelpers.getGuiHelper().createDrawableIngredient(new ItemStack(Items.TNT));
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
        @JSInfo("""
            Set the {@link IRecipeLayout} properties from the recipe.
            
            @param layout      the layout that needs its properties set.
            @param recipe      the recipe, for extra information.
            @param ingredients the ingredients, already set earlier by {@link IRecipeCategory#setIngredients}""")
        void setRecipe(IRecipeLayout layout, T recipe, IIngredients ingredients);
    }

    @FunctionalInterface
    public interface DrawHandler<T> {
        @JSInfo("""
            Draw extras or additional info about the recipe.
            Use the mouse position for things like button highlights.
            Tooltips are handled by {@link IRecipeCategory#getTooltipStrings(Object, double, double)}
            
            @param mouseX the X position of the mouse, relative to the recipe.
            @param mouseY the Y position of the mouse, relative to the recipe.
            @see event.drawables for a simple class for drawing things.
            @see event.jeiHelpers for useful functions.""")
        void draw(T recipe, PoseStack matrixStack, double mouseX, double mouseY);
    }

    @FunctionalInterface
    public interface TooltipHandler<T> {
        @JSInfo("""
            Get the tooltip for whatever under the mouse.
            Ingredient tooltips are already handled by JEI, this is for anything else.
            
            To add to ingredient tooltips, see {@link IGuiIngredientGroup#addTooltipCallback(ITooltipCallback)}
            
            @param mouseX the X position of the mouse, relative to the recipe.
            @param mouseY the Y position of the mouse, relative to the recipe.
            @return tooltip strings. If there is no tooltip at this position, return an empty list.""")
        @NotNull
        List<Component> getTooltipStrings(T recipe, double mouseX, double mouseY);
    }

    @FunctionalInterface
    public interface InputHandler<T> {
        @JSInfo("""
            Called when a player clicks the recipe.
            Useful for implementing buttons, hyperlinks, and other interactions to your recipe.
            
            @param recipe the currently hovered recipe
            @param mouseX the X position of the mouse, relative to the recipe.
            @param mouseY the Y position of the mouse, relative to the recipe.
            @param input  the current input
            @return true if the input was handled, false otherwise""")
        boolean handleInput(T recipe, double mouseX, double mouseY, int input);
    }

    @FunctionalInterface
    public interface IsRecipeHandledByCategory<T> {
        @JSInfo("""
            @return true if the given recipe can be handled by this category.""")
        boolean isHandled(T recipe);
    }

    public interface FillIngredientsHandler<T> {
        @JSInfo("""
            Sets all the recipe's ingredients by filling out an instance of {@link IIngredients}.
            This is used by JEI for lookups, to figure out what ingredients are inputs and outputs for a recipe.""")
        void setIngredients(T recipe, WrappedIngredients ingredients);
    }
}
