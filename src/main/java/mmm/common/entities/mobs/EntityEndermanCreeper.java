//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.*;
import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.world.*;
import mmm.common.entities.mobs.ai.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import mmm.eventHandler.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.monster.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.storage.loot.*;
import javax.annotation.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.network.datasync.*;

public class EntityEndermanCreeper extends EntityHalfCreeper implements IMutant
{
    public static final net.minecraft.world.biome.Biome[] SPAWN_BIOMES = new net.minecraft.world.biome.Biome[] { Biomes.PLAINS, Biomes.SKY, Biomes.BEACH, Biomes.DESERT, Biomes.FOREST, Biomes.SWAMPLAND, Biomes.HELL, Biomes.EXTREME_HILLS, Biomes.MESA, Biomes.JUNGLE, Biomes.ROOFED_FOREST, Biomes.SAVANNA, Biomes.REDWOOD_TAIGA, Biomes.TAIGA, Biomes.STONE_BEACH };
    private static final DataParameter<Integer> STATE;
    private static final DataParameter<Boolean> POWERED;
    private static final DataParameter<Boolean> IGNITED;
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime;
    private int explosionRadius;
    private int droppedSkulls;
    public int deathTicks;
    
    public EntityEndermanCreeper(final World worldIn) {
        super(worldIn);
        this.fuseTime = 30;
        this.explosionRadius = 3;
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(2, new EntityAIEndermanCreeperSwell(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0f, 1.0, 1.2));
        this.tasks.addTask(1, new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0, false));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8));
        this.tasks.addTask(6, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_EnderCreeper);
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
        this.dataManager.register(EntityEndermanCreeper.STATE, (-1));
        this.dataManager.register(EntityEndermanCreeper.POWERED, false);
        this.dataManager.register(EntityEndermanCreeper.IGNITED, false);
    }
    
    public static void registerFixesCreeper(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityEndermanCreeper.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if ((Boolean)this.dataManager.get(EntityEndermanCreeper.POWERED)) {
            compound.setBoolean("powered", true);
        }
        compound.setShort("Fuse", (short)this.fuseTime);
        compound.setByte("ExplosionRadius", (byte)this.explosionRadius);
        compound.setBoolean("ignited", this.hasIgnited());
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(EntityEndermanCreeper.POWERED, compound.getBoolean("powered"));
        if (compound.hasKey("Fuse", 99)) {
            this.fuseTime = compound.getShort("Fuse");
        }
        if (compound.hasKey("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }
        if (compound.getBoolean("ignited")) {
            this.ignite();
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
            if (this.getAttackTarget() != null && this.rand.nextInt(10) == 0) {
                this.teleportToEntity(this.getAttackTarget());
            }
        }
        super.onUpdate();
    }
    
    protected boolean teleportRandomly() {
        final double d0 = this.posX + (this.rand.nextDouble() - 0.5) * 64.0;
        final double d2 = this.posY + (this.rand.nextInt(64) - 32);
        final double d3 = this.posZ + (this.rand.nextDouble() - 0.5) * 64.0;
        return this.teleportTo(d0, d2, d3);
    }
    
    public void Teleport() {
    }
    
    protected boolean teleportToEntity(final Entity p_70816_1_) {
        Vec3d vec3d = new Vec3d(this.posX - p_70816_1_.posX, this.getEntityBoundingBox().minY + this.height / 2.0f - p_70816_1_.posY + p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
        vec3d = vec3d.normalize();
        final double d0 = 16.0;
        final double d2 = this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - vec3d.x * 16.0;
        final double d3 = this.posY + (this.rand.nextInt(16) - 8) - vec3d.y * 16.0;
        final double d4 = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - vec3d.z * 16.0;
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
    
    public void onDeath(final DamageSource cause) {
        super.onDeath(cause);
        if (this.world.getGameRules().getBoolean("doMobLoot")) {
            if (cause.getTrueSource() instanceof EntitySkeleton) {
                final int i = Item.getIdFromItem(Items.RECORD_13);
                final int j = Item.getIdFromItem(Items.RECORD_WAIT);
                final int k = i + this.rand.nextInt(j - i + 1);
                this.dropItem(Item.getItemById(k), 1);
            }
            else if (cause.getTrueSource() instanceof EntityEndermanCreeper && cause.getTrueSource() != this && ((EntityEndermanCreeper)cause.getTrueSource()).getPowered() && ((EntityEndermanCreeper)cause.getTrueSource()).ableToCauseSkullDrop()) {
                ((EntityEndermanCreeper)cause.getTrueSource()).incrementDroppedSkulls();
                this.entityDropItem(new ItemStack(Items.SKULL, 1, 4), 0.0f);
            }
        }
    }
    
    public boolean attackEntityAsMob(final Entity entityIn) {
        return true;
    }
    
    public boolean getPowered() {
        return (boolean)this.dataManager.get(EntityEndermanCreeper.POWERED);
    }
    
    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(final float p_70831_1_) {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
    }
    
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_CREEPER;
    }
    
    public int getCreeperState() {
        return (int)this.dataManager.get(EntityEndermanCreeper.STATE);
    }
    
    public void setCreeperState(final int state) {
        this.dataManager.set(EntityEndermanCreeper.STATE, state);
    }
    
    protected boolean processInteract(final EntityPlayer player, final EnumHand hand) {
        final ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.REDSTONE) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                this.ignite();
                itemstack.shrink(1);
                return true;
            }
        }
        return super.processInteract(player, hand);
    }
    
    public void Detonate() {
        this.explode();
    }
    
    private void explode() {
        if (!this.world.isRemote) {
            final boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
            final float f = this.getPowered() ? 2.0f : 1.0f;
            final double d0 = this.posX + (this.rand.nextDouble() - 0.5) * 64.0;
            final double d2 = this.posY + (this.rand.nextInt(64) - 32);
            final double d3 = this.posZ + (this.rand.nextDouble() - 0.5) * 64.0;
            if (this.getAttackTarget() != null) {
                this.teleportToEntity(this.getAttackTarget());
                this.world.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, flag);
                this.dead = true;
                this.setDead();
                this.spawnLingeringCloud();
            }
            else {
                this.teleportTo(d0, d2, d3);
                this.world.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, flag);
                this.dead = true;
                this.setDead();
                this.spawnLingeringCloud();
            }
        }
    }
    
    private void spawnLingeringCloud() {
        final Collection<PotionEffect> collection = (Collection<PotionEffect>)this.getActivePotionEffects();
        if (!collection.isEmpty()) {
            final EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
            if (this.getPowered()) {
                entityareaeffectcloud.setRadius(25.0f);
                entityareaeffectcloud.setRadiusOnUse(-0.5f);
                entityareaeffectcloud.setWaitTime(50);
                entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration());
                entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / entityareaeffectcloud.getDuration());
            }
            else {
                entityareaeffectcloud.setRadius(5.0f);
                entityareaeffectcloud.setRadiusOnUse(-0.5f);
                entityareaeffectcloud.setWaitTime(10);
                entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
                entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / entityareaeffectcloud.getDuration());
            }
            for (final PotionEffect potioneffect : collection) {
                entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
            }
            this.world.spawnEntity(entityareaeffectcloud);
        }
    }
    
    public boolean hasIgnited() {
        return (boolean)this.dataManager.get(EntityEndermanCreeper.IGNITED);
    }
    
    public void ignite() {
        this.dataManager.set(EntityEndermanCreeper.IGNITED, true);
    }
    
    public boolean ableToCauseSkullDrop() {
        return this.droppedSkulls < 1 && this.world.getGameRules().getBoolean("doMobLoot");
    }
    
    public void incrementDroppedSkulls() {
        ++this.droppedSkulls;
    }
    
    static {
        STATE = EntityDataManager.createKey(EntityEndermanCreeper.class, DataSerializers.VARINT);
        POWERED = EntityDataManager.createKey(EntityEndermanCreeper.class, DataSerializers.BOOLEAN);
        IGNITED = EntityDataManager.createKey(EntityEndermanCreeper.class, DataSerializers.BOOLEAN);
    }
}
