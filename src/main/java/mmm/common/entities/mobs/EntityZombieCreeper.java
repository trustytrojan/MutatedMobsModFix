//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import mmm.common.entities.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import java.util.*;
import mmm.eventHandler.*;
import net.minecraft.network.datasync.*;

public class EntityZombieCreeper extends EntityZombie implements IMutant
{
    public static final net.minecraft.world.biome.Biome[] SPAWN_BIOMES = new net.minecraft.world.biome.Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH };
    private static final DataParameter<Integer> STATE;
    private static final DataParameter<Boolean> POWERED;
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime;
    private int explosionRadius;
    
    public EntityZombieCreeper(final World worldIn) {
        super(worldIn);
        this.fuseTime = 30;
        this.explosionRadius = 3;
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityOcelot.class, 6.0f, 1.0, 1.2));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0, false));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8));
        this.tasks.addTask(6, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityGolem.class, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityWolf.class, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPig.class, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityZombieVillager.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
    
    public static void registerFixesCreeper(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityZombieCreeper.class);
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityZombieCreeper.STATE, (-1));
        this.dataManager.register(EntityZombieCreeper.POWERED, false);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if ((Boolean)this.dataManager.get(EntityZombieCreeper.POWERED)) {
            compound.setBoolean("powered", true);
        }
        compound.setShort("Fuse", (short)this.fuseTime);
        compound.setByte("ExplosionRadius", (byte)this.explosionRadius);
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(EntityZombieCreeper.POWERED, compound.getBoolean("powered"));
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
            if (this.getHealth() <= 18.0f) {
                this.tasks.addTask(2, new EntityAICreeperSwell6(this));
            }
            else {
                this.tasks.removeTask(new EntityAICreeperSwell6(this));
            }
        }
        super.onUpdate();
    }
    
    public int getCreeperState() {
        return (int)this.dataManager.get(EntityZombieCreeper.STATE);
    }
    
    public void setCreeperState(final int state) {
        this.dataManager.set(EntityZombieCreeper.STATE, state);
    }
    
    public void onStruckByLightning(final EntityLightningBolt lightningBolt) {
        super.onStruckByLightning(lightningBolt);
    }
    
    public boolean getPowered() {
        return (boolean)this.dataManager.get(EntityZombieCreeper.POWERED);
    }
    
    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(final float p_70831_1_) {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
    }
    
    public boolean attackEntityAsMob(final Entity par1Entity) {
        par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        if (this.rand.nextInt(3) == 0 && this.getHealth() <= 18.0f) {
            this.explode();
        }
        return true;
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
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_ZombieCreeper);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(65.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3300000041723251);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0);
    }
    
    static {
        STATE = EntityDataManager.createKey(EntityZombieCreeper.class, DataSerializers.VARINT);
        POWERED = EntityDataManager.createKey(EntityZombieCreeper.class, DataSerializers.BOOLEAN);
    }
}
