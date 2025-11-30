//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.village.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;
import mmm.common.entities.ai.*;
import com.google.common.base.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.ai.*;
import net.minecraft.world.*;
import mmm.eventHandler.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.network.datasync.*;

public class EntitySlimeGolem extends EntityGolem implements IMutant
{
    public static final net.minecraft.world.biome.Biome[] SPAWN_BIOMES = new net.minecraft.world.biome.Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH };
    protected static final DataParameter<Byte> PLAYER_CREATED;
    private static final DataParameter<Integer> SLIME_SIZE;
    private int homeCheckTimer;
    @Nullable
    Village village;
    private int attackTimer;
    private int holdRoseTick;
    
    public EntitySlimeGolem(final World worldIn) {
        super(worldIn);
        this.setSize(1.4f, 2.7f);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9, 32.0f));
        this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6, true));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6));
        this.tasks.addTask(7, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, new EntityAIProtectTheVillage2(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 10, false, true, new Predicate<EntityLiving>() {
            public boolean apply(@Nullable final EntityLiving p_apply_1_) {
                return p_apply_1_ != null && IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_) && !(p_apply_1_ instanceof EntityCreeper);
            }
        }));
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntitySlimeGolem.PLAYER_CREATED, (byte)0);
        this.dataManager.register(EntitySlimeGolem.SLIME_SIZE, 1);
    }
    
    protected void setSlimeSize(final int size, final boolean resetHealth) {
        this.dataManager.set(EntitySlimeGolem.SLIME_SIZE, size);
        this.setSize(0.5f * size, 1.6f * size);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)(size * size * 20));
        if (resetHealth) {
            this.setHealth(this.getMaxHealth());
        }
        this.experienceValue = size;
    }
    
    public int getSlimeSize() {
        return (int)this.dataManager.get(EntitySlimeGolem.SLIME_SIZE);
    }
    
    protected void updateAITasks() {
        final int homeCheckTimer = this.homeCheckTimer - 1;
        this.homeCheckTimer = homeCheckTimer;
        if (homeCheckTimer <= 0) {
            this.homeCheckTimer = 70 + this.rand.nextInt(50);
            this.village = this.world.getVillageCollection().getNearestVillage(new BlockPos(this), 32);
            if (this.village == null) {
                this.detachHome();
            }
            else {
                final BlockPos blockpos = this.village.getCenter();
                this.setHomePosAndDistance(blockpos, (int)(this.village.getVillageRadius() * 0.6f));
            }
        }
        super.updateAITasks();
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficulty, @Nullable final IEntityLivingData livingdata) {
        int i = this.rand.nextInt(ConfigHandler.SIZE_SlimeGolem_MAX);
        if (i < 3 && this.rand.nextFloat() < 0.5f * difficulty.getClampedAdditionalDifficulty()) {
            ++i;
        }
        final int j = 1 << i;
        this.setSlimeSize(j, true);
        return super.onInitialSpawn(difficulty, livingdata);
    }
    
    protected int decreaseAirSupply(final int air) {
        return air;
    }
    
    protected void collideWithEntity(final Entity entityIn) {
        if (entityIn instanceof IMob && !(entityIn instanceof EntityCreeper) && this.getRNG().nextInt(20) == 0) {
            this.setAttackTarget((EntityLivingBase)entityIn);
        }
        super.collideWithEntity(entityIn);
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        if (this.holdRoseTick > 0) {
            --this.holdRoseTick;
        }
        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7 && this.rand.nextInt(5) == 0) {
            final int i = MathHelper.floor(this.posX);
            final int j = MathHelper.floor(this.posY - 0.20000000298023224);
            final int k = MathHelper.floor(this.posZ);
            final IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));
            if (iblockstate.getMaterial() != Material.AIR) {
                this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5) * this.width, this.getEntityBoundingBox().minY + 0.1, this.posZ + (this.rand.nextFloat() - 0.5) * this.width, 4.0 * (this.rand.nextFloat() - 0.5), 0.5, (this.rand.nextFloat() - 0.5) * 4.0, new int[] { Block.getStateId(iblockstate) });
            }
        }
    }
    
    public boolean canAttackClass(final Class<? extends EntityLivingBase> cls) {
        return (!this.isPlayerCreated() || !EntityPlayer.class.isAssignableFrom(cls)) && cls != EntityGolem.class && super.canAttackClass(cls);
    }
    
    public static void registerFixesIronGolem(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntitySlimeGolem.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("PlayerCreated", this.isPlayerCreated());
        compound.setInteger("Size", this.getSlimeSize() - 1);
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setPlayerCreated(compound.getBoolean("PlayerCreated"));
        int i = compound.getInteger("Size");
        if (i < 0) {
            i = 0;
        }
        this.setSlimeSize(i + 1, false);
    }
    
    protected EntitySlimeGolem createInstance() {
        return new EntitySlimeGolem(this.world);
    }
    
    public boolean isSmallSlime() {
        return this.getSlimeSize() <= 1;
    }
    
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.SLIME;
    }
    
    public void setDead() {
        final int i = this.getSlimeSize();
        if (!this.world.isRemote && i > 1 && this.getHealth() <= 0.0f) {
            for (int j = 2 + this.rand.nextInt(3), k = 0; k < j; ++k) {
                final float f = (k % 2 - 0.5f) * i / 4.0f;
                final float f2 = (k / 2 - 0.5f) * i / 4.0f;
                final EntitySlimeGolem entityslime = this.createInstance();
                if (this.hasCustomName()) {
                    entityslime.setCustomNameTag(this.getCustomNameTag());
                }
                if (this.isNoDespawnRequired()) {
                    entityslime.enablePersistence();
                }
                entityslime.setSlimeSize(i / 2, true);
                entityslime.setLocationAndAngles(this.posX + f, this.posY + 0.5, this.posZ + f2, this.rand.nextFloat() * 360.0f, 0.0f);
                this.world.spawnEntity(entityslime);
            }
        }
        super.setDead();
    }
    
    protected void dropFewItems(final boolean par1, final int par2) {
        if (this.getSlimeSize() == 1) {
            final int i = this.rand.nextInt(3);
            for (int j = 0; j < i; ++j) {
                this.dropItem(Items.SLIME_BALL, 1);
            }
            for (int j = 0; j < i; ++j) {
                this.dropItem(Items.IRON_INGOT, 1);
            }
        }
    }
    
    public boolean attackEntityAsMob(final Entity entityIn) {
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte)4);
        final boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)(ConfigHandler.ATK_SlimeGolem_MIN + this.rand.nextInt(ConfigHandler.ATK_SlimeGolem_MAX)));
        if (flag) {
            entityIn.motionY += this.getSlimeSize();
            this.applyEnchantments((EntityLivingBase)this, entityIn);
        }
        this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0f, 1.0f);
        return flag;
    }
    
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(final byte id) {
        if (id == 4) {
            this.attackTimer = 10;
            this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0f, 1.0f);
        }
        else if (id == 11) {
            this.holdRoseTick = 400;
        }
        else if (id == 34) {
            this.holdRoseTick = 0;
        }
        else {
            super.handleStatusUpdate(id);
        }
    }
    
    public Village getVillage() {
        return this.village;
    }
    
    @SideOnly(Side.CLIENT)
    public int getAttackTimer() {
        return this.attackTimer;
    }
    
    public void setHoldingRose(final boolean p_70851_1_) {
        if (p_70851_1_) {
            this.holdRoseTick = 400;
            this.world.setEntityState(this, (byte)11);
        }
        else {
            this.holdRoseTick = 0;
            this.world.setEntityState(this, (byte)34);
        }
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_IRONGOLEM_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRONGOLEM_DEATH;
    }
    
    protected void playStepSound(final BlockPos pos, final Block blockIn) {
        this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0f, 1.0f);
    }
    
    public int getHoldRoseTick() {
        return this.holdRoseTick;
    }
    
    public boolean isPlayerCreated() {
        return ((byte)this.dataManager.get(EntitySlimeGolem.PLAYER_CREATED) & 0x1) != 0x0;
    }
    
    public void setPlayerCreated(final boolean playerCreated) {
        final byte b0 = (byte)this.dataManager.get(EntitySlimeGolem.PLAYER_CREATED);
        if (playerCreated) {
            this.dataManager.set(EntitySlimeGolem.PLAYER_CREATED, (byte)(b0 | 0x1));
        }
        else {
            this.dataManager.set(EntitySlimeGolem.PLAYER_CREATED, (byte)(b0 & 0xFFFFFFFE));
        }
    }
    
    public void onDeath(final DamageSource cause) {
        if (!this.isPlayerCreated() && this.attackingPlayer != null && this.village != null) {
            this.village.modifyPlayerReputation(this.attackingPlayer.getUniqueID(), -5);
        }
        super.onDeath(cause);
    }
    
    static {
        PLAYER_CREATED = EntityDataManager.createKey(EntitySlimeGolem.class, DataSerializers.BYTE);
        SLIME_SIZE = EntityDataManager.createKey(EntitySlimeGolem.class, DataSerializers.VARINT);
    }
}
