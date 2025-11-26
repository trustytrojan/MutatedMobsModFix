//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.boss.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import mmm.models.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

@SideOnly(Side.CLIENT)
public class RenderWitherShulker extends RenderLiving<EntityWitherShulker>
{
    private static final ResourceLocation texture;
    
    public RenderWitherShulker(final RenderManager p_i47194_1_) {
        super(p_i47194_1_, new ModelWitherShulker(), 0.0f);
        this.addLayer((LayerRenderer)new HeadLayer());
    }
    
    public ModelWitherShulker getMainModel() {
        return (ModelWitherShulker)super.getMainModel();
    }
    
    public void doRender(final EntityWitherShulker entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        final int i = entity.getClientTeleportInterp();
        if (i > 0 && entity.isAttachedToBlock()) {
            final BlockPos blockpos = entity.getAttachmentPos();
            final BlockPos blockpos2 = entity.getOldAttachPos();
            double d0 = (i - partialTicks) / 6.0;
            d0 *= d0;
            final double d2 = (blockpos.getX() - blockpos2.getX()) * d0;
            final double d3 = (blockpos.getY() - blockpos2.getY()) * d0;
            final double d4 = (blockpos.getZ() - blockpos2.getZ()) * d0;
            super.doRender(entity, x - d2, y - d3, z - d4, entityYaw, partialTicks);
        }
        else {
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
    }
    
    public boolean shouldRender(final EntityWitherShulker livingEntity, final ICamera camera, final double camX, final double camY, final double camZ) {
        if (super.shouldRender(livingEntity, camera, camX, camY, camZ)) {
            return true;
        }
        if (livingEntity.getClientTeleportInterp() > 0 && livingEntity.isAttachedToBlock()) {
            final BlockPos blockpos = livingEntity.getOldAttachPos();
            final BlockPos blockpos2 = livingEntity.getAttachmentPos();
            final Vec3d vec3d = new Vec3d((double)blockpos2.getX(), (double)blockpos2.getY(), (double)blockpos2.getZ());
            final Vec3d vec3d2 = new Vec3d((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ());
            if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(vec3d2.x, vec3d2.y, vec3d2.z, vec3d.x, vec3d.y, vec3d.z))) {
                return true;
            }
        }
        return false;
    }
    
    protected ResourceLocation getEntityTexture(final EntityWitherShulker entity) {
        return RenderWitherShulker.texture;
    }
    
    protected void applyRotations(final EntityWitherShulker entityLiving, final float ageInTicks, final float rotationYaw, final float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        switch (entityLiving.getAttachmentFacing()) {
            case EAST: {
                GlStateManager.translate(0.5f, 0.5f, 0.0f);
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                break;
            }
            case WEST: {
                GlStateManager.translate(-0.5f, 0.5f, 0.0f);
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(-90.0f, 0.0f, 0.0f, 1.0f);
                break;
            }
            case NORTH: {
                GlStateManager.translate(0.0f, 0.5f, -0.5f);
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                break;
            }
            case SOUTH: {
                GlStateManager.translate(0.0f, 0.5f, 0.5f);
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
                break;
            }
            case UP: {
                GlStateManager.translate(0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
                break;
            }
        }
    }
    
    protected void preRenderCallback(final EntityWitherShulker entitylivingbaseIn, final float partialTickTime) {
        final float f = 0.999f;
        GlStateManager.scale(3.5f, 3.5f, 3.5f);
    }
    
    static {
        texture = new ResourceLocation("mmm:textures/model/wither_shulker.png");
    }
    
    @SideOnly(Side.CLIENT)
    class HeadLayer implements LayerRenderer<EntityWitherShulker>
    {
        private HeadLayer() {
        }
        
        public void doRenderLayer(final EntityWitherShulker entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
            GlStateManager.pushMatrix();
            switch (entitylivingbaseIn.getAttachmentFacing()) {
                case EAST: {
                    GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                    GlStateManager.translate(1.0f, -1.0f, 0.0f);
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case WEST: {
                    GlStateManager.rotate(-90.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                    GlStateManager.translate(-1.0f, -1.0f, 0.0f);
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                    break;
                }
                case NORTH: {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                    GlStateManager.translate(0.0f, -1.0f, -1.0f);
                    break;
                }
                case SOUTH: {
                    GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                    GlStateManager.translate(0.0f, -1.0f, 1.0f);
                    break;
                }
                case UP: {
                    GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
                    GlStateManager.translate(0.0f, -2.0f, 0.0f);
                    break;
                }
            }
            final ModelRenderer modelrenderer = RenderWitherShulker.this.getMainModel().head;
            modelrenderer.rotateAngleY = netHeadYaw * 0.017453292f;
            modelrenderer.rotateAngleX = headPitch * 0.017453292f;
            modelrenderer.render(scale);
            GlStateManager.popMatrix();
        }
        
        public boolean shouldCombineTextures() {
            return false;
        }
    }
}
