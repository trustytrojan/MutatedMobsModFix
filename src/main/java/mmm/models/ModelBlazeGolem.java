//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.models;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.model.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import mmm.common.entities.mobs.*;

@SideOnly(Side.CLIENT)
public class ModelBlazeGolem extends ModelBase
{
    public ModelRenderer ironGolemHead;
    public ModelRenderer ironGolemBody;
    public ModelRenderer ironGolemRightArm;
    public ModelRenderer ironGolemLeftArm;
    public ModelRenderer ironGolemLeftLeg;
    public ModelRenderer ironGolemRightLeg;
    private final ModelRenderer[] blazeSticks;
    
    public ModelBlazeGolem() {
        this(0.0f);
    }
    
    public ModelBlazeGolem(final float p_i1161_1_) {
        this(p_i1161_1_, -7.0f);
        for (int i = 0; i < this.blazeSticks.length; ++i) {
            (this.blazeSticks[i] = new ModelRenderer(this, 0, 16)).addBox(0.0f, 0.0f, 0.0f, 2, 16, 2);
        }
    }
    
    public ModelBlazeGolem(final float p_i46362_1_, final float p_i46362_2_) {
        this.blazeSticks = new ModelRenderer[12];
        final int i = 128;
        final int j = 128;
        (this.ironGolemHead = new ModelRenderer(this).setTextureSize(128, 128)).setRotationPoint(0.0f, 0.0f + p_i46362_2_, -2.0f);
        this.ironGolemHead.setTextureOffset(0, 0).addBox(-4.0f, -12.0f, -5.5f, 8, 10, 8, p_i46362_1_);
        this.ironGolemHead.setTextureOffset(24, 0).addBox(-1.0f, -5.0f, -7.5f, 2, 4, 2, p_i46362_1_);
        (this.ironGolemBody = new ModelRenderer(this).setTextureSize(128, 128)).setRotationPoint(0.0f, 0.0f + p_i46362_2_, 0.0f);
        this.ironGolemBody.setTextureOffset(0, 40).addBox(-9.0f, -2.0f, -6.0f, 18, 12, 11, p_i46362_1_);
        this.ironGolemBody.setTextureOffset(0, 70).addBox(-4.5f, 10.0f, -3.0f, 9, 5, 6, p_i46362_1_ + 0.5f);
        (this.ironGolemRightArm = new ModelRenderer(this).setTextureSize(128, 128)).setRotationPoint(0.0f, -7.0f, 0.0f);
        this.ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0f, -2.5f, -3.0f, 4, 30, 6, p_i46362_1_);
        (this.ironGolemLeftArm = new ModelRenderer(this).setTextureSize(128, 128)).setRotationPoint(0.0f, -7.0f, 0.0f);
        this.ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0f, -2.5f, -3.0f, 4, 30, 6, p_i46362_1_);
        (this.ironGolemLeftLeg = new ModelRenderer(this, 0, 22).setTextureSize(128, 128)).setRotationPoint(-4.0f, 18.0f + p_i46362_2_, 0.0f);
        this.ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5f, -3.0f, -3.0f, 6, 16, 5, p_i46362_1_);
        this.ironGolemRightLeg = new ModelRenderer(this, 0, 22).setTextureSize(128, 128);
        this.ironGolemRightLeg.mirror = true;
        this.ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0f, 18.0f + p_i46362_2_, 0.0f);
        this.ironGolemRightLeg.addBox(-3.5f, -3.0f, -3.0f, 6, 16, 5, p_i46362_1_);
    }
    
    public void render(final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.ironGolemHead.render(scale);
        this.ironGolemBody.render(scale);
        this.ironGolemLeftLeg.render(scale);
        this.ironGolemRightLeg.render(scale);
        this.ironGolemRightArm.render(scale);
        this.ironGolemLeftArm.render(scale);
        for (final ModelRenderer modelrenderer : this.blazeSticks) {
            modelrenderer.render(scale);
        }
    }
    
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        this.ironGolemHead.rotateAngleY = netHeadYaw * 0.017453292f;
        this.ironGolemHead.rotateAngleX = headPitch * 0.017453292f;
        this.ironGolemLeftLeg.rotateAngleX = -1.5f * this.triangleWave(limbSwing, 13.0f) * limbSwingAmount;
        this.ironGolemRightLeg.rotateAngleX = 1.5f * this.triangleWave(limbSwing, 13.0f) * limbSwingAmount;
        this.ironGolemLeftLeg.rotateAngleY = 0.0f;
        this.ironGolemRightLeg.rotateAngleY = 0.0f;
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
    
    public void setLivingAnimations(final EntityLivingBase entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTickTime) {
        final EntityBlazeGolem EntityBlazeGolem = (EntityBlazeGolem)entitylivingbaseIn;
        final int i = EntityBlazeGolem.getAttackTimer();
        if (i > 0) {
            this.ironGolemRightArm.rotateAngleX = -2.0f + 1.5f * this.triangleWave(i - partialTickTime, 10.0f);
            this.ironGolemLeftArm.rotateAngleX = -2.0f + 1.5f * this.triangleWave(i - partialTickTime, 10.0f);
        }
        else {
            final int j = EntityBlazeGolem.getHoldRoseTick();
            if (j > 0) {
                this.ironGolemRightArm.rotateAngleX = -0.8f + 0.025f * this.triangleWave((float)j, 70.0f);
                this.ironGolemLeftArm.rotateAngleX = 0.0f;
            }
            else {
                this.ironGolemRightArm.rotateAngleX = (-0.2f + 1.5f * this.triangleWave(limbSwing, 13.0f)) * limbSwingAmount;
                this.ironGolemLeftArm.rotateAngleX = (-0.2f - 1.5f * this.triangleWave(limbSwing, 13.0f)) * limbSwingAmount;
            }
        }
    }
    
    private float triangleWave(final float p_78172_1_, final float p_78172_2_) {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5f) - p_78172_2_ * 0.25f) / (p_78172_2_ * 0.25f);
    }
}
