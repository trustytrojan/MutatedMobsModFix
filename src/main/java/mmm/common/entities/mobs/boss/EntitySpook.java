//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs.boss;

import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.ai.*;
import mmm.eventHandler.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.potion.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.world.storage.loot.*;
import javax.annotation.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraftforge.common.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.network.datasync.*;

public class EntitySpook extends EntityMob implements IBoss, IMutant
{
    private static final DataParameter<Byte> CLIMBING;
    private static final UUID BABY_SPEED_BOOST_ID;
    private static final AttributeModifier BABY_SPEED_BOOST;
    private static final DataParameter<Boolean> IS_CHILD;
    private static final DataParameter<Integer> STATE;
    private static final DataParameter<Boolean> POWERED;
    private final BossInfoServer bossInfo;
    private static final DataParameter<Integer> VILLAGER_TYPE;
    private static final DataParameter<Boolean> ARMS_RAISED;
    private final EntityAIBreakDoor breakDoor;
    private boolean isBreakDoorsTaskSet;
    private float zombieWidth;
    private float zombieHeight;
    private int punchExplosion;
    public float ExtraDamage;
    
    public EntitySpook(final World worldIn) {
        super(worldIn);
        this.bossInfo = (BossInfoServer)new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS).setDarkenSky(true);
        this.breakDoor = new EntityAIBreakDoor((EntityLiving)this);
        this.zombieWidth = -1.0f;
        this.punchExplosion = 2;
        this.ExtraDamage = 5.0f;
        this.setSize(0.6f, 1.95f);
        this.isImmuneToFire = true;
        this.isImmuneToExplosions();
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_HOE));
        this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0));
        this.tasks.addTask(7, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0));
        this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.applyEntityAI();
    }
    
    public void setCustomNameTag(final String name) {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }
    
    protected void applyEntityAI() {
        this.tasks.addTask(6, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, 1.0, false));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, true, new Class[] { EntityPigZombie.class }));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0, true));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityLivingBase.class, true));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_Spook);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(65.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3300000041723251);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ConfigHandler.ATK_Spook);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0);
    }
    
    public int getCreeperState() {
        return (int)this.dataManager.get((DataParameter)EntitySpook.STATE);
    }
    
    public void setCreeperState(final int state) {
        this.dataManager.set((DataParameter)EntitySpook.STATE, (Object)state);
    }
    
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register((DataParameter)EntitySpook.IS_CHILD, (Object)false);
        this.getDataManager().register((DataParameter)EntitySpook.ARMS_RAISED, (Object)false);
        this.dataManager.register((DataParameter)EntitySpook.STATE, (Object)(-1));
        this.dataManager.register((DataParameter)EntitySpook.POWERED, (Object)false);
        this.dataManager.register((DataParameter)EntitySpook.CLIMBING, (Object)0);
    }
    
    public void setArmsRaised(final boolean armsRaised) {
        this.getDataManager().set((DataParameter)EntitySpook.ARMS_RAISED, (Object)armsRaised);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isArmsRaised() {
        return (boolean)this.getDataManager().get((DataParameter)EntitySpook.ARMS_RAISED);
    }
    
    public boolean isBreakDoorsTaskSet() {
        return this.isBreakDoorsTaskSet;
    }
    
    public void setBreakDoorsAItask(final boolean enabled) {
        if (this.isBreakDoorsTaskSet != enabled) {
            this.isBreakDoorsTaskSet = enabled;
            ((PathNavigateGround)this.getNavigator()).setBreakDoors(enabled);
            if (enabled) {
                this.tasks.addTask(1, (EntityAIBase)this.breakDoor);
            }
            else {
                this.tasks.removeTask((EntityAIBase)this.breakDoor);
            }
        }
    }
    
    public boolean isChild() {
        return (boolean)this.getDataManager().get((DataParameter)EntitySpook.IS_CHILD);
    }
    
    protected int getExperiencePoints(final EntityPlayer player) {
        if (this.isChild()) {
            this.experienceValue *= (int)2.5f;
        }
        return super.getExperiencePoints(player);
    }
    
    public void setChild(final boolean childZombie) {
        this.getDataManager().set((DataParameter)EntitySpook.IS_CHILD, (Object)childZombie);
        if (this.world != null && !this.world.isRemote) {
            final IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            iattributeinstance.removeModifier(EntitySpook.BABY_SPEED_BOOST);
            if (childZombie) {
                iattributeinstance.applyModifier(EntitySpook.BABY_SPEED_BOOST);
            }
        }
        this.setChildSize(childZombie);
    }
    
    public void notifyDataManagerChange(final DataParameter<?> key) {
        if (EntitySpook.IS_CHILD.equals((Object)key)) {
            this.setChildSize(this.isChild());
        }
        super.notifyDataManagerChange((DataParameter)key);
    }
    
    protected boolean teleportRandomly() {
        final double d0 = this.posX + (this.rand.nextDouble() - 0.5) * 16.0;
        final double d2 = this.posY + (this.rand.nextInt(16) - 8);
        final double d3 = this.posZ + (this.rand.nextDouble() - 0.5) * 16.0;
        return this.teleportTo(d0, d2, d3);
    }
    
    protected boolean teleportToEntity(final Entity p_70816_1_) {
        Vec3d vec3d = new Vec3d(this.posX - p_70816_1_.posX, this.getEntityBoundingBox().minY + this.height / 2.0f - p_70816_1_.posY + p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
        vec3d = vec3d.normalize();
        final double d0 = 16.0;
        final double d2 = this.posX + (this.rand.nextDouble() - 0.5) * 2.0 - vec3d.x * 4.0;
        final double d3 = this.posY + (this.rand.nextInt(16) - 2) - vec3d.y * 4.0;
        final double d4 = this.posZ + (this.rand.nextDouble() - 0.5) * 2.0 - vec3d.z * 4.0;
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
    
    public void onLivingUpdate() {
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        super.onLivingUpdate();
    }
    
    public void addTrackingPlayer(final EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }
    
    public void removeTrackingPlayer(final EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }
    
    public void fall(final float distance, final float damageMultiplier) {
    }
    
    public boolean isNonBoss() {
        return false;
    }
    
    protected void updateAITasks() {
        if (this.getHealth() <= 160.0f) {
            this.dataManager.set((DataParameter)EntitySpook.POWERED, (Object)true);
            this.LightningPower();
        }
        else {
            if (this.getHealth() > 160.0f) {
                this.dataManager.set((DataParameter)EntitySpook.POWERED, (Object)false);
                this.RemoveLightningPower();
            }
            if (this.isPotionActive(MobEffects.LEVITATION)) {
                this.clearActivePotions();
            }
        }
        super.updateAITasks();
    }
    
    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        if (super.attackEntityFrom(source, amount)) {
            EntityLivingBase entitylivingbase = this.getAttackTarget();
            if (entitylivingbase == null && source.getTrueSource() instanceof EntityLivingBase) {
                entitylivingbase = (EntityLivingBase)source.getTrueSource();
            }
            return true;
        }
        return false;
    }
    
    public boolean attackEntityAsMob(final Entity entityIn) {
        final boolean flag = super.attackEntityAsMob(entityIn);
        if (flag) {
            final float f = this.world.getDifficultyForLocation(new BlockPos((Entity)this)).getAdditionalDifficulty();
            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3f) {
                entityIn.setFire(2 * (int)f);
            }
            if (this.onGround) {
                if (this.rand.nextInt(10) == 0) {
                    final double d0 = entityIn.posX - this.posX;
                    final double d2 = entityIn.posZ - this.posZ;
                    final float f2 = MathHelper.sqrt(d0 * d0 + d2 * d2);
                    this.motionX = d0 / f2 * 0.5 * 1.200000011920929 + this.motionX * 0.6000000029802323;
                    this.motionZ = d2 / f2 * 0.5 * 1.200000011920929 + this.motionZ * 0.6000000029802323;
                    this.motionY = 0.4000000059604645;
                    this.attackEntityFrom(DamageSource.MAGIC, this.getAttackTarget().getMaxHealth() / this.ExtraDamage);
                }
                if (this.rand.nextInt(25) == 0) {
                    this.teleportRandomly();
                }
                if (this.rand.nextInt(35) == 0) {
                    this.world.createExplosion((Entity)this, this.posX, this.posY, this.posZ, (float)this.punchExplosion, false);
                }
                if (this.rand.nextInt(30) == 0) {
                    ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 100));
                }
                if (this.rand.nextInt(50) == 0) {
                    final double d0 = entityIn.posY + entityIn.getEyeHeight() - 1.100000023841858;
                    final double d2 = entityIn.posX + entityIn.motionX - this.posX;
                    final double d3 = d0 - this.posY;
                    final double d4 = entityIn.posZ + entityIn.motionZ - this.posZ;
                    final float f3 = MathHelper.sqrt(d2 * d2 + d4 * d4);
                    final PotionType potiontype = PotionTypes.HARMING;
                    final EntityPotion entityPotion;
                    final EntityPotion entitypotion = entityPotion = new EntityPotion(this.world, (EntityLivingBase)this, PotionUtils.addPotionToItemStack(new ItemStack((Item)Items.SPLASH_POTION), potiontype));
                    entityPotion.rotationPitch += 20.0f;
                    entitypotion.shoot(d2, d3 + f * 0.2f, d4, 0.75f, 8.0f);
                    this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0f, 0.8f + this.rand.nextFloat() * 0.4f);
                    this.world.spawnEntity((Entity)entitypotion);
                }
                if (this.rand.nextInt(100) == 0) {
                    entityIn.motionY *= 1.5;
                }
            }
        }
        return flag;
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    public boolean isPotionApplicable(final PotionEffect potioneffectIn) {
        return potioneffectIn.getPotion() != MobEffects.POISON && super.isPotionApplicable(potioneffectIn);
    }
    
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ZOMBIE_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIE_DEATH;
    }
    
    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }
    
    protected void playStepSound(final BlockPos pos, final Block blockIn) {
        this.playSound(this.getStepSound(), 0.15f, 1.0f);
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote) {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
        if (this.getAttackTarget() != null) {
            this.setSprinting(true);
        }
        else {
            this.setSprinting(false);
        }
    }
    
    public void setBesideClimbableBlock(final boolean climbing) {
        byte b0 = (byte)this.dataManager.get((DataParameter)EntitySpook.CLIMBING);
        if (climbing) {
            b0 |= 0x1;
        }
        else {
            b0 &= 0xFFFFFFFE;
        }
        this.dataManager.set((DataParameter)EntitySpook.CLIMBING, (Object)b0);
    }
    
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_ZOMBIE;
    }
    
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }
    
    public double getMountedYOffset() {
        return this.height * 0.5f;
    }
    
    protected PathNavigate createNavigator(final World worldIn) {
        return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
    }
    
    public void setInWeb() {
    }
    
    public boolean isBesideClimbableBlock() {
        return ((byte)this.dataManager.get((DataParameter)EntitySpook.CLIMBING) & 0x1) != 0x0;
    }
    
    public static void registerFixesZombie(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, (Class)EntitySpook.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if (this.isChild()) {
            compound.setBoolean("IsBaby", true);
        }
        compound.setBoolean("CanBreakDoors", this.isBreakDoorsTaskSet());
        if ((Boolean)this.dataManager.get((DataParameter)EntitySpook.POWERED)) {
            compound.setBoolean("powered", true);
        }
        compound.setByte("ExplosionRadius", (byte)this.punchExplosion);
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.getBoolean("IsBaby")) {
            this.setChild(true);
        }
        this.setBreakDoorsAItask(compound.getBoolean("CanBreakDoors"));
        this.dataManager.set((DataParameter)EntitySpook.POWERED, (Object)compound.getBoolean("powered"));
        if (compound.hasKey("ExplosionRadius", 99)) {
            this.punchExplosion = compound.getByte("ExplosionRadius");
        }
    }
    
    public boolean getPowered() {
        return (boolean)this.dataManager.get((DataParameter)EntitySpook.POWERED);
    }
    
    public void LightningPower() {
        this.setSprinting(true);
        this.addPotionEffect(new PotionEffect(MobEffects.SPEED, 499995, 2));
        this.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 499995, 2));
        this.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 49995, 2));
        this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 49995, 0));
        this.punchExplosion = 4;
    }
    
    public void RemoveLightningPower() {
        this.setSprinting(false);
        this.addPotionEffect(new PotionEffect(MobEffects.SPEED, 0, 0));
        this.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 0, 0));
        this.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 0, 0));
        this.punchExplosion = 1;
    }
    
    public void onKillEntity(final EntityLivingBase entityLivingIn) {
        super.onKillEntity(entityLivingIn);
        if (this.getHealth() < this.getMaxHealth()) {
            this.heal(10.0f);
        }
    }
    
    public float getEyeHeight() {
        float f = 1.74f;
        if (this.isChild()) {
            f -= (float)0.81;
        }
        return f;
    }
    
    protected boolean canEquipItem(final ItemStack stack) {
        return (stack.getItem() != Items.EGG || !this.isChild() || !this.isRiding()) && super.canEquipItem(stack);
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        final float f = difficulty.getClampedAdditionalDifficulty();
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55f * f);
        if (livingdata == null) {
            livingdata = (IEntityLivingData)new GroupData(this.world.rand.nextFloat() < ForgeModContainer.zombieBabyChance);
        }
        if (livingdata instanceof GroupData) {
            final GroupData EntityZombieSpider$groupdata = (GroupData)livingdata;
            if (EntityZombieSpider$groupdata.isChild) {
                this.setChild(true);
            }
            this.setBreakDoorsAItask(this.rand.nextFloat() < f * 0.1f);
            this.setEquipmentBasedOnDifficulty(difficulty);
            this.setEnchantmentBasedOnDifficulty(difficulty);
            if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty()) {
                final Calendar calendar = this.world.getCurrentDate();
                if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25f) {
                    this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((this.rand.nextFloat() < 0.1f) ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
                    this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0f;
                }
            }
            this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806, 0));
            final double d0 = this.rand.nextDouble() * 1.5 * f;
            if (d0 > 1.0) {
                this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
            }
            if (this.rand.nextFloat() < f * 0.05f) {
                this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0 + 1.0, 2));
                this.setBreakDoorsAItask(true);
            }
        }
        return livingdata;
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
    
    public double getYOffset() {
        return this.isChild() ? 0.0 : -0.45;
    }
    
    static {
        CLIMBING = EntityDataManager.createKey((Class)EntitySpook.class, DataSerializers.BYTE);
        BABY_SPEED_BOOST_ID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
        BABY_SPEED_BOOST = new AttributeModifier(EntitySpook.BABY_SPEED_BOOST_ID, "Baby speed boost", 0.5, 1);
        IS_CHILD = EntityDataManager.createKey((Class)EntitySpook.class, DataSerializers.BOOLEAN);
        STATE = EntityDataManager.createKey((Class)EntitySpook.class, DataSerializers.VARINT);
        POWERED = EntityDataManager.createKey((Class)EntitySpook.class, DataSerializers.BOOLEAN);
        VILLAGER_TYPE = EntityDataManager.createKey((Class)EntitySpook.class, DataSerializers.VARINT);
        ARMS_RAISED = EntityDataManager.createKey((Class)EntitySpook.class, DataSerializers.BOOLEAN);
    }
    
    class GroupData implements IEntityLivingData
    {
        public boolean isChild;
        
        private GroupData(final boolean p_i47328_2_) {
            this.isChild = p_i47328_2_;
        }
    }
}
