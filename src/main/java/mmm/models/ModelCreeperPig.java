//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.models;

import net.minecraft.client.model.*;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class ModelCreeperPig extends ModelQuadruped
{
    public ModelCreeperPig() {
        this(0.0f);
    }
    
    public ModelCreeperPig(final float scale) {
        super(6, scale);
        this.head.setTextureOffset(16, 16).addBox(-2.0f, 0.0f, -9.0f, 4, 3, 1, scale);
        this.childYOffset = 4.0f;
    }
}
