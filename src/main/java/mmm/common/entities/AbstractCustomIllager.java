//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities;

import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.network.datasync.*;

public abstract class AbstractCustomIllager extends EntityMob
{
    protected static final DataParameter<Byte> AGGRESSIVE;
    
    public AbstractCustomIllager(final World p_i47509_1_) {
        super(p_i47509_1_);
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(AbstractCustomIllager.AGGRESSIVE, (byte)0);
    }
    
    @SideOnly(Side.CLIENT)
    protected boolean isAggressive(final int mask) {
        final int i = (byte)this.dataManager.get(AbstractCustomIllager.AGGRESSIVE);
        return (i & mask) != 0x0;
    }
    
    protected void setAggressive(final int mask, final boolean value) {
        int i = (byte)this.dataManager.get(AbstractCustomIllager.AGGRESSIVE);
        if (value) {
            i |= mask;
        }
        else {
            i &= ~mask;
        }
        this.dataManager.set(AbstractCustomIllager.AGGRESSIVE, (byte)(i & 0xFF));
    }
    
    public CreatureType getCreatureType() {
        return CreatureType.CREEPER;
    }
    
    @SideOnly(Side.CLIENT)
    public IllagerArmPose getArmPose() {
        return IllagerArmPose.CROSSED;
    }
    
    static {
        AGGRESSIVE = EntityDataManager.createKey(AbstractCustomIllager.class, DataSerializers.BYTE);
    }
    
    @SideOnly(Side.CLIENT)
    public enum IllagerArmPose
    {
        CROSSED, 
        ATTACKING, 
        SPELLCASTING, 
        BOW_AND_ARROW;
    }
}
