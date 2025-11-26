//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.village.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;
import mmm.common.entities.mobs.ai.*;
import com.google.common.base.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import mmm.eventHandler.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.world.storage.loot.*;
import net.minecraft.network.datasync.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;

public class EntityGuardianGolem extends EntityGolem implements IMutant
{
    protected static final DataParameter<Byte> PLAYER_CREATED;
    private static final DataParameter<Integer> TARGET_ENTITY;
    private int homeCheckTimer;
    @Nullable
    Village village;
    private int attackTimer;
    private int holdRoseTick;
    private EntityLivingBase targetedEntity;
    protected float clientSideTailAnimation;
    protected float clientSideTailAnimationO;
    protected float clientSideTailAnimationSpeed;
    protected float clientSideSpikesAnimation;
    protected float clientSideSpikesAnimationO;
    private int clientSideAttackTime;
    private boolean clientSideTouchedGround;
    protected EntityAIWander wander;
    
    public EntityGuardianGolem(final World worldIn) {
        super(worldIn);
        this.setSize(1.4f, 2.7f);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(1, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0, true));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveTowardsTarget((EntityCreature)this, 0.9, 32.0f));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, 0.6, true));
        this.tasks.addTask(4, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0));
        this.tasks.addTask(6, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 0.6));
        this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIDefendVillage8(this));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        this.tasks.addTask(4, (EntityAIBase)new AIGuardianAttack(this));
        this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityLiving.class, 10, false, true, new Predicate<EntityLiving>() {
            public boolean apply(@Nullable final EntityLiving p_apply_1_) {
                return p_apply_1_ != null && IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_) && !(p_apply_1_ instanceof EntityCreeper);
            }
        }));
    }
    
    public void notifyDataManagerChange(final DataParameter<?> key) {
        super.notifyDataManagerChange(key);
        if (EntityGuardianGolem.TARGET_ENTITY.equals(key)) {
            this.clientSideAttackTime = 0;
            this.targetedEntity = null;
        }
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityGuardianGolem.PLAYER_CREATED, (byte)0);
        this.dataManager.register(EntityGuardianGolem.TARGET_ENTITY, 0);
    }
    
    private void setTargetedEntity(final int entityId) {
        this.dataManager.set(EntityGuardianGolem.TARGET_ENTITY, entityId);
    }
    
    public boolean hasTargetedEntity() {
        return (int)this.dataManager.get(EntityGuardianGolem.TARGET_ENTITY) != 0;
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
        final Entity entity = this.world.getEntityByID((int)this.dataManager.get(EntityGuardianGolem.TARGET_ENTITY));
        if (entity instanceof EntityLivingBase) {
            return this.targetedEntity = (EntityLivingBase)entity;
        }
        return null;
    }
    
    protected void updateAITasks() {
        final int homeCheckTimer = this.homeCheckTimer - 1;
        this.homeCheckTimer = homeCheckTimer;
        if (homeCheckTimer <= 0) {
            this.homeCheckTimer = 70 + this.rand.nextInt(50);
            this.village = this.world.getVillageCollection().getNearestVillage(new BlockPos((Entity)this), 32);
            if (this.village == null) {
                this.detachHome();
            }
            else {
                final BlockPos blockpos = this.village.getCenter();
                this.setHomePosAndDistance(blockpos, (int)(this.village.getVillageRadius() * 0.6f));
            }
        }
        super.updateAITasks();
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_GuardianGolem);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
    }
    
    protected int decreaseAirSupply(final int air) {
        return air;
    }
    
    protected void collideWithEntity(final Entity entityIn) {
        if (entityIn instanceof IMob && !(entityIn instanceof EntityCreeper) && this.getRNG().nextInt(20) == 0) {
            this.setAttackTarget((EntityLivingBase)entityIn);
        }
        super.collideWithEntity(entityIn);
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        if (this.holdRoseTick > 0) {
            --this.holdRoseTick;
        }
        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7 && this.rand.nextInt(5) == 0) {
            final int i = MathHelper.floor(this.posX);
            final int j = MathHelper.floor(this.posY - 0.20000000298023224);
            final int k = MathHelper.floor(this.posZ);
            final IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));
            if (iblockstate.getMaterial() != Material.AIR) {
                this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5) * this.width, this.getEntityBoundingBox().minY + 0.1, this.posZ + (this.rand.nextFloat() - 0.5) * this.width, 4.0 * (this.rand.nextFloat() - 0.5), 0.5, (this.rand.nextFloat() - 0.5) * 4.0, new int[] { Block.getStateId(iblockstate) });
            }
        }
        if (this.hasTargetedEntity()) {
            if (this.clientSideAttackTime < this.getAttackDuration()) {
                ++this.clientSideAttackTime;
            }
            final EntityLivingBase entitylivingbase = this.getTargetedEntity();
            if (entitylivingbase != null) {
                this.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 90.0f, 90.0f);
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
                    this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + d6 * d10, this.posY + d7 * d10 + this.getEyeHeight(), this.posZ + d8 * d10, 0.0, 0.0, 0.0, new int[0]);
                }
            }
        }
        if (this.hasTargetedEntity()) {
            this.rotationYaw = this.rotationYawHead;
        }
    }
    
    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        if (!source.isMagicDamage() && source.getImmediateSource() instanceof EntityLivingBase) {
            final EntityLivingBase entitylivingbase = (EntityLivingBase)source.getImmediateSource();
            if (!source.isExplosion()) {
                entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage((Entity)this), this.lastDamage * 2.0f);
            }
        }
        return super.attackEntityFrom(source, amount);
    }
    
    public boolean canAttackClass(final Class<? extends EntityLivingBase> cls) {
        return (!this.isPlayerCreated() || !EntityPlayer.class.isAssignableFrom(cls)) && cls != EntityCreeper.class && super.canAttackClass(cls);
    }
    
    public static void registerFixesIronGolem(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityGuardianGolem.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("PlayerCreated", this.isPlayerCreated());
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setPlayerCreated(compound.getBoolean("PlayerCreated"));
    }
    
    public boolean attackEntityAsMob(final Entity entityIn) {
        this.attackTimer = 10;
        this.world.setEntityState((Entity)this, (byte)4);
        final boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)(ConfigHandler.ATK_GuardianGolem_MIN + this.rand.nextInt(ConfigHandler.ATK_GuardianGolem_MAX)));
        if (flag) {
            entityIn.motionY += 0.4000000059604645;
            this.applyEnchantments((EntityLivingBase)this, entityIn);
        }
        this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0f, 1.0f);
        return flag;
    }
    
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(final byte id) {
        if (id == 4) {
            this.attackTimer = 10;
            this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0f, 1.0f);
        }
        else if (id == 11) {
            this.holdRoseTick = 400;
        }
        else if (id == 34) {
            this.holdRoseTick = 0;
        }
        else {
            super.handleStatusUpdate(id);
        }
    }
    
    public Village getVillage() {
        return this.village;
    }
    
    public int getAttackDuration() {
        return 80;
    }
    
    @SideOnly(Side.CLIENT)
    public int getAttackTimer() {
        return this.attackTimer;
    }
    
    public void setHoldingRose(final boolean p_70851_1_) {
        if (p_70851_1_) {
            this.holdRoseTick = 400;
            this.world.setEntityState((Entity)this, (byte)11);
        }
        else {
            this.holdRoseTick = 0;
            this.world.setEntityState((Entity)this, (byte)34);
        }
    }
    
    public float getAttackAnimationScale(final float p_175477_1_) {
        return (this.clientSideAttackTime + p_175477_1_) / this.getAttackDuration();
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_IRONGOLEM_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRONGOLEM_DEATH;
    }
    
    protected void playStepSound(final BlockPos pos, final Block blockIn) {
        this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0f, 1.0f);
    }
    
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_IRON_GOLEM;
    }
    
    public int getHoldRoseTick() {
        return this.holdRoseTick;
    }
    
    public boolean isPlayerCreated() {
        return ((byte)this.dataManager.get(EntityGuardianGolem.PLAYER_CREATED) & 0x1) != 0x0;
    }
    
    public void setPlayerCreated(final boolean playerCreated) {
        final byte b0 = (byte)this.dataManager.get(EntityGuardianGolem.PLAYER_CREATED);
        if (playerCreated) {
            this.dataManager.set(EntityGuardianGolem.PLAYER_CREATED, (byte)(b0 | 0x1));
        }
        else {
            this.dataManager.set(EntityGuardianGolem.PLAYER_CREATED, (byte)(b0 & 0xFFFFFFFE));
        }
    }
    
    public void onDeath(final DamageSource cause) {
        if (!this.isPlayerCreated() && this.attackingPlayer != null && this.village != null) {
            this.village.modifyPlayerReputation(this.attackingPlayer.getUniqueID(), -5);
        }
        super.onDeath(cause);
    }
    
    static {
        PLAYER_CREATED = EntityDataManager.createKey(EntityGuardianGolem.class, DataSerializers.BYTE);
        TARGET_ENTITY = EntityDataManager.createKey(EntityGuardianGolem.class, DataSerializers.VARINT);
    }
    
    static class AIGuardianAttack extends EntityAIBase
    {
        private final EntityGuardianGolem guardian;
        private int tickCounter;
        
        public AIGuardianAttack(final EntityGuardianGolem guardian) {
            this.guardian = guardian;
            this.setMutexBits(3);
        }
        
        public boolean shouldExecute() {
            final EntityLivingBase entitylivingbase = this.guardian.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
        }
        
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting() && this.guardian.getDistanceSq((Entity)this.guardian.getAttackTarget()) > 9.0;
        }
        
        public void startExecuting() {
            this.tickCounter = -10;
            this.guardian.getNavigator().clearPath();
            this.guardian.getLookHelper().setLookPositionWithEntity((Entity)this.guardian.getAttackTarget(), 90.0f, 90.0f);
            this.guardian.isAirBorne = true;
        }
        
        public void resetTask() {
            this.guardian.setTargetedEntity(0);
            this.guardian.setAttackTarget((EntityLivingBase)null);
        }
        
        public void updateTask() {
            final EntityLivingBase entitylivingbase = this.guardian.getAttackTarget();
            this.guardian.getNavigator().clearPath();
            this.guardian.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 90.0f, 90.0f);
            if (!this.guardian.canEntityBeSeen((Entity)entitylivingbase)) {
                this.guardian.setAttackTarget((EntityLivingBase)null);
            }
            else {
                ++this.tickCounter;
                if (this.tickCounter == 0) {
                    this.guardian.setTargetedEntity(this.guardian.getAttackTarget().getEntityId());
                    this.guardian.world.setEntityState((Entity)this.guardian, (byte)21);
                }
                else if (this.tickCounter >= this.guardian.getAttackDuration()) {
                    float f = 1.0f;
                    if (this.guardian.world.getDifficulty() == EnumDifficulty.HARD) {
                        f += 2.0f;
                    }
                    entitylivingbase.attackEntityFrom(DamageSource.causeIndirectMagicDamage((Entity)this.guardian, (Entity)this.guardian), f);
                    entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this.guardian), (float)this.guardian.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                    this.guardian.setAttackTarget((EntityLivingBase)null);
                }
                super.updateTask();
            }
        }
    }
    
    static class GuardianTargetSelector implements Predicate<EntityLivingBase>
    {
        private final EntityGuardianGolem parentEntity;
        
        public GuardianTargetSelector(final EntityGuardianGolem guardian) {
            this.parentEntity = guardian;
        }
        
        public boolean apply(@Nullable final EntityLivingBase p_apply_1_) {
            return p_apply_1_ instanceof EntityMob && p_apply_1_.getDistanceSq((Entity)this.parentEntity) > 9.0;
        }
    }
}
