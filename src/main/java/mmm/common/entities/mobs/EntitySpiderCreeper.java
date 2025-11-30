//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.*;
import net.minecraft.world.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import mmm.eventHandler.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.datafix.*;
import net.minecraft.potion.*;
import net.minecraft.nbt.*;
import mmm.common.entities.ai.*;
import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.network.datasync.*;

public class EntitySpiderCreeper extends EntityHalfCreeper
{
    public static final net.minecraft.world.biome.Biome[] SPAWN_BIOMES = new net.minecraft.world.biome.Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH };
    private static final DataParameter<Integer> STATE;
    private static final DataParameter<Boolean> POWERED;
    private static final DataParameter<Boolean> CLIMBING;
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime;
    private int explosionRadius;
    private int droppedSkulls;
    
    public EntitySpiderCreeper(final World worldIn) {
        super(worldIn);
        this.fuseTime = 30;
        this.explosionRadius = 3;
        this.setSize(0.6f, 1.7f);
    }
    
    protected void initEntityAI() {
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
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_SpiderCreeper);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ConfigHandler.ATK_CreeperSpider);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(120.0);
    }
    
    protected PathNavigate createNavigator(final World worldIn) {
        return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
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
        this.dataManager.register(EntitySpiderCreeper.STATE, (-1));
        this.dataManager.register(EntitySpiderCreeper.POWERED, false);
        this.dataManager.register(EntitySpiderCreeper.CLIMBING, false);
    }
    
    public static void registerFixesCreeper(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntitySpiderCreeper.class);
    }
    
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }
    
    public double getMountedYOffset() {
        return this.height * 0.5f;
    }
    
    public void setBesideClimbableBlock(final boolean climbing) {
        this.dataManager.set(EntitySpiderCreeper.CLIMBING, climbing);
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    public boolean isPotionApplicable(final PotionEffect potioneffectIn) {
        return potioneffectIn.getPotion() != MobEffects.POISON && super.isPotionApplicable(potioneffectIn);
    }
    
    public void setInWeb() {
    }
    
    public boolean isBesideClimbableBlock() {
        return (boolean)this.dataManager.get(EntitySpiderCreeper.CLIMBING);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if ((Boolean)this.dataManager.get(EntitySpiderCreeper.POWERED)) {
            compound.setBoolean("powered", true);
        }
        compound.setShort("Fuse", (short)this.fuseTime);
        compound.setByte("ExplosionRadius", (byte)this.explosionRadius);
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(EntitySpiderCreeper.POWERED, compound.getBoolean("powered"));
        if (compound.hasKey("Fuse", 99)) {
            this.fuseTime = compound.getShort("Fuse");
        }
        if (compound.hasKey("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }
    }
    
    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;
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
            if (!this.world.isRemote) {
                this.setBesideClimbableBlock(this.collidedHorizontally);
            }
            if (this.getHealth() <= 18.0f) {
                this.tasks.addTask(2, new EntityAICreeperSwell4(this));
            }
            else {
                this.tasks.removeTask(new EntityAICreeperSwell4(this));
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
            else if (cause.getTrueSource() instanceof EntitySpiderCreeper && cause.getTrueSource() != this && ((EntitySpiderCreeper)cause.getTrueSource()).getPowered() && ((EntitySpiderCreeper)cause.getTrueSource()).ableToCauseSkullDrop()) {
                ((EntitySpiderCreeper)cause.getTrueSource()).incrementDroppedSkulls();
                this.entityDropItem(new ItemStack(Items.SKULL, 1, 4), 0.0f);
            }
        }
    }
    
    public boolean attackEntityAsMob(final Entity par1Entity) {
        par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        if (this.rand.nextInt(3) == 0 && this.getHealth() <= 18.0f) {
            this.explode();
        }
        return true;
    }
    
    public boolean getPowered() {
        return (boolean)this.dataManager.get(EntitySpiderCreeper.POWERED);
    }
    
    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(final float p_70831_1_) {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
    }
    
    protected void dropFewItems(final boolean par1, final int par2) {
        final int i = this.rand.nextInt(3);
        for (int j = 0; j < i; ++j) {
            this.dropItem(Items.GUNPOWDER, 1);
        }
        for (int j = 0; j < i; ++j) {
            this.dropItem(Items.STRING, 1);
        }
        for (int j = 0; j < i; ++j) {
            this.dropItem(Items.FLINT, 1);
        }
    }
    
    public int getCreeperState() {
        return (int)this.dataManager.get(EntitySpiderCreeper.STATE);
    }
    
    public void setCreeperState(final int state) {
        this.dataManager.set(EntitySpiderCreeper.STATE, state);
    }
    
    public void onStruckByLightning(final EntityLightningBolt lightningBolt) {
        super.onStruckByLightning(lightningBolt);
    }
    
    private void explode() {
        if (!this.world.isRemote) {
            final boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
            final float f = this.getPowered() ? 2.0f : 1.0f;
            this.dead = true;
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius * f, flag);
            this.setDead();
            this.spawnLingeringCloud();
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
    
    public boolean ableToCauseSkullDrop() {
        return this.droppedSkulls < 1 && this.world.getGameRules().getBoolean("doMobLoot");
    }
    
    public void incrementDroppedSkulls() {
        ++this.droppedSkulls;
    }
    
    static {
        STATE = EntityDataManager.createKey(EntitySpiderCreeper.class, DataSerializers.VARINT);
        POWERED = EntityDataManager.createKey(EntitySpiderCreeper.class, DataSerializers.BOOLEAN);
        CLIMBING = EntityDataManager.createKey(EntitySpiderCreeper.class, DataSerializers.BOOLEAN);
    }
}
