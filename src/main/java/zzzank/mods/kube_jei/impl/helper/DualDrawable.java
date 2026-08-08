package zzzank.mods.kube_jei.impl.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.latvian.mods.kubejs.typings.Info;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

/**
 * @author ZZZank
 */
@Info("""
    Mainly use for icons that want to display two ingredients""")
public record DualDrawable(IDrawable primary, IDrawable secondary) implements IDrawable {
    public static final int SIZE = 18;

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
