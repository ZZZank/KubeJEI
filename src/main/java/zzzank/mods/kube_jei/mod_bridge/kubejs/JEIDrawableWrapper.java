package zzzank.mods.kube_jei.mod_bridge.kubejs;

import dev.latvian.kubejs.fluid.FluidStackJS;
import dev.latvian.kubejs.item.ItemStackJS;
import lombok.val;
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

        val jeiHelpers = JEIEventJS.JEI_HELPERS;
        if (jeiHelpers == null) {
            throw new IllegalStateException("IDrawable Type Wrapper unavailable before JEI Runtime is available.");
        }

        if (o instanceof String s) {
            //treat string as item
            return jeiHelpers.getGuiHelper().createDrawableIngredient(ItemStackJS.of(s).getItemStack());
        } else if (o instanceof FluidStackJS fluidStackJS) {
            return jeiHelpers.getGuiHelper().createDrawableIngredient(fluidStackJS.getFluidStack());
        } else if (o instanceof ItemStackJS itemStackJS) {
            return jeiHelpers.getGuiHelper().createDrawableIngredient(itemStackJS.getItemStack());
        } else if (o instanceof ItemStack itemStack) {
			return jeiHelpers.getGuiHelper().createDrawableIngredient(itemStack);
        } else if (o instanceof ItemLike itemLike) {
			return jeiHelpers.getGuiHelper().createDrawableIngredient(new ItemStack(itemLike.asItem()));
        }

        return null;
    }
}
