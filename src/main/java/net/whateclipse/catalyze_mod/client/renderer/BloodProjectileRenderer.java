package net.whateclipse.catalyze_mod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.whateclipse.catalyze_mod.Catalyze_mod;
import net.whateclipse.catalyze_mod.entity.BloodProjectileEntity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import javax.annotation.Nonnull;

public class BloodProjectileRenderer extends EntityRenderer<BloodProjectileEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Catalyze_mod.MODID, "textures/entity/blood_projectile.png");

    public BloodProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@Nonnull BloodProjectileEntity entity, float entityYaw, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        
        // Rotate to match movement direction
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));

        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        PoseStack.Pose posestack$pose = poseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();

        // Texture 32x16. We map it to a 2x1 block area.
        // U=0..1 is the 32px side (Width/Z), V=0..1 is the 16px side (Length/X).
        // Centered box: x from -0.5 to 0.5, z from -1.0 to 1.0.
        float x0 = -0.5F; // Back
        float x1 = 0.5F;  // Front
        float z0 = -1.0F; // Left
        float z1 = 1.0F;  // Right

        // UVs: V=0 is Tip (x1), V=1 is Tail (x0). U=0 is Left (z0), U=1 is Right (z1).
        // Order: Front-Left, Front-Right, Back-Right, Back-Left
        this.vertex(matrix4f, matrix3f, vertexconsumer, x1, 0, z0, 0, 0, 0, 1, 0, packedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, x1, 0, z1, 1, 0, 0, 1, 0, packedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, x0, 0, z1, 1, 1, 0, 1, 0, packedLight);
        this.vertex(matrix4f, matrix3f, vertexconsumer, x0, 0, z0, 0, 1, 0, 1, 0, packedLight);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private void vertex(Matrix4f matrix, Matrix3f normal, VertexConsumer consumer, float x, float y, float z, float u, float v, int nx, int ny, int nz, int packedLight) {
        @SuppressWarnings("null")
        VertexConsumer vertex = consumer.addVertex(matrix, x, y, z);
        vertex.setColor(255, 255, 255, 255)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(nx, ny, nz);
    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull BloodProjectileEntity entity) {
        return TEXTURE;
    }
}
