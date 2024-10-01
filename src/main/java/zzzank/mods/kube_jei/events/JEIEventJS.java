package zzzank.mods.kube_jei.events;

import dev.latvian.kubejs.event.EventJS;
import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import mezz.jei.api.helpers.IJeiHelpers;
import net.minecraft.client.Minecraft;
import zzzank.mods.kube_jei.impl.helpers.RenderHelper;
import zzzank.mods.kube_jei.impl.recipe_type.KubeJEIRecipeTypes;
import zzzank.mods.kube_jei.impl.helpers.JEIDrawableWrapper;

public class JEIEventJS extends EventJS {
    public static IJeiHelpers JEI_HELPERS;

    @JSInfo("""
        helpers and tools for addon mods. Provided by JEI
        
        I recommend you to replace the GuiHelper provided by it with our drawable wrapper: {@link getDrawables}""")
    public IJeiHelpers getJeiHelpers() {
        return JEI_HELPERS;
    }

    @JSInfo("""
        same as {@link getJeiHelpers}""")
    public IJeiHelpers getHelpers() {
        return JEI_HELPERS;
    }

    @JSInfo("""
        a better GuiHelper, with shorter naming, more doc, and new features like dual drawables""")
    public JEIDrawableWrapper getDrawables() {
        return JEIDrawableWrapper.INSTANCE;
    }

    @JSInfo("""
        helper for rendering, usually used in custom category""")
    public RenderHelper getRenderHelper() {
        return RenderHelper.INSTANCE;
    }

    public Minecraft getMc() {
        return Minecraft.getInstance();
    }

    @JSInfo("""
        i dont know, do you actually need this?""")
    @Deprecated
    public Class<KubeJEIRecipeTypes> getRecipeTypes() {
        return KubeJEIRecipeTypes.class;
    }
}
