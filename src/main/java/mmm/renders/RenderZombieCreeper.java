//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import mmm.models.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

@SideOnly(Side.CLIENT)
public class RenderZombieCreeper extends RenderBiped<EntityZombieCreeper>
{
    private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE;
    
    public RenderZombieCreeper(final RenderManager renderManagerIn) {
        super(renderManagerIn, (ModelBiped)new ModelZombieCreeper(), 0.5f);
        this.addLayer((LayerRenderer)new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new ModelZombieCreeper(0.5f, true);
                this.modelArmor = new ModelZombieCreeper(1.0f, true);
            }
        });
    }
    
    protected void preRenderCallback(final EntityZombieCreeper entitylivingbaseIn, final float partialTickTime) {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        final float f2 = 1.0f + MathHelper.sin(f * 100.0f) * f * 0.01f;
        f = MathHelper.clamp(f, 0.0f, 1.0f);
        f *= f;
        f *= f;
        final float f3 = (1.0f + f * 0.4f) * f2;
        final float f4 = (1.0f + f * 0.1f) / f2;
        GlStateManager.scale(f3, f4, f3);
    }
    
    protected int getColorMultiplier(final EntityZombieCreeper entitylivingbaseIn, final float lightBrightness, final float partialTickTime) {
        final float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        if ((int)(f * 10.0f) % 2 == 0) {
            return 0;
        }
        int i = (int)(f * 0.2f * 255.0f);
        i = MathHelper.clamp(i, 0, 255);
        return i << 24 | 0x30FFFFFF;
    }
    
    protected ResourceLocation getEntityTexture(final EntityZombieCreeper entity) {
        return RenderZombieCreeper.ZOMBIE_PIGMAN_TEXTURE;
    }
    
    static {
        ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("mmm:textures/model/zombie_creeper.png");
    }
}
