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
public class RenderBlazeSnowman extends RenderLiving<EntityBlazeSnowman>
{
    private static final ResourceLocation SNOW_MAN_TEXTURES;
    
    public RenderBlazeSnowman(final RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelBlazeSnowman(), 0.5f);
        this.addLayer(new LayerBlazeSnowmanHead(this));
    }
    
    protected ResourceLocation getEntityTexture(final EntityBlazeSnowman entity) {
        return RenderBlazeSnowman.SNOW_MAN_TEXTURES;
    }
    
    public ModelBlazeSnowman getMainModel() {
        return (ModelBlazeSnowman)super.getMainModel();
    }
    
    static {
        SNOW_MAN_TEXTURES = new ResourceLocation("mmm:textures/model/blazing_snowman.png");
    }
}
