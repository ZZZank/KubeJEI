package zank.mods.kube_jei.impl;

import dev.latvian.mods.kubejs.script.ConsoleJS;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.inputs.IJeiInputHandler;
import mezz.jei.api.gui.inputs.IJeiUserInput;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class CustomRecipeCategory<T> implements IRecipeCategory<T> {

    private final CustomCategoryBuilder<T> builder;

    public CustomRecipeCategory(CustomCategoryBuilder<T> builder) {
        this.builder = builder;
    }

    @Override
    public @NotNull RecipeType<T> getRecipeType() {
        return builder.type;
    }

    @Override
    public @NotNull Component getTitle() {
        return builder.title;
    }

    @Override
    public int getWidth() {
        return builder.background != null ? builder.background.getWidth() : builder.width;
    }

    @Override
    public int getHeight() {
        return builder.background != null ? builder.background.getHeight() : builder.height;
    }

    @Override
    public IDrawable getIcon() {
        return builder.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder layout, @NotNull T recipe, @NotNull IFocusGroup focuses) {
        var handler = builder.recipeSetHandler;
        if (handler != null) {
            try {
                handler.setRecipe(layout, recipe, focuses);
            } catch (Throwable e) {
                ConsoleJS.CLIENT.error("Error setting recipe for category: " + getRecipeType().getUid(), e);
            }
        }
    }

    @Override
    public void createRecipeExtras(@NotNull IRecipeExtrasBuilder extrasBuilder, @NotNull T recipe, @NotNull IFocusGroup focuses) {
        var handler = builder.inputHandler;
        if (handler != null) {
            extrasBuilder.addInputHandler(new IJeiInputHandler() {
                @Override
                public @NotNull ScreenRectangle getArea() {
                    return new ScreenRectangle(0, 0, CustomRecipeCategory.this.getWidth(), CustomRecipeCategory.this.getHeight());
                }

                @Override
                public boolean handleInput(double mouseX, double mouseY, @NotNull IJeiUserInput input) {
                    try {
                        return handler.handleInput(recipe, mouseX, mouseY, input);
                    } catch (Throwable e) {
                        ConsoleJS.CLIENT.error("Error creating recipe extras for category: " + getRecipeType().getUid(), e);
                        return IJeiInputHandler.super.handleInput(mouseX, mouseY, input);
                    }
                }
            });
        }
    }

    @Override
    public void draw(@NotNull T recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        var background = builder.background;
        if (background != null) {
            try {
                background.draw(guiGraphics, 0, 0);
            } catch (Throwable e) {
                ConsoleJS.CLIENT.error("Error drawing background for category: " + getRecipeType().getUid(), e);
            }
        }
        var handler = builder.drawHandler;
        if (handler != null) {
            try {
                handler.draw(recipe, guiGraphics, mouseX, mouseY);
            } catch (Throwable e) {
                ConsoleJS.CLIENT.error("Error drawing category: " + getRecipeType().getUid(), e);
            }
        }
    }

    @Override
    public void getTooltip(@NotNull ITooltipBuilder tooltip, @NotNull T recipe, @NotNull IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        var handler = builder.tooltipHandler;
        if (handler != null) {
            try {
                tooltip.addAll(handler.getTooltipStrings(recipe, mouseX, mouseY));
            } catch (Throwable e) {
                ConsoleJS.CLIENT.error("Error getting tooltip for category: " + getRecipeType().getUid(), e);
            }
        }
    }

    @Override
    public boolean isHandled(@NotNull T recipe) {
        var handler = builder.recipeHandlePredicate;
        if (handler != null) {
            try {
                return handler.isHandled(recipe);
            } catch (Throwable e) {
                ConsoleJS.CLIENT.error("Error checking recipe for category: " + getRecipeType().getUid(), e);
            }
        }
        return true;
    }

    @Override
    public boolean needsRecipeBorder() {
        return builder.needsRecipeBorder;
    }
}
