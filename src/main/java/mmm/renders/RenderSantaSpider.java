//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.boss.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import mmm.models.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

@SideOnly(Side.CLIENT)
public class RenderSantaSpider extends RenderBiped<EntitySantaSpider>
{
    private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE;
    
    public RenderSantaSpider(final RenderManager renderManagerIn) {
        super(renderManagerIn, (ModelBiped)new ModelSpiderSanta(), 0.5f);
    }
    
    protected ResourceLocation getEntityTexture(final EntitySantaSpider entity) {
        return RenderSantaSpider.ZOMBIE_PIGMAN_TEXTURE;
    }
    
    static {
        ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("mmm:textures/model/santa_spider.png");
    }
}
