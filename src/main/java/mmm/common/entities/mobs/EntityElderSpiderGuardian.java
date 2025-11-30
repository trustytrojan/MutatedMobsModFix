//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import mmm.eventHandler.*;
import net.minecraft.util.datafix.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.network.datasync.*;
import net.minecraft.world.*;
import com.google.common.base.*;

public class EntityElderSpiderGuardian extends EntityMob implements IMutant
{
    public static final net.minecraft.world.biome.Biome[] SPAWN_BIOMES = new net.minecraft.world.biome.Biome[] { Biomes.OCEAN, Biomes.RIVER, Biomes.DEEP_OCEAN, Biomes.FROZEN_RIVER, Biomes.BEACH };
    private static final DataParameter<Boolean> CLIMBING = EntityDataManager.createKey(EntityElderSpiderGuardian.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> MOVING = EntityDataManager.createKey(EntityElderSpiderGuardian.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> TARGET_ENTITY = EntityDataManager.createKey(EntityElderSpiderGuardian.class, DataSerializers.VARINT);
    protected float clientSideTailAnimation;
    protected float clientSideTailAnimationO;
    protected float clientSideTailAnimationSpeed;
    protected float clientSideSpikesAnimation;
    protected float clientSideSpikesAnimationO;
    private EntityLivingBase targetedEntity;
    private int clientSideAttackTime;
    private boolean clientSideTouchedGround;
    
    public EntityElderSpiderGuardian(final World worldIn) {
        super(worldIn);
        this.experienceValue = 10;
        this.setSize(2.0f, 2.0f);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(8, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAIWatchClosest((EntityLiving)this, EntityElderSpiderGuardian.class, 12.0f, 0.01f));
        this.tasks.addTask(9, new EntityAILookIdle((EntityLiving)this));
        this.tasks.addTask(0, new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater((EntityCreature)this, 1.0, 0.0f));
        this.tasks.addTask(1, new EntityAIAttackMelee((EntityCreature)this, 1.0, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget((EntityCreature)this, EntityPlayer.class, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget((EntityCreature)this, EntityGolem.class, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget((EntityCreature)this, EntityVillager.class, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget((EntityCreature)this, EntitySquid.class, true));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ConfigHandler.ATK_ElderSpiderGuardian);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(160.0);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_ElderSpiderGuardian);
    }
    
    public static void registerFixesGuardian(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityElderSpiderGuardian.class);
    }
    
    protected float getWaterSlowDown() {
        return 0.98f;
    }
    
    protected PathNavigate createNavigator(final World worldIn) {
        return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(MOVING, false);
        this.dataManager.register(TARGET_ENTITY, 0);
        this.dataManager.register(CLIMBING, false);
    }
    
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }
    
    public double getMountedYOffset() {
        return this.height * 0.5f;
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote) {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }
    
    public void setBesideClimbableBlock(final boolean climbing) {
        this.dataManager.set(EntityElderSpiderGuardian.CLIMBING, climbing);
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    public boolean isPotionApplicable(final PotionEffect potioneffectIn) {
        return potioneffectIn.getPotion() != MobEffects.POISON && super.isPotionApplicable(potioneffectIn);
    }
    
    public void setInWeb() {
    }
    
    public boolean isBesideClimbableBlock() {
        return (boolean)this.dataManager.get(EntityElderSpiderGuardian.CLIMBING);
    }
    
    public boolean isMoving() {
        return (boolean)this.dataManager.get(EntityElderSpiderGuardian.MOVING);
    }
    
    private void setMoving(final boolean moving) {
        this.dataManager.set(EntityElderSpiderGuardian.MOVING, moving);
    }
    
    public int getAttackDuration() {
        return 80;
    }
    
    private void setTargetedEntity(final int entityId) {
        this.dataManager.set(EntityElderSpiderGuardian.TARGET_ENTITY, entityId);
    }
    
    public boolean hasTargetedEntity() {
        return (int)this.dataManager.get(EntityElderSpiderGuardian.TARGET_ENTITY) != 0;
    }
    
    @Nullable
    public EntityLivingBase getTargetedEntity() {
        if (!this.hasTargetedEntity()) {
            return null;
        }
        if (!this.world.isRemote) {
            return this.getAttackTarget();
        }
        if (this.targetedEntity != null) {
            return this.targetedEntity;
        }
        final Entity entity = this.world.getEntityByID((int)this.dataManager.get(EntityElderSpiderGuardian.TARGET_ENTITY));
        if (entity instanceof EntityLivingBase) {
            return this.targetedEntity = (EntityLivingBase)entity;
        }
        return null;
    }
    
    public void notifyDataManagerChange(final DataParameter<?> key) {
        super.notifyDataManagerChange(key);
        if (EntityElderSpiderGuardian.TARGET_ENTITY.equals(key)) {
            this.clientSideAttackTime = 0;
            this.targetedEntity = null;
        }
    }
    
    public int getTalkInterval() {
        return 160;
    }
    
    protected SoundEvent getAmbientSound() {
        return this.isInWater() ? SoundEvents.ENTITY_SPIDER_AMBIENT : SoundEvents.ENTITY_GUARDIAN_AMBIENT_LAND;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return this.isInWater() ? SoundEvents.ENTITY_GUARDIAN_HURT : SoundEvents.ENTITY_GUARDIAN_HURT_LAND;
    }
    
    protected SoundEvent getDeathSound() {
        return this.isInWater() ? SoundEvents.ENTITY_GUARDIAN_DEATH : SoundEvents.ENTITY_GUARDIAN_DEATH_LAND;
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public float getEyeHeight() {
        return this.height * 0.5f;
    }
    
    public float getBlockPathWeight(final BlockPos pos) {
        return (this.world.getBlockState(pos).getMaterial() == Material.WATER) ? (10.0f + this.world.getLightBrightness(pos) - 0.5f) : super.getBlockPathWeight(pos);
    }
    
    public void onLivingUpdate() {
        this.clientSideSpikesAnimationO = this.clientSideSpikesAnimation;
        if (!this.isInWater()) {
            this.clientSideSpikesAnimation = this.rand.nextFloat();
        }
        else if (this.isMoving()) {
            this.clientSideSpikesAnimation += (0.0f - this.clientSideSpikesAnimation) * 0.25f;
        }
        else {
            this.clientSideSpikesAnimation += (1.0f - this.clientSideSpikesAnimation) * 0.06f;
        }
        if (this.isMoving() && this.isInWater()) {
            final Vec3d vec3d = this.getLook(0.0f);
            for (int i = 0; i < 2; ++i) {
                this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX + (this.rand.nextDouble() - 0.5) * this.width - vec3d.x * 1.5, this.posY + this.rand.nextDouble() * this.height - vec3d.y * 1.5, this.posZ + (this.rand.nextDouble() - 0.5) * this.width - vec3d.z * 1.5, 0.0, 0.0, 0.0, new int[0]);
            }
        }
        if (this.hasTargetedEntity()) {
            if (this.clientSideAttackTime < this.getAttackDuration()) {
                ++this.clientSideAttackTime;
            }
            final EntityLivingBase entitylivingbase = this.getTargetedEntity();
            if (entitylivingbase != null) {
                this.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0f, 90.0f);
                this.getLookHelper().onUpdateLook();
                final double d5 = this.getAttackAnimationScale(0.0f);
                double d6 = entitylivingbase.posX - this.posX;
                double d7 = entitylivingbase.posY + entitylivingbase.height * 0.5f - (this.posY + this.getEyeHeight());
                double d8 = entitylivingbase.posZ - this.posZ;
                final double d9 = Math.sqrt(d6 * d6 + d7 * d7 + d8 * d8);
                d6 /= d9;
                d7 /= d9;
                d8 /= d9;
                double d10 = this.rand.nextDouble();
                while (d10 < d9) {
                    d10 += 1.8 - d5 + this.rand.nextDouble() * (1.7 - d5);
                    this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX + d6 * d10, this.posY + d7 * d10 + this.getEyeHeight(), this.posZ + d8 * d10, 0.0, 0.0, 0.0, new int[0]);
                }
            }
        }
        if (this.hasTargetedEntity()) {
            this.rotationYaw = this.rotationYawHead;
        }
        super.onLivingUpdate();
    }
    
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_GUARDIAN_FLOP;
    }
    
