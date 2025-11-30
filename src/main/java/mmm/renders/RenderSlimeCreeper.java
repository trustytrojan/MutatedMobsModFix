//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import mmm.renders.layers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderSlimeCreeper extends RenderLiving<EntitySlimeCreeper>
{
    private static final ResourceLocation CREEPER_TEXTURES;
    
    public RenderSlimeCreeper(final RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelCreeper(), 0.5f);
        this.addLayer(new LayerSlimeCreeperCharge(this));
        this.addLayer(new LayerSlimeCreeperGel(this));
    }
    
    protected void preRenderCallback(final EntitySlimeCreeper entitylivingbaseIn, final float partialTickTime) {
        final float f = 0.999f;
        GlStateManager.scale(0.999f, 0.999f, 0.999f);
        final float f2 = (float)entitylivingbaseIn.getSlimeSize();
        final float f3 = 1.0f;
        GlStateManager.scale(f3 * f2, 1.0f / f3 * f2, f3 * f2);
    }
    
    public void doRender(final EntitySlimeCreeper entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        this.shadowSize = 0.25f * entity.getSlimeSize();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    protected int getColorMultiplier(final EntitySlimeCreeper entitylivingbaseIn, final float lightBrightness, final float partialTickTime) {
        final float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        if ((int)(f * 10.0f) % 2 == 0) {
            return 0;
        }
        int i = (int)(f * 0.2f * 255.0f);
        i = MathHelper.clamp(i, 0, 255);
        return i << 24 | 0x30FFFFFF;
    }
    
    protected ResourceLocation getEntityTexture(final EntitySlimeCreeper entity) {
        return RenderSlimeCreeper.CREEPER_TEXTURES;
    }
    
    static {
        CREEPER_TEXTURES = new ResourceLocation("mmm:textures/model/slime_creeper.png");
    }
}
