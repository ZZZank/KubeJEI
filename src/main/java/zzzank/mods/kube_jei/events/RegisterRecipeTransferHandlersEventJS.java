package zzzank.mods.kube_jei.events;

import dev.latvian.kubejs.event.EventJS;
import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class RegisterRecipeTransferHandlersEventJS extends EventJS {
    public final IRecipeTransferRegistration registration;

    public RegisterRecipeTransferHandlersEventJS(IRecipeTransferRegistration registration) {
        this.registration = registration;
    }

    public IRecipeTransferHandlerHelper getTransferHelper() {
        return registration.getTransferHelper();
    }

    @JSInfo("""
        Basic method for adding a recipe transfer handler.
        
        @param containerClass     the class of the container that this recipe transfer handler is for
        @param recipeCategoryUid  the recipe categories that this container can use
        @param recipeSlotStart    the first slot for recipe inputs
        @param recipeSlotCount    the number of slots for recipe inputs
        @param inventorySlotStart the first slot of the available inventory (usually player inventory)
        @param inventorySlotCount the number of slots of the available inventory""")
    public <C extends AbstractContainerMenu> void addRecipeTransferHandler(Class<C> containerClass, ResourceLocation recipeCategoryUid, int recipeSlotStart, int recipeSlotCount, int inventorySlotStart, int inventorySlotCount) {
        registration.addRecipeTransferHandler(containerClass, recipeCategoryUid, recipeSlotStart,recipeSlotCount,inventorySlotStart,inventorySlotCount);
    }

    @JSInfo("""
        Advanced method for adding a recipe transfer handler.
        
        Use this when recipe slots or inventory slots are spread out in different number ranges.""")
    public <C extends AbstractContainerMenu> void addRecipeTransferHandler(IRecipeTransferInfo<C> recipeTransferInfo) {
        registration.addRecipeTransferHandler(recipeTransferInfo);
    }

    @JSInfo("""
        Complete control over recipe transfer.
        Use this when the container has a non-standard inventory or crafting area.""")
    public void addRecipeTransferHandler(IRecipeTransferHandler<?> recipeTransferHandler, ResourceLocation recipeCategoryUid) {
        registration.addRecipeTransferHandler(recipeTransferHandler, recipeCategoryUid);
    }

    @JSInfo("""
        Add a universal handler that can handle any category of recipe.
        Useful for mods with recipe pattern encoding, for automated recipe systems.""")
    public void addUniversalRecipeTransferHandler(IRecipeTransferHandler<?> recipeTransferHandler) {
        registration.addUniversalRecipeTransferHandler(recipeTransferHandler);
    }
}
