//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.*;
import mmm.eventHandler.*;
import javax.annotation.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.datafix.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.math.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.network.datasync.*;

public class EntityEnderManSkeleton extends EntityMob implements IRangedAttackMob, IMutant
{
    public int weaponType;
    private static final DataParameter<Boolean> SWINGING_ARMS;
    public final EntityAIAttackRangedBow<EntityEnderManSkeleton> aiArrowAttack;
    public final EntityAIAttackMelee aiAttackOnCollide;
    
    public EntityEnderManSkeleton(final World worldIn) {
        super(worldIn);
        this.aiArrowAttack = (EntityAIAttackRangedBow<EntityEnderManSkeleton>)new EntityAIAttackRangedBow((EntityMob)this, 1.0, 25, 1.0f);
        this.aiAttackOnCollide = new EntityAIAttackMelee((EntityCreature)this, 1.2, false) {
            public void resetTask() {
                super.resetTask();
                EntityEnderManSkeleton.this.setSwingingArms(false);
            }
            
            public void startExecuting() {
                super.startExecuting();
                EntityEnderManSkeleton.this.setSwingingArms(true);
            }
        };
        this.weaponType = this.rand.nextInt(3);
        this.setCombatTask();
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0));
        this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, true));
        this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityWolf.class, true));
        this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityGolem.class, true));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_EndermanSkeleton);
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register((DataParameter)EntityEnderManSkeleton.SWINGING_ARMS, (Object)false);
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    public void updateRidden() {
        super.updateRidden();
        if (this.getRidingEntity() instanceof EntityCreature) {
            final EntityCreature entitycreature = (EntityCreature)this.getRidingEntity();
            this.renderYawOffset = entitycreature.renderYawOffset;
        }
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        this.setCombatTask();
        this.SwitchWeapon();
        if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty()) {
            final Calendar calendar = this.world.getCurrentDate();
            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25f) {
                this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((this.rand.nextFloat() < 0.1f) ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
                this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0f;
            }
        }
        return livingdata;
    }
    
    public void setCombatTask() {
        if (this.world != null && !this.world.isRemote) {
            this.tasks.removeTask((EntityAIBase)this.aiAttackOnCollide);
            this.tasks.removeTask((EntityAIBase)this.aiArrowAttack);
            final ItemStack itemstack = this.getHeldItemMainhand();
            if (itemstack.getItem() == Items.BOW) {
                int i = 20;
                if (this.world.getDifficulty() != EnumDifficulty.HARD) {
                    i = 40;
                }
                this.aiArrowAttack.setAttackCooldown(i);
                this.tasks.addTask(4, (EntityAIBase)this.aiArrowAttack);
            }
            else {
                this.tasks.addTask(4, (EntityAIBase)this.aiAttackOnCollide);
            }
        }
    }
    
    public boolean attackEntityAsMob(final Entity par1Entity) {
        par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)ConfigHandler.ATK_EnderSkeleton_Melee);
        this.teleportRandomly();
        return true;
    }
    
    public static void registerFixesSkeleton(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, (Class)EntityEnderManSkeleton.class);
    }
    
    protected void dropFewItems(final boolean par1, final int par2) {
        final int i = this.rand.nextInt(3);
        for (int j = 0; j < i; ++j) {
            this.dropItem(Items.BONE, 1);
        }
        for (int j = 0; j < i; ++j) {
            this.dropItem(Items.ARROW, 1);
        }
        for (int j = 0; j < i; ++j) {
            this.dropItem(Items.ENDER_PEARL, 1);
        }
    }
    
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDERMEN_AMBIENT;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }
    
    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_SKELETON_STEP;
    }
    
    protected boolean teleportRandomly() {
        final double d0 = this.posX + (this.rand.nextDouble() - 0.5) * 32.0;
        final double d2 = this.posY + (this.rand.nextInt(16) - 8);
        final double d3 = this.posZ + (this.rand.nextDouble() - 0.5) * 32.0;
        return this.teleportTo(d0, d2, d3);
    }
    
    protected boolean teleportToEntity(final Entity p_70816_1_) {
        Vec3d vec3d = new Vec3d(this.posX - p_70816_1_.posX, this.getEntityBoundingBox().minY + this.height / 2.0f - p_70816_1_.posY + p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
        vec3d = vec3d.normalize();
        final double d0 = 16.0;
        final double d2 = this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - vec3d.x * 16.0;
        final double d3 = this.posY + (this.rand.nextInt(16) - 8) - vec3d.y * 16.0;
        final double d4 = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - vec3d.z * 16.0;
        return this.teleportTo(d2, d3, d4);
    }
    
    private boolean teleportTo(final double x, final double y, final double z) {
        final EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)this, x, y, z, 0.0f);
        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
            return false;
        }
        final boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());
        if (flag) {
            this.world.playSound((EntityPlayer)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0f, 1.0f);
            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
        }
        return flag;
    }
    
    public void SwitchWeapon() {
        switch (this.weaponType) {
            case 1: {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack((Item)Items.BOW));
                this.setCombatTask();
                this.weaponType = this.rand.nextInt(3);
                break;
            }
            case 2: {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
                this.setCombatTask();
                this.weaponType = this.rand.nextInt(3);
                break;
            }
            default: {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_AXE));
                this.setCombatTask();
                this.weaponType = this.rand.nextInt(3);
                break;
            }
        }
    }
    
    public void onDeath(final DamageSource cause) {
        super.onDeath(cause);
        if (cause.getTrueSource() instanceof EntityCreeper) {
            final EntityCreeper entitycreeper = (EntityCreeper)cause.getTrueSource();
            if (entitycreeper.getPowered() && entitycreeper.ableToCauseSkullDrop()) {
                entitycreeper.incrementDroppedSkulls();
                this.entityDropItem(new ItemStack(Items.SKULL, 1, 0), 0.0f);
            }
        }
    }
    
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float distanceFactor) {
        final EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.world, (EntityLivingBase)this);
        final double d0 = target.posX - this.posX;
        final double d2 = target.getEntityBoundingBox().minY + target.height / 2.0f - entitytippedarrow.posY;
        final double d3 = target.posZ - this.posZ;
        final double d4 = MathHelper.sqrt(d0 * d0 + d3 * d3);
        entitytippedarrow.shoot(d0, d2 + d4 * 0.20000000298023224, d3, 1.6f, (float)(14 - this.world.getDifficulty().getDifficultyId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        entitytippedarrow.setIsCritical(true);
        entitytippedarrow.setDamage((double)ConfigHandler.ATK_EnderSkeleton_Arrow);
        this.world.spawnEntity((Entity)entitytippedarrow);
        this.teleportRandomly();
    }
    
    protected EntityTippedArrow getArrow(final float p_190726_1_) {
        final EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.world, (EntityLivingBase)this);
        return entitytippedarrow;
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setCombatTask();
    }
    
    public void setItemStackToSlot(final EntityEquipmentSlot slotIn, final ItemStack stack) {
        super.setItemStackToSlot(slotIn, stack);
        if (!this.world.isRemote && slotIn == EntityEquipmentSlot.MAINHAND) {
            this.setCombatTask();
        }
    }
    
    public float getEyeHeight() {
        return 1.74f;
    }
    
    public double getYOffset() {
        return -0.6;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isSwingingArms() {
        return (boolean)this.dataManager.get((DataParameter)EntityEnderManSkeleton.SWINGING_ARMS);
    }
    
    public void setSwingingArms(final boolean swingingArms) {
        this.dataManager.set((DataParameter)EntityEnderManSkeleton.SWINGING_ARMS, (Object)swingingArms);
    }
    
    static {
        SWINGING_ARMS = EntityDataManager.createKey((Class)EntityEnderManSkeleton.class, DataSerializers.BOOLEAN);
    }
}
