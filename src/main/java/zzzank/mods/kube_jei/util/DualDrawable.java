package zzzank.mods.kube_jei.util;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.latvian.mods.kubejs.typings.Info;
import lombok.AllArgsConstructor;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

/**
 * @author ZZZank
 */
@Info("""
    Mainly use for icons that want to display two ingredients""")
@AllArgsConstructor
public class DualDrawable implements IDrawable {
    public static final int SIZE = 18;

    public final IDrawable primary;
    public final IDrawable secondary;

    @Override
    public int getWidth() {
        return SIZE;
    }

    @Override
    public int getHeight() {
        return SIZE;
    }

    @Override
    public void draw(@NotNull GuiGraphics guiGraphics, int xOffset, int yOffset) {
        var matrixStack = guiGraphics.pose();

        RenderSystem.enableDepthTest();
        matrixStack.pushPose();

        matrixStack.translate(1, 1, 0);
        primary.draw(guiGraphics, xOffset, yOffset);

        matrixStack.translate((SIZE + xOffset) >> 1, (SIZE + yOffset) >> 1, 100); // what
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        secondary.draw(guiGraphics, xOffset, yOffset);

        matrixStack.popPose();
    }
}
