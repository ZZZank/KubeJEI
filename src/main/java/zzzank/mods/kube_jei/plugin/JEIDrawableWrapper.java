package zzzank.mods.kube_jei.plugin;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import zzzank.mods.kube_jei.events.JEIEventJS;

public class JEIDrawableWrapper {
    public static IDrawable of(Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof IDrawable drawable) {
			return drawable;
        }

        if (JEIEventJS.JEI_HELPERS == null) {
            throw new IllegalStateException("IDrawable Type Wrapper unavailable before JEI Runtime is available.");
        }
        if (o instanceof ItemStack itemStack) {
			JEIEventJS.JEI_HELPERS.getGuiHelper().createDrawableIngredient(itemStack);
        } else if (o instanceof ItemLike itemLike) {
			JEIEventJS.JEI_HELPERS.getGuiHelper().createDrawableIngredient(new ItemStack(itemLike.asItem()));
        }

        return null;
    }
}
