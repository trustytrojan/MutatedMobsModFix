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
public class RenderSpiderHusk extends RenderBiped<EntityHuskSpider>
{
    private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE;
    
    public RenderSpiderHusk(final RenderManager renderManagerIn) {
        super(renderManagerIn, (ModelBiped)new ModelSpiderHusk(), 0.5f);
        this.addLayer((LayerRenderer)new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new ModelSpiderHusk(0.5f, true);
                this.modelArmor = new ModelSpiderHusk(1.0f, true);
            }
        });
    }
    
    protected ResourceLocation getEntityTexture(final EntityHuskSpider entity) {
        return RenderSpiderHusk.ZOMBIE_PIGMAN_TEXTURE;
    }
    
    static {
        ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("mmm:textures/model/zombiehusk_spider.png");
    }
}
