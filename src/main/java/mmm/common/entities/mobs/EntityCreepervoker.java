//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.*;
import mmm.eventHandler.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import net.minecraft.entity.effect.*;
import net.minecraft.init.*;
import mmm.common.items.*;
import javax.annotation.*;
import net.minecraft.util.*;
import net.minecraft.network.datasync.*;
import mmm.common.entities.projectiles.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import com.google.common.base.*;
import net.minecraft.item.*;
import java.util.*;

public class EntityCreepervoker extends EntitySpellcasterIllager implements IMutant
{
    private EntitySheep wololoTarget;
    private static final DataParameter<Integer> STATE = EntityDataManager.createKey(EntityCreepervoker.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(EntityCreepervoker.class, DataSerializers.BOOLEAN);
    public static final net.minecraft.world.biome.Biome[] SPAWN_BIOMES = new net.minecraft.world.biome.Biome[] { Biomes.SWAMPLAND, Biomes.MUTATED_SWAMPLAND };
    
    public EntityCreepervoker(final World worldIn) {
        super(worldIn);
        this.setSize(0.6f, 1.95f);
        this.experienceValue = 10;
    }
    
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, new AICastingSpell());
        this.tasks.addTask(2, new EntityAIAvoidEntity((EntityCreature)this, EntityPlayer.class, 8.0f, 0.6, 1.0));
        this.tasks.addTask(4, new AISummonSpell());
        this.tasks.addTask(5, new AIAttackSpell());
        this.tasks.addTask(6, new AIWololoSpell());
        this.tasks.addTask(8, new EntityAIWander((EntityCreature)this, 0.6));
        this.tasks.addTask(9, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 3.0f, 1.0f));
        this.tasks.addTask(10, new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0f));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget((EntityCreature)this, true, new Class[] { EntityCreepervoker.class }));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget((EntityCreature)this, EntityPlayer.class, true).setUnseenMemoryTicks(300));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget((EntityCreature)this, EntityVillager.class, false).setUnseenMemoryTicks(300));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget((EntityCreature)this, EntityIronGolem.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget((EntityCreature)this, EntityGolem.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget((EntityCreature)this, EntityCreeperPig.class, false));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(160.0);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_CreeperVoker);
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityCreepervoker.STATE, (-1));
        this.dataManager.register(EntityCreepervoker.POWERED, false);
    }
    
    public static void registerFixesEvoker(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityCreepervoker.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if ((Boolean)this.dataManager.get(EntityCreepervoker.POWERED)) {
            compound.setBoolean("powered", true);
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(EntityCreepervoker.POWERED, compound.getBoolean("powered"));
    }
    
    protected void updateAITasks() {
        super.updateAITasks();
    }
    
    public void onUpdate() {
        super.onUpdate();
    }
    
    protected boolean processInteract(final EntityPlayer player, final EnumHand hand) {
        final ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
            player.swingArm(hand);
            if (!this.world.isRemote) {
                itemstack.damageItem(1, (EntityLivingBase)player);
                return true;
            }
        }
        return super.processInteract(player, hand);
    }
    
    private void spawnLingeringCloud() {
        final Collection<PotionEffect> collection = (Collection<PotionEffect>)this.getActivePotionEffects();
        if (!collection.isEmpty()) {
            final EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
            entityareaeffectcloud.setRadius(10.0f);
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
    
    public int getCreeperState() {
        return (int)this.dataManager.get(EntityCreepervoker.STATE);
    }
    
    public void setCreeperState(final int state) {
        this.dataManager.set(EntityCreepervoker.STATE, state);
    }
    
    public void onStruckByLightning(final EntityLightningBolt lightningBolt) {
        super.onStruckByLightning(lightningBolt);
        this.dataManager.set(EntityCreepervoker.POWERED, true);
        this.setSprinting(true);
        this.addPotionEffect(new PotionEffect(MobEffects.SPEED, 100, 10));
        this.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, 5));
        this.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 100, 100));
        this.heal(100.0f);
    }
    
    public boolean getPowered() {
        return (boolean)this.dataManager.get(EntityCreepervoker.POWERED);
    }
    
    protected void dropFewItems(final boolean par1, final int par2) {
        final int i = this.rand.nextInt(3);
        for (int j = 0; j < i; ++j) {
            this.dropItem(Items.GUNPOWDER, 1);
        }
        for (int j = 0; j < i; ++j) {
            this.dropItem(Items.TOTEM_OF_UNDYING, 1);
        }
        for (int j = 0; j < i; ++j) {
            this.dropItem(ModItems.detonator, 1);
        }
    }
    
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GENERIC_EXPLODE;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_EVOCATION_ILLAGER_HURT;
    }
    
    private void setWololoTarget(@Nullable final EntitySheep wololoTargetIn) {
        this.wololoTarget = wololoTargetIn;
    }
    
    @Nullable
    private EntitySheep getWololoTarget() {
        return this.wololoTarget;
    }
    
    protected SoundEvent getSpellSound() {
        return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.isSpellcasting()) {
            for (int i = 0; i < 2; ++i) {
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }
    
    class AIAttackSpell extends EntitySpellcasterIllager.AIUseSpell
    {
        private AIAttackSpell() {
            super();
        }
        
        protected int getCastingTime() {
            return 40;
        }
        
        protected int getCastingInterval() {
            return 100;
        }
        
        protected void castSpell() {
            final EntityLivingBase entitylivingbase = EntityCreepervoker.this.getAttackTarget();
            final double d0 = Math.min(entitylivingbase.posY, EntityCreepervoker.this.posY);
            final double d2 = Math.max(entitylivingbase.posY, EntityCreepervoker.this.posY) + 1.0;
            final float f = (float)MathHelper.atan2(entitylivingbase.posZ - EntityCreepervoker.this.posZ, entitylivingbase.posX - EntityCreepervoker.this.posX);
            if (EntityCreepervoker.this.getDistanceSq(entitylivingbase) < 9.0) {
                for (int i = 0; i < 5; ++i) {
                    final float f2 = f + i * 3.1415927f * 0.4f;
                    this.spawnFangs(EntityCreepervoker.this.posX + MathHelper.cos(f2) * 1.5, EntityCreepervoker.this.posZ + MathHelper.sin(f2) * 1.5, d0, d2, f2, 0);
                }
                for (int k = 0; k < 8; ++k) {
                    final float f3 = f + k * 3.1415927f * 2.0f / 8.0f + 1.2566371f;
                    this.spawnFangs(EntityCreepervoker.this.posX + MathHelper.cos(f3) * 2.5, EntityCreepervoker.this.posZ + MathHelper.sin(f3) * 2.5, d0, d2, f3, 3);
                }
            }
            else {
                for (int l = 0; l < 16; ++l) {
                    final double d3 = 1.25 * (l + 1);
                    final int j = 1 * l;
                    this.spawnFangs(EntityCreepervoker.this.posX + MathHelper.cos(f) * d3, EntityCreepervoker.this.posZ + MathHelper.sin(f) * d3, d0, d2, f, j);
                }
            }
        }
        
        private void spawnFangs(final double p_190876_1_, final double p_190876_3_, final double p_190876_5_, final double p_190876_7_, final float p_190876_9_, final int p_190876_10_) {
            BlockPos blockpos = new BlockPos(p_190876_1_, p_190876_7_, p_190876_3_);
            double d0 = 0.0;
            while (EntityCreepervoker.this.world.isBlockNormalCube(blockpos, true) || !EntityCreepervoker.this.world.isBlockNormalCube(blockpos.down(), true)) {
                blockpos = blockpos.down();
                if (blockpos.getY() < MathHelper.floor(p_190876_5_) - 1) {
                    return;
                }
            }
            if (!EntityCreepervoker.this.world.isAirBlock(blockpos)) {
                final IBlockState iblockstate = EntityCreepervoker.this.world.getBlockState(blockpos);
                final AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox((IBlockAccess)EntityCreepervoker.this.world, blockpos);
                if (axisalignedbb != null) {
                    d0 = axisalignedbb.maxY;
                }
            }
            final EntityCreeperfangs EntityCreepervokerfangs = new EntityCreeperfangs(EntityCreepervoker.this.world, p_190876_1_, blockpos.getY() + d0, p_190876_3_, p_190876_9_, p_190876_10_, (EntityLivingBase)EntityCreepervoker.this);
            EntityCreepervoker.this.world.spawnEntity(EntityCreepervokerfangs);
        }
        
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
        }
        
        protected EntitySpellcasterIllager.SpellType getSpellType() {
            return EntitySpellcasterIllager.SpellType.FANGS;
        }
    }
    
    class AICastingSpell extends EntitySpellcasterIllager.AICastingApell
    {
        private AICastingSpell() {
            super();
        }
        
        public void updateTask() {
            if (EntityCreepervoker.this.getAttackTarget() != null) {
                EntityCreepervoker.this.getLookHelper().setLookPositionWithEntity(EntityCreepervoker.this.getAttackTarget(), (float)EntityCreepervoker.this.getHorizontalFaceSpeed(), (float)EntityCreepervoker.this.getVerticalFaceSpeed());
            }
            else if (EntityCreepervoker.this.getWololoTarget() != null) {
                EntityCreepervoker.this.getLookHelper().setLookPositionWithEntity(EntityCreepervoker.this.getWololoTarget(), (float)EntityCreepervoker.this.getHorizontalFaceSpeed(), (float)EntityCreepervoker.this.getVerticalFaceSpeed());
            }
        }
    }
    
    class AISummonSpell extends EntitySpellcasterIllager.AIUseSpell
    {
        private AISummonSpell() {
            super();
        }
        
        public boolean shouldExecute() {
            if (!super.shouldExecute()) {
                return false;
            }
            final int i = EntityCreepervoker.this.world.getEntitiesWithinAABB(EntityVex.class, EntityCreepervoker.this.getEntityBoundingBox().grow(16.0)).size();
            return EntityCreepervoker.this.rand.nextInt(8) + 1 > i;
        }
        
        protected int getCastingTime() {
            return 100;
        }
        
        protected int getCastingInterval() {
            return 340;
        }
        
        protected void castSpell() {
            for (int i = 0; i < 3; ++i) {
                final BlockPos blockpos = new BlockPos(EntityCreepervoker.this).add(-2 + EntityCreepervoker.this.rand.nextInt(5), 2, -2 + EntityCreepervoker.this.rand.nextInt(5));
                final EntityVex entityvex = new EntityVex(EntityCreepervoker.this.world);
                entityvex.moveToBlockPosAndAngles(blockpos, 0.0f, 0.0f);
                entityvex.onInitialSpawn(EntityCreepervoker.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
                entityvex.setOwner((EntityLiving)EntityCreepervoker.this);
                entityvex.setBoundOrigin(blockpos);
                entityvex.setLimitedLife(60 * (90 + EntityCreepervoker.this.rand.nextInt(90)));
                EntityCreepervoker.this.world.spawnEntity(entityvex);
            }
        }
        
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON;
        }
        
        protected EntitySpellcasterIllager.SpellType getSpellType() {
            return EntitySpellcasterIllager.SpellType.SUMMON_VEX;
        }
    }
    
    public class AIWololoSpell extends EntitySpellcasterIllager.AIUseSpell
    {
        final Predicate<EntitySheep> wololoSelector;
        
        public AIWololoSpell() {
            super();
            this.wololoSelector = (Predicate<EntitySheep>)new Predicate<EntitySheep>() {
                public boolean apply(final EntitySheep p_apply_1_) {
                    return p_apply_1_.getFleeceColor() == EnumDyeColor.BLUE;
                }
            };
        }
        
        public boolean shouldExecute() {
            if (EntityCreepervoker.this.getAttackTarget() != null) {
                return false;
            }
            if (EntityCreepervoker.this.isSpellcasting()) {
                return false;
            }
            if (EntityCreepervoker.this.ticksExisted < this.spellCooldown) {
                return false;
            }
            if (!EntityCreepervoker.this.world.getGameRules().getBoolean("mobGriefing")) {
                return false;
            }
            final List<EntitySheep> list = (List<EntitySheep>)EntityCreepervoker.this.world.getEntitiesWithinAABB(EntitySheep.class, EntityCreepervoker.this.getEntityBoundingBox().grow(16.0, 4.0, 16.0), this.wololoSelector);
            if (list.isEmpty()) {
                return false;
            }
            EntityCreepervoker.this.setWololoTarget(list.get(EntityCreepervoker.this.rand.nextInt(list.size())));
            return true;
        }
        
        public boolean shouldContinueExecuting() {
            return EntityCreepervoker.this.getWololoTarget() != null && this.spellWarmup > 0;
        }
        
        public void resetTask() {
            super.resetTask();
            EntityCreepervoker.this.setWololoTarget(null);
        }
        
        protected void castSpell() {
            final EntitySheep entitysheep = EntityCreepervoker.this.getWololoTarget();
            if (entitysheep != null && entitysheep.isEntityAlive()) {
                entitysheep.setFleeceColor(EnumDyeColor.ORANGE);
            }
        }
        
        protected int getCastWarmupTime() {
            return 40;
        }
        
        protected int getCastingTime() {
            return 60;
        }
        
        protected int getCastingInterval() {
            return 140;
        }
        
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_WOLOLO;
        }
        
        protected EntitySpellcasterIllager.SpellType getSpellType() {
            return EntitySpellcasterIllager.SpellType.WOLOLO;
        }
    }
}
