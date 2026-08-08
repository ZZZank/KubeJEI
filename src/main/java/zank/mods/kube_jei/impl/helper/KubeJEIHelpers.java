package zank.mods.kube_jei.impl.helper;

import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.function.IntSupplier;

/**
 * @author ZZZank
 */
public class KubeJEIHelpers {
    public static final KubeJEIHelpers INSTANCE = new KubeJEIHelpers();

    public DualDrawable dualDrawable(IDrawable primary, IDrawable secondary) {
        return new DualDrawable(primary, secondary);
    }

    public ITickTimer customTickTimer(IntSupplier currentValue, int maxValue) {
        return new CustomTickTimer(currentValue, maxValue);
    }

    public void renderEntityFollowsMouse(
        GuiGraphics guiGraphics,
        int x1,
        int y1,
        int x2,
        int y2,
        int scale,
        float yOffset,
        float mouseX,
        float mouseY,
        LivingEntity entity
    ) {
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, x1, y1, x2, y2, scale, yOffset, mouseX, mouseY, entity);
    }

    public void renderEntityFollowsAngle(
        GuiGraphics guiGraphics,
        int x1,
        int y1,
        int x2,
        int y2,
        int scale,
        float yOffset,
        float angleXComponent,
        float angleYComponent,
        LivingEntity entity
    ) {
        InventoryScreen.renderEntityInInventoryFollowsAngle(guiGraphics, x1, y1, x2, y2, scale, yOffset, angleXComponent, angleYComponent, entity);
    }

    public void renderEntity(
        GuiGraphics guiGraphics,
        float x,
        float y,
        float scale,
        Vector3f translate,
        Quaternionf pose,
        @Nullable Quaternionf cameraOrientation,
        LivingEntity entity
    ) {
        InventoryScreen.renderEntityInInventory(guiGraphics, x, y, scale, translate, pose, cameraOrientation, entity);
    }
}
