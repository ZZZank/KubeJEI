package zzzank.mods.kube_jei.impl.helpers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

/**
 * drawing helper for preventing ambiguous types and providing better param names
 *
 * @author ZZZank
 */
public final class RenderHelper {
    public static final RenderHelper INSTANCE = new RenderHelper();

    private RenderHelper() {
    }

    private static Minecraft mc() {
        return Minecraft.getInstance();
    }

    private Font font() {
        return mc().font;
    }

    private EntityRenderDispatcher entityRenderer() {
        return mc().getEntityRenderDispatcher();
    }

    public int text(PoseStack matrixStack, Component text, float x, float y, int color) {
        return font().draw(matrixStack, text, x, y, color);
    }

    public int textWithShadow(PoseStack matrixStack, Component text, float x, float y, int color) {
        return font().drawShadow(matrixStack, text, x, y, color);
    }

    public int textWithShadow(PoseStack matrixStack, String text, float x, float y, int color, boolean transparency) {
        return font().drawShadow(matrixStack, text, x, y, color, transparency);
    }

    public int textInBatch(
        Component text,
        float x,
        float y,
        int color,
        boolean shadow,
        Matrix4f matrix,
        MultiBufferSource vertexConsumers,
        boolean seeThrough,
        int backgroundColor,
        int light
    ) {
        return font()
            .drawInBatch(text, x, y, color, shadow, matrix, vertexConsumers, seeThrough, backgroundColor, light);
    }

    public int textInBatch(
        String text,
        float x,
        float y,
        int color,
        boolean dropShadow,
        Matrix4f matrix,
        MultiBufferSource buffer,
        boolean transparent,
        int colorBackground,
        int packedLight
    ) {
        return font()
            .drawInBatch(text, x, y, color, dropShadow, matrix, buffer, transparent, colorBackground, packedLight);
    }

    /**
     * <a href="https://github.com/VazkiiMods/Patchouli/blob/1057a024a036c7bb194b70014312c836d1e4e0b2/src/main/java/vazkii/patchouli/client/book/page/PageEntity.java#L88">Patchouli impl</a>
     */
    public void entity(
        PoseStack matrixStack,
        Entity entity,
        Vector3f scales,
        Vector3d offsets,
        Vector3f rotationDegrees,
        float partialTicks
    ) {
        if (entity.isAddedToWorld() && entity.level != mc().level) {
            throw new IllegalArgumentException("entity should either be a virtual entity so that its level(world) can be safely modified, or at the same level(world) as client-side player does");
        }
        val oldLevel = entity.level;
        if (!entity.isAddedToWorld()) { //is virtual entity, so its level can be safely modified
            entity.level = mc().level;
        }

        matrixStack.pushPose();

        //move
        matrixStack.translate(offsets.x, offsets.y, offsets.z);
        //scale
        matrixStack.scale(scales.x(), scales.y(), scales.z());
        //rotation
        val rotation = Vector3f.ZP.rotationDegrees(180);
        rotation.mul(Vector3f.XP.rotationDegrees(rotationDegrees.x()));
        rotation.mul(Vector3f.YP.rotationDegrees(rotationDegrees.y()));
        rotation.mul(Vector3f.ZP.rotationDegrees(rotationDegrees.z()));
        matrixStack.mulPose(rotation);

        //render
        val entityRenderDispatcher = entityRenderer();
        val bufferSource = mc().renderBuffers().bufferSource();
        entityRenderDispatcher.setRenderShadow(false);
        /*
        void render(Entity e, double x, double y, double z, float rotationYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {}
         */
        entityRenderDispatcher.render(entity, 0, 0, 0, 0, partialTicks, matrixStack, bufferSource, 0xF000F0);
        entityRenderDispatcher.setRenderShadow(true);

        bufferSource.endBatch();
        matrixStack.popPose();

        if (!entity.isAddedToWorld()) {
            entity.level = oldLevel;
        }
    }

    public void entitySimple(
        PoseStack matrixStack,
        Entity entity,
        float scale,
        Vector3d offsets,
        float rotation,
        float partialTicks
    ) {
        entity(
            matrixStack,
            entity,
            new Vector3f(scale, scale, scale),
            offsets,
            new Vector3f(0, rotation, 0),
            partialTicks
        );
    }

    public void entitySimple(
        PoseStack matrixStack,
        Entity entity,
        float scale,
        Vector3d offsets,
        float rotation
    ) {
        entitySimple(
            matrixStack,
            entity,
            scale,
            offsets,
            rotation,
            1
        );
    }
}
