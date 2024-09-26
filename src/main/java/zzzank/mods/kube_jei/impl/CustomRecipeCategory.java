package zzzank.mods.kube_jei.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.latvian.kubejs.util.ConsoleJS;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import zzzank.mods.kube_jei.impl.builder.RecipeCategoryBuilder;
import zzzank.mods.kube_jei.impl.helpers.WrappedIngredients;

import java.util.List;

public class CustomRecipeCategory<T> implements IRecipeCategory<T> {

    private final RecipeCategoryBuilder<T> builder;

    public CustomRecipeCategory(RecipeCategoryBuilder<T> builder) {
        this.builder = builder;
    }

	@Override
	public @NotNull ResourceLocation getUid() {
		return this.builder.type.uid();
	}

	@Override
	public @NotNull Class<? extends T> getRecipeClass() {
		return this.builder.type.type();
	}

	@Override
    public @NotNull String getTitle() {
        return this.builder.title.getString();
    }

	@Override
	public @NotNull Component getTitleAsTextComponent() {
		return builder.title.component();
	}

	/**
     * Returns the drawable background for a single recipe in this category.
     */
    @Override
    public @NotNull IDrawable getBackground() {
        return this.builder.background;
    }

    /**
     * Icon for the category tab.
     * You can use {@link IGuiHelper#createDrawableIngredient(Object)}
     * to create a drawable from an ingredient.
     *
     * @return icon to draw on the category tab, max size is 16x16 pixels.
     */
    @Override
    public @NotNull IDrawable getIcon() {
        return this.builder.icon;
    }

	@Override
	public void setIngredients(@NotNull T recipe, @NotNull IIngredients ingredients) {
        if (this.builder.fillIngredientsHandler == null) {
            return;
        }
        try {
            this.builder.fillIngredientsHandler.setIngredients(recipe, new WrappedIngredients(ingredients));
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error setting recipe for recipe category: " + this.getUid(), e);
        }
	}

    /**
     * Set the {@link IRecipeLayout} properties from the recipe.
     *
     * @param layout      the layout that needs its properties set.
     * @param recipe      the recipe, for extra information.
     * @param ingredients the ingredients, already set earlier by {@link IRecipeCategory#setIngredients}
     */
    @Override
    public void setRecipe(@NotNull IRecipeLayout layout, @NotNull T recipe, @NotNull IIngredients ingredients) {
        if (this.builder.recipeSetHandler == null) {
            return;
        }
        try {
            this.builder.recipeSetHandler.setRecipe(layout, recipe, ingredients);
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error setting recipe for recipe category: " + this.getUid(), e);
        }
    }

	@Override
	public void draw(@NotNull T recipe, @NotNull PoseStack matrixStack, double mouseX, double mouseY) {
		try {
			if (this.builder.drawHandler != null) {
				this.builder.drawHandler.draw(recipe, matrixStack, mouseX, mouseY);
			}
		} catch (Throwable e) {
			ConsoleJS.CLIENT.error("Error drawing recipe category: " + this.getUid(), e);
		}
		IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);
	}

    @Override
    public @NotNull List<Component> getTooltipStrings(@NotNull T recipe, double mouseX, double mouseY) {
        try {
            if (this.builder.tooltipHandler != null) {
                return this.builder.tooltipHandler.getTooltipStrings(recipe, mouseX, mouseY);
            }
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error getting tooltip strings for recipe category: " + this.getUid(), e);
        }
        return IRecipeCategory.super.getTooltipStrings(recipe, mouseX, mouseY);
    }

	/**
     * Called when a player clicks the recipe.
     * Useful for implementing buttons, hyperlinks, and other interactions to your recipe.
     *
     * @param recipe the currently hovered recipe
     * @param mouseX the X position of the mouse, relative to the recipe.
     * @param mouseY the Y position of the mouse, relative to the recipe.
     * @param input  the current input
     * @return true if the input was handled, false otherwise
     * @since 8.3.0
     */
    @Override
    public boolean handleClick(@NotNull T recipe, double mouseX, double mouseY, int input) {
        try {
            if (this.builder.inputHandler != null) {
                return this.builder.inputHandler.handleInput(recipe, mouseX, mouseY, input);
            }
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error handling input for recipe category: " + this.getUid(), e);
        }
        return IRecipeCategory.super.handleClick(recipe, mouseX, mouseY, input);
    }

    /**
     * @param recipe the recipe to check
     * @return true if the given recipe can be handled by this category.
     * @since 7.2.0
     */
    @Override
    public boolean isHandled(@NotNull T recipe) {
        try {
            if (this.builder.recipeHandlePredicate != null) {
                return this.builder.recipeHandlePredicate.isHandled(recipe);
            }
        } catch (Throwable e) {
            ConsoleJS.CLIENT.error("Error checking if recipe is handled by category: " + this.getUid(), e);
        }
        return IRecipeCategory.super.isHandled(recipe);
    }
}
