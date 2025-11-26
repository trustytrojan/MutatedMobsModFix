//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import net.minecraft.entity.passive.*;
import mmm.common.entities.mobs.interfaces.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import mmm.eventHandler.*;
import javax.annotation.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.init.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import mmm.common.items.*;
import net.minecraft.entity.effect.*;
import net.minecraft.inventory.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.network.datasync.*;
import com.google.common.collect.*;

public class EntityBlazePig extends EntityAnimal implements IJumpingMount, IMutant
{
    private float heightOffset;
    private int heightOffsetUpdateTime;
    private static final DataParameter<Integer> BOOST_TIME;
    private static final Set<Item> TEMPTATION_ITEMS;
    private boolean boosting;
    private int boostTime;
    private int totalBoostTime;
    protected float jumpPower;
    
    public EntityBlazePig(final World worldIn) {
        super(worldIn);
        this.heightOffset = 0.5f;
        this.setSize(0.9f, 0.9f);
        this.isImmuneToFire = true;
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIMate((EntityAnimal)this, 1.0));
        this.tasks.addTask(4, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.2, Items.CARROT_ON_A_STICK, false));
        this.tasks.addTask(4, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.2, false, (Set)EntityBlazePig.TEMPTATION_ITEMS));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowParent((EntityAnimal)this, 1.1));
        this.tasks.addTask(6, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0));
        this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveTowardsTarget((EntityCreature)this, 0.7, 70.0f));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, 0.7, true));
        this.tasks.addTask(4, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0, false));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_BlazePig);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
    }
    
    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }
    
    public void fall(final float distance, final float damageMultiplier) {
    }
    
    public void updateRidden() {
        super.updateRidden();
        if (this.getRidingEntity() instanceof EntityCreature) {
            final EntityCreature entitycreature = (EntityCreature)this.getRidingEntity();
            this.renderYawOffset = entitycreature.renderYawOffset;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }
    
    public float getBrightness() {
        return 1.0f;
    }
    
    public void onLivingUpdate() {
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }
        if (this.world.isRemote) {
            if (this.rand.nextInt(24) == 0 && !this.isSilent()) {
                this.world.playSound(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f, false);
            }
            for (int i = 0; i < 2; ++i) {
                this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0, new int[0]);
            }
            if (this.onGround) {
                this.motionY += (0.600000011920929 - this.motionY) * 0.600000011920929;
                this.isAirBorne = true;
            }
        }
        super.onLivingUpdate();
    }
    
    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 0.5f);
        }
        --this.heightOffsetUpdateTime;
        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5f + (float)this.rand.nextGaussian() * 3.0f;
        }
        final EntityLivingBase entitylivingbase = this.getAttackTarget();
        if (entitylivingbase != null && entitylivingbase.posY + entitylivingbase.getEyeHeight() > this.posY + this.getEyeHeight() + this.heightOffset) {
            this.motionY += (0.40000001192092893 - this.motionY) * 0.40000001192092893;
            this.isAirBorne = true;
        }
        super.updateAITasks();
    }
    
    public boolean attackEntityAsMob(final Entity par1Entity) {
        par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 3.0f);
        par1Entity.setFire(3);
        return true;
    }
    
    public boolean canBeSteered() {
        return this.getControllingPassenger() instanceof EntityLivingBase;
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
    
    public void notifyDataManagerChange(final DataParameter<?> key) {
        if (EntityBlazePig.BOOST_TIME.equals((Object)key) && this.world.isRemote) {
            this.boosting = true;
            this.boostTime = 0;
            this.totalBoostTime = (int)this.dataManager.get((DataParameter)EntityBlazePig.BOOST_TIME);
        }
        super.notifyDataManagerChange((DataParameter)key);
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register((DataParameter)EntityBlazePig.BOOST_TIME, (Object)0);
    }
    
    public static void registerFixesPig(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, (Class)EntityBlazePig.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
    }
    
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PIG_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }
    
    protected void playStepSound(final BlockPos pos, final Block blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1.0f);
    }
    
    public boolean processInteract(final EntityPlayer player, final EnumHand hand) {
        if (super.processInteract(player, hand)) {
            return true;
        }
        final ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.NAME_TAG) {
            itemstack.interactWithEntity(player, (EntityLivingBase)this, hand);
            return true;
        }
        if (!this.isBeingRidden()) {
            if (!this.world.isRemote) {
                this.mountTo(player);
            }
            return true;
        }
        return false;
    }
    
    public void onDeath(final DamageSource cause) {
        super.onDeath(cause);
        if (!this.world.isRemote) {
            this.dropItem(ModItems.porkchop_blazing, 1);
            this.dropItem(Items.BLAZE_POWDER, 1);
        }
    }
    
    public void onStruckByLightning(final EntityLightningBolt lightningBolt) {
        if (!this.world.isRemote && !this.isDead) {
            final EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);
            entitypigzombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
            entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                entitypigzombie.setCustomNameTag(this.getCustomNameTag());
                entitypigzombie.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            this.world.spawnEntity((Entity)entitypigzombie);
            this.setDead();
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
                this.motionY = 3.0 * this.jumpPower;
                this.setJumping(true);
                this.isAirBorne = true;
                if (forward > 0.0f) {
                    final float f = MathHelper.sin(this.rotationYaw * 0.017453292f);
                    final float f2 = MathHelper.cos(this.rotationYaw * 0.017453292f);
                    this.motionX += -0.4f * f * this.jumpPower;
                    this.motionZ += 0.4f * f2 * this.jumpPower;
                    this.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, 0.4f, 1.0f);
                }
                this.jumpPower = 0.0f;
            }
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.25f;
            if (this.canPassengerSteer()) {
                this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
                super.travel(strafe, vertical, forward);
            }
            else if (entitylivingbase instanceof EntityPlayer) {
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
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
            this.jumpMovementFactor = 0.02f;
            super.travel(strafe, vertical, forward);
        }
        if (this.isInLava()) {
            this.motionY += (1.600000011920929 - this.motionY) * 0.600000011920929;
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5f + (float)this.rand.nextGaussian() * 3.0f;
        }
        if (this.onGround) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5f + (float)this.rand.nextGaussian() * 3.0f;
            this.motionY += (1.600000011920929 - this.motionY) * 0.600000011920929;
            this.isAirBorne = true;
        }
        if (this.collidedHorizontally) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5f + (float)this.rand.nextGaussian() * 3.0f;
            this.motionY += (1.600000011920929 - this.motionY) * 0.600000011920929;
            this.isAirBorne = true;
        }
    }
    
    protected void collideWithEntity(final Entity entityIn) {
        if (this.isBeingRidden() && entityIn instanceof EntityLiving && (!(entityIn instanceof EntityBlazePig) || !(entityIn instanceof EntityGolem)) && this.getRNG().nextInt(2) == 0) {
            entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)ConfigHandler.ATK_BlazePig);
            entityIn.setFire(10);
        }
        super.collideWithEntity(entityIn);
    }
    
    public boolean boost() {
        if (this.boosting) {
            return false;
        }
        this.boosting = true;
        this.boostTime = 0;
        this.totalBoostTime = this.getRNG().nextInt(841) + 140;
        this.getDataManager().set((DataParameter)EntityBlazePig.BOOST_TIME, (Object)this.totalBoostTime);
        return true;
    }
    
    public EntityBlazePig createChild(final EntityAgeable ageable) {
        return new EntityBlazePig(this.world);
    }
    
    public boolean isBreedingItem(final ItemStack stack) {
        return EntityBlazePig.TEMPTATION_ITEMS.contains(stack.getItem());
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
    
    public void jumps() {
        this.motionY += (0.600000011920929 - this.motionY) * 0.5000000119209289;
        this.setJumping(this.isAirBorne = true);
    }
    
    public boolean canJump() {
        return false;
    }
    
    public void handleStartJump(final int p_184775_1_) {
    }
    
    public void handleStopJump() {
    }
    
    static {
        BOOST_TIME = EntityDataManager.createKey((Class)EntityBlazePig.class, DataSerializers.VARINT);
        TEMPTATION_ITEMS = Sets.newHashSet(new Item[] { Items.CARROT, Items.POTATO, Items.BEETROOT });
    }
}
