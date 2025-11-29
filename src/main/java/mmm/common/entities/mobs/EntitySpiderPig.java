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
import net.minecraft.entity.monster.*;
import net.minecraft.potion.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.storage.loot.*;
import net.minecraft.entity.effect.*;
import net.minecraft.inventory.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.network.datasync.*;
import com.google.common.collect.*;

public class EntitySpiderPig extends EntityAnimal implements IJumpingMount, IMutant
{
    public static final net.minecraft.world.biome.Biome[] SPAWN_BIOMES = new net.minecraft.world.biome.Biome[] { Biomes.EXTREME_HILLS };
    private static final DataParameter<Byte> CLIMBING;
    private static final DataParameter<Integer> BOOST_TIME;
    private static final Set<Item> TEMPTATION_ITEMS;
    private boolean boosting;
    private int boostTime;
    private int totalBoostTime;
    protected float jumpPower;
    
    public EntitySpiderPig(final World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 0.9f);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, new EntityAIPanic((EntityCreature)this, 1.25));
        this.tasks.addTask(3, new EntityAIMate((EntityAnimal)this, 1.0));
        this.tasks.addTask(4, new EntityAITempt((EntityCreature)this, 1.2, Items.CARROT_ON_A_STICK, false));
        this.tasks.addTask(4, new EntityAITempt((EntityCreature)this, 1.2, false, (Set)EntitySpiderPig.TEMPTATION_ITEMS));
        this.tasks.addTask(5, new EntityAIFollowParent((EntityAnimal)this, 1.1));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater((EntityCreature)this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle((EntityLiving)this));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_SpiderPig);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6);
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
    
    public void onUpdate() {
        if (!this.world.isRemote) {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
        super.onUpdate();
    }
    
    protected void collideWithEntity(final Entity entityIn) {
        if (this.isBeingRidden() && entityIn instanceof EntityLiving && (!(entityIn instanceof EntitySpiderPig) || !(entityIn instanceof EntityGolem)) && this.getRNG().nextInt(2) == 0) {
            entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 2.5f);
        }
        super.collideWithEntity(entityIn);
    }
    
    public boolean canBeSteered() {
        return this.getControllingPassenger() instanceof EntityLivingBase;
    }
    
    public void notifyDataManagerChange(final DataParameter<?> key) {
        if (EntitySpiderPig.BOOST_TIME.equals(key) && this.world.isRemote) {
            this.boosting = true;
            this.boostTime = 0;
            this.totalBoostTime = (int)this.dataManager.get(EntitySpiderPig.BOOST_TIME);
        }
        super.notifyDataManagerChange(key);
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntitySpiderPig.BOOST_TIME, 0);
        this.dataManager.register(EntitySpiderPig.CLIMBING, (byte)0);
    }
    
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }
    
    public double getMountedYOffset() {
        return this.height * 0.5f;
    }
    
    public void setBesideClimbableBlock(final boolean climbing) {
        byte b0 = (byte)this.dataManager.get(EntitySpiderPig.CLIMBING);
        if (climbing) {
            b0 |= 0x1;
        }
        else {
            b0 &= 0xFFFFFFFE;
        }
        this.dataManager.set(EntitySpiderPig.CLIMBING, b0);
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
        return ((byte)this.dataManager.get(EntitySpiderPig.CLIMBING) & 0x1) != 0x0;
    }
    
    public static void registerFixesPig(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntitySpiderPig.class);
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
        if (itemstack.getItem() == Items.GOLDEN_CARROT) {
            this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 220, 3));
            this.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 430, 1));
            this.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 430, 2));
            this.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 150, 6));
            itemstack.shrink(1);
            return true;
        }
        if (itemstack.getItem() == Items.CARROT) {
            this.heal(1.0f);
            itemstack.shrink(1);
            return true;
        }
        if (itemstack.getItem() == Items.STRING) {
            this.heal(2.0f);
            itemstack.shrink(1);
            return true;
        }
        if (itemstack.getItem() == Items.SUGAR) {
            this.addPotionEffect(new PotionEffect(MobEffects.SPEED, 150, 10));
            itemstack.shrink(1);
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
        if (!this.world.isRemote) {}
    }
    
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_PIG;
    }
    
    public void onStruckByLightning(final EntityLightningBolt lightningBolt) {
        if (!this.world.isRemote && !this.isDead) {
            final EntityZombieSpiderPigman entitypigzombie = new EntityZombieSpiderPigman(this.world);
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
        if (this.collidedHorizontally) {
            this.setBesideClimbableBlock(true);
        }
    }
    
    public void fall(final float distance, final float damageMultiplier) {
    }
    
    public boolean boost() {
        if (this.boosting) {
            return false;
        }
        this.boosting = true;
        this.boostTime = 0;
        this.totalBoostTime = this.getRNG().nextInt(841) + 140;
        this.getDataManager().set(EntitySpiderPig.BOOST_TIME, this.totalBoostTime);
        return true;
    }
    
    public EntitySpiderPig createChild(final EntityAgeable ageable) {
        return new EntitySpiderPig(this.world);
    }
    
    public boolean isBreedingItem(final ItemStack stack) {
        return EntitySpiderPig.TEMPTATION_ITEMS.contains(stack.getItem());
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
    
    static {
        CLIMBING = EntityDataManager.createKey(EntitySpiderPig.class, DataSerializers.BYTE);
        BOOST_TIME = EntityDataManager.createKey(EntitySpiderPig.class, DataSerializers.VARINT);
        TEMPTATION_ITEMS = Sets.newHashSet(new Item[] { Items.CARROT, Items.POTATO, Items.BEETROOT });
    }
}
