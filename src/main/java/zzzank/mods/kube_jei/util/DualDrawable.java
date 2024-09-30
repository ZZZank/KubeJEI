package zzzank.mods.kube_jei.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.latvian.mods.rhino.annotations.typing.JSInfo;
import lombok.AllArgsConstructor;
import mezz.jei.api.gui.drawable.IDrawable;
import org.jetbrains.annotations.NotNull;

/**
 * @author ZZZank
 */
@JSInfo("""
    Mainly use for icons that need to display two ingredients""")
@AllArgsConstructor
public class DualDrawable implements IDrawable {
    public final IDrawable primary;
    public final IDrawable secondary;

    @Override
    public int getWidth() {
        return 18;
    }

    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public void draw(@NotNull PoseStack matrixStack, int xOffset, int yOffset) {
        RenderSystem.enableDepthTest();
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 0);

        matrixStack.pushPose();
        matrixStack.translate(1, 1, 0);
        primary.draw(matrixStack, xOffset, yOffset);
        matrixStack.popPose();

        matrixStack.pushPose();
        matrixStack.translate(10, 10, 123);
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        secondary.draw(matrixStack, xOffset, yOffset);
        matrixStack.popPose();

        matrixStack.popPose();
    }
}
