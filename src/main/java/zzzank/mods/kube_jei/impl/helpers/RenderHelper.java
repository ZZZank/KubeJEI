package zzzank.mods.kube_jei.impl.helpers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import zzzank.mods.kube_jei.util.Lazy;

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
     * https://github.com/VazkiiMods/Patchouli/blob/1057a024a036c7bb194b70014312c836d1e4e0b2/src/main/java/vazkii/patchouli/client/book/page/PageEntity.java#L88
     */
    public void entity(PoseStack matrixStack, Entity entity, float scale, double offset, float rotation) {
        if (entity.isAddedToWorld() && entity.level != mc().level) {
            throw new IllegalArgumentException("entity should either be a virtual entity so that its level(world) can be safely modified, or at the same level(world) as client-side player does");
        }
        val oldLevel = entity.level;
        if (!entity.isAddedToWorld()) { //is virtual entity, so its level can be safely modified
            entity.level = mc().level;
        }

        matrixStack.pushPose();

        // This part mostly comes from looking at how patchouli does it
        matrixStack.translate(58, 60, 50);
        matrixStack.scale(scale, scale, scale);
        matrixStack.translate(0, offset, 0);
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(rotation));

        val entityRenderDispatcher = entityRenderer();
        val bufferSource = mc().renderBuffers().bufferSource();
        entityRenderDispatcher.setRenderShadow(false);
        entityRenderDispatcher.render(entity, 0, 0, 0, 0, 1, matrixStack, bufferSource, 0xF000F0);
        entityRenderDispatcher.setRenderShadow(true);

        bufferSource.endBatch();
        matrixStack.popPose();

        if (!entity.isAddedToWorld()) {
            entity.level = oldLevel;
        }
    }

    /**
     * todo: suggest players to reuse entity for better performance
     * @return newly created item entity
     */
    public ItemEntity entityItem(PoseStack matrixStack, ItemStack stack, float scale, double offset, float rotation) {
        val itemEntity = EntityType.ITEM.create(mc().level);
        itemEntity.setItem(stack);
        entity(matrixStack, itemEntity, scale, offset, rotation);
        return itemEntity;
    }
}
