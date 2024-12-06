package zzzank.mods.kube_jei.impl.builder;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import lombok.Setter;
import lombok.experimental.Accessors;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author ZZZank
 */
@Setter
@Accessors(chain = true)
public class CustomRecipeManagerPluginBuilder {
    @JSInfo("""
        Returns a list of Recipe Categories offered for the focus.
        This is used internally by JEI to implement `IRecipeManager.getRecipeCategories(IFocus, boolean)`.""")
    public RecipeMatcher recipeMatcher;
    @JSInfo("""
        Returns a list of Recipes in the recipeCategory that have the focus.
        This is used internally by JEI to implement `IRecipeManager.getRecipes(IRecipeCategory, IFocus, boolean)`.""")
    public NoFocusRecipeMatcher noFocusRecipeMatcher;
    @JSInfo("""
        Returns a list of all Recipes in the recipeCategory.
        This is used internally by JEI to implement `IRecipeManager.getRecipes(IRecipeCategory, IFocus, boolean)`.""")
    public UidMatcher uidMatcher;

    @FunctionalInterface
    public interface UidMatcher {
        @NotNull List<ResourceLocation> getRecipeCategoryUids(IFocus.Mode mode, Object value);
    }

    @FunctionalInterface
    public interface RecipeMatcher {
        <T> List<T> getRecipes(IRecipeCategory<T> category, IFocus.Mode mode, Object value);
    }

    @FunctionalInterface
    public interface NoFocusRecipeMatcher {
        <T> List<T> getRecipes(IRecipeCategory<T> category);
    }
}
