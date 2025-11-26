//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.models;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import mmm.common.entities.mobs.*;

@SideOnly(Side.CLIENT)
public class ModelEnderGolem extends ModelBiped
{
    public boolean isCarrying;
    public boolean isAttacking;
    
    public ModelEnderGolem(final float scale) {
        super(0.0f, -14.0f, 64, 32);
        final float f = -14.0f;
        (this.bipedHeadwear = new ModelRenderer((ModelBase)this, 0, 16)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, scale - 0.5f);
        this.bipedHeadwear.setRotationPoint(0.0f, -14.0f, 0.0f);
        (this.bipedBody = new ModelRenderer((ModelBase)this, 32, 16)).addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, scale);
        this.bipedBody.setRotationPoint(0.0f, -14.0f, 0.0f);
        (this.bipedRightArm = new ModelRenderer((ModelBase)this, 56, 0)).addBox(-1.0f, -2.0f, -1.0f, 2, 30, 6, scale);
        this.bipedRightArm.setRotationPoint(0.0f, -7.0f, 0.0f);
        this.bipedLeftArm = new ModelRenderer((ModelBase)this, 56, 0);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -1.0f, 2, 30, 6, scale);
        this.bipedLeftArm.setRotationPoint(5.0f, -7.0f, 0.0f);
        (this.bipedRightLeg = new ModelRenderer((ModelBase)this, 56, 0)).addBox(-1.0f, 0.0f, -1.0f, 2, 30, 2, scale);
        this.bipedRightLeg.setRotationPoint(-2.0f, -2.0f, 0.0f);
        this.bipedLeftLeg = new ModelRenderer((ModelBase)this, 56, 0);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, 2, 30, 2, scale);
        this.bipedLeftLeg.setRotationPoint(2.0f, -2.0f, 0.0f);
    }
    
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.bipedHead.showModel = true;
        final float f = -14.0f;
        this.bipedBody.rotateAngleX = 0.0f;
        this.bipedBody.rotationPointY = -14.0f;
        this.bipedBody.rotationPointZ = -0.0f;
        final ModelRenderer bipedRightLeg = this.bipedRightLeg;
        bipedRightLeg.rotateAngleX -= 0.0f;
        final ModelRenderer bipedLeftLeg = this.bipedLeftLeg;
        bipedLeftLeg.rotateAngleX -= 0.0f;
        this.bipedRightArm.rotateAngleX *= 0.5;
        this.bipedLeftArm.rotateAngleX *= 0.5;
        this.bipedRightLeg.rotateAngleX *= 0.5;
        this.bipedLeftLeg.rotateAngleX *= 0.5;
        final float f2 = 0.4f;
        if (this.bipedRightArm.rotateAngleX > 0.4f) {
            this.bipedRightArm.rotateAngleX = 0.4f;
        }
        if (this.bipedLeftArm.rotateAngleX > 0.4f) {
            this.bipedLeftArm.rotateAngleX = 0.4f;
        }
        if (this.bipedRightArm.rotateAngleX < -0.4f) {
            this.bipedRightArm.rotateAngleX = -0.4f;
        }
        if (this.bipedLeftArm.rotateAngleX < -0.4f) {
            this.bipedLeftArm.rotateAngleX = -0.4f;
        }
        if (this.bipedRightLeg.rotateAngleX > 0.4f) {
            this.bipedRightLeg.rotateAngleX = 0.4f;
        }
        if (this.bipedLeftLeg.rotateAngleX > 0.4f) {
            this.bipedLeftLeg.rotateAngleX = 0.4f;
        }
        if (this.bipedRightLeg.rotateAngleX < -0.4f) {
            this.bipedRightLeg.rotateAngleX = -0.4f;
        }
        if (this.bipedLeftLeg.rotateAngleX < -0.4f) {
            this.bipedLeftLeg.rotateAngleX = -0.4f;
        }
        if (this.isCarrying) {
            this.bipedRightArm.rotateAngleX = -0.5f;
            this.bipedLeftArm.rotateAngleX = -0.5f;
            this.bipedRightArm.rotateAngleZ = 0.05f;
            this.bipedLeftArm.rotateAngleZ = -0.05f;
        }
        this.bipedRightArm.rotationPointZ = 0.0f;
        this.bipedLeftArm.rotationPointZ = 0.0f;
        this.bipedRightLeg.rotationPointZ = 0.0f;
        this.bipedLeftLeg.rotationPointZ = 0.0f;
        this.bipedRightLeg.rotationPointY = -5.0f;
        this.bipedLeftLeg.rotationPointY = -5.0f;
        this.bipedHead.rotationPointZ = -0.0f;
        this.bipedHead.rotationPointY = -13.0f;
        this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
        this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
        this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;
        if (this.isAttacking) {
            final float f3 = 1.0f;
            final ModelRenderer bipedHead = this.bipedHead;
            bipedHead.rotationPointY -= 5.0f;
        }
    }
    
    public void setLivingAnimations(final EntityLivingBase entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTickTime) {
        final EntityEnderGolem entityirongolem = (EntityEnderGolem)entitylivingbaseIn;
        final int i = entityirongolem.getAttackTimer();
        if (i > 0) {
            this.bipedRightArm.rotateAngleX = -2.0f + 1.5f * this.triangleWave(i - partialTickTime, 10.0f);
            this.bipedLeftArm.rotateAngleX = -2.0f + 1.5f * this.triangleWave(i - partialTickTime, 10.0f);
        }
        else {
            this.bipedRightArm.rotateAngleX = (-0.2f + 1.5f * this.triangleWave(limbSwing, 13.0f)) * limbSwingAmount;
            this.bipedLeftArm.rotateAngleX = (-0.2f - 1.5f * this.triangleWave(limbSwing, 13.0f)) * limbSwingAmount;
        }
    }
    
    private float triangleWave(final float p_78172_1_, final float p_78172_2_) {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5f) - p_78172_2_ * 0.25f) / (p_78172_2_ * 0.25f);
    }
}
