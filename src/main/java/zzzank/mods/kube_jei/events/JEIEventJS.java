package zzzank.mods.kube_jei.events;

import dev.latvian.kubejs.event.EventJS;
import mezz.jei.api.helpers.IJeiHelpers;
import net.minecraft.client.Minecraft;
import zzzank.mods.kube_jei.impl.recipe_type.KubeJEIRecipeTypes;
import zzzank.mods.kube_jei.impl.JEIDrawables;

public class JEIEventJS extends EventJS {
    public static IJeiHelpers JEI_HELPERS;

    public IJeiHelpers getJeiHelpers() {
        return JEI_HELPERS;
    }

    public IJeiHelpers getHelpers() {
        return JEI_HELPERS;
    }

    public JEIDrawables getDrawables() {
        return JEIDrawables.get();
    }

    public Minecraft getMc() {
        return Minecraft.getInstance();
    }

    /**
     * do we actually need this?
     */
    @Deprecated
    public Class<KubeJEIRecipeTypes> getRecipeTypes() {
        return KubeJEIRecipeTypes.class;
    }
}
