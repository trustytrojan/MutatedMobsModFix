//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.*;
import mmm.common.entities.ai.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.world.*;
import javax.annotation.*;
import mmm.eventHandler.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.effect.*;
import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.network.datasync.*;

public class EntitySlimeCreeper extends EntityHalfCreeper
{
    private static final DataParameter<Integer> STATE;
    private static final DataParameter<Boolean> POWERED;
    private static final DataParameter<Boolean> IGNITED;
    private static final DataParameter<Integer> SLIME_SIZE;
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime;
    private int explosionRadius;
    private int droppedSkulls;
    
    public EntitySlimeCreeper(final World worldIn) {
        super(worldIn);
        this.fuseTime = 30;
        this.explosionRadius = 3;
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(2, new EntityAICreeperSwell3(this));
        this.tasks.addTask(1, new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(3, new EntityAIAvoidEntity((EntityCreature)this, EntityOcelot.class, 6.0f, 1.0, 1.2));
        this.tasks.addTask(4, new EntityAIAttackMelee((EntityCreature)this, 1.0, false));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater((EntityCreature)this, 0.8));
        this.tasks.addTask(6, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget((EntityCreature)this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.21);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(120.0);
    }
    
    public int getMaxFallHeight() {
        return (this.getAttackTarget() == null) ? 3 : (3 + (int)(this.getHealth() - 1.0f));
    }
    
    public void fall(final float distance, final float damageMultiplier) {
        super.fall(distance, damageMultiplier);
        this.timeSinceIgnited += (int)(distance * 1.5f);
        if (this.timeSinceIgnited > this.fuseTime - 5) {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntitySlimeCreeper.STATE, (-1));
        this.dataManager.register(EntitySlimeCreeper.POWERED, false);
        this.dataManager.register(EntitySlimeCreeper.IGNITED, false);
        this.dataManager.register(EntitySlimeCreeper.SLIME_SIZE, 1);
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficulty, @Nullable final IEntityLivingData livingdata) {
        int i = this.rand.nextInt(ConfigHandler.SIZE_SlimeCreeper_MAX);
        if (i < 3 && this.rand.nextFloat() < 0.5f * difficulty.getClampedAdditionalDifficulty()) {
            ++i;
        }
        final int j = 1 << i;
        this.setSlimeSize(j, true);
        return super.onInitialSpawn(difficulty, livingdata);
    }
    
    protected void setSlimeSize(final int size, final boolean resetHealth) {
        this.dataManager.set(EntitySlimeCreeper.SLIME_SIZE, size);
        this.setSize(0.8f * size, 2.0f * size);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)(size * size * 2));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)(0.2f + 0.1f * size));
        if (resetHealth) {
            this.setHealth(this.getMaxHealth());
        }
        this.experienceValue = size;
    }
    
    public int getSlimeSize() {
        return (int)this.dataManager.get(EntitySlimeCreeper.SLIME_SIZE);
    }
    
    public static void registerFixesCreeper(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntitySlimeCreeper.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if ((Boolean)this.dataManager.get(EntitySlimeCreeper.POWERED)) {
            compound.setBoolean("powered", true);
        }
        compound.setShort("Fuse", (short)this.fuseTime);
        compound.setByte("ExplosionRadius", (byte)this.explosionRadius);
        compound.setBoolean("ignited", this.hasIgnited());
        compound.setInteger("Size", this.getSlimeSize() - 1);
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(EntitySlimeCreeper.POWERED, compound.getBoolean("powered"));
        if (compound.hasKey("Fuse", 99)) {
            this.fuseTime = compound.getShort("Fuse");
        }
        if (compound.hasKey("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }
        if (compound.getBoolean("ignited")) {
            this.ignite();
        }
        int i = compound.getInteger("Size");
        if (i < 0) {
            i = 0;
        }
        this.setSlimeSize(i + 1, false);
    }
    
    protected EntitySlimeCreeper createInstance() {
        return new EntitySlimeCreeper(this.world);
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
                final EntitySlimeCreeper entityslime = this.createInstance();
                if (this.hasCustomName()) {
                    entityslime.setCustomNameTag(this.getCustomNameTag());
                }
                if (this.isNoDespawnRequired()) {
                    entityslime.enablePersistence();
                }
                entityslime.setSlimeSize(i / 2, true);
                entityslime.setLocationAndAngles(this.posX + f, this.posY + 0.5, this.posZ + f2, this.rand.nextFloat() * 360.0f, 0.0f);
                this.world.spawnEntity((Entity)entityslime);
                if (this.getSlimeSize() == 1) {
                    this.SpawnMobs();
                }
            }
        }
        super.setDead();
    }
    
    public void SpawnLittleCreeps() {
        final int i = this.getSlimeSize();
        if (!this.world.isRemote && i > 1 && this.getHealth() <= 0.0f) {
            for (int j = 2 + this.rand.nextInt(3), k = 0; k < j; ++k) {
                final float f = (k % 2 - 0.5f) * i / 4.0f;
                final float f2 = (k / 2 - 0.5f) * i / 4.0f;
                final EntitySlimeCreeper entityslime = this.createInstance();
                if (this.hasCustomName()) {
                    entityslime.setCustomNameTag(this.getCustomNameTag());
                }
                if (this.isNoDespawnRequired()) {
                    entityslime.enablePersistence();
                }
                entityslime.setSlimeSize(i / 2, true);
                entityslime.setLocationAndAngles(this.posX + f, this.posY + 0.5, this.posZ + f2, this.rand.nextFloat() * 360.0f, 0.0f);
                this.world.spawnEntity((Entity)entityslime);
            }
        }
    }
    
    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;
            if (this.hasIgnited()) {
                this.setCreeperState(1);
            }
            final int i = this.getCreeperState();
            if (i > 0 && this.timeSinceIgnited == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0f, 0.5f);
            }
            this.timeSinceIgnited += i;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }
            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                this.explode();
            }
            if (this.getHealth() <= 1.0f && this.getSlimeSize() == 1) {
                this.dataManager.set(EntitySlimeCreeper.POWERED, true);
            }
        }
        super.onUpdate();
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CREEPER_DEATH;
    }
    
    public void onDeath(final DamageSource cause) {
        super.onDeath(cause);
        if (this.world.getGameRules().getBoolean("doMobLoot")) {
            if (cause.getTrueSource() instanceof EntitySkeleton) {
                final int i = Item.getIdFromItem(Items.RECORD_13);
                final int j = Item.getIdFromItem(Items.RECORD_WAIT);
                final int k = i + this.rand.nextInt(j - i + 1);
                this.dropItem(Item.getItemById(k), 1);
            }
            else if (cause.getTrueSource() instanceof EntitySlimeCreeper && cause.getTrueSource() != this && ((EntitySlimeCreeper)cause.getTrueSource()).getPowered() && ((EntitySlimeCreeper)cause.getTrueSource()).ableToCauseSkullDrop()) {
                ((EntitySlimeCreeper)cause.getTrueSource()).incrementDroppedSkulls();
                this.entityDropItem(new ItemStack(Items.SKULL, 1, 4), 0.0f);
            }
        }
    }
    
    public boolean attackEntityAsMob(final Entity entityIn) {
        return true;
    }
    
    public boolean getPowered() {
        return (boolean)this.dataManager.get(EntitySlimeCreeper.POWERED);
    }
    
    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(final float p_70831_1_) {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
    }
    
    protected void dropFewItems(final boolean par1, final int par2) {
        if (this.getSlimeSize() == 1) {
            final int i = this.rand.nextInt(3);
            for (int j = 0; j < i; ++j) {
                this.dropItem(Items.GUNPOWDER, 1);
            }
            for (int j = 0; j < i; ++j) {
                this.dropItem(Items.SLIME_BALL, 1);
            }
        }
    }
    
    public int getCreeperState() {
        return (int)this.dataManager.get(EntitySlimeCreeper.STATE);
    }
    
    public void setCreeperState(final int state) {
        this.dataManager.set(EntitySlimeCreeper.STATE, state);
    }
    
    public void onStruckByLightning(final EntityLightningBolt lightningBolt) {
        super.onStruckByLightning(lightningBolt);
        this.dataManager.set(EntitySlimeCreeper.POWERED, true);
    }
    
    protected boolean processInteract(final EntityPlayer player, final EnumHand hand) {
        final ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                this.ignite();
                itemstack.damageItem(1, (EntityLivingBase)player);
                return true;
            }
        }
        return super.processInteract(player, hand);
    }
    
    private void explode() {
        if (!this.world.isRemote) {
            final boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
            final float f = this.getPowered() ? 2.0f : 1.0f;
            this.dead = true;
            this.world.createExplosion((Entity)this, this.posX, this.posY, this.posZ, (float)this.getSlimeSize(), flag);
            this.SpawnLittleCreeps();
            this.setDead();
            this.spawnLingeringCloud();
        }
    }
    
    public void SpawnMobs() {
        if (!this.world.isRemote) {
            final EntitySlime entitypigzombie = new EntitySlime(this.world);
            final EntityCreeper entitypigzombie2 = new EntityCreeper(this.world);
            entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            if (this.hasCustomName()) {
                entitypigzombie.setCustomNameTag(this.getCustomNameTag());
                entitypigzombie.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
                entitypigzombie2.setCustomNameTag(this.getCustomNameTag());
                entitypigzombie2.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            this.world.spawnEntity((Entity)entitypigzombie);
            this.world.spawnEntity((Entity)entitypigzombie2);
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
    
    public boolean hasIgnited() {
        return (boolean)this.dataManager.get(EntitySlimeCreeper.IGNITED);
    }
    
    public void ignite() {
        this.dataManager.set(EntitySlimeCreeper.IGNITED, true);
    }
    
    public boolean ableToCauseSkullDrop() {
        return this.droppedSkulls < 1 && this.world.getGameRules().getBoolean("doMobLoot");
    }
    
    public void incrementDroppedSkulls() {
        ++this.droppedSkulls;
    }
    
    static {
        STATE = EntityDataManager.createKey(EntitySlimeCreeper.class, DataSerializers.VARINT);
        POWERED = EntityDataManager.createKey(EntitySlimeCreeper.class, DataSerializers.BOOLEAN);
        IGNITED = EntityDataManager.createKey(EntitySlimeCreeper.class, DataSerializers.BOOLEAN);
        SLIME_SIZE = EntityDataManager.createKey(EntitySlimeCreeper.class, DataSerializers.VARINT);
    }
}
