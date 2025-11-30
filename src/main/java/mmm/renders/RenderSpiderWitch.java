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
public class RenderSpiderWitch extends RenderLiving<EntitySpiderWitch>
{
    private static final ResourceLocation WITCH_TEXTURES;
    
    public RenderSpiderWitch(final RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelSpiderWitch(0.0f), 0.5f);
        this.addLayer(new LayerHeldItemSpiderWitch(this));
    }
    
    public ModelSpiderWitch getMainModel() {
        return (ModelSpiderWitch)super.getMainModel();
    }
    
    public void doRender(final EntitySpiderWitch entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        ((ModelSpiderWitch)this.mainModel).holdingItem = !entity.getHeldItemMainhand().isEmpty();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    protected ResourceLocation getEntityTexture(final EntitySpiderWitch entity) {
        return RenderSpiderWitch.WITCH_TEXTURES;
    }
    
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }
    
    protected void preRenderCallback(final EntitySpiderWitch entitylivingbaseIn, final float partialTickTime) {
        final float f = 0.9375f;
        GlStateManager.scale(0.9375f, 0.9375f, 0.9375f);
    }
    
    static {
        WITCH_TEXTURES = new ResourceLocation("mmm:textures/model/spider_witch.png");
    }
}
