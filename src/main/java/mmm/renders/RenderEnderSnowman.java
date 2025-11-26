//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import mmm.models.*;
import net.minecraft.client.model.*;
import mmm.renders.layers.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderEnderSnowman extends RenderLiving<EntityEnderSnowman>
{
    private static final ResourceLocation SNOW_MAN_TEXTURES;
    
    public RenderEnderSnowman(final RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelEnderSnowman(), 0.5f);
        this.addLayer((LayerRenderer)new LayerEnderSnowmanHead(this));
    }
    
    protected ResourceLocation getEntityTexture(final EntityEnderSnowman entity) {
        return RenderEnderSnowman.SNOW_MAN_TEXTURES;
    }
    
    public ModelEnderSnowman getMainModel() {
        return (ModelEnderSnowman)super.getMainModel();
    }
    
    static {
        SNOW_MAN_TEXTURES = new ResourceLocation("mmm:textures/model/ender_snowman.png");
    }
}
