//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import mmm.models.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import mmm.renders.layers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderIliusionWitherSkeleton extends RenderBiped<EntityIlliusionerWitherSkeleton>
{
    private static final ResourceLocation STRAY_SKELETON_TEXTURES;
    
    public RenderIliusionWitherSkeleton(final RenderManager renderManagerIn) {
        super(renderManagerIn, (ModelBiped)new ModelIWitherSkeleton(), 0.5f);
        this.addLayer(new LayerHeldItem((RenderLivingBase)this));
        this.addLayer(new LayerBipedArmor((RenderLivingBase)this));
        this.addLayer(new LayerIliusionClothing((RenderLivingBase)this));
    }
    
    protected ResourceLocation getEntityTexture(final EntityIlliusionerWitherSkeleton entity) {
        return RenderIliusionWitherSkeleton.STRAY_SKELETON_TEXTURES;
    }
    
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.09375f, 0.1875f, 0.0f);
    }
    
    static {
        STRAY_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    }
}
