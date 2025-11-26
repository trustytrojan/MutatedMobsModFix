//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import mmm.models.*;
import net.minecraft.client.model.*;
import mmm.renders.layers.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderSlimeGolem extends RenderLiving<EntitySlimeGolem>
{
    private static final ResourceLocation IRON_GOLEM_TEXTURES;
    
    public RenderSlimeGolem(final RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelSlimeGolem(), 0.5f);
        this.addLayer((LayerRenderer)new LayerSlimeGolemFlower(this));
    }
    
    protected ResourceLocation getEntityTexture(final EntitySlimeGolem entity) {
        return RenderSlimeGolem.IRON_GOLEM_TEXTURES;
    }
    
    protected void preRenderCallback(final EntitySlimeGolem entitylivingbaseIn, final float partialTickTime) {
        final float f = 0.999f;
        GlStateManager.scale(0.999f, 0.999f, 0.999f);
        final float f2 = (float)entitylivingbaseIn.getSlimeSize();
        final float f3 = 1.0f;
        GlStateManager.scale(f3 * f2, 1.0f / f3 * f2, f3 * f2);
    }
    
    public void doRender(final EntitySlimeGolem entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        this.shadowSize = 0.25f * entity.getSlimeSize();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    protected void applyRotations(final EntitySlimeGolem entityLiving, final float ageInTicks, final float rotationYaw, final float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        if (entityLiving.limbSwingAmount >= 0.01) {
            final float f = 13.0f;
            final float f2 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0f - partialTicks) + 6.0f;
            final float f3 = (Math.abs(f2 % 13.0f - 6.5f) - 3.25f) / 3.25f;
            GlStateManager.rotate(6.5f * f3, 0.0f, 0.0f, 1.0f);
        }
    }
    
    static {
        IRON_GOLEM_TEXTURES = new ResourceLocation("mmm:textures/model/slime_golem.png");
    }
}
