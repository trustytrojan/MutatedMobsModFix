//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.renderer.entity.*;
import mmm.models.*;
import net.minecraft.client.model.*;
import mmm.renders.layers.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderBlenderman extends RenderLiving<EntityBlenderman>
{
    private static final ResourceLocation ENDERMAN_TEXTURES;
    private final Random rnd;
    
    public RenderBlenderman(final RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelBlenderman(), 0.5f);
        this.rnd = new Random();
        this.addLayer((LayerRenderer)new LayerHeldBlockBlenderman(this));
    }
    
    public ModelBlenderman getMainModel() {
        return (ModelBlenderman)super.getMainModel();
    }
    
    protected ResourceLocation getEntityTexture(final EntityBlenderman entity) {
        return RenderBlenderman.ENDERMAN_TEXTURES;
    }
    
    protected void applyRotations(final EntityBlenderman entityLiving, final float ageInTicks, final float rotationYaw, final float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        if (entityLiving.limbSwingAmount >= 0.01 && entityLiving.getAttackTarget() != null) {
            final float f = 13.0f;
            final float f2 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0f - partialTicks) + 6.0f;
            final float f3 = (Math.abs(f2 % 13.0f - 6.5f) - 3.25f) / 3.25f;
            GlStateManager.rotate(6.5f * f3, 0.0f, 0.0f, 1.0f);
        }
    }
    
    static {
        ENDERMAN_TEXTURES = new ResourceLocation("mmm:textures/model/blenderman.png");
    }
}
