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
    public void draw(@NotNull PoseStack matrixStack, int xOffset, int yOffset) {
        RenderSystem.enableDepthTest();
        matrixStack.pushPose();

//        matrixStack.pushPose();
        matrixStack.translate(1, 1, 0);
        primary.draw(matrixStack, xOffset, yOffset);
//        matrixStack.popPose();

//        matrixStack.pushPose();
        matrixStack.translate((SIZE + xOffset) >> 1, (SIZE + yOffset) >> 1, 100); // what
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        secondary.draw(matrixStack, xOffset, yOffset);
//        matrixStack.popPose();

        matrixStack.popPose();
    }
}
