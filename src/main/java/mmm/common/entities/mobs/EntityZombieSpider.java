//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.mobs.interfaces.*;
import mmm.common.entities.mobs.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import mmm.eventHandler.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.potion.*;
import net.minecraft.block.*;
import net.minecraft.world.storage.loot.*;
import javax.annotation.*;
import net.minecraft.pathfinding.*;
import net.minecraft.world.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraftforge.common.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.network.datasync.*;

public class EntityZombieSpider extends EntityMob implements IMutant
{
    public static final net.minecraft.world.biome.Biome[] SPAWN_BIOMES = new net.minecraft.world.biome.Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH };
    private static final DataParameter<Byte> CLIMBING;
    private static final UUID BABY_SPEED_BOOST_ID;
    private static final AttributeModifier BABY_SPEED_BOOST;
    private static final DataParameter<Boolean> IS_CHILD;
    private static final DataParameter<Integer> VILLAGER_TYPE;
    private static final DataParameter<Boolean> ARMS_RAISED;
    private final EntityAIBreakDoor breakDoor;
    private boolean isBreakDoorsTaskSet;
    private float zombieWidth;
    private float zombieHeight;
    
    public EntityZombieSpider(final World worldIn) {
        super(worldIn);
        this.breakDoor = new EntityAIBreakDoor((EntityLiving)this);
        this.zombieWidth = -1.0f;
        this.setSize(0.6f, 1.95f);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(2, new EntityAIZombieSpiderAttack(this, 1.0, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater((EntityCreature)this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAILookIdle((EntityLiving)this));
        this.applyEntityAI();
    }
    
    protected void applyEntityAI() {
        this.tasks.addTask(6, new EntityAIMoveThroughVillage((EntityCreature)this, 1.0, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget((EntityCreature)this, true, new Class[] { EntityPigZombie.class }));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget((EntityCreature)this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget((EntityCreature)this, EntityVillager.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget((EntityCreature)this, EntityIronGolem.class, true));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_ZombieSpider);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(65.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3300000041723251);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ConfigHandler.ATK_ZombieSpider);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0);
    }
    
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(EntityZombieSpider.IS_CHILD, false);
        this.getDataManager().register(EntityZombieSpider.ARMS_RAISED, false);
        this.dataManager.register(EntityZombieSpider.CLIMBING, (byte)0);
    }
    
    public void setArmsRaised(final boolean armsRaised) {
        this.getDataManager().set(EntityZombieSpider.ARMS_RAISED, armsRaised);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isArmsRaised() {
        return (boolean)this.getDataManager().get(EntityZombieSpider.ARMS_RAISED);
    }
    
    public boolean isBreakDoorsTaskSet() {
        return this.isBreakDoorsTaskSet;
    }
    
    public void setBreakDoorsAItask(final boolean enabled) {
        if (this.isBreakDoorsTaskSet != enabled) {
            this.isBreakDoorsTaskSet = enabled;
            ((PathNavigateGround)this.getNavigator()).setBreakDoors(enabled);
            if (enabled) {
                this.tasks.addTask(1, this.breakDoor);
            }
            else {
                this.tasks.removeTask(this.breakDoor);
            }
        }
    }
    
    public boolean isChild() {
        return (boolean)this.getDataManager().get(EntityZombieSpider.IS_CHILD);
    }
    
    protected int getExperiencePoints(final EntityPlayer player) {
        if (this.isChild()) {
            this.experienceValue *= (int)2.5f;
        }
        return super.getExperiencePoints(player);
    }
    
    public void setChild(final boolean childZombie) {
        this.getDataManager().set(EntityZombieSpider.IS_CHILD, childZombie);
        if (this.world != null && !this.world.isRemote) {
            final IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            iattributeinstance.removeModifier(EntityZombieSpider.BABY_SPEED_BOOST);
            if (childZombie) {
                iattributeinstance.applyModifier(EntityZombieSpider.BABY_SPEED_BOOST);
            }
        }
        this.setChildSize(childZombie);
    }
    
    public void notifyDataManagerChange(final DataParameter<?> key) {
        if (EntityZombieSpider.IS_CHILD.equals(key)) {
            this.setChildSize(this.isChild());
        }
        super.notifyDataManagerChange(key);
    }
    
    public void onLivingUpdate() {
        if (this.world.isDaytime() && !this.world.isRemote && !this.isChild() && this.shouldBurnInDay()) {
            final float f = this.getBrightness();
            if (f > 0.5f && this.rand.nextFloat() * 30.0f < (f - 0.4f) * 2.0f && this.world.canSeeSky(new BlockPos(this.posX, this.posY + this.getEyeHeight(), this.posZ))) {
                boolean flag = true;
                final ItemStack itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                if (!itemstack.isEmpty()) {
                    if (itemstack.isItemStackDamageable()) {
                        itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));
                        if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
                            this.renderBrokenItemStack(itemstack);
                            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }
                    flag = false;
                }
                if (flag) {
                    this.setFire(8);
                }
            }
        }
        super.onLivingUpdate();
    }
    
    protected boolean shouldBurnInDay() {
        return true;
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
            if (this.onGround && this.rand.nextInt(10) == 0) {
                final double d0 = entityIn.posX - this.posX;
                final double d2 = entityIn.posZ - this.posZ;
                final float f2 = MathHelper.sqrt(d0 * d0 + d2 * d2);
                this.motionX = d0 / f2 * 0.5 * 1.200000011920929 + this.motionX * 0.6000000029802323;
                this.motionZ = d2 / f2 * 0.5 * 1.200000011920929 + this.motionZ * 0.6000000029802323;
                this.motionY = 0.4000000059604645;
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
        byte b0 = (byte)this.dataManager.get(EntityZombieSpider.CLIMBING);
        if (climbing) {
            b0 |= 0x1;
        }
        else {
            b0 &= 0xFFFFFFFE;
        }
        this.dataManager.set(EntityZombieSpider.CLIMBING, b0);
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
        return ((byte)this.dataManager.get(EntityZombieSpider.CLIMBING) & 0x1) != 0x0;
    }
    
    protected void setEquipmentBasedOnDifficulty(final DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);
        if (this.rand.nextFloat() < ((this.world.getDifficulty() == EnumDifficulty.HARD) ? 0.05f : 0.01f)) {
            final int i = this.rand.nextInt(3);
            if (i == 0) {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
            }
            else {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
            }
        }
    }
    
    public static void registerFixesZombie(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityZombieSpider.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if (this.isChild()) {
            compound.setBoolean("IsBaby", true);
        }
        compound.setBoolean("CanBreakDoors", this.isBreakDoorsTaskSet());
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.getBoolean("IsBaby")) {
            this.setChild(true);
        }
        this.setBreakDoorsAItask(compound.getBoolean("CanBreakDoors"));
    }
    
    public void onKillEntity(final EntityLivingBase entityLivingIn) {
        super.onKillEntity(entityLivingIn);
        if ((this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD) && entityLivingIn instanceof EntityVillager) {
            if (this.world.getDifficulty() != EnumDifficulty.HARD && this.rand.nextBoolean()) {
                return;
            }
            final EntityVillager entityvillager = (EntityVillager)entityLivingIn;
            final EntityZombieVillager EntityZombieSpidervillager = new EntityZombieVillager(this.world);
            EntityZombieSpidervillager.copyLocationAndAnglesFrom((Entity)entityvillager);
            this.world.removeEntity((Entity)entityvillager);
            EntityZombieSpidervillager.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)EntityZombieSpidervillager)), (IEntityLivingData)new GroupData(false));
            EntityZombieSpidervillager.setProfession(entityvillager.getProfession());
            EntityZombieSpidervillager.setChild(entityvillager.isChild());
            EntityZombieSpidervillager.setNoAI(entityvillager.isAIDisabled());
            if (entityvillager.hasCustomName()) {
                EntityZombieSpidervillager.setCustomNameTag(entityvillager.getCustomNameTag());
                EntityZombieSpidervillager.setAlwaysRenderNameTag(entityvillager.getAlwaysRenderNameTag());
            }
            this.world.spawnEntity((Entity)EntityZombieSpidervillager);
            this.world.playEvent((EntityPlayer)null, 1026, new BlockPos((Entity)this), 0);
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
                if (this.world.rand.nextFloat() < 0.05) {
                    final List<EntityChicken> list = (List<EntityChicken>)this.world.getEntitiesWithinAABB(EntityChicken.class, this.getEntityBoundingBox().grow(5.0, 3.0, 5.0), EntitySelectors.IS_STANDALONE);
                    if (!list.isEmpty()) {
                        final EntityChicken entitychicken = list.get(0);
                        entitychicken.setChickenJockey(true);
                        this.startRiding((Entity)entitychicken);
                    }
                }
                else if (this.world.rand.nextFloat() < 0.05) {
                    final EntityChicken entitychicken2 = new EntityChicken(this.world);
                    entitychicken2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                    entitychicken2.onInitialSpawn(difficulty, (IEntityLivingData)null);
                    entitychicken2.setChickenJockey(true);
                    this.world.spawnEntity((Entity)entitychicken2);
                    this.startRiding((Entity)entitychicken2);
                }
            }
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
    
    public void onDeath(final DamageSource cause) {
        super.onDeath(cause);
        if (cause.getTrueSource() instanceof EntityCreeper) {
            final EntityCreeper entitycreeper = (EntityCreeper)cause.getTrueSource();
            if (entitycreeper.getPowered() && entitycreeper.ableToCauseSkullDrop()) {
                entitycreeper.incrementDroppedSkulls();
                final ItemStack itemstack = this.getSkullDrop();
                if (!itemstack.isEmpty()) {
                    this.entityDropItem(itemstack, 0.0f);
                }
            }
        }
    }
    
    protected ItemStack getSkullDrop() {
        return new ItemStack(Items.SKULL, 1, 2);
    }
    
    static {
        CLIMBING = EntityDataManager.createKey(EntityZombieSpiderPigman.class, DataSerializers.BYTE);
        BABY_SPEED_BOOST_ID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
        BABY_SPEED_BOOST = new AttributeModifier(EntityZombieSpider.BABY_SPEED_BOOST_ID, "Baby speed boost", 0.5, 1);
        IS_CHILD = EntityDataManager.createKey(EntityZombieSpider.class, DataSerializers.BOOLEAN);
        VILLAGER_TYPE = EntityDataManager.createKey(EntityZombieSpider.class, DataSerializers.VARINT);
        ARMS_RAISED = EntityDataManager.createKey(EntityZombieSpider.class, DataSerializers.BOOLEAN);
    }
    
    class GroupData implements IEntityLivingData
    {
        public boolean isChild;
        
        private GroupData(final boolean p_i47328_2_) {
            this.isChild = p_i47328_2_;
        }
    }
}