    @SideOnly(Side.CLIENT)
    public float getSpikesAnimation(final float p_175469_1_) {
        return this.clientSideSpikesAnimationO + (this.clientSideSpikesAnimation - this.clientSideSpikesAnimationO) * p_175469_1_;
    }
    
    public float getAttackAnimationScale(final float p_175477_1_) {
        return (this.clientSideAttackTime + p_175477_1_) / this.getAttackDuration();
    }
    
    protected boolean isValidLightLevel() {
        return true;
    }
    
    public boolean isNotColliding() {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty();
    }
    
    public boolean getCanSpawnHere() {
        return (this.rand.nextInt(20) == 0 || !this.world.canBlockSeeSky(new BlockPos(this))) && super.getCanSpawnHere();
    }
    
    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        if (!source.isMagicDamage() && source.getImmediateSource() instanceof EntityLivingBase) {
            final EntityLivingBase entitylivingbase = (EntityLivingBase)source.getImmediateSource();
            if (!source.isExplosion()) {
                entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage(this), this.lastDamage * 1.25f);
            }
        }
        return super.attackEntityFrom(source, amount);
    }
    
    protected int decreaseAirSupply(final int air) {
        return air;
    }
    
    public boolean attackEntityAsMob(final Entity par1Entity) {
        par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 150));
        ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 150));
        ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 150));
        this.SetWeb(par1Entity);
        return true;
    }
    
    public void SetWeb(final Entity target) {
        if (!this.world.isRemote) {
            int i = MathHelper.floor(target.posX);
            int j = MathHelper.floor(target.posY);
            int k = MathHelper.floor(target.posZ);
            for (int l = 0; l < 3; ++l) {
                i = MathHelper.floor(this.posX + (l % 2 * 2 - 1) * 0.25f);
                j = MathHelper.floor(this.posY);
                k = MathHelper.floor(this.posZ + (l / 2 % 2 * 2 - 1) * 0.25f);
                final BlockPos blockpos = new BlockPos(i, j, k);
                if (target.world.getBlockState(blockpos).getMaterial() == Material.AIR && Blocks.WEB.canPlaceBlockAt(this.world, blockpos)) {
                    target.world.setBlockState(blockpos, Blocks.WEB.getDefaultState());
                }
            }
        }
    }
    
    static class AIGuardianAttack extends EntityAIBase
    {
        private final EntityElderSpiderGuardian guardian;
        private int tickCounter;
        
        public AIGuardianAttack(final EntityElderSpiderGuardian guardian) {
            this.guardian = guardian;
            this.setMutexBits(3);
        }
        
        public boolean shouldExecute() {
            final EntityLivingBase entitylivingbase = this.guardian.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
        }
        
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting() && this.guardian.getDistanceSq(this.guardian.getAttackTarget()) > 9.0;
        }
        
        public void startExecuting() {
            this.tickCounter = -10;
            this.guardian.getNavigator().clearPath();
            this.guardian.getLookHelper().setLookPositionWithEntity(this.guardian.getAttackTarget(), 90.0f, 90.0f);
            this.guardian.isAirBorne = true;
        }
        
        public void resetTask() {
            this.guardian.setTargetedEntity(0);
            this.guardian.setAttackTarget((EntityLivingBase)null);
        }
        
        public void updateTask() {
            final EntityLivingBase entitylivingbase = this.guardian.getAttackTarget();
            this.guardian.getNavigator().clearPath();
            this.guardian.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0f, 90.0f);
            if (!this.guardian.canEntityBeSeen(entitylivingbase)) {
                this.guardian.setAttackTarget((EntityLivingBase)null);
            }
            else {
                ++this.tickCounter;
                if (this.tickCounter == 0) {
                    this.guardian.setTargetedEntity(this.guardian.getAttackTarget().getEntityId());
                    this.guardian.world.setEntityState(this.guardian, (byte)21);
                }
                else if (this.tickCounter >= this.guardian.getAttackDuration()) {
                    float f = 2.0f;
                    if (this.guardian.world.getDifficulty() == EnumDifficulty.HARD) {
                        f += 4.0f;
                    }
                    entitylivingbase.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.guardian, this.guardian), f);
                    entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this.guardian), (float)this.guardian.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                    this.guardian.setAttackTarget((EntityLivingBase)null);
                    entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 150));
                    entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 150));
                    entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 150));
                }
                super.updateTask();
            }
        }
    }
    
    static class GuardianTargetSelector implements Predicate<EntityLivingBase>
    {
        private final EntityElderSpiderGuardian parentEntity;
        
        public GuardianTargetSelector(final EntityElderSpiderGuardian guardian) {
            this.parentEntity = guardian;
        }
        
        public boolean apply(@Nullable final EntityLivingBase p_apply_1_) {
            return (p_apply_1_ instanceof EntityPlayer || p_apply_1_ instanceof EntitySquid || p_apply_1_ instanceof EntityVillager) && p_apply_1_.getDistanceSq(this.parentEntity) > 9.0;
        }
    }
}
