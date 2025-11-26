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
public class RenderDeadPolarWolf extends RenderLiving<EntityDeadPolarWolf>
{
    private static final ResourceLocation Tamed_TEXTURES;
    
    public RenderDeadPolarWolf(final RenderManager p_i47198_1_) {
        super(p_i47198_1_, new ModelPolarWolf(), 0.5f);
    }
    
    protected void preRenderCallback(final EntityDeadPolarWolf entitylivingbaseIn, final float partialTickTime) {
        if (entitylivingbaseIn.isChild()) {
            GlStateManager.scale(0.45f, 0.45f, 0.45f);
        }
        else {
            GlStateManager.scale(1.15f, 1.15f, 1.15f);
        }
    }
    
    protected ResourceLocation getEntityTexture(final EntityDeadPolarWolf entity) {
        return RenderDeadPolarWolf.Tamed_TEXTURES;
    }
    
    static {
        Tamed_TEXTURES = new ResourceLocation("mmm:textures/model/deadpolar_wolf.png");
    }
}
