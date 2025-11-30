//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import mmm.models.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

@SideOnly(Side.CLIENT)
public class RenderSpiderZombie extends RenderBiped<EntityZombieSpider>
{
    private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE;
    
    public RenderSpiderZombie(final RenderManager renderManagerIn) {
        super(renderManagerIn, (ModelBiped)new ModelSpiderZombie(), 0.5f);
        this.addLayer(new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new ModelSpiderZombie(0.5f, true);
                this.modelArmor = new ModelSpiderZombie(1.0f, true);
            }
        });
    }
    
    protected ResourceLocation getEntityTexture(final EntityZombieSpider entity) {
        return RenderSpiderZombie.ZOMBIE_PIGMAN_TEXTURE;
    }
    
    static {
        ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("mmm:textures/model/zombie_spider.png");
    }
}
