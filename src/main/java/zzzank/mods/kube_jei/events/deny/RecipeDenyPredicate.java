package zzzank.mods.kube_jei.events.deny;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import net.minecraft.resources.ResourceLocation;

/**
 * @author ZZZank
 */
public interface RecipeDenyPredicate {
    @JSInfo("""
        @param categoryId the id of recipe category that the recipe is targeting at
        @param recipe recipe instance. It's usually (but not guaranteed to be) an instance of {@link net.minecraft.world.item.crafting.Recipe}.
        It's actual type is restricted by its recipe category, or more accurately, restricted to be an instance of: `IRecipeCategory#getRecipeClass()`
        @return true if you want to deny the `recipe`""")
    boolean shouldDeny(ResourceLocation categoryId, Object recipe);
}
