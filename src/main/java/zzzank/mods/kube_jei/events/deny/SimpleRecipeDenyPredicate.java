package zzzank.mods.kube_jei.events.deny;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;

/**
 * @author ZZZank
 */
public interface SimpleRecipeDenyPredicate {
    SimpleRecipeDenyPredicate ALWAYS_DENY = (recipe) -> true;
    SimpleRecipeDenyPredicate ALWAYS_ALLOW = (recipe) -> false;

    @JSInfo("""
        @param recipe recipe instance. It's usually (but not guaranteed to be) an instance of {@link net.minecraft.world.item.crafting.Recipe}.
        It's actual type is restricted by its recipe category, or more accurately, restricted to be an instance of: `IRecipeCategory#getRecipeClass()`
        @return true if you want to deny the `recipe`""")
    boolean shouldDeny(Object recipe);
}
