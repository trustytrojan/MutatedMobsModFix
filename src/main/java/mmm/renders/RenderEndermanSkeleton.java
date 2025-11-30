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
public class RenderEndermanSkeleton extends RenderBiped<EntityEnderManSkeleton>
{
    private static final ResourceLocation texture;
    
    public RenderEndermanSkeleton(final RenderManager renderManagerIn) {
        super(renderManagerIn, (ModelBiped)new ModelEndermanSkeleton(), 0.5f);
        this.addLayer(new LayerHeldItem((RenderLivingBase)this));
        this.addLayer(new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new ModelEndermanSkeleton(0.5f, true);
                this.modelArmor = new ModelEndermanSkeleton(1.0f, true);
            }
        });
    }
    
    protected ResourceLocation getEntityTexture(final EntityEnderManSkeleton entity) {
        return RenderEndermanSkeleton.texture;
    }
    
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.09375f, 0.1875f, 0.0f);
    }
    
    static {
        texture = new ResourceLocation("mmm:textures/model/ender_skeleton.png");
    }
}
