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
public class RenderZombieSpiderPigman extends RenderBiped<EntityZombieSpiderPigman>
{
    private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE;
    
    public RenderZombieSpiderPigman(final RenderManager renderManagerIn) {
        super(renderManagerIn, (ModelBiped)new ModelSpiderPigman(), 0.5f);
        this.addLayer(new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new ModelSpiderPigman(0.5f, true);
                this.modelArmor = new ModelSpiderPigman(1.0f, true);
            }
        });
    }
    
    protected ResourceLocation getEntityTexture(final EntityZombieSpiderPigman entity) {
        return RenderZombieSpiderPigman.ZOMBIE_PIGMAN_TEXTURE;
    }
    
    static {
        ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("mmm:textures/model/zombiespider_pigman.png");
    }
}
