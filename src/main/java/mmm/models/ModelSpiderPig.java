//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.models;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;

@SideOnly(Side.CLIENT)
public class ModelSpiderPig extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer spiderLeg1;
    public ModelRenderer spiderLeg2;
    public ModelRenderer spiderLeg3;
    public ModelRenderer spiderLeg4;
    public ModelRenderer spiderLeg5;
    public ModelRenderer spiderLeg6;
    public ModelRenderer spiderLeg7;
    public ModelRenderer spiderLeg8;
    protected float childYOffset;
    protected float childZOffset;
    
    public ModelSpiderPig(final int height, final float scale) {
        this.head = new ModelRenderer(this, 0, 0);
        this.childYOffset = 8.0f;
        this.childZOffset = 4.0f;
        this.head.addBox(-4.0f, -4.0f, -8.0f, 8, 8, 8, scale);
        this.head.setRotationPoint(0.0f, (float)(18 - height), -6.0f);
        this.head.setTextureOffset(16, 16).addBox(-2.0f, 0.0f, -9.0f, 4, 3, 1, scale);
        (this.body = new ModelRenderer(this, 28, 8)).addBox(-5.0f, -10.0f, -7.0f, 10, 16, 8, scale);
        this.body.setRotationPoint(0.0f, (float)(17 - height), 2.0f);
        (this.spiderLeg1 = new ModelRenderer(this, 18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.spiderLeg1.setRotationPoint(-4.0f, 15.0f, 2.0f);
        (this.spiderLeg2 = new ModelRenderer(this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.spiderLeg2.setRotationPoint(4.0f, 15.0f, 2.0f);
        (this.spiderLeg3 = new ModelRenderer(this, 18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.spiderLeg3.setRotationPoint(-4.0f, 15.0f, 1.0f);
        (this.spiderLeg4 = new ModelRenderer(this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.spiderLeg4.setRotationPoint(4.0f, 15.0f, 1.0f);
        (this.spiderLeg5 = new ModelRenderer(this, 18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.spiderLeg5.setRotationPoint(-4.0f, 15.0f, 0.0f);
        (this.spiderLeg6 = new ModelRenderer(this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.spiderLeg6.setRotationPoint(4.0f, 15.0f, 0.0f);
        (this.spiderLeg7 = new ModelRenderer(this, 18, 0)).addBox(-15.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.spiderLeg7.setRotationPoint(-4.0f, 15.0f, -1.0f);
        (this.spiderLeg8 = new ModelRenderer(this, 18, 0)).addBox(-1.0f, -1.0f, -1.0f, 16, 2, 2, 0.0f);
        this.spiderLeg8.setRotationPoint(4.0f, 15.0f, -1.0f);
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
            this.body.render(scale);
            this.spiderLeg1.render(scale);
            this.spiderLeg2.render(scale);
            this.spiderLeg3.render(scale);
            this.spiderLeg4.render(scale);
            this.spiderLeg5.render(scale);
            this.spiderLeg6.render(scale);
            this.spiderLeg7.render(scale);
            this.spiderLeg8.render(scale);
            GlStateManager.popMatrix();
        }
        else {
            this.head.render(scale);
            this.body.render(scale);
            this.spiderLeg1.render(scale);
            this.spiderLeg2.render(scale);
            this.spiderLeg3.render(scale);
            this.spiderLeg4.render(scale);
            this.spiderLeg5.render(scale);
            this.spiderLeg6.render(scale);
            this.spiderLeg7.render(scale);
            this.spiderLeg8.render(scale);
        }
    }
    
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        this.head.rotateAngleX = headPitch * 0.017453292f;
        this.head.rotateAngleY = netHeadYaw * 0.017453292f;
        this.body.rotateAngleX = 1.5707964f;
        this.spiderLeg1.rotateAngleZ = -0.7853982f;
        this.spiderLeg2.rotateAngleZ = 0.7853982f;
        this.spiderLeg3.rotateAngleZ = -0.58119464f;
        this.spiderLeg4.rotateAngleZ = 0.58119464f;
        this.spiderLeg5.rotateAngleZ = -0.58119464f;
        this.spiderLeg6.rotateAngleZ = 0.58119464f;
        this.spiderLeg7.rotateAngleZ = -0.7853982f;
        this.spiderLeg8.rotateAngleZ = 0.7853982f;
        final float f1 = -0.0f;
        final float f2 = 0.3926991f;
        this.spiderLeg1.rotateAngleY = 0.7853982f;
        this.spiderLeg2.rotateAngleY = -0.7853982f;
        this.spiderLeg3.rotateAngleY = 0.3926991f;
        this.spiderLeg4.rotateAngleY = -0.3926991f;
        this.spiderLeg5.rotateAngleY = -0.3926991f;
        this.spiderLeg6.rotateAngleY = 0.3926991f;
        this.spiderLeg7.rotateAngleY = -0.7853982f;
        this.spiderLeg8.rotateAngleY = 0.7853982f;
        final float f3 = -(MathHelper.cos(limbSwing * 0.6662f * 2.0f + 0.0f) * 0.4f) * limbSwingAmount;
        final float f4 = -(MathHelper.cos(limbSwing * 0.6662f * 2.0f + 3.1415927f) * 0.4f) * limbSwingAmount;
        final float f5 = -(MathHelper.cos(limbSwing * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * limbSwingAmount;
        final float f6 = -(MathHelper.cos(limbSwing * 0.6662f * 2.0f + 4.712389f) * 0.4f) * limbSwingAmount;
        final float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662f + 0.0f) * 0.4f) * limbSwingAmount;
        final float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662f + 3.1415927f) * 0.4f) * limbSwingAmount;
        final float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662f + 1.5707964f) * 0.4f) * limbSwingAmount;
        final float f10 = Math.abs(MathHelper.sin(limbSwing * 0.6662f + 4.712389f) * 0.4f) * limbSwingAmount;
        final ModelRenderer spiderLeg1 = this.spiderLeg1;
        spiderLeg1.rotateAngleY += f3;
        final ModelRenderer spiderLeg2 = this.spiderLeg2;
        spiderLeg2.rotateAngleY += -f3;
        final ModelRenderer spiderLeg3 = this.spiderLeg3;
        spiderLeg3.rotateAngleY += f4;
        final ModelRenderer spiderLeg4 = this.spiderLeg4;
        spiderLeg4.rotateAngleY += -f4;
        final ModelRenderer spiderLeg5 = this.spiderLeg5;
        spiderLeg5.rotateAngleY += f5;
        final ModelRenderer spiderLeg6 = this.spiderLeg6;
        spiderLeg6.rotateAngleY += -f5;
        final ModelRenderer spiderLeg7 = this.spiderLeg7;
        spiderLeg7.rotateAngleY += f6;
        final ModelRenderer spiderLeg8 = this.spiderLeg8;
        spiderLeg8.rotateAngleY += -f6;
        final ModelRenderer spiderLeg9 = this.spiderLeg1;
        spiderLeg9.rotateAngleZ += f7;
        final ModelRenderer spiderLeg10 = this.spiderLeg2;
        spiderLeg10.rotateAngleZ += -f7;
        final ModelRenderer spiderLeg11 = this.spiderLeg3;
        spiderLeg11.rotateAngleZ += f8;
        final ModelRenderer spiderLeg12 = this.spiderLeg4;
        spiderLeg12.rotateAngleZ += -f8;
        final ModelRenderer spiderLeg13 = this.spiderLeg5;
        spiderLeg13.rotateAngleZ += f9;
        final ModelRenderer spiderLeg14 = this.spiderLeg6;
        spiderLeg14.rotateAngleZ += -f9;
        final ModelRenderer spiderLeg15 = this.spiderLeg7;
        spiderLeg15.rotateAngleZ += f10;
        final ModelRenderer spiderLeg16 = this.spiderLeg8;
        spiderLeg16.rotateAngleZ += -f10;
    }
}
