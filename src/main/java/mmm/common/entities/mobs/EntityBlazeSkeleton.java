//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import javax.annotation.*;
import java.util.*;
import net.minecraft.util.datafix.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import mmm.eventHandler.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.network.datasync.*;
import net.minecraft.entity.monster.*;

public class EntityBlazeSkeleton extends EntityMob implements IRangedAttackMob, IMutant
{
    public static final net.minecraft.world.biome.Biome[] SPAWN_BIOMES = new net.minecraft.world.biome.Biome[] { Biomes.HELL };
    private static final DataParameter<Boolean> SWINGING_ARMS;
    private static final DataParameter<Boolean> IS_CHILD;
    private float heightOffset;
    private int heightOffsetUpdateTime;
    private float zombieWidth;
    private float zombieHeight;
    
    public EntityBlazeSkeleton(final World worldIn) {
        super(worldIn);
        this.heightOffset = 0.5f;
        this.zombieWidth = -1.0f;
        this.setPathPriority(PathNodeType.WATER, -1.0f);
        this.setPathPriority(PathNodeType.LAVA, 8.0f);
        this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0f);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0f);
        this.isImmuneToFire = true;
        this.setSize(0.7f, 1.7f);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(6, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityGolem.class, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0, 0.0f));
        this.tasks.addTask(8, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.tasks.addTask(2, new EntityAIAttackRangedBow((EntityMob)this, 0.4, 12, 240.0f));
    }
    
    public void fall(final float distance, final float damageMultiplier) {
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityBlazeSkeleton.SWINGING_ARMS, false);
        this.getDataManager().register(EntityBlazeSkeleton.IS_CHILD, false);
    }
    
    public boolean isChild() {
        return (boolean)this.getDataManager().get(EntityBlazeSkeleton.IS_CHILD);
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
    
    public void setChild(final boolean childZombie) {
        this.getDataManager().set(EntityBlazeSkeleton.IS_CHILD, childZombie);
        if (this.world == null || this.world.isRemote || childZombie) {}
        this.setChildSize(childZombie);
    }
    
    public void notifyDataManagerChange(final DataParameter<?> key) {
        if (EntityBlazeSkeleton.IS_CHILD.equals(key)) {
            this.setChildSize(this.isChild());
        }
        super.notifyDataManagerChange(key);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if (this.isChild()) {
            compound.setBoolean("IsBaby", true);
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.getBoolean("IsBaby")) {
            this.setChild(true);
        }
    }
    
    public void setChildSize(final boolean isChild) {
        this.multiplySize(isChild ? 0.5f : 1.0f);
    }
    
    protected final void setSize(final float width, final float height) {
        final boolean flag = this.zombieWidth > 0.0f && this.zombieHeight > 0.0f;
        this.zombieWidth = width;
        this.zombieHeight = height;
        if (!flag) {
            this.multiplySize(1.0f);
        }
    }
    
    protected final void multiplySize(final float size) {
        super.setSize(this.zombieWidth * size, this.zombieHeight * size);
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
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack((Item)Items.BOW));
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
        EntityLiving.registerFixesMob(fixer, EntityBlazeSkeleton.class);
    }
    
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BLAZE_AMBIENT;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_BLAZE_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
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
        if (this.getAttackTarget() != null && this.onGround) {
            this.motionY += (0.40000001192092893 - this.motionY) * 0.30000001192092896;
            this.isAirBorne = true;
        }
        final EntityLivingBase entitylivingbase = this.getAttackTarget();
        if (entitylivingbase != null && entitylivingbase.posY + entitylivingbase.getEyeHeight() > this.posY + this.getEyeHeight() + this.heightOffset) {
            this.motionY += (0.40000001192092893 - this.motionY) * 0.40000001192092893;
            this.isAirBorne = true;
        }
        super.updateAITasks();
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_BlazeSkeleton);
    }
    
    protected void dropFewItems(final boolean par1, final int par2) {
        final int i = this.rand.nextInt(4);
        for (int j = 0; j < i; ++j) {
            this.dropItem(Items.BLAZE_ROD, 1);
        }
        for (int j = 1; j < i; ++j) {
            this.dropItem(Items.BONE, 1);
        }
        for (int j = 2; j < i; ++j) {
            this.dropItem(Items.ARROW, 1);
        }
        for (int j = 4; j < i; ++j) {
            this.dropItem(Items.BLAZE_POWDER, 1);
        }
        for (int j = 5; j < i; ++j) {
            this.dropItem(Items.FIRE_CHARGE, 1);
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
        entitytippedarrow.setFire(5);
        if (target.isBurning() && ConfigHandler.S_BlazeSkeletonDualDamage) {
            entitytippedarrow.setDamage((double)(ConfigHandler.ATK_BlazeSkeleton_Arrow * 2));
        }
        else {
            entitytippedarrow.setDamage((double)ConfigHandler.ATK_BlazeSkeleton_Arrow);
        }
        this.world.spawnEntity(entitytippedarrow);
    }
    
    protected EntityArrow getArrow(final float p_190726_1_) {
        final EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.world, (EntityLivingBase)this);
        entitytippedarrow.setEnchantmentEffectsFromEntity((EntityLivingBase)this, p_190726_1_);
        return (EntityArrow)entitytippedarrow;
    }
    
    public float getEyeHeight() {
        return 1.74f;
    }
    
    public double getYOffset() {
        return -0.6;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isSwingingArms() {
        return (boolean)this.dataManager.get(EntityBlazeSkeleton.SWINGING_ARMS);
    }
    
    public void setSwingingArms(final boolean swingingArms) {
        this.dataManager.set(EntityBlazeSkeleton.SWINGING_ARMS, swingingArms);
    }
    
    static {
        SWINGING_ARMS = EntityDataManager.createKey(EntityBlazeSkeleton.class, DataSerializers.BOOLEAN);
        IS_CHILD = EntityDataManager.createKey(EntityBlazeSkeleton.class, DataSerializers.BOOLEAN);
    }
    
    class GroupData implements IEntityLivingData
    {
        public boolean isChild;
        
        private GroupData(final boolean p_i47328_2_) {
            this.isChild = p_i47328_2_;
        }
    }
}
