//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.projectiles.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderShulkerSkull extends Render<EntityShulkerSkull>
{
    private static final ResourceLocation INVULNERABLE_WITHER_TEXTURES;
    private static final ResourceLocation WITHER_TEXTURES;
    private final ModelSkeletonHead skeletonHeadModel;
    
    public RenderShulkerSkull(final RenderManager renderManagerIn) {
        super(renderManagerIn);
        this.skeletonHeadModel = new ModelSkeletonHead();
    }
    
    private float getRenderYaw(final float p_82400_1_, final float p_82400_2_, final float p_82400_3_) {
        float f;
        for (f = p_82400_2_ - p_82400_1_; f < -180.0f; f += 360.0f) {}
        while (f >= 180.0f) {
            f -= 360.0f;
        }
        return p_82400_1_ + p_82400_3_ * f;
    }
    
    public void doRender(final EntityShulkerSkull entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        final float f = this.getRenderYaw(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
        final float f2 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        GlStateManager.translate((float)x, (float)y, (float)z);
        final float f3 = 0.0625f;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        GlStateManager.enableAlpha();
        this.bindEntityTexture(entity);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }
        this.skeletonHeadModel.render((Entity)entity, 0.0f, 0.0f, 0.0f, f, f2, 0.0625f);
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    protected ResourceLocation getEntityTexture(final EntityShulkerSkull entity) {
        return entity.isInvulnerable() ? RenderShulkerSkull.INVULNERABLE_WITHER_TEXTURES : RenderShulkerSkull.WITHER_TEXTURES;
    }
    
    static {
        INVULNERABLE_WITHER_TEXTURES = new ResourceLocation("mmm:textures/model/shulkerwither_invulnerable.png");
        WITHER_TEXTURES = new ResourceLocation("mmm:textures/model/shulker_wither.png");
    }
}
