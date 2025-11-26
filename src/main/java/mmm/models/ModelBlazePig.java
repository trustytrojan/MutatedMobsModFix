//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.models;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;

@SideOnly(Side.CLIENT)
public class ModelBlazePig extends ModelBase
{
    private final ModelRenderer[] blazeSticks;
    public ModelRenderer head;
    protected float childYOffset;
    protected float childZOffset;
    
    public ModelBlazePig(final int height, final float scale) {
        this.blazeSticks = new ModelRenderer[12];
        this.head = new ModelRenderer((ModelBase)this, 0, 0);
        this.childYOffset = 8.0f;
        this.childZOffset = 4.0f;
        for (int i = 0; i < this.blazeSticks.length; ++i) {
            (this.blazeSticks[i] = new ModelRenderer((ModelBase)this, 0, 16)).addBox(-5.0f, 2.0f, -7.0f, 2, 8, 2);
        }
        this.head.addBox(-5.0f, -15.0f, -7.0f, 8, 8, 8, scale);
        this.head.setTextureOffset(16, 16).addBox(-3.0f, -10.0f, -8.0f, 4, 3, 1, scale);
        this.head.setRotationPoint(0.0f, (float)(18 - height), -6.0f);
    }
    
    public void render(final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        if (this.isChild) {
            final float f = 2.0f;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, this.childYOffset * scale, this.childZOffset * scale);
            this.head.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            GlStateManager.translate(0.0f, 24.0f * scale, 0.0f);
            for (final ModelRenderer modelrenderer : this.blazeSticks) {
                modelrenderer.render(scale);
            }
            GlStateManager.popMatrix();
        }
        else {
            this.head.render(scale);
            for (final ModelRenderer modelrenderer2 : this.blazeSticks) {
                modelrenderer2.render(scale);
            }
        }
    }
    
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        this.head.rotateAngleX = headPitch * 0.017453292f;
        this.head.rotateAngleY = netHeadYaw * 0.017453292f;
        float ff = ageInTicks * 3.1415927f * -0.1f;
        for (int i = 0; i < 4; ++i) {
            this.blazeSticks[i].rotationPointY = -2.0f + MathHelper.cos((i * 2 + ageInTicks) * 0.25f);
            this.blazeSticks[i].rotationPointX = MathHelper.cos(ff) * 9.0f;
            this.blazeSticks[i].rotationPointZ = MathHelper.sin(ff) * 9.0f;
            ++ff;
        }
        ff = 0.7853982f + ageInTicks * 3.1415927f * 0.03f;
        for (int j = 4; j < 8; ++j) {
            this.blazeSticks[j].rotationPointY = 2.0f + MathHelper.cos((j * 2 + ageInTicks) * 0.25f);
            this.blazeSticks[j].rotationPointX = MathHelper.cos(ff) * 7.0f;
            this.blazeSticks[j].rotationPointZ = MathHelper.sin(ff) * 7.0f;
            ++ff;
        }
        ff = 0.47123894f + ageInTicks * 3.1415927f * -0.05f;
        for (int k = 8; k < 12; ++k) {
            this.blazeSticks[k].rotationPointY = 11.0f + MathHelper.cos((k * 1.5f + ageInTicks) * 0.5f);
            this.blazeSticks[k].rotationPointX = MathHelper.cos(ff) * 5.0f;
            this.blazeSticks[k].rotationPointZ = MathHelper.sin(ff) * 5.0f;
            ++ff;
        }
    }
}
