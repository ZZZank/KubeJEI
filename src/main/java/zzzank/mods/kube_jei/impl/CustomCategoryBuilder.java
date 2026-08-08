package zzzank.mods.kube_jei.impl;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.inputs.IJeiUserInput;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomCategoryBuilder<T> {

    public final IJeiHelpers jeiHelpers;
    @NotNull
    public final RecipeType<T> type;

    @NotNull
    public Component title;
    @Nullable
    public IDrawable background;
    @NotNull
    public IDrawable icon;
    public int width;
    public int height;
    public boolean needsRecipeBorder;

    public RecipeSetHandler<T> recipeSetHandler;
    public DrawHandler<T> drawHandler;
    public TooltipHandler<T> tooltipHandler;
    public InputHandler<T> inputHandler;
    public IsRecipeHandledByCategory<T> recipeHandlePredicate;

    private CustomRecipeCategory<T> category;

    public CustomCategoryBuilder(@NotNull RecipeType<T> recipeType, @NotNull IJeiHelpers jeiHelpers) {
        this.type = recipeType;
        this.jeiHelpers = jeiHelpers;
        this.title = Component.literal("KubeJEI Custom Category");
        this.background = null;
        this.icon = jeiHelpers.getGuiHelper().createDrawableItemLike(Items.TNT);
        this.width = 120;
        this.height = 60;
        this.needsRecipeBorder = true;
    }

    public CustomRecipeCategory<T> asCategory() {
        return category == null ? (category = new CustomRecipeCategory<>(this)) : category;
    }

    public CustomCategoryBuilder<T> title(String title) {
        this.title = Component.literal(title);
        return this;
    }

    public CustomCategoryBuilder<T> iconItem(ItemStack stack) {
        this.icon = jeiHelpers.getGuiHelper().createDrawableItemStack(stack);
        return this;
    }

    public CustomCategoryBuilder<T> iconItem(ItemLike itemLike) {
        this.icon = jeiHelpers.getGuiHelper().createDrawableItemLike(itemLike);
        return this;
    }

    public CustomCategoryBuilder<T> backgroundTexture(ResourceLocation texture, int u, int v, int w, int h) {
        this.background = jeiHelpers.getGuiHelper().createDrawable(texture, u, v, w, h);
        return this;
    }

    public CustomCategoryBuilder<T> size(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @FunctionalInterface
    public interface RecipeSetHandler<T> {
        void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses);
    }

    @FunctionalInterface
    public interface DrawHandler<T> {
        void draw(T recipe, GuiGraphics guiGraphics, double mouseX, double mouseY);
    }

    @FunctionalInterface
    public interface TooltipHandler<T> {
        @NotNull
        List<Component> getTooltipStrings(T recipe, double mouseX, double mouseY);
    }

    @FunctionalInterface
    public interface InputHandler<T> {
        boolean handleInput(T recipe, double mouseX, double mouseY, IJeiUserInput input);
    }

    @FunctionalInterface
    public interface IsRecipeHandledByCategory<T> {
        boolean isHandled(T recipe);
    }
}
