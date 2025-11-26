//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs.boss;

import net.minecraft.entity.monster.*;
import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import net.minecraft.world.*;
import net.minecraftforge.event.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import mmm.common.entities.projectiles.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.potion.*;
import mmm.eventHandler.*;
import net.minecraft.entity.*;
import net.minecraft.network.datasync.*;
import javax.annotation.*;

public class EntityShulkerWither extends EntityMob implements IRangedAttackMob, IBoss, IMutant
{
    private static final DataParameter<Integer> FIRST_HEAD_TARGET;
    private static final DataParameter<Integer> SECOND_HEAD_TARGET;
    private static final DataParameter<Integer> THIRD_HEAD_TARGET;
    private static final DataParameter<Integer>[] HEAD_TARGETS;
    private static final DataParameter<Integer> INVULNERABILITY_TIME;
    private final float[] xRotationHeads;
    private final float[] yRotationHeads;
    private final float[] xRotOHeads;
    private final float[] yRotOHeads;
    private final int[] nextHeadUpdate;
    private final int[] idleHeadUpdates;
    private int blockBreakCounter;
    private final BossInfoServer bossInfo;
    private static final Predicate<Entity> NOT_UNDEAD;
    
    public EntityShulkerWither(final World worldIn) {
        super(worldIn);
        this.xRotationHeads = new float[2];
        this.yRotationHeads = new float[2];
        this.xRotOHeads = new float[2];
        this.yRotOHeads = new float[2];
        this.nextHeadUpdate = new int[2];
        this.idleHeadUpdates = new int[2];
        this.bossInfo = (BossInfoServer)new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS).setDarkenSky(true);
        this.setHealth(this.getMaxHealth());
        this.setSize(0.9f, 3.5f);
        this.isImmuneToFire = true;
        ((PathNavigateGround)this.getNavigator()).setCanSwim(true);
        this.experienceValue = 50;
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(0, (EntityAIBase)new AIDoNothing());
        this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIAttackRanged((IRangedAttackMob)this, 1.0, 40, 20.0f));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0));
        this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityLiving.class, 0, false, false, (Predicate)EntityShulkerWither.NOT_UNDEAD));
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register((DataParameter)EntityShulkerWither.FIRST_HEAD_TARGET, (Object)0);
        this.dataManager.register((DataParameter)EntityShulkerWither.SECOND_HEAD_TARGET, (Object)0);
        this.dataManager.register((DataParameter)EntityShulkerWither.THIRD_HEAD_TARGET, (Object)0);
        this.dataManager.register((DataParameter)EntityShulkerWither.INVULNERABILITY_TIME, (Object)0);
    }
    
    public static void registerFixesWither(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, (Class)EntityShulkerWither.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Invul", this.getInvulTime());
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setInvulTime(compound.getInteger("Invul"));
        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }
    }
    
    public void setCustomNameTag(final String name) {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }
    
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SHULKER_AMBIENT;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_WITHER_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WITHER_DEATH;
    }
    
    public void onLivingUpdate() {
        this.motionY *= 0.6000000238418579;
        if (!this.world.isRemote && this.getWatchedTargetId(0) > 0) {
            final Entity entity = this.world.getEntityByID(this.getWatchedTargetId(0));
            if (entity != null) {
                if (this.posY < entity.posY || (!this.isArmored() && this.posY < entity.posY + 5.0)) {
                    if (this.motionY < 0.0) {
                        this.motionY = 0.0;
                    }
                    this.motionY += (0.5 - this.motionY) * 0.6000000238418579;
                }
                final double d0 = entity.posX - this.posX;
                final double d2 = entity.posZ - this.posZ;
                final double d3 = d0 * d0 + d2 * d2;
                if (d3 > 9.0) {
                    final double d4 = MathHelper.sqrt(d3);
                    this.motionX += (d0 / d4 * 0.5 - this.motionX) * 0.6000000238418579;
                    this.motionZ += (d2 / d4 * 0.5 - this.motionZ) * 0.6000000238418579;
                }
            }
        }
        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806) {
            this.rotationYaw = (float)MathHelper.atan2(this.motionZ, this.motionX) * 57.295776f - 90.0f;
        }
        super.onLivingUpdate();
        for (int i = 0; i < 2; ++i) {
            this.yRotOHeads[i] = this.yRotationHeads[i];
            this.xRotOHeads[i] = this.xRotationHeads[i];
        }
        for (int j = 0; j < 2; ++j) {
            final int k = this.getWatchedTargetId(j + 1);
            Entity entity2 = null;
            if (k > 0) {
                entity2 = this.world.getEntityByID(k);
            }
            if (entity2 != null) {
                final double d5 = this.getHeadX(j + 1);
                final double d6 = this.getHeadY(j + 1);
                final double d7 = this.getHeadZ(j + 1);
                final double d8 = entity2.posX - d5;
                final double d9 = entity2.posY + entity2.getEyeHeight() - d6;
                final double d10 = entity2.posZ - d7;
                final double d11 = MathHelper.sqrt(d8 * d8 + d10 * d10);
                final float f = (float)(MathHelper.atan2(d10, d8) * 57.29577951308232) - 90.0f;
                final float f2 = (float)(-(MathHelper.atan2(d9, d11) * 57.29577951308232));
                this.xRotationHeads[j] = this.rotlerp(this.xRotationHeads[j], f2, 40.0f);
                this.yRotationHeads[j] = this.rotlerp(this.yRotationHeads[j], f, 10.0f);
            }
            else {
                this.yRotationHeads[j] = this.rotlerp(this.yRotationHeads[j], this.renderYawOffset, 10.0f);
            }
        }
        final boolean flag = this.isArmored();
        for (int l = 0; l < 3; ++l) {
            final double d12 = this.getHeadX(l);
            final double d13 = this.getHeadY(l);
            final double d14 = this.getHeadZ(l);
            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d12 + this.rand.nextGaussian() * 0.30000001192092896, d13 + this.rand.nextGaussian() * 0.30000001192092896, d14 + this.rand.nextGaussian() * 0.30000001192092896, 0.0, 0.0, 0.0, new int[0]);
            if (flag && this.world.rand.nextInt(4) == 0) {
                this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, d12 + this.rand.nextGaussian() * 0.30000001192092896, d13 + this.rand.nextGaussian() * 0.30000001192092896, d14 + this.rand.nextGaussian() * 0.30000001192092896, 0.699999988079071, 0.699999988079071, 0.5, new int[0]);
            }
        }
        if (this.getInvulTime() > 0) {
            for (int i2 = 0; i2 < 3; ++i2) {
                this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + this.rand.nextGaussian(), this.posY + this.rand.nextFloat() * 3.3f, this.posZ + this.rand.nextGaussian(), 0.699999988079071, 0.699999988079071, 0.8999999761581421, new int[0]);
            }
        }
    }
    
    public void ShootShulkerBalls() {
        if (this.getAttackTarget() != null) {
            for (int i = 0; i < 1; ++i) {
                final double d7 = this.getAttackTarget().posX + (this.getAttackTarget().getRNG().nextDouble() - 0.5) * 8.0;
                final double d8 = MathHelper.clamp(this.getAttackTarget().posY + (this.getAttackTarget().getRNG().nextInt(16) - 8), 0.0, (double)(this.getAttackTarget().world.getActualHeight() - 4));
                final double d9 = this.getAttackTarget().posZ + (this.getAttackTarget().getRNG().nextDouble() - 0.5) * 8.0;
                final EntityShulkerBullet entityshulkerbullet = new EntityShulkerBullet(this.world, (EntityLivingBase)this, (Entity)this.getAttackTarget(), (EnumFacing.Axis)null);
                this.world.spawnEntity((Entity)entityshulkerbullet);
                this.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 2.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
        }
    }
    
    protected void updateAITasks() {
        if (this.getInvulTime() > 0) {
            final int j1 = this.getInvulTime() - 1;
            if (j1 <= 0) {
                this.world.newExplosion((Entity)this, this.posX, this.posY + this.getEyeHeight(), this.posZ, 7.0f, false, this.world.getGameRules().getBoolean("mobGriefing"));
                this.world.playBroadcastSound(1023, new BlockPos((Entity)this), 0);
            }
            this.setInvulTime(j1);
            if (this.ticksExisted % 10 == 0) {
                this.heal(10.0f);
            }
        }
        else {
            super.updateAITasks();
            for (int i = 1; i < 3; ++i) {
                if (this.ticksExisted >= this.nextHeadUpdate[i - 1]) {
                    this.nextHeadUpdate[i - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);
                    if (this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD) {
                        final int j2 = i - 1;
                        final int k3 = this.idleHeadUpdates[i - 1];
                        this.idleHeadUpdates[j2] = this.idleHeadUpdates[i - 1] + 1;
                        if (k3 > 15) {
                            final float f = 10.0f;
                            final float f2 = 5.0f;
                            final double d0 = MathHelper.nextDouble(this.rand, this.posX - 10.0, this.posX + 10.0);
                            final double d2 = MathHelper.nextDouble(this.rand, this.posY - 5.0, this.posY + 5.0);
                            final double d3 = MathHelper.nextDouble(this.rand, this.posZ - 10.0, this.posZ + 10.0);
                            this.launchWitherSkullToCoords(i + 1, d0, d2, d3, true);
                            this.idleHeadUpdates[i - 1] = 0;
                        }
                    }
                    final int k4 = this.getWatchedTargetId(i);
                    if (k4 > 0) {
                        final Entity entity = this.world.getEntityByID(k4);
                        if (entity != null && entity.isEntityAlive() && this.getDistanceSq(entity) <= 900.0 && this.canEntityBeSeen(entity)) {
                            if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.disableDamage) {
                                this.updateWatchedTargetId(i, 0);
                            }
                            else {
                                this.launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
                                this.nextHeadUpdate[i - 1] = this.ticksExisted + 40 + this.rand.nextInt(20);
                                this.idleHeadUpdates[i - 1] = 0;
                            }
                        }
                        else {
                            this.updateWatchedTargetId(i, 0);
                        }
                    }
                    else {
                        final List<EntityLivingBase> list = (List<EntityLivingBase>)this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(20.0, 8.0, 20.0), Predicates.and((Predicate)EntityShulkerWither.NOT_UNDEAD, EntitySelectors.NOT_SPECTATING));
                        int j3 = 0;
                        while (j3 < 10 && !list.isEmpty()) {
                            final EntityLivingBase entitylivingbase = list.get(this.rand.nextInt(list.size()));
                            if (entitylivingbase != this && entitylivingbase.isEntityAlive() && this.canEntityBeSeen((Entity)entitylivingbase)) {
                                if (!(entitylivingbase instanceof EntityPlayer)) {
                                    this.updateWatchedTargetId(i, entitylivingbase.getEntityId());
                                    break;
                                }
                                if (!((EntityPlayer)entitylivingbase).capabilities.disableDamage) {
                                    this.updateWatchedTargetId(i, entitylivingbase.getEntityId());
                                    break;
                                }
                                break;
                            }
                            else {
                                list.remove(entitylivingbase);
                                ++j3;
                            }
                        }
                    }
                }
            }
            if (this.getAttackTarget() != null) {
                this.updateWatchedTargetId(0, this.getAttackTarget().getEntityId());
            }
            else {
                this.updateWatchedTargetId(0, 0);
            }
            if (this.blockBreakCounter > 0) {
                --this.blockBreakCounter;
                if (this.blockBreakCounter == 0 && this.world.getGameRules().getBoolean("mobGriefing")) {
                    final int i2 = MathHelper.floor(this.posY);
                    final int l1 = MathHelper.floor(this.posX);
                    final int i3 = MathHelper.floor(this.posZ);
                    boolean flag = false;
                    for (int k5 = -1; k5 <= 1; ++k5) {
                        for (int l2 = -1; l2 <= 1; ++l2) {
                            for (int m = 0; m <= 3; ++m) {
                                final int i4 = l1 + k5;
                                final int k6 = i2 + m;
                                final int l3 = i3 + l2;
                                final BlockPos blockpos = new BlockPos(i4, k6, l3);
                                final IBlockState iblockstate = this.world.getBlockState(blockpos);
                                final Block block = iblockstate.getBlock();
                                if (!block.isAir(iblockstate, (IBlockAccess)this.world, blockpos) && block.canEntityDestroy(iblockstate, (IBlockAccess)this.world, blockpos, (Entity)this) && ForgeEventFactory.onEntityDestroyBlock((EntityLivingBase)this, blockpos, iblockstate)) {
                                    flag = (this.world.destroyBlock(blockpos, true) || flag);
                                }
                            }
                        }
                    }
                    if (flag) {
                        this.world.playEvent((EntityPlayer)null, 1022, new BlockPos((Entity)this), 0);
                    }
                }
            }
            if (this.ticksExisted % 20 == 0) {
                this.heal(1.0f);
            }
            this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        }
    }
    
    public static boolean canDestroyBlock(final Block blockIn) {
        return blockIn != Blocks.BEDROCK && blockIn != Blocks.END_PORTAL && blockIn != Blocks.END_PORTAL_FRAME && blockIn != Blocks.COMMAND_BLOCK && blockIn != Blocks.REPEATING_COMMAND_BLOCK && blockIn != Blocks.CHAIN_COMMAND_BLOCK && blockIn != Blocks.BARRIER && blockIn != Blocks.STRUCTURE_BLOCK && blockIn != Blocks.STRUCTURE_VOID && blockIn != Blocks.PISTON_EXTENSION && blockIn != Blocks.END_GATEWAY;
    }
    
    public void ignite() {
        this.setInvulTime(220);
        this.setHealth(this.getMaxHealth() / 3.0f);
    }
    
    public void setInWeb() {
    }
    
    public void addTrackingPlayer(final EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }
    
    public void removeTrackingPlayer(final EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }
    
    private double getHeadX(final int p_82214_1_) {
        if (p_82214_1_ <= 0) {
            return this.posX;
        }
        final float f = (this.renderYawOffset + 180 * (p_82214_1_ - 1)) * 0.017453292f;
        final float f2 = MathHelper.cos(f);
        return this.posX + f2 * 1.3;
    }
    
    private double getHeadY(final int p_82208_1_) {
        return (p_82208_1_ <= 0) ? (this.posY + 3.0) : (this.posY + 2.2);
    }
    
    private double getHeadZ(final int p_82213_1_) {
        if (p_82213_1_ <= 0) {
            return this.posZ;
        }
        final float f = (this.renderYawOffset + 180 * (p_82213_1_ - 1)) * 0.017453292f;
        final float f2 = MathHelper.sin(f);
        return this.posZ + f2 * 1.3;
    }
    
    private float rotlerp(final float p_82204_1_, final float p_82204_2_, final float p_82204_3_) {
        float f = MathHelper.wrapDegrees(p_82204_2_ - p_82204_1_);
        if (f > p_82204_3_) {
            f = p_82204_3_;
        }
        if (f < -p_82204_3_) {
            f = -p_82204_3_;
        }
        return p_82204_1_ + f;
    }
    
    private void launchWitherSkullToEntity(final int p_82216_1_, final EntityLivingBase p_82216_2_) {
        this.launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5, p_82216_2_.posZ, p_82216_1_ == 0 && this.rand.nextFloat() < 0.001f);
    }
    
    private void launchWitherSkullToCoords(final int p_82209_1_, final double x, final double y, final double z, final boolean invulnerable) {
        this.world.playEvent((EntityPlayer)null, 1024, new BlockPos((Entity)this), 0);
        final double d0 = this.getHeadX(p_82209_1_);
        final double d2 = this.getHeadY(p_82209_1_);
        final double d3 = this.getHeadZ(p_82209_1_);
        final double d4 = x - d0;
        final double d5 = y - d2;
        final double d6 = z - d3;
        final EntityShulkerSkull EntityShulkerSkull = new EntityShulkerSkull(this.world, (EntityLivingBase)this, d4, d5, d6);
        if (invulnerable) {
            EntityShulkerSkull.setInvulnerable(true);
        }
        EntityShulkerSkull.posY = d2;
        EntityShulkerSkull.posX = d0;
        EntityShulkerSkull.posZ = d3;
        this.world.spawnEntity((Entity)EntityShulkerSkull);
    }
    
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float distanceFactor) {
        this.launchWitherSkullToEntity(0, target);
        this.ShootShulkerBalls();
    }
    
    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        }
        if (source == DamageSource.DROWN || source.getTrueSource() instanceof EntityShulkerWither) {
            return false;
        }
        if (this.getInvulTime() > 0 && source != DamageSource.OUT_OF_WORLD) {
            return false;
        }
        if (this.isArmored()) {
            final Entity entity = source.getImmediateSource();
            if (entity instanceof EntityArrow) {
                return false;
            }
        }
        final Entity entity2 = source.getTrueSource();
        if (entity2 != null && !(entity2 instanceof EntityPlayer) && entity2 instanceof EntityLivingBase && ((EntityLivingBase)entity2).getCreatureAttribute() == this.getCreatureAttribute()) {
            return false;
        }
        if (this.blockBreakCounter <= 0) {
            this.blockBreakCounter = 20;
        }
        for (int i = 0; i < this.idleHeadUpdates.length; ++i) {
            final int[] idleHeadUpdates = this.idleHeadUpdates;
            final int n = i;
            idleHeadUpdates[n] += 3;
        }
        return super.attackEntityFrom(source, amount);
    }
    
    protected void dropFewItems(final boolean wasRecentlyHit, final int lootingModifier) {
        final EntityItem entityitem = this.dropItem(Items.NETHER_STAR, 1);
        final EntityItem entityitem2 = this.dropItem(Items.SHULKER_SHELL, 1);
        if (entityitem != null) {
            entityitem.setNoDespawn();
        }
        if (entityitem2 != null) {
            entityitem2.setNoDespawn();
        }
    }
    
    protected void despawnEntity() {
        this.idleTime = 0;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }
    
    public void fall(final float distance, final float damageMultiplier) {
    }
    
    public void addPotionEffect(final PotionEffect potioneffectIn) {
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_ShulkerWither);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6000000238418579);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(12.0);
    }
    
    @SideOnly(Side.CLIENT)
    public float getHeadYRotation(final int p_82207_1_) {
        return this.yRotationHeads[p_82207_1_];
    }
    
    @SideOnly(Side.CLIENT)
    public float getHeadXRotation(final int p_82210_1_) {
        return this.xRotationHeads[p_82210_1_];
    }
    
    public int getInvulTime() {
        return (int)this.dataManager.get((DataParameter)EntityShulkerWither.INVULNERABILITY_TIME);
    }
    
    public void setInvulTime(final int time) {
        this.dataManager.set((DataParameter)EntityShulkerWither.INVULNERABILITY_TIME, (Object)time);
    }
    
    public int getWatchedTargetId(final int head) {
        return (int)this.dataManager.get((DataParameter)EntityShulkerWither.HEAD_TARGETS[head]);
    }
    
    public void updateWatchedTargetId(final int targetOffset, final int newId) {
        this.dataManager.set((DataParameter)EntityShulkerWither.HEAD_TARGETS[targetOffset], (Object)newId);
    }
    
    public boolean isArmored() {
        return this.getHealth() <= this.getMaxHealth() / 2.0f;
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    protected boolean canBeRidden(final Entity entityIn) {
        return false;
    }
    
    public boolean isNonBoss() {
        return false;
    }
    
    public void setSwingingArms(final boolean swingingArms) {
    }
    
    static {
        FIRST_HEAD_TARGET = EntityDataManager.createKey((Class)EntityShulkerWither.class, DataSerializers.VARINT);
        SECOND_HEAD_TARGET = EntityDataManager.createKey((Class)EntityShulkerWither.class, DataSerializers.VARINT);
        THIRD_HEAD_TARGET = EntityDataManager.createKey((Class)EntityShulkerWither.class, DataSerializers.VARINT);
        HEAD_TARGETS = new DataParameter[] { EntityShulkerWither.FIRST_HEAD_TARGET, EntityShulkerWither.SECOND_HEAD_TARGET, EntityShulkerWither.THIRD_HEAD_TARGET };
        INVULNERABILITY_TIME = EntityDataManager.createKey((Class)EntityShulkerWither.class, DataSerializers.VARINT);
        NOT_UNDEAD = (Predicate)new Predicate<Entity>() {
            public boolean apply(@Nullable final Entity p_apply_1_) {
                return p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && ((EntityLivingBase)p_apply_1_).attackable();
            }
        };
    }
    
    class AIDoNothing extends EntityAIBase
    {
        public AIDoNothing() {
            this.setMutexBits(7);
        }
        
        public boolean shouldExecute() {
            return EntityShulkerWither.this.getInvulTime() > 0;
        }
    }
}
