//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.models;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class ModelPolarWolf extends ModelBase
{
    ModelRenderer Body;
    ModelRenderer Mane;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Tail;
    ModelRenderer Ear1;
    ModelRenderer Ear2;
    ModelRenderer Nose;
    
    public ModelPolarWolf() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        (this.Body = new ModelRenderer(this, 15, 31)).addBox(-4.0f, -2.0f, -3.0f, 12, 18, 12);
        this.Body.setRotationPoint(-1.0f, 14.0f, -3.0f);
        this.Body.setTextureSize(64, 64);
        this.Body.mirror = true;
        this.setRotation(this.Body, 1.570796f, 0.0f, 0.0f);
        (this.Mane = new ModelRenderer(this, 33, 0)).addBox(-4.0f, -3.0f, -3.0f, 8, 6, 7);
        this.Mane.setRotationPoint(1.0f, 11.0f, -8.0f);
        this.Mane.setTextureSize(64, 64);
        this.Mane.mirror = true;
        this.setRotation(this.Mane, 1.570796f, 0.0f, 0.0f);
        (this.Leg1 = new ModelRenderer(this, 1, 52)).addBox(-1.0f, 0.0f, -1.0f, 3, 7, 2);
        this.Leg1.setRotationPoint(-3.5f, 17.0f, 10.0f);
        this.Leg1.setTextureSize(64, 64);
        this.Leg1.mirror = true;
        this.setRotation(this.Leg1, 0.0f, 0.0f, 0.0f);
        (this.Leg2 = new ModelRenderer(this, 1, 52)).addBox(-1.0f, 0.0f, -1.0f, 3, 7, 2);
        this.Leg2.setRotationPoint(3.5f, 17.0f, 10.0f);
        this.Leg2.setTextureSize(64, 64);
        this.Leg2.mirror = true;
        this.setRotation(this.Leg2, 0.0f, 0.0f, 0.0f);
        (this.Leg3 = new ModelRenderer(this, 1, 52)).addBox(-1.0f, 0.0f, -1.0f, 3, 7, 2);
        this.Leg3.setRotationPoint(-2.5f, 17.0f, -4.0f);
        this.Leg3.setTextureSize(64, 64);
        this.Leg3.mirror = true;
        this.setRotation(this.Leg3, 0.0f, 0.0f, 0.0f);
        (this.Leg4 = new ModelRenderer(this, 1, 52)).addBox(-1.0f, 0.0f, -1.0f, 3, 7, 2);
        this.Leg4.setRotationPoint(3.5f, 17.0f, -4.0f);
        this.Leg4.setTextureSize(64, 64);
        this.Leg4.mirror = true;
        this.setRotation(this.Leg4, 0.0f, 0.0f, 0.0f);
        (this.Tail = new ModelRenderer(this, 9, 18)).addBox(-1.0f, 0.0f, -1.0f, 3, 8, 3);
        this.Tail.setRotationPoint(1.0f, 11.0f, 12.0f);
        this.Tail.setTextureSize(64, 64);
        this.Tail.mirror = true;
        this.setRotation(this.Tail, 1.130069f, 0.0f, 0.0f);
        (this.Ear1 = new ModelRenderer(this, 26, 14)).addBox(-3.0f, -5.0f, 0.0f, 2, 2, 1);
        this.Ear1.setRotationPoint(0.0f, 10.0f, -7.0f);
        this.Ear1.setTextureSize(64, 64);
        this.Ear1.mirror = true;
        this.setRotation(this.Ear1, 0.0f, 0.0f, 0.0f);
        (this.Ear2 = new ModelRenderer(this, 26, 14)).addBox(1.0f, -5.0f, 0.0f, 2, 2, 1);
        this.Ear2.setRotationPoint(2.0f, 10.0f, -7.0f);
        this.Ear2.setTextureSize(64, 64);
        this.Ear2.mirror = true;
        this.setRotation(this.Ear2, 0.0f, 0.0f, 0.0f);
        (this.Nose = new ModelRenderer(this, 0, 10)).addBox(-2.0f, 0.0f, -5.0f, 3, 2, 4);
        this.Nose.setRotationPoint(1.5f, 10.9f, -10.0f);
        this.Nose.setTextureSize(64, 64);
        this.Nose.mirror = true;
        this.setRotation(this.Nose, 0.0f, 0.0f, 0.0f);
    }
    
    public void render(final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.Body.render(scale);
        this.Mane.render(scale);
        this.Leg1.render(scale);
        this.Leg2.render(scale);
        this.Leg3.render(scale);
        this.Leg4.render(scale);
        this.Tail.render(scale);
        this.Ear1.render(scale);
        this.Ear2.render(scale);
        this.Nose.render(scale);
    }
    
    private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        final float f = 1.0f;
        this.Leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount / f;
        this.Leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount / f;
        this.Leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount / f;
        this.Leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount / f;
        this.Leg1.rotateAngleY = 0.0f;
        this.Leg2.rotateAngleY = 0.0f;
        this.Leg3.rotateAngleY = 0.0f;
        this.Leg4.rotateAngleY = 0.0f;
        this.Leg1.rotateAngleZ = 0.0f;
        this.Leg2.rotateAngleZ = 0.0f;
        this.Leg3.rotateAngleZ = 0.0f;
        this.Leg4.rotateAngleZ = 0.0f;
    }
}
