//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import net.minecraft.entity.monster.*;
import mmm.common.entities.mobs.interfaces.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.*;
import mmm.common.entities.projectiles.*;
import net.minecraft.entity.player.*;
import mmm.eventHandler.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.network.datasync.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import java.util.*;
import net.minecraft.util.math.*;

public class EntitySquidGhast extends EntityFlying implements IMob, IMutant
{
    private static final DataParameter<Boolean> ATTACKING;
    private int explosionStrength;
    
    public EntitySquidGhast(final World worldIn) {
        super(worldIn);
        this.explosionStrength = 0;
        this.setSize(4.0f, 4.0f);
        this.isImmuneToFire = true;
        this.experienceValue = 15;
        this.moveHelper = new GhastMoveHelper(this);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(5, new AIRandomFly(this));
        this.tasks.addTask(7, new AILookAround(this));
        this.tasks.addTask(7, new AIFireballAttack(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer((EntityLiving)this));
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isAttacking() {
        return (boolean)this.dataManager.get(EntitySquidGhast.ATTACKING);
    }
    
    public void setAttacking(final boolean attacking) {
        this.dataManager.set(EntitySquidGhast.ATTACKING, attacking);
    }
    
    public int getFireballStrength() {
        return this.explosionStrength;
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
    }
    
    private Object getAttachmentFacing() {
        return null;
    }
    
    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        }
        if (source.getImmediateSource() instanceof EntitySquidFireball && source.getTrueSource() instanceof EntityPlayer) {
            super.attackEntityFrom(source, 0.0f);
            return true;
        }
        return super.attackEntityFrom(source, amount);
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntitySquidGhast.ATTACKING, false);
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_SquidGhast);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0);
    }
    
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }
    
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_GHAST_AMBIENT;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_GHAST_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GHAST_DEATH;
    }
    
    protected float getSoundVolume() {
        return 10.0f;
    }
    
    public boolean getCanSpawnHere() {
        return this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }
    
    public int getMaxSpawnedInChunk() {
        return 1;
    }
    
    public static void registerFixesGhast(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntitySquidGhast.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("ExplosionPower", this.explosionStrength);
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("ExplosionPower", 99)) {
            this.explosionStrength = compound.getInteger("ExplosionPower");
        }
    }
    
    public float getEyeHeight() {
        return 2.6f;
    }
    
    static {
        ATTACKING = EntityDataManager.createKey(EntitySquidGhast.class, DataSerializers.BOOLEAN);
    }
    
    static class AIFireballAttack extends EntityAIBase
    {
        private final EntitySquidGhast parentEntity;
        public int attackTimer;
        
        public AIFireballAttack(final EntitySquidGhast ghast) {
            this.parentEntity = ghast;
        }
        
        public boolean shouldExecute() {
            return this.parentEntity.getAttackTarget() != null;
        }
        
        public void startExecuting() {
            this.attackTimer = 0;
        }
        
        public void resetTask() {
            this.parentEntity.setAttacking(false);
        }
        
        public void updateTask() {
            final EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
            final double d0 = 64.0;
            if (entitylivingbase.getDistanceSq((Entity)this.parentEntity) < 4096.0 && this.parentEntity.canEntityBeSeen((Entity)entitylivingbase)) {
                final World world = this.parentEntity.world;
                ++this.attackTimer;
                if (this.attackTimer == 10) {
                    world.playEvent((EntityPlayer)null, 1015, new BlockPos((Entity)this.parentEntity), 0);
                }
                if (this.attackTimer == 20) {
                    final double d2 = 4.0;
                    final Vec3d vec3d = this.parentEntity.getLook(1.0f);
                    final double d3 = entitylivingbase.posX - (this.parentEntity.posX + vec3d.x * 4.0);
                    final double d4 = entitylivingbase.getEntityBoundingBox().minY + entitylivingbase.height / 2.0f - (0.5 + this.parentEntity.posY + this.parentEntity.height / 2.0f);
                    final double d5 = entitylivingbase.posZ - (this.parentEntity.posZ + vec3d.z * 4.0);
                    world.playEvent((EntityPlayer)null, 1016, new BlockPos((Entity)this.parentEntity), 0);
                    final EntitySquidFireball EntitySquidFireball = new EntitySquidFireball(world, (EntityLivingBase)this.parentEntity, d3, d4, d5);
                    EntitySquidFireball.explosionPower = this.parentEntity.getFireballStrength();
                    EntitySquidFireball.posX = this.parentEntity.posX + vec3d.x * 4.0;
                    EntitySquidFireball.posY = this.parentEntity.posY + this.parentEntity.height / 2.0f + 0.5;
                    EntitySquidFireball.posZ = this.parentEntity.posZ + vec3d.z * 4.0;
                    world.spawnEntity((Entity)EntitySquidFireball);
                    this.attackTimer = -40;
                }
            }
            else if (this.attackTimer > 0) {
                --this.attackTimer;
            }
            this.parentEntity.setAttacking(this.attackTimer > 10);
        }
    }
    
    static class AIFakeFireballAttack extends EntityAIBase
    {
        private final EntityIlliusionerGhast parentEntity;
        public int attackTimer;
        
        public AIFakeFireballAttack(final EntityIlliusionerGhast ghast) {
            this.parentEntity = ghast;
        }
        
        public boolean shouldExecute() {
            return this.parentEntity.getAttackTarget() != null;
        }
        
        public void startExecuting() {
            this.attackTimer = 0;
        }
        
        public void resetTask() {
            this.parentEntity.setAttacking(false);
        }
        
        public void updateTask() {
            final EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
            final double d0 = 64.0;
            if (entitylivingbase.getDistanceSq((Entity)this.parentEntity) < 4096.0 && this.parentEntity.canEntityBeSeen((Entity)entitylivingbase)) {
                final World world = this.parentEntity.world;
                ++this.attackTimer;
                if (this.attackTimer == 10) {
                    world.playEvent((EntityPlayer)null, 1015, new BlockPos((Entity)this.parentEntity), 0);
                }
                if (this.attackTimer == 20) {
                    final double d2 = 4.0;
                    final Vec3d vec3d = this.parentEntity.getLook(1.0f);
                    final double d3 = entitylivingbase.posX - (this.parentEntity.posX + vec3d.x * 4.0);
                    final double d4 = entitylivingbase.getEntityBoundingBox().minY + entitylivingbase.height / 2.0f - (0.5 + this.parentEntity.posY + this.parentEntity.height / 2.0f);
                    final double d5 = entitylivingbase.posZ - (this.parentEntity.posZ + vec3d.z * 4.0);
                    world.playEvent((EntityPlayer)null, 1016, new BlockPos((Entity)this.parentEntity), 0);
                    final EntitySquidFireball EntitySquidFireball = new EntitySquidFireball(world, (EntityLivingBase)this.parentEntity, d3, d4, d5);
                    EntitySquidFireball.explosionPower = this.parentEntity.getFireballStrength();
                    EntitySquidFireball.posX = this.parentEntity.posX + vec3d.x * 4.0;
                    EntitySquidFireball.posY = this.parentEntity.posY + this.parentEntity.height / 2.0f + 0.5;
                    EntitySquidFireball.posZ = this.parentEntity.posZ + vec3d.z * 4.0;
                    world.spawnEntity((Entity)EntitySquidFireball);
                    this.attackTimer = -40;
                }
            }
            else if (this.attackTimer > 0) {
                --this.attackTimer;
            }
            this.parentEntity.setAttacking(this.attackTimer > 10);
        }
    }
    
    static class AILookAround extends EntityAIBase
    {
        private final EntitySquidGhast parentEntity;
        
        public AILookAround(final EntitySquidGhast ghast) {
            this.parentEntity = ghast;
            this.setMutexBits(2);
        }
        
        public boolean shouldExecute() {
            return true;
        }
        
        public void updateTask() {
            if (this.parentEntity.getAttackTarget() == null) {
                this.parentEntity.rotationYaw = -(float)MathHelper.atan2(this.parentEntity.motionX, this.parentEntity.motionZ) * 57.295776f;
                this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
            }
            else {
                final EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
                final double d0 = 64.0;
                if (entitylivingbase.getDistanceSq((Entity)this.parentEntity) < 4096.0) {
                    final double d2 = entitylivingbase.posX - this.parentEntity.posX;
                    final double d3 = entitylivingbase.posZ - this.parentEntity.posZ;
                    this.parentEntity.rotationYaw = -(float)MathHelper.atan2(d2, d3) * 57.295776f;
                    this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
                }
            }
        }
    }
    
    static class AIRandomFly extends EntityAIBase
    {
        private final EntitySquidGhast parentEntity;
        
        public AIRandomFly(final EntitySquidGhast ghast) {
            this.parentEntity = ghast;
            this.setMutexBits(1);
        }
        
        public boolean shouldExecute() {
            final EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();
            if (!entitymovehelper.isUpdating()) {
                return true;
            }
            final double d0 = entitymovehelper.getX() - this.parentEntity.posX;
            final double d2 = entitymovehelper.getY() - this.parentEntity.posY;
            final double d3 = entitymovehelper.getZ() - this.parentEntity.posZ;
            final double d4 = d0 * d0 + d2 * d2 + d3 * d3;
            return d4 < 1.0 || d4 > 3600.0;
        }
        
        public boolean shouldContinueExecuting() {
            return false;
        }
        
        public void startExecuting() {
            final Random random = this.parentEntity.getRNG();
            final double d0 = this.parentEntity.posX + (random.nextFloat() * 2.0f - 1.0f) * 16.0f;
            final double d2 = this.parentEntity.posY + (random.nextFloat() * 2.0f - 1.0f) * 16.0f;
            final double d3 = this.parentEntity.posZ + (random.nextFloat() * 2.0f - 1.0f) * 16.0f;
            this.parentEntity.getMoveHelper().setMoveTo(d0, d2, d3, 1.0);
        }
    }
    
    static class GhastMoveHelper extends EntityMoveHelper
    {
        private final EntitySquidGhast parentEntity;
        private int courseChangeCooldown;
        
        public GhastMoveHelper(final EntitySquidGhast ghast) {
            super((EntityLiving)ghast);
            this.parentEntity = ghast;
        }
        
        public void onUpdateMoveHelper() {
            if (this.action == EntityMoveHelper.Action.MOVE_TO) {
                final double d0 = this.posX - this.parentEntity.posX;
                final double d2 = this.posY - this.parentEntity.posY;
                final double d3 = this.posZ - this.parentEntity.posZ;
                double d4 = d0 * d0 + d2 * d2 + d3 * d3;
                if (this.courseChangeCooldown-- <= 0) {
                    this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
                    d4 = MathHelper.sqrt(d4);
                    if (this.isNotColliding(this.posX, this.posY, this.posZ, d4)) {
                        final EntitySquidGhast parentEntity = this.parentEntity;
                        parentEntity.motionX += d0 / d4 * 0.1;
                        final EntitySquidGhast parentEntity2 = this.parentEntity;
                        parentEntity2.motionY += d2 / d4 * 0.1;
                        final EntitySquidGhast parentEntity3 = this.parentEntity;
                        parentEntity3.motionZ += d3 / d4 * 0.1;
                    }
                    else {
                        this.action = EntityMoveHelper.Action.WAIT;
                    }
                }
            }
        }
        
        private boolean isNotColliding(final double x, final double y, final double z, final double p_179926_7_) {
            final double d0 = (x - this.parentEntity.posX) / p_179926_7_;
            final double d2 = (y - this.parentEntity.posY) / p_179926_7_;
            final double d3 = (z - this.parentEntity.posZ) / p_179926_7_;
            AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();
            for (int i = 1; i < p_179926_7_; ++i) {
                axisalignedbb = axisalignedbb.offset(d0, d2, d3);
                if (!this.parentEntity.world.getCollisionBoxes((Entity)this.parentEntity, axisalignedbb).isEmpty()) {
                    return false;
                }
            }
            return true;
        }
    }
}
