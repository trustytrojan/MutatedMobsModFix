//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs.ai;

import net.minecraft.entity.ai.*;
import mmm.common.entities.mobs.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

public class EntityAIDestroyBlock extends EntityAIMoveToBlock
{
    private final EntityZombieSpiderPigman villager;
    private boolean hasFarmItem;
    private boolean wantsToReapStuff;
    private int currentTask;
    
    public EntityAIDestroyBlock(final EntityZombieSpiderPigman villagerIn, final double speedIn) {
        super((EntityCreature)villagerIn, speedIn, 16);
        this.villager = villagerIn;
    }
    
    public boolean shouldExecute() {
        if (this.runDelay <= 0) {
            if (!this.villager.world.getGameRules().getBoolean("mobGriefing")) {
                return false;
            }
            this.currentTask = -1;
        }
        return super.shouldExecute();
    }
    
    public boolean shouldContinueExecuting() {
        return this.currentTask >= 0 && super.shouldContinueExecuting();
    }
    
    public void updateTask() {
        super.updateTask();
        this.villager.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5, (double)(this.destinationBlock.getY() + 1), this.destinationBlock.getZ() + 0.5, 10.0f, (float)this.villager.getVerticalFaceSpeed());
        if (this.getIsAboveDestination()) {
            final World world = this.villager.world;
            final BlockPos blockpos = this.destinationBlock.up();
            final IBlockState iblockstate = world.getBlockState(blockpos);
            final Block block = iblockstate.getBlock();
            if (this.currentTask == 0 && block instanceof BlockYellowFlower) {
                world.destroyBlock(blockpos, true);
            }
            else if (this.currentTask != 1 || iblockstate.getMaterial() == Material.AIR) {}
        }
    }
    
    protected boolean shouldMoveTo(final World worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos).getBlock();
        if (block == Blocks.YELLOW_FLOWER) {
            pos = pos.up();
            final IBlockState iblockstate = worldIn.getBlockState(pos);
            block = iblockstate.getBlock();
            if (block instanceof BlockYellowFlower && (this.currentTask == 0 || this.currentTask < 0)) {
                this.currentTask = 0;
                return true;
            }
            if (iblockstate.getMaterial() == Material.AIR && (this.currentTask == 1 || this.currentTask < 0)) {
                this.currentTask = 1;
                return true;
            }
        }
        return false;
    }
}
