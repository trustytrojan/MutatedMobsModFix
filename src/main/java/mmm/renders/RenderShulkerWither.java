//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.boss.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import mmm.models.*;
import net.minecraft.client.model.*;
import mmm.renders.layers.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderShulkerWither extends RenderLiving<EntityShulkerWither>
{
    private static final ResourceLocation INVULNERABLE_WITHER_TEXTURES;
    private static final ResourceLocation WITHER_TEXTURES;
    
    public RenderShulkerWither(final RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelShulkerWither(0.0f), 1.0f);
        this.addLayer(new LayerShulkerWitherAura(this));
    }
    
    protected ResourceLocation getEntityTexture(final EntityShulkerWither entity) {
        final int i = entity.getInvulTime();
        return (i > 0 && (i > 80 || i / 5 % 2 != 1)) ? RenderShulkerWither.INVULNERABLE_WITHER_TEXTURES : RenderShulkerWither.WITHER_TEXTURES;
    }
    
    protected void preRenderCallback(final EntityShulkerWither entitylivingbaseIn, final float partialTickTime) {
        float f = 1.5f;
        final int i = entitylivingbaseIn.getInvulTime();
        if (i > 0) {
            f -= (i - partialTickTime) / 220.0f * 0.5f;
        }
        GlStateManager.scale(f, f, f);
    }
    
    static {
        INVULNERABLE_WITHER_TEXTURES = new ResourceLocation("mmm:textures/model/shulkerwither_invulnerable.png");
        WITHER_TEXTURES = new ResourceLocation("mmm:textures/model/shulker_wither.png");
    }
}
