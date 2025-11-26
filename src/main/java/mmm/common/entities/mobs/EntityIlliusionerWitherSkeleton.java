//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.ai.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import javax.annotation.*;
import java.util.*;
import net.minecraft.util.datafix.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import mmm.eventHandler.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.network.datasync.*;

public class EntityIlliusionerWitherSkeleton extends EntityMob implements IMutant
{
    private static final DataParameter<Boolean> SWINGING_ARMS;
    private static final DataParameter<Boolean> SPLIT;
    private static final DataParameter<Boolean> Clone;
    private float heightOffset;
    private int heightOffsetUpdateTime;
    private float zombieWidth;
    private float zombieHeight;
    public boolean CanSplit;
    
    public EntityIlliusionerWitherSkeleton(final World worldIn) {
        super(worldIn);
        this.heightOffset = 0.5f;
        this.zombieWidth = -1.0f;
        this.setPathPriority(PathNodeType.WATER, -1.0f);
        this.setPathPriority(PathNodeType.LAVA, 8.0f);
        this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0f);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0f);
        this.isImmuneToFire = true;
        this.setSize(0.7f, 1.7f);
        this.CanSplit = true;
        if (this.isIlliusion()) {
            return;
        }
        this.experienceValue = 3;
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityPlayer.class, true));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityGolem.class, true));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0));
        this.tasks.addTask(7, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0, 0.0f));
        this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0, false));
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityIlliusionerWitherSkeleton.SWINGING_ARMS, false);
        this.dataManager.register(EntityIlliusionerWitherSkeleton.Clone, false);
        this.dataManager.register(EntityIlliusionerWitherSkeleton.SPLIT, false);
    }
    
    protected int getExperiencePoints(final EntityPlayer player) {
        if (this.isChild()) {
            this.experienceValue *= (int)2.5f;
        }
        return super.getExperiencePoints(player);
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Illiusion", this.isIlliusion());
        compound.setBoolean("Split", this.HasSplited());
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.getBoolean("Illiusion")) {
            this.Illiusion();
        }
        if (compound.getBoolean("Split")) {
            this.SetSplit();
        }
    }
    
    private void SetSplit() {
        this.dataManager.set(EntityIlliusionerWitherSkeleton.SPLIT, false);
        this.CanSplit = false;
    }
    
    public void split() {
        if (!this.world.isRemote && !this.isDead) {
            final EntityIlliusionerWitherSkeleton entitypigzombie = new EntityIlliusionerWitherSkeleton(this.world);
            final EntityIlliusionerWitherSkeleton entitypigzombie2 = new EntityIlliusionerWitherSkeleton(this.world);
            final EntityIlliusionerWitherSkeleton entitypigzombie3 = new EntityIlliusionerWitherSkeleton(this.world);
            final EntityIlliusionerWitherSkeleton entitypigzombie4 = new EntityIlliusionerWitherSkeleton(this.world);
            final EntityIlliusionerWitherSkeleton entitypigzombie5 = new EntityIlliusionerWitherSkeleton(this.world);
            entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie.setAttackTarget(this.getAttackTarget());
            entitypigzombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
            entitypigzombie.Illiusion();
            entitypigzombie2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie2.setAttackTarget(this.getAttackTarget());
            entitypigzombie2.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
            entitypigzombie2.Illiusion();
            entitypigzombie3.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie3.setAttackTarget(this.getAttackTarget());
            entitypigzombie3.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
            entitypigzombie3.Illiusion();
            entitypigzombie4.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie4.setAttackTarget(this.getAttackTarget());
            entitypigzombie4.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
            entitypigzombie4.Illiusion();
            entitypigzombie5.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie5.setAttackTarget(this.getAttackTarget());
            entitypigzombie5.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
            entitypigzombie5.Illiusion();
            if (this.hasCustomName()) {
                entitypigzombie.setCustomNameTag(this.getCustomNameTag());
                entitypigzombie.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
                entitypigzombie2.setCustomNameTag(this.getCustomNameTag());
                entitypigzombie2.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
                entitypigzombie3.setCustomNameTag(this.getCustomNameTag());
                entitypigzombie3.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
                entitypigzombie4.setCustomNameTag(this.getCustomNameTag());
                entitypigzombie4.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
                entitypigzombie5.setCustomNameTag(this.getCustomNameTag());
                entitypigzombie5.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            this.world.spawnEntity((Entity)entitypigzombie);
            this.world.spawnEntity((Entity)entitypigzombie2);
            this.world.spawnEntity((Entity)entitypigzombie3);
            this.world.spawnEntity((Entity)entitypigzombie4);
            this.world.spawnEntity((Entity)entitypigzombie5);
            this.WillSplit();
        }
    }
    
    public void updateRidden() {
        super.updateRidden();
        if (this.getRidingEntity() instanceof EntityCreature) {
            final EntityCreature entitycreature = (EntityCreature)this.getRidingEntity();
            this.renderYawOffset = entitycreature.renderYawOffset;
        }
    }
    
    protected void setEquipmentBasedOnDifficulty(final DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
        this.setEnchantmentBasedOnDifficulty(difficulty);
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty()) {
            final Calendar calendar = this.world.getCurrentDate();
            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25f) {
                this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((this.rand.nextFloat() < 0.1f) ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
                this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0f;
            }
        }
        return livingdata;
    }
    
    public static void registerFixesSkeleton(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityIlliusionerWitherSkeleton.class);
    }
    
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_WITHER_SKELETON_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WITHER_SKELETON_DEATH;
    }
    
    protected void updateAITasks() {
        if (this.getAttackTarget() != null && !this.isIlliusion() && this.CanSplit) {
            this.split();
        }
        super.updateAITasks();
    }
    
    public void onLivingUpdate() {
        if (this.world.isRemote) {
            if (this.rand.nextInt(24) == 0 && !this.isSilent()) {
                this.world.playSound(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f, false);
            }
            if (this.getAttackTarget() != null) {
                for (int i = 0; i < 2; ++i) {
                    this.world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0, new int[0]);
                }
            }
        }
        super.onLivingUpdate();
    }
    
    public boolean attackEntityAsMob(final Entity par1Entity) {
        if (!this.isIlliusion()) {
            ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.WITHER, 240, 2));
        }
        else {
            par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 0.0f);
        }
        return true;
    }
    
    public boolean isIlliusion() {
        return (boolean)this.dataManager.get(EntityIlliusionerWitherSkeleton.Clone);
    }
    
    public boolean HasSplited() {
        return (boolean)this.dataManager.get(EntityIlliusionerWitherSkeleton.SPLIT);
    }
    
    public void Illiusion() {
        this.dataManager.set(EntityIlliusionerWitherSkeleton.Clone, true);
    }
    
    public void WillSplit() {
        this.dataManager.set(EntityIlliusionerWitherSkeleton.SPLIT, false);
        this.CanSplit = false;
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_IlliusionerWitherSkeleton);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(75.0);
        if (this.isIlliusion()) {
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ConfigHandler.ATK_WitherSkeletonIlliusioner_Clone);
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ConfigHandler.ATK_WitherSkeletonIlliusioner);
        }
    }
    
    protected void dropFewItems(final boolean par1, final int par2) {
        if (!this.isIlliusion()) {
            final int i = this.rand.nextInt(4);
            for (int j = 1; j < i; ++j) {
                this.dropItem(Items.BONE, 1);
            }
            for (int j = 2; j < i; ++j) {
                this.dropItem(Items.ARROW, 1);
            }
            for (int j = 4; j < i; ++j) {
                this.entityDropItem(new ItemStack(Items.SKULL, 1, 1), 0.0f);
            }
            for (int j = 5; j < i; ++j) {
                this.dropItem(Items.GLOWSTONE_DUST, 1);
            }
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
        return (boolean)this.dataManager.get(EntityIlliusionerWitherSkeleton.SWINGING_ARMS);
    }
    
    public void setSwingingArms(final boolean swingingArms) {
        this.dataManager.set(EntityIlliusionerWitherSkeleton.SWINGING_ARMS, swingingArms);
    }
    
    static {
        SWINGING_ARMS = EntityDataManager.createKey(EntityIlliusionerWitherSkeleton.class, DataSerializers.BOOLEAN);
        SPLIT = EntityDataManager.createKey(EntityIlliusionerWitherSkeleton.class, DataSerializers.BOOLEAN);
        Clone = EntityDataManager.createKey(EntityIlliusionerWitherSkeleton.class, DataSerializers.BOOLEAN);
    }
    
    class GroupData implements IEntityLivingData
    {
        public boolean isChild;
        
        private GroupData(final boolean p_i47328_2_) {
            this.isChild = p_i47328_2_;
        }
    }
}
