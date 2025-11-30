//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.mobs.interfaces.*;
import javax.annotation.*;
import mmm.eventHandler.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.math.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.network.datasync.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;

public class EntityZombieSpiderPigman extends EntityZombie implements IMutant
{
    public static final net.minecraft.world.biome.Biome[] SPAWN_BIOMES = new net.minecraft.world.biome.Biome[] { Biomes.HELL };
    private static final DataParameter<Boolean> CLIMBING;
    private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID;
    private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER;
    private int angerLevel;
    private int randomSoundDelay;
    private UUID angerTargetUUID;
    
    public EntityZombieSpiderPigman(final World worldIn) {
        super(worldIn);
        this.isImmuneToFire = true;
    }
    
    public void setRevengeTarget(@Nullable final EntityLivingBase livingBase) {
        super.setRevengeTarget(livingBase);
        if (livingBase != null) {
            this.angerTargetUUID = livingBase.getUniqueID();
        }
    }
    
    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new AIHurtByAggressor(this));
        this.targetTasks.addTask(2, new AITargetAggressor(this));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(EntityZombieSpiderPigman.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3200000041723251);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ConfigHandler.ATK_ZombieSpiderPigman);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_ZombieSpiderPigman);
    }
    
    protected void updateAITasks() {
        final IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        if (this.isAngry()) {
            if (!this.isChild() && !iattributeinstance.hasModifier(EntityZombieSpiderPigman.ATTACK_SPEED_BOOST_MODIFIER)) {
                iattributeinstance.applyModifier(EntityZombieSpiderPigman.ATTACK_SPEED_BOOST_MODIFIER);
            }
            --this.angerLevel;
        }
        else if (iattributeinstance.hasModifier(EntityZombieSpiderPigman.ATTACK_SPEED_BOOST_MODIFIER)) {
            iattributeinstance.removeModifier(EntityZombieSpiderPigman.ATTACK_SPEED_BOOST_MODIFIER);
        }
        if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0) {
            this.playSound(SoundEvents.ENTITY_ZOMBIE_PIG_ANGRY, this.getSoundVolume() * 2.0f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 1.8f);
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
        this.dataManager.register(EntityZombieSpiderPigman.CLIMBING, false);
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
        return (boolean)this.dataManager.get(EntityZombieSpiderPigman.CLIMBING);
    }
    
    public boolean attackEntityAsMob(final Entity par1Entity) {
        par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 8.0f);
        if (this.onGround && this.rand.nextInt(10) == 0) {
            final double d0 = par1Entity.posX - this.posX;
            final double d2 = par1Entity.posZ - this.posZ;
            final float f2 = MathHelper.sqrt(d0 * d0 + d2 * d2);
            this.motionX = d0 / f2 * 0.5 * 1.200000011920929 + this.motionX * 0.6000000029802323;
            this.motionZ = d2 / f2 * 0.5 * 1.200000011920929 + this.motionZ * 0.6000000029802323;
            this.motionY = 0.4000000059604645;
        }
        return true;
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    public boolean isPotionApplicable(final PotionEffect potioneffectIn) {
        return potioneffectIn.getPotion() != MobEffects.POISON && super.isPotionApplicable(potioneffectIn);
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
        this.dataManager.set(EntityZombieSpiderPigman.CLIMBING, climbing);
    }
    
    public static void registerFixesPigZombie(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityZombieSpiderPigman.class);
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
        EntityLiving.registerFixesMob(fixer, EntityZombieSpiderPigman.class);
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
            this.world.spawnEntity(entityskeleton);
            entityskeleton.startRiding(this);
        }
        if (this.world.rand.nextInt(80) == 0) {
            final EntityCreeper entityskeleton2 = new EntityCreeper(this.world);
            entityskeleton2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityskeleton2.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity(entityskeleton2);
            entityskeleton2.startRiding(this);
        }
        if (this.world.rand.nextInt(95) == 0) {
            final EntityZombie entityskeleton3 = new EntityZombie(this.world);
            entityskeleton3.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityskeleton3.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity(entityskeleton3);
            entityskeleton3.startRiding(this);
        }
        if (this.world.rand.nextInt(120) == 0) {
            final EntityHusk entityskeleton4 = new EntityHusk(this.world);
            entityskeleton4.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityskeleton4.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity(entityskeleton4);
            entityskeleton4.startRiding(this);
        }
        if (this.world.rand.nextInt(150) == 0) {
            final EntityWitch entityskeleton5 = new EntityWitch(this.world);
            entityskeleton5.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            entityskeleton5.onInitialSpawn(difficulty, (IEntityLivingData)null);
            this.world.spawnEntity(entityskeleton5);
            entityskeleton5.startRiding(this);
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
        return SoundEvents.ENTITY_ZOMBIE_PIG_AMBIENT;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ZOMBIE_PIG_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIE_PIG_DEATH;
    }
    
    protected void dropFewItems(final boolean par1, final int par2) {
        if (this.getClass() == EntityZombieSpiderPigman.class) {
            final int i = this.rand.nextInt(3);
            for (int j = 0; j < i; ++j) {
                this.dropItem(Items.SPIDER_EYE, 1);
            }
            for (int j = 0; j < i; ++j) {
                this.dropItem(Items.STRING, 1);
            }
            for (int j = 0; j < i; ++j) {
                this.dropItem(Items.ROTTEN_FLESH, 1);
            }
            for (int j = 0; j < i; ++j) {
                this.dropItem(Items.GOLD_INGOT, 1);
            }
        }
    }
    
    public boolean processInteract(final EntityPlayer player, final EnumHand hand) {
        return false;
    }
    
    protected void setEquipmentBasedOnDifficulty(final DifficultyInstance difficulty) {
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
    }
    
    protected ItemStack getSkullDrop() {
        return ItemStack.EMPTY;
    }
    
    public boolean isPreventingPlayerRest(final EntityPlayer playerIn) {
        return this.isAngry();
    }
    
    static {
        CLIMBING = EntityDataManager.createKey(EntityZombieSpiderPigman.class, DataSerializers.BOOLEAN);
        ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
        ATTACK_SPEED_BOOST_MODIFIER = new AttributeModifier(EntityZombieSpiderPigman.ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.05, 0).setSaved(false);
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
        public AIHurtByAggressor(final EntityZombieSpiderPigman p_i45828_1_) {
            super((EntityCreature)p_i45828_1_, true, new Class[0]);
        }
        
        protected void setEntityAttackTarget(final EntityCreature creatureIn, final EntityLivingBase entityLivingBaseIn) {
            super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);
            if (creatureIn instanceof EntityZombieSpiderPigman) {
                ((EntityZombieSpiderPigman)creatureIn).becomeAngryAt(entityLivingBaseIn);
            }
        }
    }
    
    static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer>
    {
        public AITargetAggressor(final EntityZombieSpiderPigman p_i45829_1_) {
            super((EntityCreature)p_i45829_1_, EntityPlayer.class, true);
        }
        
        public boolean shouldExecute() {
            return ((EntityZombieSpiderPigman)this.taskOwner).isAngry() && super.shouldExecute();
        }
    }
}
