package zank.mods.kube_jei.impl.helper;

import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.common.util.TickTimer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.LivingEntity;

/**
 * @author ZZZank
 */
public class KubeJEIHelpers {
    public static final KubeJEIHelpers INSTANCE = new KubeJEIHelpers();

    public DualDrawable dualDrawable(IDrawable primary, IDrawable secondary) {
        return new DualDrawable(primary, secondary);
    }

    public ITickTimer tickTimer(int ticksPerCycle, int maxValue, boolean countDown) {
        return new TickTimer(ticksPerCycle, maxValue, countDown);
    }

    public void renderEntityFollowsMouse(
        GuiGraphicsExtractor guiGraphics,
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
        InventoryScreen.extractEntityInInventoryFollowsMouse(guiGraphics, x1, y1, x2, y2, scale, yOffset, mouseX, mouseY, entity);
    }

    public void renderEntityFollowsAngle(
        GuiGraphicsExtractor guiGraphics,
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
}
