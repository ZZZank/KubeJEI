package zzzank.mods.kube_jei.events;

import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import mezz.jei.api.gui.handlers.*;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;

public class RegisterGUIHandlersEventJS extends JEIEventJS {
    public final IGuiHandlerRegistration data;

    public RegisterGUIHandlersEventJS(IGuiHandlerRegistration data) {
        this.data = data;
    }

    @JSInfo("""
        Add a handler to give JEI extra information about how to layout the item list next to a specific type of {@link AbstractContainerScreen}.
        Multiple handlers can be registered for one {@link AbstractContainerScreen}.
        
        @see #addGenericGuiContainerHandler(Class, IGuiContainerHandler) for handlers that use Java Generics
        """)
    public <T extends AbstractContainerScreen<?>> void addGuiContainerHandler(
        Class<? extends T> guiClass,
        IGuiContainerHandler<T> guiHandler
    ) {
        data.addGuiContainerHandler(guiClass, guiHandler);
    }

    @JSInfo("""
        Same as {@link #addGuiContainerHandler(Class, IGuiContainerHandler)} but for handlers that use Java Generics to
        support multiple types of containers. This type of handler runs into type issues with the regular method.
        """)
    public <T extends AbstractContainerScreen<?>> void addGenericGuiContainerHandler(
        Class<? extends T> guiClass,
        IGuiContainerHandler<?> guiHandler
    ) {
        data.addGenericGuiContainerHandler(guiClass, guiHandler);
    }

    @JSInfo("""
        By default, JEI can only draw next to {@link AbstractContainerScreen}.
        Add a handler to let JEI draw next to a specific class (or subclass) of {@link Screen}.
        """)
    public <T extends Screen> void addGuiScreenHandler(Class<T> guiClass, IScreenHandler<T> handler) {
        data.addGuiScreenHandler(guiClass, handler);
    }

    @JSInfo("""
        Add a handler to give JEI extra information about how to layout the ingredient list.
        Used for guis that display next to GUIs and would normally intersect with JEI.
        """)
    public void addGlobalGuiHandler(IGlobalGuiHandler globalGuiHandler) {
        data.addGlobalGuiHandler(globalGuiHandler);
    }

    @JSInfo("""
        Add a clickable area on a gui to jump to specific categories of recipes in JEI.
        
        @param guiContainerClass  the gui class for JEI to detect.
        @param xPos               left x position of the clickable area, relative to the left edge of the gui.
        @param yPos               top y position of the clickable area, relative to the top edge of the gui.
        @param width              the width of the clickable area.
        @param height             the height of the clickable area.
        @param recipeCategoryUids the recipe categories that JEI should display.
        """)
    public <T extends AbstractContainerScreen<?>> void addRecipeClickArea(
        Class<? extends T> guiContainerClass,
        int xPos,
        int yPos,
        int width,
        int height,
        ResourceLocation... recipeCategoryUids
    ) {
        data.addRecipeClickArea(guiContainerClass, xPos, yPos, width, height, recipeCategoryUids);
    }

    @JSInfo("""
        Lets mods accept ghost ingredients from JEI.
        These ingredients are dragged from the ingredient list on to your gui, and are useful
        for setting recipes or anything else that does not need the real ingredient to exist.
        """)
    public <T extends Screen> void addGhostIngredientHandler(Class<T> guiClass, IGhostIngredientHandler<T> handler) {
        data.addGhostIngredientHandler(guiClass, handler);
    }
}
