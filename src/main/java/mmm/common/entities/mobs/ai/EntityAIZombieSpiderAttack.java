//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs.ai;

import net.minecraft.entity.ai.*;
import mmm.common.entities.mobs.*;
import net.minecraft.entity.*;

public class EntityAIZombieSpiderAttack extends EntityAIAttackMelee
{
    private final EntityZombieSpider zombie;
    private int raiseArmTicks;
    
    public EntityAIZombieSpiderAttack(final EntityZombieSpider zombieIn, final double speedIn, final boolean longMemoryIn) {
        super(zombieIn, speedIn, longMemoryIn);
        this.zombie = zombieIn;
    }
    
    public void startExecuting() {
        super.startExecuting();
        this.raiseArmTicks = 0;
    }
    
    public void resetTask() {
        super.resetTask();
        this.zombie.setArmsRaised(false);
    }
    
    public void updateTask() {
        super.updateTask();
        ++this.raiseArmTicks;
        if (this.raiseArmTicks >= 5 && this.attackTick < 10) {
            this.zombie.setArmsRaised(true);
        }
        else {
            this.zombie.setArmsRaised(false);
        }
    }
}
