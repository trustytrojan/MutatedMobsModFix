//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders.layers;

import net.minecraft.client.renderer.entity.layers.*;
import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import mmm.models.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class LayerIliusionClothing implements LayerRenderer<EntityIlliusionerWitherSkeleton>
{
    private static final ResourceLocation STRAY_CLOTHES_TEXTURES;
    private final RenderLivingBase<?> renderer;
    private final ModelIWitherSkeleton layerModel;
    
    public LayerIliusionClothing(final RenderLivingBase<?> p_i47183_1_) {
        this.layerModel = new ModelIWitherSkeleton(0.25f, true);
        this.renderer = p_i47183_1_;
    }
    
    public void doRenderLayer(final EntityIlliusionerWitherSkeleton entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        this.layerModel.setModelAttributes(this.renderer.getMainModel());
        this.layerModel.setLivingAnimations((EntityLivingBase)entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.renderer.bindTexture(LayerIliusionClothing.STRAY_CLOTHES_TEXTURES);
        this.layerModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
    
    public boolean shouldCombineTextures() {
        return true;
    }
    
    static {
        STRAY_CLOTHES_TEXTURES = new ResourceLocation("mmm:textures/model/iliusionist_overlay.png");
    }
}
