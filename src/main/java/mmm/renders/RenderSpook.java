//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import mmm.common.entities.mobs.boss.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import mmm.models.*;
import mmm.renders.layers.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

@SideOnly(Side.CLIENT)
public class RenderSpook extends RenderBiped<EntitySpook>
{
    private static final ResourceLocation SPOOK_NORMAL;
    private static final ResourceLocation SPOOK_ATK;
    
    public RenderSpook(final RenderManager renderManagerIn) {
        super(renderManagerIn, (ModelBiped)new ModelSpook(), 0.5f);
        this.addLayer((LayerRenderer)new LayerSpookArmor(this));
        this.addLayer((LayerRenderer)new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new ModelSpook(0.5f, true);
                this.modelArmor = new ModelSpook(1.0f, true);
            }
        });
    }
    
    protected ResourceLocation getEntityTexture(final EntitySpook entity) {
        return entity.isSwingInProgress ? RenderSpook.SPOOK_ATK : RenderSpook.SPOOK_NORMAL;
    }
    
    static {
        SPOOK_NORMAL = new ResourceLocation("mmm:textures/model/spook.png");
        SPOOK_ATK = new ResourceLocation("mmm:textures/model/spook_attacking.png");
    }
}
