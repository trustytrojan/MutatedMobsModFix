//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.mobs.interfaces.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;
import com.google.common.base.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.ai.*;
import net.minecraftforge.event.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import mmm.eventHandler.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;

public class EntityPolarWolf extends EntityTameable implements IJumpingMount, IMutant
{
    private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID;
    private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER;
    private int angerLevel;
    private int randomSoundDelay;
    private UUID angerTargetUUID;
    private boolean boosting;
    private int boostTime;
    private int totalBoostTime;
    protected float jumpPower;
    
    public EntityPolarWolf(final World worldIn) {
        super(worldIn);
        this.setSize(1.3f, 1.4f);
        this.setTamed(false);
    }
    
    public void setRevengeTarget(@Nullable final EntityLivingBase livingBase) {
        super.setRevengeTarget(livingBase);
        if (livingBase != null) {
            this.angerTargetUUID = livingBase.getUniqueID();
        }
    }
    
    protected void initEntityAI() {
        super.initEntityAI();
        this.targetTasks.addTask(1, (EntityAIBase)new AIHurtByAggressor(this));
        this.targetTasks.addTask(2, (EntityAIBase)new AITargetAggressor(this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(4, (EntityAIBase)new EntityAILeapAtTarget((EntityLiving)this, 0.4f));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0, true));
        this.tasks.addTask(6, (EntityAIBase)new EntityAIFollowOwner((EntityTameable)this, 1.0, 10.0f, 2.0f));
        this.tasks.addTask(7, (EntityAIBase)new EntityAIMate((EntityAnimal)this, 1.0));
        this.tasks.addTask(8, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0));
        this.tasks.addTask(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(10, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIOwnerHurtByTarget((EntityTameable)this));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAIOwnerHurtTarget((EntityTameable)this));
        this.targetTasks.addTask(3, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.targetTasks.addTask(4, (EntityAIBase)new EntityAITargetNonTamed((EntityTameable)this, EntityAnimal.class, false, new Predicate<Entity>() {
            public boolean apply(@Nullable final Entity p_apply_1_) {
                return p_apply_1_ instanceof EntitySheep || p_apply_1_ instanceof EntityRabbit;
            }
        }));
        this.targetTasks.addTask(5, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, AbstractSkeleton.class, false));
    }
    
    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }
    
    protected double getModifiedJumpStrength() {
        return 0.4000000059604645 + this.rand.nextDouble() * 0.2 + this.rand.nextDouble() * 0.2 + this.rand.nextDouble() * 0.2;
    }
    
    protected double getModifiedMovementSpeed() {
        return (0.6 + this.rand.nextDouble() * 0.3 + this.rand.nextDouble() * 0.3 + this.rand.nextDouble() * 0.3) * 0.25;
    }
    
    public boolean canBeSteered() {
        return this.getControllingPassenger() instanceof EntityLivingBase;
    }
    
    public boolean processInteract(final EntityPlayer player, final EnumHand hand) {
        final ItemStack itemstack = player.getHeldItem(hand);
        if (this.isTamed()) {
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() instanceof ItemFood) {
                    final ItemFood itemfood = (ItemFood)itemstack.getItem();
                    if (itemfood.isWolfsFavoriteMeat()) {
                        if (!player.capabilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }
                        this.heal((float)itemfood.getHealAmount(itemstack));
                        return true;
                    }
                }
            }
            else {
                if (!this.isBeingRidden() && this.isTamed() && this.isOwner((EntityLivingBase)player)) {
                    if (!this.world.isRemote) {
                        this.mountTo(player);
                    }
                    return true;
                }
                if (this.isBeingRidden() || !this.isTamed()) {
                    return false;
                }
            }
            if (this.isOwner((EntityLivingBase)player) && !this.world.isRemote && !this.isBreedingItem(itemstack)) {
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget((EntityLivingBase)null);
            }
        }
        else if (itemstack.getItem() == Items.BONE && !this.isAngry()) {
            if (!player.capabilities.isCreativeMode) {
                itemstack.shrink(1);
            }
            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame((EntityAnimal)this, player)) {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget((EntityLivingBase)null);
                    this.aiSit.setSitting(false);
                    this.setHealth(150.0f);
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)7);
                }
                else {
                    this.playTameEffect(false);
                    this.world.setEntityState((Entity)this, (byte)6);
                }
            }
            return true;
        }
        return super.processInteract(player, hand);
    }
    
    public boolean isBreedingItem(final ItemStack stack) {
        return stack.getItem() instanceof ItemFood && ((ItemFood)stack.getItem()).isWolfsFavoriteMeat();
    }
    
    protected boolean isMovementBlocked() {
        return super.isMovementBlocked() && this.isBeingRidden();
    }
    
    protected void mountTo(final EntityPlayer player) {
        player.rotationYaw = this.rotationYaw;
        player.rotationPitch = this.rotationPitch;
        if (!this.world.isRemote) {
            player.startRiding((Entity)this);
        }
    }
    
    public void travel(float strafe, final float vertical, float forward) {
        if (this.isBeingRidden() && this.canBeSteered()) {
            final EntityLivingBase entitylivingbase = (EntityLivingBase)this.getControllingPassenger();
            this.rotationYaw = entitylivingbase.rotationYaw;
            this.prevRotationYaw = this.rotationYaw;
            this.rotationPitch = entitylivingbase.rotationPitch * 0.5f;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.renderYawOffset = this.rotationYaw;
            this.rotationYawHead = this.renderYawOffset;
            strafe = entitylivingbase.moveStrafing * 0.5f;
            forward = entitylivingbase.moveForward;
            if (forward <= 0.0f) {
                forward *= 0.25f;
            }
            if (this.jumpPower > 0.0f && !this.isJumping && this.onGround) {
                this.motionY = 0.0;
                if (this.isPotionActive(MobEffects.JUMP_BOOST)) {
                    this.motionY += (this.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f;
                }
                this.setJumping(true);
                this.isAirBorne = true;
                if (forward > 0.0f) {
                    final float f = MathHelper.sin(this.rotationYaw * 0.017453292f);
                    final float f2 = MathHelper.cos(this.rotationYaw * 0.017453292f);
                    this.motionX += -0.4f * f * 0.5;
                    this.motionZ += 0.4f * f2 * 0.5;
                    this.playSound(SoundEvents.ENTITY_HORSE_JUMP, 0.4f, 1.0f);
                }
            }
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.6f;
            if (this.canPassengerSteer()) {
                this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
                super.travel(strafe, vertical, forward);
            }
            else if (entitylivingbase instanceof EntityPlayer) {
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }
            if (entitylivingbase.isSprinting()) {
                this.setSprinting(true);
            }
            else {
                this.setSprinting(false);
            }
            if (this.onGround) {
                this.setJumping(false);
            }
            this.prevLimbSwingAmount = this.limbSwingAmount;
            final double d1 = this.posX - this.prevPosX;
            final double d2 = this.posZ - this.prevPosZ;
            float f3 = MathHelper.sqrt(d1 * d1 + d2 * d2) * 4.0f;
            if (f3 > 1.0f) {
                f3 = 1.0f;
            }
            this.limbSwingAmount += (f3 - this.limbSwingAmount) * 0.4f;
            this.limbSwing += this.limbSwingAmount;
        }
        else {
            this.jumpMovementFactor = 0.0f;
            super.travel(strafe, vertical, forward);
        }
        if (this.collidedHorizontally && this.onGround) {
            this.jump();
        }
    }
    
    public void fall(final float distance, final float damageMultiplier) {
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3200000041723251);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_PolarWolf);
    }
    
    protected void updateAITasks() {
        final IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        if (this.isAngry()) {
            if (!this.isChild() && !iattributeinstance.hasModifier(EntityPolarWolf.ATTACK_SPEED_BOOST_MODIFIER)) {
                iattributeinstance.applyModifier(EntityPolarWolf.ATTACK_SPEED_BOOST_MODIFIER);
            }
            --this.angerLevel;
        }
        else if (iattributeinstance.hasModifier(EntityPolarWolf.ATTACK_SPEED_BOOST_MODIFIER)) {
            iattributeinstance.removeModifier(EntityPolarWolf.ATTACK_SPEED_BOOST_MODIFIER);
        }
        if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0) {
            this.playSound(SoundEvents.ENTITY_POLAR_BEAR_WARNING, this.getSoundVolume() * 2.0f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 1.8f);
        }
        if (this.angerLevel > 0 && this.angerTargetUUID != null && this.getRevengeTarget() == null) {
            final EntityPlayer entityplayer = this.world.getPlayerEntityByUUID(this.angerTargetUUID);
            this.setRevengeTarget((EntityLivingBase)entityplayer);
            this.attackingPlayer = entityplayer;
            this.recentlyHit = this.getRevengeTimer();
        }
        super.updateAITasks();
    }
    
    public boolean getCanSpawnHere() {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }
    
    protected void entityInit() {
        super.entityInit();
    }
    
    public double getMountedYOffset() {
        return this.height * 0.85f;
    }
    
    public boolean attackEntityAsMob(final Entity par1Entity) {
        par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)ConfigHandler.ATK_PolarWolf);
        if (this.onGround && this.rand.nextInt(30) == 0) {
            final double d0 = par1Entity.posX - this.posX;
            final double d2 = par1Entity.posZ - this.posZ;
            final float f2 = MathHelper.sqrt(d0 * d0 + d2 * d2);
            this.motionX = d0 / f2 * 0.5 * 1.200000011920929 + this.motionX * 0.6000000029802323;
            this.motionZ = d2 / f2 * 0.5 * 1.200000011920929 + this.motionZ * 0.6000000029802323;
            this.motionY = 0.4000000059604645;
        }
        return true;
    }
    
    public boolean isPotionApplicable(final PotionEffect potioneffectIn) {
        return potioneffectIn.getPotion() != MobEffects.POISON && super.isPotionApplicable(potioneffectIn);
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (this.getAttackTarget() != null) {
            this.setSprinting(true);
        }
        else {
            this.setSprinting(false);
        }
    }
    
    public static void registerFixesPigZombie(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityPolarWolf.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setShort("Anger", (short)this.angerLevel);
        if (this.angerTargetUUID != null) {
            compound.setString("HurtBy", this.angerTargetUUID.toString());
        }
        else {
            compound.setString("HurtBy", "");
        }
    }
    
    public static void registerFixesSpider(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityPolarWolf.class);
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.angerLevel = compound.getShort("Anger");
        final String s = compound.getString("HurtBy");
        if (!s.isEmpty()) {
            this.angerTargetUUID = UUID.fromString(s);
            final EntityPlayer entityplayer = this.world.getPlayerEntityByUUID(this.angerTargetUUID);
            this.setRevengeTarget((EntityLivingBase)entityplayer);
            if (entityplayer != null) {
                this.attackingPlayer = entityplayer;
                this.recentlyHit = this.getRevengeTimer();
            }
        }
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        if (this.world.rand.nextInt(100) == 0) {
            final EntitySkeleton entityskeleton = new EntitySkeleton(this.world);
            entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityskeleton.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity((Entity)entityskeleton);
            entityskeleton.startRiding((Entity)this);
        }
        if (this.world.rand.nextInt(80) == 0) {
            final EntityCreeper entityskeleton2 = new EntityCreeper(this.world);
            entityskeleton2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityskeleton2.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity((Entity)entityskeleton2);
            entityskeleton2.startRiding((Entity)this);
        }
        if (this.world.rand.nextInt(95) == 0) {
            final EntityZombie entityskeleton3 = new EntityZombie(this.world);
            entityskeleton3.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityskeleton3.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity((Entity)entityskeleton3);
            entityskeleton3.startRiding((Entity)this);
        }
        if (this.world.rand.nextInt(120) == 0) {
            final EntityHusk entityskeleton4 = new EntityHusk(this.world);
            entityskeleton4.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityskeleton4.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity((Entity)entityskeleton4);
            entityskeleton4.startRiding((Entity)this);
        }
        if (this.world.rand.nextInt(150) == 0) {
            final EntityWitch entityskeleton5 = new EntityWitch(this.world);
            entityskeleton5.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityskeleton5.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity((Entity)entityskeleton5);
            entityskeleton5.startRiding((Entity)this);
        }
        if (livingdata == null) {
            livingdata = (IEntityLivingData)new GroupData();
            if (this.world.getDifficulty() == EnumDifficulty.HARD && this.world.rand.nextFloat() < 0.1f * difficulty.getClampedAdditionalDifficulty()) {
                ((GroupData)livingdata).setRandomEffect(this.world.rand);
            }
        }
        if (livingdata instanceof GroupData) {
            final Potion potion = ((GroupData)livingdata).effect;
            if (potion != null) {
                this.addPotionEffect(new PotionEffect(potion, Integer.MAX_VALUE));
            }
        }
        return livingdata;
    }
    
    public void onDeath(final DamageSource cause) {
        super.onDeath(cause);
        if ((cause.getTrueSource() instanceof EntityZombie || cause.getTrueSource() instanceof EntityZombieSpider || cause.getTrueSource() instanceof EntityZombieSpiderPigman || cause.getTrueSource() instanceof EntityZombieCreeper || cause.getTrueSource() instanceof EntityHuskSpider || cause.getTrueSource() instanceof EntityDeadPolarWolf || cause.getTrueSource() instanceof EntitySkeleton) && !this.world.isRemote && !this.isDead) {
            final EntityDeadPolarWolf entitypigzombie = new EntityDeadPolarWolf(this.world);
            entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie.setNoAI(this.isAIDisabled());
            entitypigzombie.setHealth(150.0f);
            if (this.hasCustomName()) {
                entitypigzombie.setCustomNameTag(this.getCustomNameTag());
                entitypigzombie.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            this.world.spawnEntity((Entity)entitypigzombie);
            this.setDead();
        }
    }
    
    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        }
        final Entity entity = source.getTrueSource();
        if (entity instanceof EntityPlayer) {
            this.becomeAngryAt(entity);
        }
        return super.attackEntityFrom(source, amount);
    }
    
    private void becomeAngryAt(final Entity p_70835_1_) {
        this.angerLevel = 400 + this.rand.nextInt(400);
        this.randomSoundDelay = this.rand.nextInt(40);
        if (p_70835_1_ instanceof EntityLivingBase) {
            this.setRevengeTarget((EntityLivingBase)p_70835_1_);
        }
    }
    
    public boolean isAngry() {
        return this.angerLevel > 0;
    }
    
    protected SoundEvent getAmbientSound() {
        return this.isChild() ? SoundEvents.ENTITY_POLAR_BEAR_BABY_AMBIENT : SoundEvents.ENTITY_POLAR_BEAR_AMBIENT;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_POLAR_BEAR_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_POLAR_BEAR_DEATH;
    }
    
    protected float getWaterSlowDown() {
        return 0.98f;
    }
    
    protected ItemStack getSkullDrop() {
        return ItemStack.EMPTY;
    }
    
    public boolean isPreventingPlayerRest(final EntityPlayer playerIn) {
        return this.isAngry();
    }
    
    public boolean canMateWith(final EntityAnimal otherAnimal) {
        if (otherAnimal == this) {
            return false;
        }
        if (!this.isTamed()) {
            return false;
        }
        if (!(otherAnimal instanceof EntityPolarWolf)) {
            return false;
        }
        final EntityPolarWolf EntityPolarWolf = (EntityPolarWolf)otherAnimal;
        return EntityPolarWolf.isTamed() && !EntityPolarWolf.isSitting() && this.isInLove() && EntityPolarWolf.isInLove();
    }
    
    public void setJumpPower(int jumpPowerIn) {
        if (this.isBeingRidden()) {
            if (jumpPowerIn >= 0) {
                return;
            }
            jumpPowerIn = 0;
            if (jumpPowerIn >= 90) {
                this.jumpPower = 1.0f;
            }
            else {
                this.jumpPower = 0.4f + 0.4f * jumpPowerIn / 90.0f;
            }
        }
    }
    
    public boolean canJump() {
        return false;
    }
    
    public void handleStartJump(final int p_184775_1_) {
    }
    
    public void handleStopJump() {
    }
    
    public EntityPolarWolf createChild(final EntityAgeable ageable) {
        final EntityPolarWolf EntityPolarWolf = new EntityPolarWolf(this.world);
        final UUID uuid = this.getOwnerId();
        if (uuid != null) {
            EntityPolarWolf.setOwnerId(uuid);
            EntityPolarWolf.setTamed(true);
        }
        return EntityPolarWolf;
    }
    
    static {
        ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
        ATTACK_SPEED_BOOST_MODIFIER = new AttributeModifier(EntityPolarWolf.ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.05, 0).setSaved(false);
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
    
    static class AIHurtByAggressor extends EntityAIHurtByTarget
    {
        public AIHurtByAggressor(final EntityPolarWolf p_i45828_1_) {
            super((EntityCreature)p_i45828_1_, true, new Class[0]);
        }
        
        protected void setEntityAttackTarget(final EntityCreature creatureIn, final EntityLivingBase entityLivingBaseIn) {
            super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);
            if (creatureIn instanceof EntityPolarWolf) {
                ((EntityPolarWolf)creatureIn).becomeAngryAt((Entity)entityLivingBaseIn);
            }
        }
    }
    
    static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer>
    {
        public AITargetAggressor(final EntityPolarWolf p_i45829_1_) {
            super((EntityCreature)p_i45829_1_, EntityPlayer.class, true);
        }
        
        public boolean shouldExecute() {
            return ((EntityPolarWolf)this.taskOwner).isAngry() && super.shouldExecute();
        }
    }
}
