//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import mmm.models.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

@SideOnly(Side.CLIENT)
public class RenderBlazeSkeleton extends RenderBiped<EntityBlazeSkeleton>
{
    private static final ResourceLocation texture;
    
    public RenderBlazeSkeleton(final RenderManager renderManagerIn) {
        super(renderManagerIn, (ModelBiped)new ModelBlazeSkeleton(), 0.5f);
        this.addLayer((LayerRenderer)new LayerHeldItem((RenderLivingBase)this));
        this.addLayer((LayerRenderer)new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new ModelBlazeSkeleton(0.5f, true);
                this.modelArmor = new ModelBlazeSkeleton(1.0f, true);
            }
        });
    }
    
    protected ResourceLocation getEntityTexture(final EntityBlazeSkeleton entity) {
        return RenderBlazeSkeleton.texture;
    }
    
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.09375f, 0.1875f, 0.0f);
    }
    
    static {
        texture = new ResourceLocation("mmm:textures/model/blaze_skeleton.png");
    }
}
