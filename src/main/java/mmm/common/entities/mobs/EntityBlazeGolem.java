//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.village.*;
import javax.annotation.*;
import net.minecraft.pathfinding.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.player.*;
import mmm.common.entities.mobs.ai.*;
import com.google.common.base.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.ai.*;
import mmm.eventHandler.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.datafix.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.network.datasync.*;

public class EntityBlazeGolem extends EntityGolem implements IMutant
{
    protected static final DataParameter<Byte> PLAYER_CREATED;
    private int homeCheckTimer;
    @Nullable
    Village village;
    private int attackTimer;
    private int holdRoseTick;
    private int lastActiveTime;
    private float heightOffset;
    private int heightOffsetUpdateTime;
    
    public EntityBlazeGolem(final World worldIn) {
        super(worldIn);
        this.heightOffset = 0.5f;
        this.setPathPriority(PathNodeType.WATER, -1.0f);
        this.setPathPriority(PathNodeType.LAVA, 8.0f);
        this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0f);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0f);
        this.isImmuneToFire = true;
        this.setSize(1.4f, 2.7f);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }
    
    public float getBrightness() {
        return 1.0f;
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIAttackMelee((EntityCreature)this, 1.0, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget((EntityCreature)this, 0.9, 32.0f));
        this.tasks.addTask(3, new EntityAIMoveThroughVillage((EntityCreature)this, 0.6, true));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater((EntityCreature)this, 0.6));
        this.tasks.addTask(7, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, new EntityAIProtectTheVillage4(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget((EntityCreature)this, EntityLiving.class, 10, false, true, new Predicate<EntityLiving>() {
            public boolean apply(@Nullable final EntityLiving p_apply_1_) {
                return p_apply_1_ != null && IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_) && !(p_apply_1_ instanceof EntityCreeper);
            }
        }));
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityBlazeGolem.PLAYER_CREATED, (byte)0);
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
        if (--this.homeCheckTimer <= 0) {
            this.homeCheckTimer = 70 + this.rand.nextInt(50);
            this.village = this.world.getVillageCollection().getNearestVillage(new BlockPos((Entity)this), 32);
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_BlazeGolem);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficulty, @Nullable final IEntityLivingData livingdata) {
        int i = this.rand.nextInt(3);
        if (i < 3 && this.rand.nextFloat() < 0.5f * difficulty.getClampedAdditionalDifficulty()) {
            ++i;
        }
        final int j = 1 << i;
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
        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7 && this.rand.nextInt(5) == 0) {
            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY - 0.20000000298023224);
            int k = MathHelper.floor(this.posZ);
            final IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));
            if (iblockstate.getMaterial() != Material.AIR) {
                this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5) * this.width, this.getEntityBoundingBox().minY + 0.1, this.posZ + (this.rand.nextFloat() - 0.5) * this.width, 4.0 * (this.rand.nextFloat() - 0.5), 0.5, (this.rand.nextFloat() - 0.5) * 4.0, new int[] { Block.getStateId(iblockstate) });
            }
            for (int l = 0; l < 4; ++l) {
                i = MathHelper.floor(this.posX + (l % 2 * 5 - 1) * 0.25f);
                j = MathHelper.floor(this.posY - 1.0);
                k = MathHelper.floor(this.posZ + (l / 2 % 2 * 5 - 1) * 0.25f);
                final BlockPos blockpos = new BlockPos(i, j, k);
                if (this.world.getBlockState(blockpos).getMaterial() == Material.WATER && Blocks.MAGMA.canPlaceBlockAt(this.world, blockpos)) {
                    this.world.setBlockState(blockpos, Blocks.MAGMA.getDefaultState());
                }
            }
        }
    }
    
    public boolean canAttackClass(final Class<? extends EntityLivingBase> cls) {
        return (!this.isPlayerCreated() || !EntityPlayer.class.isAssignableFrom(cls)) && cls != EntityGolem.class && super.canAttackClass(cls);
    }
    
    public static void registerFixesIronGolem(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityBlazeGolem.class);
    }
    
    protected boolean processInteract(final EntityPlayer player, final EnumHand hand) {
        final ItemStack itemstack = player.getHeldItem(hand);
        if (ConfigHandler.S_BlazeGolemCookFoodOnRightClick && itemstack.getItem() == Items.PORKCHOP) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                this.dropItem(Items.COOKED_PORKCHOP, 1);
                itemstack.shrink(1);
                return true;
            }
        }
        if (ConfigHandler.S_BlazeGolemCookFoodOnRightClick && itemstack.getItem() == Items.BEEF) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                this.dropItem(Items.COOKED_BEEF, 1);
                itemstack.shrink(1);
                return true;
            }
        }
        if (ConfigHandler.S_BlazeGolemCookFoodOnRightClick && itemstack.getItem() == Items.FISH) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                this.dropItem(Items.COOKED_FISH, 1);
                itemstack.shrink(1);
                return true;
            }
        }
        if (ConfigHandler.S_BlazeGolemCookFoodOnRightClick && itemstack.getItem() == Items.POTATO) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                this.dropItem(Items.BAKED_POTATO, 1);
                itemstack.shrink(1);
                return true;
            }
        }
        if (ConfigHandler.S_BlazeGolemCookFoodOnRightClick && itemstack.getItem() == Items.CHICKEN) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                this.dropItem(Items.COOKED_CHICKEN, 1);
                itemstack.shrink(1);
                return true;
            }
        }
        if (ConfigHandler.S_BlazeGolemCookFoodOnRightClick && itemstack.getItem() == Items.MUTTON) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                this.dropItem(Items.COOKED_MUTTON, 1);
                itemstack.shrink(1);
                return true;
            }
        }
        if (ConfigHandler.S_BlazeGolemCookFoodOnRightClick && itemstack.getItem() == Items.RABBIT) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                this.dropItem(Items.COOKED_RABBIT, 1);
                itemstack.shrink(1);
                return true;
            }
        }
        if (ConfigHandler.S_BlazeGolemCookFoodOnRightClick && itemstack.getItem() == Items.CLAY_BALL) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                this.dropItem(Items.BRICK, 1);
                itemstack.shrink(1);
                return true;
            }
        }
        if (ConfigHandler.S_BlazeGolemCookFoodOnRightClick && itemstack.getItem() == Items.CHORUS_FRUIT) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                this.dropItem(Items.CHORUS_FRUIT_POPPED, 1);
                itemstack.shrink(1);
                return true;
            }
        }
        return super.processInteract(player, hand);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("PlayerCreated", this.isPlayerCreated());
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setPlayerCreated(compound.getBoolean("PlayerCreated"));
    }
    
    protected EntityBlazeGolem createInstance() {
        return new EntityBlazeGolem(this.world);
    }
    
    protected void dropFewItems(final boolean par1, final int par2) {
        this.dropItem(Items.BLAZE_POWDER, 1);
        this.dropItem(Items.IRON_INGOT, 1);
    }
    
    public boolean attackEntityAsMob(final Entity entityIn) {
        this.attackTimer = 10;
        this.world.setEntityState((Entity)this, (byte)4);
        final boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)(ConfigHandler.ATK_BlazeGolem_MIN + this.rand.nextInt(ConfigHandler.ATK_BlazeGolem_MAX)));
        entityIn.setFire(5);
        if (flag) {
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
            this.world.setEntityState((Entity)this, (byte)11);
        }
        else {
            this.holdRoseTick = 0;
            this.world.setEntityState((Entity)this, (byte)34);
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
        return ((byte)this.dataManager.get(EntityBlazeGolem.PLAYER_CREATED) & 0x1) != 0x0;
    }
    
    public void setPlayerCreated(final boolean playerCreated) {
        final byte b0 = (byte)this.dataManager.get(EntityBlazeGolem.PLAYER_CREATED);
        if (playerCreated) {
            this.dataManager.set(EntityBlazeGolem.PLAYER_CREATED, (byte)(b0 | 0x1));
        }
        else {
            this.dataManager.set(EntityBlazeGolem.PLAYER_CREATED, (byte)(b0 & 0xFFFFFFFE));
        }
    }
    
    public void onDeath(final DamageSource cause) {
        if (!this.isPlayerCreated() && this.attackingPlayer != null && this.village != null) {
            this.village.modifyPlayerReputation(this.attackingPlayer.getUniqueID(), -5);
        }
        super.onDeath(cause);
    }
    
    static {
        PLAYER_CREATED = EntityDataManager.createKey(EntityBlazeGolem.class, DataSerializers.BYTE);
    }
}
