//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import net.minecraft.entity.monster.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import mmm.common.entities.mobs.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderCreepervoker extends RenderLiving<EntityMob>
{
    private static final ResourceLocation VINDICATOR_TEXTURE;
    
    public RenderCreepervoker(final RenderManager p_i47207_1_) {
        super(p_i47207_1_, new ModelIllager(0.0f, 0.0f, 64, 64), 0.5f);
        this.addLayer(new LayerHeldItem(this) {
            public void doRenderLayer(final EntityLivingBase entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
                if (((EntityCreepervoker)entitylivingbaseIn).isSpellcasting()) {
                    super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                }
            }
            
            protected void translateToHand(final EnumHandSide p_191361_1_) {
                ((ModelIllager)this.livingEntityRenderer.getMainModel()).getArm(p_191361_1_).postRender(0.0625f);
            }
        });
    }
    
    protected ResourceLocation getEntityTexture(final EntityMob entity) {
        return RenderCreepervoker.VINDICATOR_TEXTURE;
    }
    
    protected void preRenderCallback(final EntityMob entitylivingbaseIn, final float partialTickTime) {
        final float f = 0.9375f;
        GlStateManager.scale(0.9375f, 0.9375f, 0.9375f);
    }
    
    static {
        VINDICATOR_TEXTURE = new ResourceLocation("mmm:textures/model/creeperevoker.png");
    }
}
