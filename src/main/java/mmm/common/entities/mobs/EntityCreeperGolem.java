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
import mmm.eventHandler.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.effect.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.network.datasync.*;

public class EntityCreeperGolem extends EntityGolem implements IMutant
{
    protected static final DataParameter<Byte> PLAYER_CREATED;
    private static final DataParameter<Integer> STATE;
    private static final DataParameter<Boolean> POWERED;
    private int homeCheckTimer;
    @Nullable
    Village village;
    private int attackTimer;
    private int holdRoseTick;
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime;
    private int explosionRadius;
    private int droppedSkulls;
    public int deathTicks;
    
    public EntityCreeperGolem(final World worldIn) {
        super(worldIn);
        this.fuseTime = 50;
        this.explosionRadius = 10;
        this.setSize(1.4f, 2.7f);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIAttackMelee((EntityCreature)this, 1.0, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget((EntityCreature)this, 0.9, 32.0f));
        this.tasks.addTask(3, new EntityAIMoveThroughVillage((EntityCreature)this, 0.6, true));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater((EntityCreature)this, 0.6));
        this.tasks.addTask(7, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, new EntityAIProtectTheVillage3(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget((EntityCreature)this, EntityLiving.class, 10, false, true, new Predicate<EntityLiving>() {
            public boolean apply(@Nullable final EntityLiving p_apply_1_) {
                return p_apply_1_ != null && IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_) && !(p_apply_1_ instanceof EntityCreeper);
            }
        }));
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityCreeperGolem.PLAYER_CREATED, (byte)0);
        this.dataManager.register(EntityCreeperGolem.STATE, (-1));
        this.dataManager.register(EntityCreeperGolem.POWERED, false);
    }
    
    protected void onDeathUpdate() {
        if (ConfigHandler.S_CreeperGolemExplodeOnDeath) {
            ++this.deathTicks;
            if (this.deathTicks >= 180 && this.deathTicks <= 200) {
                final float f = (this.rand.nextFloat() - 0.5f) * 8.0f;
                final float f2 = (this.rand.nextFloat() - 0.5f) * 4.0f;
                final float f3 = (this.rand.nextFloat() - 0.5f) * 8.0f;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + f, this.posY + 2.0 + f2, this.posZ + f3, 0.0, 0.0, 0.0, new int[0]);
                this.setAir(2);
            }
            final boolean flag = this.world.getGameRules().getBoolean("doMobLoot");
            final int i = 500;
            if (!this.world.isRemote) {
                if (this.deathTicks > 150 && this.deathTicks % 5 == 0 && flag) {
                    this.fuseTime = 50;
                    this.motionY *= 2.5999999046325684;
                }
                if (this.deathTicks == 1) {
                    this.world.playBroadcastSound(1028, new BlockPos((Entity)this), 0);
                }
            }
            this.move(MoverType.SELF, 0.0, 0.10000000149011612, 0.0);
            this.rotationYaw += 20.0f;
            this.renderYawOffset = this.rotationYaw;
            if (this.deathTicks == 200 && !this.world.isRemote) {
                this.explode();
                this.motionY /= 2.5;
                this.setDead();
            }
        }
        else if (!ConfigHandler.S_CreeperGolemExplodeOnDeath) {
            this.setDead();
        }
    }
    
    protected boolean processInteract(final EntityPlayer player, final EnumHand hand) {
        final ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.GUNPOWDER) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_BOTTLE_FILL, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                for (int i = 0; i < 2; ++i) {
                    this.world.spawnParticle(EnumParticleTypes.HEART, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0, new int[0]);
                }
                this.heal(5.0f);
                itemstack.shrink(1);
                return true;
            }
        }
        else if (itemstack.getItem() == Items.FIRE_CHARGE) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_BOTTLE_FILL, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                for (int i = 0; i < 2; ++i) {
                    this.world.spawnParticle(EnumParticleTypes.HEART, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0, new int[0]);
                }
                this.heal(15.0f);
                itemstack.shrink(1);
                return true;
            }
        }
        return super.processInteract(player, hand);
    }
    
    protected void updateAITasks() {
        final int homeCheckTimer = this.homeCheckTimer - 1;
        this.homeCheckTimer = homeCheckTimer;
        if (homeCheckTimer <= 0) {
            this.homeCheckTimer = 70 + this.rand.nextInt(50);
            this.village = this.world.getVillageCollection().getNearestVillage(new BlockPos((Entity)this), 32);
            if (this.village == null) {
                this.detachHome();
            }
            else {
                final BlockPos blockpos = this.village.getCenter();
                this.setHomePosAndDistance(blockpos, (int)(this.village.getVillageRadius() * 0.6f));
            }
            if (this.getHealth() <= 50.0f) {
                this.dataManager.set(EntityCreeperGolem.POWERED, true);
                this.LightningPower();
            }
            else if (this.getHealth() > 50.0f) {
                this.dataManager.set(EntityCreeperGolem.POWERED, false);
                this.RemoveLightningPower();
            }
        }
        super.updateAITasks();
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_CreeperGolem);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0);
    }
    
    private void explode() {
        if (!this.world.isRemote) {
            final boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
            final float f = this.getPowered() ? 2.0f : 1.0f;
            this.dead = true;
            this.world.createExplosion((Entity)this, this.posX, this.posY, this.posZ, this.explosionRadius * f, false);
            this.setDead();
        }
    }
    
    private void spawnLingeringCloud() {
        final Collection<PotionEffect> collection = (Collection<PotionEffect>)this.getActivePotionEffects();
        if (!collection.isEmpty()) {
            final EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
            entityareaeffectcloud.setRadius(2.5f);
            entityareaeffectcloud.setRadiusOnUse(-0.5f);
            entityareaeffectcloud.setWaitTime(10);
            entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
            entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / entityareaeffectcloud.getDuration());
            for (final PotionEffect potioneffect : collection) {
                entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
            }
            this.world.spawnEntity((Entity)entityareaeffectcloud);
        }
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
        EntityLiving.registerFixesMob(fixer, EntityCreeperGolem.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("PlayerCreated", this.isPlayerCreated());
        if ((Boolean)this.dataManager.get(EntityCreeperGolem.POWERED)) {
            compound.setBoolean("powered", true);
        }
        compound.setShort("Fuse", (short)this.fuseTime);
        compound.setByte("ExplosionRadius", (byte)this.explosionRadius);
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setPlayerCreated(compound.getBoolean("PlayerCreated"));
        this.dataManager.set(EntityCreeperGolem.POWERED, compound.getBoolean("powered"));
        if (compound.hasKey("Fuse", 99)) {
            this.fuseTime = compound.getShort("Fuse");
        }
        if (compound.hasKey("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }
    }
    
    protected EntityCreeperGolem createInstance() {
        return new EntityCreeperGolem(this.world);
    }
    
    public boolean getPowered() {
        return (boolean)this.dataManager.get(EntityCreeperGolem.POWERED);
    }
    
    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(final float p_70831_1_) {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
    }
    
    public int getCreeperState() {
        return (int)this.dataManager.get(EntityCreeperGolem.STATE);
    }
    
    public void setCreeperState(final int state) {
        this.dataManager.set(EntityCreeperGolem.STATE, state);
    }
    
    public void onStruckByLightning(final EntityLightningBolt lightningBolt) {
        super.onStruckByLightning(lightningBolt);
        this.dataManager.set(EntityCreeperGolem.POWERED, true);
        this.LightningPower();
    }
    
    public void LightningPower() {
        this.setSprinting(true);
        this.addPotionEffect(new PotionEffect(MobEffects.SPEED, 499995, 3));
        this.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 499995, 3));
        this.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 49995, 3));
        this.explosionRadius = 10;
    }
    
    public void RemoveLightningPower() {
        this.setSprinting(false);
        this.addPotionEffect(new PotionEffect(MobEffects.SPEED, 0, 0));
        this.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 0, 0));
        this.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 0, 0));
        this.explosionRadius = 5;
    }
    
    protected void dropFewItems(final boolean par1, final int par2) {
        this.dropItem(Items.GUNPOWDER, 1);
        this.dropItem(Items.IRON_INGOT, 1);
    }
    
    public boolean attackEntityAsMob(final Entity entityIn) {
        this.attackTimer = 10;
        this.world.setEntityState((Entity)this, (byte)4);
        final boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)(ConfigHandler.ATK_CreeperGolem_MIN + this.rand.nextInt(ConfigHandler.ATK_CreeperGolem_MAX)));
        this.world.createExplosion((Entity)this, this.posX, this.posY, this.posZ, 1.0f, false);
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
        return ((byte)this.dataManager.get(EntityCreeperGolem.PLAYER_CREATED) & 0x1) != 0x0;
    }
    
    public void setPlayerCreated(final boolean playerCreated) {
        final byte b0 = (byte)this.dataManager.get(EntityCreeperGolem.PLAYER_CREATED);
        if (playerCreated) {
            this.dataManager.set(EntityCreeperGolem.PLAYER_CREATED, (byte)(b0 | 0x1));
        }
        else {
            this.dataManager.set(EntityCreeperGolem.PLAYER_CREATED, (byte)(b0 & 0xFFFFFFFE));
        }
    }
    
    public void onDeath(final DamageSource cause) {
        if (!this.isPlayerCreated() && this.attackingPlayer != null && this.village != null) {
            this.village.modifyPlayerReputation(this.attackingPlayer.getUniqueID(), -5);
        }
        super.onDeath(cause);
    }
    
    static {
        PLAYER_CREATED = EntityDataManager.createKey(EntityCreeperGolem.class, DataSerializers.BYTE);
        STATE = EntityDataManager.createKey(EntityCreeperGolem.class, DataSerializers.VARINT);
        POWERED = EntityDataManager.createKey(EntityCreeperGolem.class, DataSerializers.BOOLEAN);
    }
}
