//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.models;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import mmm.common.entities.mobs.*;

@SideOnly(Side.CLIENT)
public class ModelBlazeSkeleton extends ModelBiped
{
    private final ModelRenderer[] blazeSticks;
    
    public ModelBlazeSkeleton() {
        this(0.0f, false);
    }
    
    public ModelBlazeSkeleton(final float modelSize, final boolean p_i46303_2_) {
        super(modelSize, 0.0f, 64, 32);
        this.blazeSticks = new ModelRenderer[12];
        for (int i = 0; i < this.blazeSticks.length; ++i) {
            (this.blazeSticks[i] = new ModelRenderer((ModelBase)this, 0, 16)).addBox(0.0f, 0.0f, 0.0f, 2, 8, 2);
        }
        if (!p_i46303_2_) {
            (this.bipedRightArm = new ModelRenderer((ModelBase)this, 40, 16)).addBox(-1.0f, -2.0f, -1.0f, 2, 12, 2, modelSize);
            this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
            this.bipedLeftArm = new ModelRenderer((ModelBase)this, 40, 16);
            this.bipedLeftArm.mirror = true;
            this.bipedLeftArm.addBox(-1.0f, -2.0f, -1.0f, 2, 12, 2, modelSize);
            this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
            (this.bipedRightLeg = new ModelRenderer((ModelBase)this, 0, 16)).addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2, modelSize);
            this.bipedRightLeg.setRotationPoint(-2.0f, 12.0f, 0.0f);
            this.bipedLeftLeg = new ModelRenderer((ModelBase)this, 0, 16);
            this.bipedLeftLeg.mirror = true;
            this.bipedLeftLeg.addBox(-1.0f, 0.0f, -1.0f, 2, 12, 2, modelSize);
            this.bipedLeftLeg.setRotationPoint(2.0f, 12.0f, 0.0f);
        }
    }
    
    public void render(final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        GlStateManager.pushMatrix();
        if (this.isChild) {
            final float f = 2.0f;
            GlStateManager.scale(0.75f, 0.75f, 0.75f);
            GlStateManager.translate(0.0f, 16.0f * scale, 0.0f);
            this.bipedHead.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            GlStateManager.translate(0.0f, 24.0f * scale, 0.0f);
            this.bipedBody.render(scale);
            this.bipedRightArm.render(scale);
            this.bipedLeftArm.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
            this.bipedHeadwear.render(scale);
            for (final ModelRenderer modelrenderer : this.blazeSticks) {
                modelrenderer.render(scale);
            }
        }
        else {
            if (entityIn.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            this.bipedHead.render(scale);
            this.bipedBody.render(scale);
            this.bipedRightArm.render(scale);
            this.bipedLeftArm.render(scale);
            this.bipedHeadwear.render(scale);
            for (final ModelRenderer modelrenderer2 : this.blazeSticks) {
                modelrenderer2.render(scale);
            }
        }
        GlStateManager.popMatrix();
    }
    
    public void setLivingAnimations(final EntityLivingBase entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTickTime) {
        this.rightArmPose = ModelBiped.ArmPose.EMPTY;
        this.leftArmPose = ModelBiped.ArmPose.EMPTY;
        final ItemStack itemstack = entitylivingbaseIn.getHeldItem(EnumHand.MAIN_HAND);
        if (itemstack.getItem() == Items.BOW) {
            if (entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT) {
                this.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
            }
            else {
                this.leftArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
            }
        }
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
    }
    
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
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
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        final ItemStack itemstack = ((EntityLivingBase)entityIn).getHeldItemMainhand();
        final EntityBlazeSkeleton AbstractBabySkeleton = (EntityBlazeSkeleton)entityIn;
        if (AbstractBabySkeleton.isSwingingArms() && (itemstack.isEmpty() || itemstack.getItem() != Items.BOW)) {
            final float f = MathHelper.sin(this.swingProgress * 3.1415927f);
            final float f2 = MathHelper.sin((1.0f - (1.0f - this.swingProgress) * (1.0f - this.swingProgress)) * 3.1415927f);
            this.bipedRightArm.rotateAngleZ = 0.0f;
            this.bipedLeftArm.rotateAngleZ = 0.0f;
            this.bipedRightArm.rotateAngleY = -(0.1f - f * 0.6f);
            this.bipedLeftArm.rotateAngleY = 0.1f - f * 0.6f;
            this.bipedRightArm.rotateAngleX = -1.5707964f;
            this.bipedLeftArm.rotateAngleX = -1.5707964f;
            final ModelRenderer bipedRightArm = this.bipedRightArm;
            bipedRightArm.rotateAngleX -= f * 1.2f - f2 * 0.4f;
            final ModelRenderer bipedLeftArm = this.bipedLeftArm;
            bipedLeftArm.rotateAngleX -= f * 1.2f - f2 * 0.4f;
            final ModelRenderer bipedRightArm2 = this.bipedRightArm;
            bipedRightArm2.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09f) * 0.05f + 0.05f;
            final ModelRenderer bipedLeftArm2 = this.bipedLeftArm;
            bipedLeftArm2.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09f) * 0.05f + 0.05f;
            final ModelRenderer bipedRightArm3 = this.bipedRightArm;
            bipedRightArm3.rotateAngleX += MathHelper.sin(ageInTicks * 0.067f) * 0.05f;
            final ModelRenderer bipedLeftArm3 = this.bipedLeftArm;
            bipedLeftArm3.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067f) * 0.05f;
        }
    }
    
    public void postRenderArm(final float scale, final EnumHandSide side) {
        final float f = (side == EnumHandSide.RIGHT) ? 1.0f : -1.0f;
        final ModelRenderer getArmForSide;
        final ModelRenderer modelrenderer = getArmForSide = this.getArmForSide(side);
        getArmForSide.rotationPointX += f;
        modelrenderer.postRender(scale);
        final ModelRenderer modelRenderer = modelrenderer;
        modelRenderer.rotationPointX -= f;
    }
}
