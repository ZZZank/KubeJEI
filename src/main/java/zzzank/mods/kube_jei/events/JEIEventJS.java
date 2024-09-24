package zzzank.mods.kube_jei.events;

import dev.latvian.kubejs.event.EventJS;
import mezz.jei.api.helpers.IJeiHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import zzzank.mods.kube_jei.impl.RecipeType;

public class JEIEventJS extends EventJS {
    public static IJeiHelpers JEI_HELPERS;

    public IJeiHelpers getJeiHelpers() {
        return JEI_HELPERS;
    }

    public Minecraft getMc() {
        return Minecraft.getInstance();
    }

    public <T> RecipeType<T> createRecipeType(ResourceLocation id, Class<T> type) {
        return new RecipeType<>(id, type);
    }
}
