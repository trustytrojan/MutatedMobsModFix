//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders.layers;

import net.minecraft.client.renderer.entity.layers.*;
import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import mmm.renders.*;
import mmm.models.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class LayerCreeperPigCharge implements LayerRenderer<EntityCreeperPig>
{
    private static final ResourceLocation LIGHTNING_TEXTURE;
    private final RenderCreeperPig creeperRenderer;
    private final ModelCreeperPig creeperModel;
    
    public LayerCreeperPigCharge(final RenderCreeperPig creeperRendererIn) {
        this.creeperModel = new ModelCreeperPig(2.0f);
        this.creeperRenderer = creeperRendererIn;
    }
    
    public void doRenderLayer(final EntityCreeperPig entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (entitylivingbaseIn.getPowered()) {
            final boolean flag = entitylivingbaseIn.isInvisible();
            GlStateManager.depthMask(!flag);
            this.creeperRenderer.bindTexture(LayerCreeperPigCharge.LIGHTNING_TEXTURE);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            final float f = entitylivingbaseIn.ticksExisted + partialTicks;
            GlStateManager.translate(f * 0.01f, f * 0.01f, 0.0f);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            final float f2 = 0.5f;
            GlStateManager.color(0.5f, 0.5f, 0.5f, 1.0f);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            this.creeperModel.setModelAttributes(this.creeperRenderer.getMainModel());
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.creeperModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(flag);
        }
    }
    
    public boolean shouldCombineTextures() {
        return false;
    }
    
    static {
        LIGHTNING_TEXTURE = new ResourceLocation("mmm:textures/model/creeper_armor.png");
    }
}
