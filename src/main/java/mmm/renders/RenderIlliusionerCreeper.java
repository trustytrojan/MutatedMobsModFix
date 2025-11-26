//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderIlliusionerCreeper extends RenderLiving<EntityIlliusionerCreeper>
{
    private static final ResourceLocation CREEPER_TEXTURES;
    
    public RenderIlliusionerCreeper(final RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelCreeper(), 0.5f);
    }
    
    protected void preRenderCallback(final EntityIlliusionerCreeper entitylivingbaseIn, final float partialTickTime) {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        final float f2 = 1.0f + MathHelper.sin(f * 100.0f) * f * 0.01f;
        f = MathHelper.clamp(f, 0.0f, 1.0f);
        f *= f;
        f *= f;
        final float f3 = (1.0f + f * 0.4f) * f2;
        final float f4 = (1.0f + f * 0.1f) / f2;
        GlStateManager.scale(f3, f4, f3);
    }
    
    protected int getColorMultiplier(final EntityIlliusionerCreeper entitylivingbaseIn, final float lightBrightness, final float partialTickTime) {
        final float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        if ((int)(f * 10.0f) % 2 == 0) {
            return 0;
        }
        int i = (int)(f * 0.2f * 255.0f);
        i = MathHelper.clamp(i, 0, 255);
        return i << 24 | 0x30FFFFFF;
    }
    
    protected ResourceLocation getEntityTexture(final EntityIlliusionerCreeper entity) {
        return RenderIlliusionerCreeper.CREEPER_TEXTURES;
    }
    
    static {
        CREEPER_TEXTURES = new ResourceLocation("mmm:textures/model/illiusion_creeper.png");
    }
}
