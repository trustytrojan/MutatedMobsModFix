//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderShulkerGhast extends RenderLiving<EntityShulkerGhast>
{
    private static final ResourceLocation GHAST_TEXTURES;
    private static final ResourceLocation GHAST_SHOOTING_TEXTURES;
    
    public RenderShulkerGhast(final RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelGhast(), 0.5f);
    }
    
    protected ResourceLocation getEntityTexture(final EntityShulkerGhast entity) {
        return entity.isAttacking() ? RenderShulkerGhast.GHAST_SHOOTING_TEXTURES : RenderShulkerGhast.GHAST_TEXTURES;
    }
    
    protected void preRenderCallback(final EntityShulkerGhast entitylivingbaseIn, final float partialTickTime) {
        final float f = 1.0f;
        final float f2 = 2.5f;
        final float f3 = 2.5f;
        GlStateManager.scale(2.5f, 2.5f, 2.5f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    static {
        GHAST_TEXTURES = new ResourceLocation("mmm:textures/model/shulker_ghast.png");
        GHAST_SHOOTING_TEXTURES = new ResourceLocation("mmm:textures/model/shulker_ghast_shooting.png");
    }
}
