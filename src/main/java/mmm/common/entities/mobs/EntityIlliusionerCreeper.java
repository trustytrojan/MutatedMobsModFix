//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.*;
import net.minecraft.world.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import mmm.common.entities.ai.*;
import mmm.eventHandler.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import net.minecraft.entity.monster.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.network.datasync.*;

public class EntityIlliusionerCreeper extends EntityHalfCreeper
{
    public static final net.minecraft.world.biome.Biome[] SPAWN_BIOMES = new net.minecraft.world.biome.Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH };
    private static final DataParameter<Integer> STATE;
    private static final DataParameter<Boolean> POWERED;
    private static final DataParameter<Boolean> IGNITED;
    private static final DataParameter<Boolean> SPLIT;
    private static final DataParameter<Boolean> Clone;
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime;
    private int explosionRadius;
    private int droppedSkulls;
    public boolean CanSplit;
    
    public EntityIlliusionerCreeper(final World worldIn) {
        super(worldIn);
        this.fuseTime = 30;
        this.explosionRadius = 3;
        this.setSize(0.6f, 1.7f);
        this.CanSplit = true;
        if (!this.isIlliusion()) {
            this.experienceValue = 3;
        }
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityOcelot.class, 6.0f, 1.0, 1.2));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0, false));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8));
        this.tasks.addTask(6, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.tasks.addTask(2, new EntityAICreeperSwell5(this));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_IlliusionerCreeper);
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
        this.dataManager.register(EntityIlliusionerCreeper.STATE, (-1));
        this.dataManager.register(EntityIlliusionerCreeper.POWERED, false);
        this.dataManager.register(EntityIlliusionerCreeper.IGNITED, false);
        this.dataManager.register(EntityIlliusionerCreeper.Clone, false);
        this.dataManager.register(EntityIlliusionerCreeper.SPLIT, false);
    }
    
    public static void registerFixesCreeper(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityIlliusionerCreeper.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if ((Boolean)this.dataManager.get(EntityIlliusionerCreeper.POWERED)) {
            compound.setBoolean("powered", true);
        }
        compound.setShort("Fuse", (short)this.fuseTime);
        compound.setByte("ExplosionRadius", (byte)this.explosionRadius);
        compound.setBoolean("ignited", this.hasIgnited());
        compound.setBoolean("Illiusion", this.isIlliusion());
        compound.setBoolean("Split", this.HasSplited());
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(EntityIlliusionerCreeper.POWERED, compound.getBoolean("powered"));
        if (compound.hasKey("Fuse", 99)) {
            this.fuseTime = compound.getShort("Fuse");
        }
        if (compound.hasKey("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }
        if (compound.getBoolean("ignited")) {
            this.ignite();
        }
        if (compound.getBoolean("Illiusion")) {
            this.Illiusion();
        }
        if (compound.getBoolean("Split")) {
            this.SetSplit();
        }
    }
    
    private void SetSplit() {
        this.dataManager.set(EntityIlliusionerCreeper.SPLIT, false);
        this.CanSplit = false;
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
            if (this.getAttackTarget() != null && !this.isIlliusion() && this.CanSplit) {
                this.split();
            }
        }
        super.onUpdate();
    }
    
    public void split() {
        if (!this.world.isRemote && !this.isDead) {
            final EntityIlliusionerCreeper entitypigzombie = new EntityIlliusionerCreeper(this.world);
            final EntityIlliusionerCreeper entitypigzombie2 = new EntityIlliusionerCreeper(this.world);
            final EntityIlliusionerCreeper entitypigzombie3 = new EntityIlliusionerCreeper(this.world);
            final EntityIlliusionerCreeper entitypigzombie4 = new EntityIlliusionerCreeper(this.world);
            final EntityIlliusionerCreeper entitypigzombie5 = new EntityIlliusionerCreeper(this.world);
            this.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 900));
            entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie.setAttackTarget(this.getAttackTarget());
            entitypigzombie.Illiusion();
            entitypigzombie2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie2.setAttackTarget(this.getAttackTarget());
            entitypigzombie2.Illiusion();
            entitypigzombie3.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie3.setAttackTarget(this.getAttackTarget());
            entitypigzombie3.Illiusion();
            entitypigzombie4.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie4.setAttackTarget(this.getAttackTarget());
            entitypigzombie4.Illiusion();
            entitypigzombie5.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie5.setAttackTarget(this.getAttackTarget());
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
            this.world.spawnEntity(entitypigzombie);
            this.world.spawnEntity(entitypigzombie2);
            this.world.spawnEntity(entitypigzombie3);
            this.world.spawnEntity(entitypigzombie4);
            this.world.spawnEntity(entitypigzombie5);
            this.WillSplit();
        }
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CREEPER_DEATH;
    }
    
    public void onDeath(final DamageSource cause) {
        super.onDeath(cause);
        if (!this.isIlliusion() && this.world.getGameRules().getBoolean("doMobLoot")) {
            if (cause.getTrueSource() instanceof EntitySkeleton) {
                final int i = Item.getIdFromItem(Items.RECORD_13);
                final int j = Item.getIdFromItem(Items.RECORD_WAIT);
                final int k = i + this.rand.nextInt(j - i + 1);
                this.dropItem(Item.getItemById(k), 1);
            }
            else if (cause.getTrueSource() instanceof EntityIlliusionerCreeper && cause.getTrueSource() != this && ((EntityIlliusionerCreeper)cause.getTrueSource()).getPowered() && ((EntityIlliusionerCreeper)cause.getTrueSource()).ableToCauseSkullDrop()) {
                ((EntityIlliusionerCreeper)cause.getTrueSource()).incrementDroppedSkulls();
                this.entityDropItem(new ItemStack(Items.SKULL, 1, 4), 0.0f);
            }
        }
    }
    
    public boolean attackEntityAsMob(final Entity entityIn) {
        this.explode();
        return true;
    }
    
    public boolean getPowered() {
        return (boolean)this.dataManager.get(EntityIlliusionerCreeper.POWERED);
    }
    
    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(final float p_70831_1_) {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
    }
    
    protected void dropFewItems(final boolean par1, final int par2) {
        final int i = this.rand.nextInt(3);
        if (!this.isIlliusion()) {
            for (int j = 0; j < i; ++j) {
                this.dropItem(Items.GUNPOWDER, 1);
            }
        }
    }
    
    public int getCreeperState() {
        return (int)this.dataManager.get(EntityIlliusionerCreeper.STATE);
    }
    
    public void setCreeperState(final int state) {
        this.dataManager.set(EntityIlliusionerCreeper.STATE, state);
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
            if (this.isIlliusion()) {
                this.dead = true;
                this.spawnExplosionParticle();
                this.setDead();
            }
            else {
                final boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
                final float f = this.getPowered() ? 2.0f : 1.0f;
                this.dead = true;
                this.world.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, flag);
                this.setDead();
            }
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
            this.world.spawnEntity(entityareaeffectcloud);
        }
    }
    
    public boolean hasIgnited() {
        return (boolean)this.dataManager.get(EntityIlliusionerCreeper.IGNITED);
    }
    
    public boolean isIlliusion() {
        return (boolean)this.dataManager.get(EntityIlliusionerCreeper.Clone);
    }
    
    public boolean HasSplited() {
        return (boolean)this.dataManager.get(EntityIlliusionerCreeper.SPLIT);
    }
    
    public void Illiusion() {
        this.dataManager.set(EntityIlliusionerCreeper.Clone, true);
    }
    
    public void WillSplit() {
        this.dataManager.set(EntityIlliusionerCreeper.SPLIT, false);
        this.CanSplit = false;
    }
    
    public void ignite() {
        this.dataManager.set(EntityIlliusionerCreeper.IGNITED, true);
    }
    
    public boolean ableToCauseSkullDrop() {
        return this.droppedSkulls < 1 && this.world.getGameRules().getBoolean("doMobLoot");
    }
    
    public void incrementDroppedSkulls() {
        ++this.droppedSkulls;
    }
    
    static {
        STATE = EntityDataManager.createKey(EntityIlliusionerCreeper.class, DataSerializers.VARINT);
        POWERED = EntityDataManager.createKey(EntityIlliusionerCreeper.class, DataSerializers.BOOLEAN);
        IGNITED = EntityDataManager.createKey(EntityIlliusionerCreeper.class, DataSerializers.BOOLEAN);
        SPLIT = EntityDataManager.createKey(EntityIlliusionerCreeper.class, DataSerializers.BOOLEAN);
        Clone = EntityDataManager.createKey(EntityIlliusionerCreeper.class, DataSerializers.BOOLEAN);
    }
}
