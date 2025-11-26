//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import mmm.models.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderPolarWolf extends RenderLiving<EntityPolarWolf>
{
    private static final ResourceLocation Wild_TEXTURES;
    private static final ResourceLocation Tamed_TEXTURES;
    
    public RenderPolarWolf(final RenderManager p_i47198_1_) {
        super(p_i47198_1_, new ModelPolarWolf(), 0.5f);
    }
    
    protected void preRenderCallback(final EntityPolarWolf entitylivingbaseIn, final float partialTickTime) {
        if (entitylivingbaseIn.isChild()) {
            GlStateManager.scale(0.45f, 0.45f, 0.45f);
        }
        else {
            GlStateManager.scale(1.15f, 1.15f, 1.15f);
        }
    }
    
    protected ResourceLocation getEntityTexture(final EntityPolarWolf entity) {
        if (entity.isTamed()) {
            return RenderPolarWolf.Tamed_TEXTURES;
        }
        return RenderPolarWolf.Wild_TEXTURES;
    }
    
    static {
        Wild_TEXTURES = new ResourceLocation("mmm:textures/model/wildpolar_wolf.png");
        Tamed_TEXTURES = new ResourceLocation("mmm:textures/model/polar_wolf.png");
    }
}
