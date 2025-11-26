//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.models;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;

public class ModelBlenderman extends ModelBase
{
    ModelRenderer Head;
    ModelRenderer Headwear;
    ModelRenderer Body;
    ModelRenderer RightLeg;
    ModelRenderer LeftLeg;
    private final ModelRenderer[] blazeSticks;
    
    public ModelBlenderman() {
        this.blazeSticks = new ModelRenderer[12];
        this.textureWidth = 64;
        this.textureHeight = 64;
        (this.Head = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8);
        this.Head.setRotationPoint(0.0f, 2.0f, 0.0f);
        this.Head.setTextureSize(64, 64);
        this.setRotation(this.Head, 0.0f, 0.0f, 0.0f);
        (this.Headwear = new ModelRenderer((ModelBase)this, 0, 16)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8);
        this.Headwear.setRotationPoint(0.0f, 2.0f, 0.0f);
        this.Headwear.setTextureSize(64, 64);
        this.setRotation(this.Headwear, 0.0f, 0.0f, 0.0f);
        (this.Body = new ModelRenderer((ModelBase)this, 32, 16)).addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4);
        this.Body.setRotationPoint(0.0f, 2.0f, 0.0f);
        this.Body.setTextureSize(64, 64);
        this.setRotation(this.Body, 0.0f, 0.0f, 0.0f);
        (this.RightLeg = new ModelRenderer((ModelBase)this, 56, 0)).addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2);
        this.RightLeg.setRotationPoint(-2.0f, 14.0f, 0.0f);
        this.RightLeg.setTextureSize(64, 64);
        this.setRotation(this.RightLeg, 0.0f, 0.0f, 0.0f);
        (this.LeftLeg = new ModelRenderer((ModelBase)this, 56, 0)).addBox(-1.0f, 0.0f, -1.0f, 2, 10, 2);
        this.LeftLeg.setRotationPoint(2.0f, 14.0f, 0.0f);
        this.LeftLeg.setTextureSize(64, 64);
        this.setRotation(this.LeftLeg, 0.0f, 0.0f, 0.0f);
        this.LeftLeg.mirror = false;
        for (int i = 0; i < this.blazeSticks.length; ++i) {
            (this.blazeSticks[i] = new ModelRenderer((ModelBase)this, 0, 16)).addBox(0.0f, 0.0f, 0.0f, 2, 8, 2);
        }
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        GlStateManager.pushMatrix();
        this.Head.render(f5);
        this.Headwear.render(f5);
        for (final ModelRenderer modelrenderer : this.blazeSticks) {
            modelrenderer.render(f5);
        }
        GlStateManager.popMatrix();
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        this.Head.rotateAngleY = netHeadYaw * 0.017453292f;
        this.Head.rotateAngleX = headPitch * 0.017453292f;
        this.Headwear.rotateAngleY = netHeadYaw * 0.017453292f;
        this.Headwear.rotateAngleX = headPitch * 0.017453292f;
        this.LeftLeg.rotateAngleX = -1.5f * this.triangleWave(limbSwing, 13.0f) * limbSwingAmount;
        this.RightLeg.rotateAngleX = 1.5f * this.triangleWave(limbSwing, 13.0f) * limbSwingAmount;
        this.LeftLeg.rotateAngleY = 0.0f;
        this.RightLeg.rotateAngleY = 0.0f;
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
    
    private float triangleWave(final float p_78172_1_, final float p_78172_2_) {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5f) - p_78172_2_ * 0.25f) / (p_78172_2_ * 0.25f);
    }
}
