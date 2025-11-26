//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders.layers;

import net.minecraft.client.renderer.entity.layers.*;
import mmm.common.entities.mobs.boss.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import mmm.renders.*;
import mmm.models.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class LayerShulkerWitherAura implements LayerRenderer<EntityShulkerWither>
{
    private static final ResourceLocation WITHER_ARMOR;
    private final RenderShulkerWither witherRenderer;
    private final ModelShulkerWither witherModel;
    
    public LayerShulkerWitherAura(final RenderShulkerWither witherRendererIn) {
        this.witherModel = new ModelShulkerWither(0.5f);
        this.witherRenderer = witherRendererIn;
    }
    
    public void doRenderLayer(final EntityShulkerWither entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (entitylivingbaseIn.isArmored()) {
            GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
            this.witherRenderer.bindTexture(LayerShulkerWitherAura.WITHER_ARMOR);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            final float f = entitylivingbaseIn.ticksExisted + partialTicks;
            final float f2 = MathHelper.cos(f * 0.02f) * 3.0f;
            final float f3 = f * 0.01f;
            GlStateManager.translate(f2, f3, 0.0f);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            final float f4 = 0.5f;
            GlStateManager.color(0.5f, 0.5f, 0.5f, 1.0f);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            this.witherModel.setLivingAnimations((EntityLivingBase)entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.witherModel.setModelAttributes(this.witherRenderer.getMainModel());
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.witherModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
        }
    }
    
    public boolean shouldCombineTextures() {
        return false;
    }
    
    static {
        WITHER_ARMOR = new ResourceLocation("mmm:textures/model/shulkerwither_armor.png");
    }
}
