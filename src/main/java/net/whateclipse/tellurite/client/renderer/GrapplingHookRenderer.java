package net.whateclipse.tellurite.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.whateclipse.tellurite.Tellurite;
import net.whateclipse.tellurite.entity.GrapplingHookEntity;
import net.whateclipse.tellurite.items.ModItems;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import javax.annotation.Nonnull;

public class GrapplingHookRenderer extends EntityRenderer<GrapplingHookEntity> {
    private static final ResourceLocation ROPE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Tellurite.MODID, "textures/entity/rope.png");
    private final ItemRenderer itemRenderer;

    public GrapplingHookRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(@Nonnull GrapplingHookEntity entity, float entityYaw, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // Render the Grappling Hook item
        poseStack.pushPose();
        
        // Match the rotation of the entity for the hook model
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
        
        ItemStack itemStack = new ItemStack(ModItems.GRAPPLING_HOOK.get());
        this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
        
        poseStack.popPose();

        // Render the Rope
        LivingEntity owner = (LivingEntity) entity.getOwner();
        if (owner != null) {
            poseStack.pushPose();
            
            // Calculate starting and ending points for the rope
            Vec3 entityPos = entity.getPosition(partialTicks);
            
            // Get the player's hand position or fall back to eye position
            Vec3 ownerPos = getPlayerHandPos((Player) owner, partialTicks);
            
            // The rope starts from the player and goes to the grappling hook
            float dx = (float) (ownerPos.x - entityPos.x);
            float dy = (float) (ownerPos.y - entityPos.y);
            float dz = (float) (ownerPos.z - entityPos.z);
            
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(ROPE_TEXTURE));
            Matrix4f matrix4f = poseStack.last().pose();
            Matrix3f matrix3f = poseStack.last().normal();

            // Calculate the length of the rope
            float length = Mth.sqrt(dx * dx + dy * dy + dz * dz);
            
            // Normalize direction vectors
            float dirX = dx / length;
            float dirY = dy / length;
            float dirZ = dz / length;

            // Draw a basic line/strip rendering the rope
            float width = 0.05f; // Rope thickness
            
            for (float i = 0; i < length; i += 0.5f) {
                float seg0 = i;
                float seg1 = Math.min(i + 0.5f, length);
                
                float sx0 = dx * (seg0 / length);
                float sy0 = dy * (seg0 / length);
                float sz0 = dz * (seg0 / length);
                
                float sx1 = dx * (seg1 / length);
                float sy1 = dy * (seg1 / length);
                float sz1 = dz * (seg1 / length);
                
                // Draw a simple 2D ribbon pointing upwards (or cross a quad)
                vertexConsumer.addVertex(matrix4f, sx0, sy0 - width, sz0).setColor(255, 255, 255, 255).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack.last(), dirX, dirY, dirZ);
                vertexConsumer.addVertex(matrix4f, sx1, sy1 - width, sz1).setColor(255, 255, 255, 255).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack.last(), dirX, dirY, dirZ);
                vertexConsumer.addVertex(matrix4f, sx1, sy1 + width, sz1).setColor(255, 255, 255, 255).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack.last(), dirX, dirY, dirZ);
                vertexConsumer.addVertex(matrix4f, sx0, sy0 + width, sz0).setColor(255, 255, 255, 255).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseStack.last(), dirX, dirY, dirZ);
            }
            
            poseStack.popPose();
        }

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private Vec3 getPlayerHandPos(Player player, float partialTicks) {
        int side = player.getMainArm() == net.minecraft.world.entity.HumanoidArm.RIGHT ? 1 : -1;
        float yRot = Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot) * ((float) Math.PI / 180F);
        double offsetX = Mth.sin(yRot) * side * 0.4D;
        double offsetZ = Mth.cos(yRot) * side * -0.4D;
        return new Vec3(player.xOld + (player.getX() - player.xOld) * partialTicks + offsetX, 
                        player.yOld + (player.getY() - player.yOld) * partialTicks + player.getEyeHeight() * 0.8, 
                        player.zOld + (player.getZ() - player.zOld) * partialTicks + offsetZ);
    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull GrapplingHookEntity entity) {
        return ROPE_TEXTURE;
    }
}
