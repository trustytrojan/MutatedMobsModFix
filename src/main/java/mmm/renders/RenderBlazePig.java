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
public class RenderBlazePig extends RenderLiving<EntityBlazePig>
{
    private static final ResourceLocation PIG_TEXTURES;
    
    public RenderBlazePig(final RenderManager p_i47198_1_) {
        super(p_i47198_1_, new ModelBlazePig(0, 0.5f), 0.7f);
    }
    
    protected ResourceLocation getEntityTexture(final EntityBlazePig entity) {
        return RenderBlazePig.PIG_TEXTURES;
    }
    
    static {
        PIG_TEXTURES = new ResourceLocation("mmm:textures/model/blaze_pig.png");
    }
}
