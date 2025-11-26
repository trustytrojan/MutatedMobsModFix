//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import mmm.models.*;
import net.minecraft.client.model.*;
import mmm.renders.layers.*;
import net.minecraft.client.renderer.entity.layers.*;
import mmm.common.entities.mobs.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderBlazeGolem extends RenderLiving<EntityBlazeGolem>
{
    private static final ResourceLocation IRON_GOLEM_TEXTURES;
    
    public RenderBlazeGolem(final RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelBlazeGolem(), 0.5f);
        this.addLayer((LayerRenderer)new LayerBlazeGolemFlower(this));
    }
    
    protected ResourceLocation getEntityTexture(final EntityBlazeGolem entity) {
        return RenderBlazeGolem.IRON_GOLEM_TEXTURES;
    }
    
    protected void preRenderCallback(final EntityCreeperGolem entitylivingbaseIn, final float partialTickTime) {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        final float f2 = 1.0f + MathHelper.sin(f * 100.0f) * f * 0.01f;
        f = MathHelper.clamp(f, 0.0f, 1.0f);
        f *= f;
        f *= f;
        final float f3 = (1.0f + f * 0.4f) * f2;
        final float f4 = (1.0f + f * 0.1f) / f2;
        GlStateManager.scale(f3, f4, f3);
    }
    
    public void doRender(final EntityBlazeGolem entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    protected void applyRotations(final EntityBlazeGolem entityLiving, final float ageInTicks, final float rotationYaw, final float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        if (entityLiving.limbSwingAmount >= 0.01) {
            final float f = 13.0f;
            final float f2 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0f - partialTicks) + 6.0f;
            final float f3 = (Math.abs(f2 % 13.0f - 6.5f) - 3.25f) / 3.25f;
            GlStateManager.rotate(6.5f * f3, 0.0f, 0.0f, 1.0f);
        }
    }
    
    static {
        IRON_GOLEM_TEXTURES = new ResourceLocation("mmm:textures/model/blaze_golem.png");
    }
}
