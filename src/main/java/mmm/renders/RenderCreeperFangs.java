//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.projectiles.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderCreeperFangs extends Render<EntityCreeperfangs>
{
    private static final ResourceLocation EVOKER_ILLAGER_FANGS;
    private final ModelEvokerFangs model;
    
    public RenderCreeperFangs(final RenderManager p_i47208_1_) {
        super(p_i47208_1_);
        this.model = new ModelEvokerFangs();
    }
    
    public void doRender(final EntityCreeperfangs entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        final float f = entity.getAnimationProgress(partialTicks);
        if (f != 0.0f) {
            float f2 = 2.0f;
            if (f > 0.9f) {
                f2 *= (float)((1.0 - f) / 0.10000000149011612);
            }
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            GlStateManager.enableAlpha();
            this.bindEntityTexture(entity);
            GlStateManager.translate((float)x, (float)y, (float)z);
            GlStateManager.rotate(90.0f - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.scale(-f2, -f2, f2);
            final float f3 = 0.03125f;
            GlStateManager.translate(0.0f, -0.626f, 0.0f);
            this.model.render(entity, f, 0.0f, 0.0f, entity.rotationYaw, entity.rotationPitch, 0.03125f);
            GlStateManager.popMatrix();
            GlStateManager.enableCull();
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
    }
    
    protected ResourceLocation getEntityTexture(final EntityCreeperfangs entity) {
        return RenderCreeperFangs.EVOKER_ILLAGER_FANGS;
    }
    
    static {
        EVOKER_ILLAGER_FANGS = new ResourceLocation("mmm:textures/model/creeper_fangs.png");
    }
}
