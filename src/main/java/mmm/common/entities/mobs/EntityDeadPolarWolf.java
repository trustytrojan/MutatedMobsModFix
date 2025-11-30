//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.*;
import mmm.eventHandler.*;
import net.minecraft.util.*;
import javax.annotation.*;
import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import java.util.*;

public class EntityDeadPolarWolf extends EntityMob implements IMutant
{
    public EntityDeadPolarWolf(final World worldIn) {
        super(worldIn);
        this.setSize(1.3f, 1.4f);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(4, new EntityAILeapAtTarget((EntityLiving)this, 0.4f));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAILookIdle((EntityLiving)this));
        this.applyEntityAI();
    }
    
    protected void applyEntityAI() {
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityPolarWolf.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityTameable.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityGolem.class, true));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_DeadPolarWolf);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(65.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3300000041723251);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ConfigHandler.ATK_DeadPolarWolf);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0);
    }
    
    public void fall(final float distance, final float damageMultiplier) {
    }
    
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_POLAR_BEAR_AMBIENT;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_POLAR_BEAR_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_POLAR_BEAR_DEATH;
    }
    
    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_POLAR_BEAR_DEATH;
    }
    
    protected float getWaterSlowDown() {
        return 0.98f;
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        if (this.world.rand.nextInt(20) == 0) {
            final EntitySkeleton entityskeleton = new EntitySkeleton(this.world);
            entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityskeleton.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity(entityskeleton);
            entityskeleton.startRiding(this);
        }
        if (this.world.rand.nextInt(40) == 0) {
            final EntityCreeper entityskeleton2 = new EntityCreeper(this.world);
            entityskeleton2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityskeleton2.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity(entityskeleton2);
            entityskeleton2.startRiding(this);
        }
        if (this.world.rand.nextInt(45) == 0) {
            final EntityZombie entityskeleton3 = new EntityZombie(this.world);
            entityskeleton3.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityskeleton3.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity(entityskeleton3);
            entityskeleton3.startRiding(this);
        }
        if (this.world.rand.nextInt(60) == 0) {
            final EntityHusk entityskeleton4 = new EntityHusk(this.world);
            entityskeleton4.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityskeleton4.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity(entityskeleton4);
            entityskeleton4.startRiding(this);
        }
        if (this.world.rand.nextInt(75) == 0) {
            final EntityWitch entityskeleton5 = new EntityWitch(this.world);
            entityskeleton5.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityskeleton5.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity(entityskeleton5);
            entityskeleton5.startRiding(this);
        }
        if (livingdata == null) {
            livingdata = (IEntityLivingData)new EntityPolarWolf.GroupData();
            if (this.world.getDifficulty() == EnumDifficulty.HARD && this.world.rand.nextFloat() < 0.1f * difficulty.getClampedAdditionalDifficulty()) {
                ((EntityPolarWolf.GroupData)livingdata).setRandomEffect(this.world.rand);
            }
        }
        if (livingdata instanceof EntityPolarWolf.GroupData) {
            final Potion potion = ((EntityPolarWolf.GroupData)livingdata).effect;
            if (potion != null) {
                this.addPotionEffect(new PotionEffect(potion, Integer.MAX_VALUE));
            }
        }
        return livingdata;
    }
    
    public boolean attackEntityAsMob(final Entity entityIn) {
        final boolean flag = super.attackEntityAsMob(entityIn);
        ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 150));
        if (this.onGround && this.rand.nextInt(10) == 0) {
            final double d0 = entityIn.posX - this.posX;
            final double d2 = entityIn.posZ - this.posZ;
            final float f2 = MathHelper.sqrt(d0 * d0 + d2 * d2);
            this.motionX = d0 / f2 * 0.5 * 1.200000011920929 + this.motionX * 0.6000000029802323;
            this.motionZ = d2 / f2 * 0.5 * 1.200000011920929 + this.motionZ * 0.6000000029802323;
            this.motionY = 0.4000000059604645;
        }
        return flag;
    }
    
    public static class GroupData implements IEntityLivingData
    {
        public Potion effect;
        
        public void setRandomEffect(final Random rand) {
            final int i = rand.nextInt(5);
            if (i <= 1) {
                this.effect = MobEffects.SPEED;
            }
            else if (i <= 2) {
                this.effect = MobEffects.STRENGTH;
            }
            else if (i <= 3) {
                this.effect = MobEffects.REGENERATION;
            }
            else if (i <= 4) {
                this.effect = MobEffects.INVISIBILITY;
            }
        }
    }
}
