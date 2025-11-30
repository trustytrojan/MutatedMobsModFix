//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders.layers;

import net.minecraft.client.renderer.entity.layers.*;
import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import mmm.renders.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class LayerSlimeCreeperGel implements LayerRenderer<EntitySlimeCreeper>
{
    private final RenderSlimeCreeper slimeRenderer;
    private final ModelBase slimeModel;
    
    public LayerSlimeCreeperGel(final RenderSlimeCreeper slimeRendererIn) {
        this.slimeModel = new ModelCreeper(0.0f);
        this.slimeRenderer = slimeRendererIn;
    }
    
    public void doRenderLayer(final EntitySlimeCreeper entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (!entitylivingbaseIn.isInvisible()) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
            this.slimeModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
        }
    }
    
    public boolean shouldCombineTextures() {
        return true;
    }
}
