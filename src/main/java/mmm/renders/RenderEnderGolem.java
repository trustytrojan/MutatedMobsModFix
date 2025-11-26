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
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderEnderGolem extends RenderLiving<EntityEnderGolem>
{
    private static final ResourceLocation ENDERMAN_TEXTURES;
    private final Random rnd;
    
    public RenderEnderGolem(final RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelEnderGolem(0.0f), 0.5f);
        this.rnd = new Random();
        this.addLayer((LayerRenderer)new LayerHeldBlockGolem(this));
    }
    
    public ModelEnderGolem getMainModel() {
        return (ModelEnderGolem)super.getMainModel();
    }
    
    public void doRender(final EntityEnderGolem entity, double x, final double y, double z, final float entityYaw, final float partialTicks) {
        final IBlockState iblockstate = entity.getHeldBlockState();
        final ModelEnderGolem modelenderman = this.getMainModel();
        modelenderman.isCarrying = (iblockstate != null);
        modelenderman.isAttacking = entity.isScreaming();
        if (entity.isScreaming()) {
            final double d0 = 0.02;
            x += this.rnd.nextGaussian() * 0.02;
            z += this.rnd.nextGaussian() * 0.02;
        }
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    protected ResourceLocation getEntityTexture(final EntityEnderGolem entity) {
        return RenderEnderGolem.ENDERMAN_TEXTURES;
    }
    
    protected void applyRotations(final EntityEnderGolem entityLiving, final float ageInTicks, final float rotationYaw, final float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        if (entityLiving.limbSwingAmount >= 0.01) {
            final float f = 13.0f;
            final float f2 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0f - partialTicks) + 6.0f;
            final float f3 = (Math.abs(f2 % 13.0f - 6.5f) - 3.25f) / 3.25f;
            GlStateManager.rotate(6.5f * f3, 0.0f, 0.0f, 1.0f);
        }
    }
    
    static {
        ENDERMAN_TEXTURES = new ResourceLocation("mmm:textures/model/ender_golem.png");
    }
}
