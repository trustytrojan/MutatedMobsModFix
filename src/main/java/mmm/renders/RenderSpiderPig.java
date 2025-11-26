//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import mmm.models.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderSpiderPig extends RenderLiving<EntitySpiderPig>
{
    private static final ResourceLocation PIG_TEXTURES;
    
    public RenderSpiderPig(final RenderManager p_i47198_1_) {
        super(p_i47198_1_, new ModelSpiderPig(0, 0.0f), 0.7f);
    }
    
    protected ResourceLocation getEntityTexture(final EntitySpiderPig entity) {
        return RenderSpiderPig.PIG_TEXTURES;
    }
    
    static {
        PIG_TEXTURES = new ResourceLocation("mmm:textures/model/spider_pig.png");
    }
}
