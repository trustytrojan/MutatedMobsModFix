//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.models;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

@SideOnly(Side.CLIENT)
public class ModelBlazeSnowman extends ModelBase
{
    public ModelRenderer body;
    public ModelRenderer bottomBody;
    public ModelRenderer head;
    public ModelRenderer rightHand;
    public ModelRenderer leftHand;
    private final ModelRenderer[] blazeSticks;
    
    public ModelBlazeSnowman() {
        this.blazeSticks = new ModelRenderer[12];
        final float f = 4.0f;
        final float f2 = 0.0f;
        (this.head = new ModelRenderer(this, 0, 0).setTextureSize(64, 64)).addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8, -0.5f);
        this.head.setRotationPoint(0.0f, 4.0f, 0.0f);
        for (int i = 0; i < this.blazeSticks.length; ++i) {
            (this.blazeSticks[i] = new ModelRenderer(this, 0, 16)).addBox(0.0f, 0.0f, 0.0f, 2, 8, 2);
        }
    }
    
    public void setRotationAngles(final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor, final Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.head.rotateAngleY = netHeadYaw * 0.017453292f;
        this.head.rotateAngleX = headPitch * 0.017453292f;
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
    
    public void render(final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.head.render(scale);
        for (final ModelRenderer modelrenderer : this.blazeSticks) {
            modelrenderer.render(scale);
        }
    }
}
