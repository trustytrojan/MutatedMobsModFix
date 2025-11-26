//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs.ai;

import net.minecraft.entity.ai.*;
import mmm.common.entities.mobs.*;
import net.minecraft.entity.*;

public class EntityAIEndermanCreeperSwell extends EntityAIBase
{
    EntityEndermanCreeper swellingCreeper;
    EntityLivingBase creeperAttackTarget;
    
    public EntityAIEndermanCreeperSwell(final EntityEndermanCreeper EntityEndermanCreeperIn) {
        this.swellingCreeper = EntityEndermanCreeperIn;
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute() {
        final EntityLivingBase entitylivingbase = this.swellingCreeper.getAttackTarget();
        return this.swellingCreeper.getCreeperState() > 0 || (entitylivingbase != null && this.swellingCreeper.getDistanceSq((Entity)entitylivingbase) < 9.0);
    }
    
    public void startExecuting() {
        this.swellingCreeper.getNavigator().clearPath();
        this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
    }
    
    public void resetTask() {
        this.creeperAttackTarget = null;
    }
    
    public void updateTask() {
        if (this.creeperAttackTarget == null) {
            this.swellingCreeper.setCreeperState(-1);
        }
        else if (this.swellingCreeper.getDistanceSq((Entity)this.creeperAttackTarget) > 49.0) {
            this.swellingCreeper.setCreeperState(-1);
        }
        else if (!this.swellingCreeper.getEntitySenses().canSee((Entity)this.creeperAttackTarget)) {
            this.swellingCreeper.setCreeperState(-1);
        }
        else {
            this.swellingCreeper.setCreeperState(1);
        }
    }
}
