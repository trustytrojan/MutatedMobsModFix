//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.projectiles.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderWitherBullet extends Render<EntityWitherBullet>
{
    private static final ResourceLocation SHULKER_SPARK_TEXTURE;
    private final ModelShulkerBullet model;
    
    public RenderWitherBullet(final RenderManager manager) {
        super(manager);
        this.model = new ModelShulkerBullet();
    }
    
    private float rotLerp(final float p_188347_1_, final float p_188347_2_, final float p_188347_3_) {
        float f;
        for (f = p_188347_2_ - p_188347_1_; f < -180.0f; f += 360.0f) {}
        while (f >= 180.0f) {
            f -= 360.0f;
        }
        return p_188347_1_ + p_188347_3_ * f;
    }
    
    public void doRender(final EntityWitherBullet entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        GlStateManager.pushMatrix();
        final float f = this.rotLerp(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
        final float f2 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        final float f3 = entity.ticksExisted + partialTicks;
        GlStateManager.translate((float)x, (float)y + 0.15f, (float)z);
        GlStateManager.rotate(MathHelper.sin(f3 * 0.1f) * 180.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(MathHelper.cos(f3 * 0.1f) * 180.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(MathHelper.sin(f3 * 0.15f) * 360.0f, 0.0f, 0.0f, 1.0f);
        final float f4 = 0.03125f;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        this.bindEntityTexture(entity);
        this.model.render(entity, 0.0f, 0.0f, 0.0f, f, f2, 0.03125f);
        GlStateManager.enableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.5f);
        GlStateManager.scale(8.0f, 8.0f, 8.0f);
        this.model.render(entity, 0.0f, 0.0f, 0.0f, f, f2, 0.03125f);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    protected ResourceLocation getEntityTexture(final EntityWitherBullet entity) {
        return RenderWitherBullet.SHULKER_SPARK_TEXTURE;
    }
    
    static {
        SHULKER_SPARK_TEXTURE = new ResourceLocation("mmm:textures/model/wither_spark.png");
    }
}
