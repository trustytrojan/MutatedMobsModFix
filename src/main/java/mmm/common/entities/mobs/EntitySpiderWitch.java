//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import net.minecraft.entity.monster.*;
import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.world.*;
import net.minecraft.util.datafix.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.pathfinding.*;
import mmm.eventHandler.*;
import net.minecraft.inventory.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.potion.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.world.storage.loot.*;
import javax.annotation.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.network.datasync.*;

public class EntitySpiderWitch extends EntityMob implements IRangedAttackMob, IMutant
{
    public static final net.minecraft.world.biome.Biome[] SPAWN_BIOMES = new net.minecraft.world.biome.Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH };
    private static final UUID MODIFIER_UUID;
    private static final AttributeModifier MODIFIER;
    private static final DataParameter<Boolean> IS_AGGRESSIVE;
    private static final DataParameter<Boolean> CLIMBING;
    private int witchAttackTimer;
    
    public EntitySpiderWitch(final World worldIn) {
        super(worldIn);
        this.setSize(0.6f, 1.95f);
    }
    
    public static void registerFixesWitch(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntitySpiderWitch.class);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(2, new EntityAIAttackRanged((IRangedAttackMob)this, 1.0, 15, 75.0f));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(3, new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(EntitySpiderWitch.IS_AGGRESSIVE, false);
        this.dataManager.register(EntitySpiderWitch.CLIMBING, false);
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
    
    public void setBesideClimbableBlock(final boolean climbing) {
        this.dataManager.set(EntitySpiderWitch.CLIMBING, climbing);
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
        return (boolean)this.dataManager.get(EntitySpiderWitch.CLIMBING);
    }
    
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WITCH_AMBIENT;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_WITCH_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WITCH_DEATH;
    }
    
    public void setAggressive(final boolean aggressive) {
        this.getDataManager().set(EntitySpiderWitch.IS_AGGRESSIVE, aggressive);
    }
    
    public boolean isDrinkingPotion() {
        return (boolean)this.getDataManager().get(EntitySpiderWitch.IS_AGGRESSIVE);
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_SpiderWitch);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.36);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(120.0);
    }
    
    public void onLivingUpdate() {
        if (!this.world.isRemote) {
            if (this.isDrinkingPotion()) {
                if (this.witchAttackTimer-- <= 0) {
                    this.setAggressive(false);
                    final ItemStack itemstack = this.getHeldItemMainhand();
                    this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    if (itemstack.getItem() == Items.POTIONITEM) {
                        final List<PotionEffect> list = (List<PotionEffect>)PotionUtils.getEffectsFromStack(itemstack);
                        if (list != null) {
                            for (final PotionEffect potioneffect : list) {
                                this.addPotionEffect(new PotionEffect(potioneffect));
                            }
                        }
                    }
                    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(EntitySpiderWitch.MODIFIER);
                }
            }
            else {
                PotionType potiontype = null;
                if (this.rand.nextFloat() < 0.15f && this.isInsideOfMaterial(Material.WATER) && !this.isPotionActive(MobEffects.WATER_BREATHING)) {
                    potiontype = PotionTypes.WATER_BREATHING;
                }
                else if (this.rand.nextFloat() < 0.15f && (this.isBurning() || (this.getLastDamageSource() != null && this.getLastDamageSource().isFireDamage())) && !this.isPotionActive(MobEffects.FIRE_RESISTANCE)) {
                    potiontype = PotionTypes.FIRE_RESISTANCE;
                }
                else if (this.rand.nextFloat() < 0.05f && this.getHealth() < this.getMaxHealth()) {
                    potiontype = PotionTypes.HEALING;
                }
                else if (this.rand.nextFloat() < 0.5f && this.getAttackTarget() != null && !this.isPotionActive(MobEffects.SPEED) && this.getAttackTarget().getDistanceSq(this) > 121.0) {
                    potiontype = PotionTypes.SWIFTNESS;
                }
                if (potiontype != null) {
                    this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, PotionUtils.addPotionToItemStack(new ItemStack((Item)Items.POTIONITEM), potiontype));
                    this.witchAttackTimer = this.getHeldItemMainhand().getMaxItemUseDuration();
                    this.setAggressive(true);
                    this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_DRINK, this.getSoundCategory(), 1.0f, 0.8f + this.rand.nextFloat() * 0.4f);
                    final IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
                    iattributeinstance.removeModifier(EntitySpiderWitch.MODIFIER);
                    iattributeinstance.applyModifier(EntitySpiderWitch.MODIFIER);
                }
            }
            if (this.rand.nextFloat() < 7.5E-4f) {
                this.world.setEntityState(this, (byte)15);
            }
        }
        super.onLivingUpdate();
    }
    
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(final byte id) {
        if (id == 15) {
            for (int i = 0; i < this.rand.nextInt(35) + 10; ++i) {
                this.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX + this.rand.nextGaussian() * 0.12999999523162842, this.getEntityBoundingBox().maxY + 0.5 + this.rand.nextGaussian() * 0.12999999523162842, this.posZ + this.rand.nextGaussian() * 0.12999999523162842, 0.0, 0.0, 0.0, new int[0]);
            }
        }
        else {
            super.handleStatusUpdate(id);
        }
    }
    
    protected float applyPotionDamageCalculations(final DamageSource source, float damage) {
        damage = super.applyPotionDamageCalculations(source, damage);
        if (source.getTrueSource() == this) {
            damage = 0.0f;
        }
        if (source.isMagicDamage()) {
            damage *= (float)0.15;
        }
        return damage;
    }
    
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_WITCH;
    }
    
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float distanceFactor) {
        if (!this.isDrinkingPotion()) {
            final double d0 = target.posY + target.getEyeHeight() - 1.100000023841858;
            final double d2 = target.posX + target.motionX - this.posX;
            final double d3 = d0 - this.posY;
            final double d4 = target.posZ + target.motionZ - this.posZ;
            final float f = MathHelper.sqrt(d2 * d2 + d4 * d4);
            PotionType potiontype = PotionTypes.HARMING;
            if (f >= 8.0f && !target.isPotionActive(MobEffects.SLOWNESS)) {
                potiontype = PotionTypes.SLOWNESS;
            }
            else if (target.getHealth() >= 8.0f && !target.isPotionActive(MobEffects.POISON)) {
                potiontype = PotionTypes.POISON;
            }
            else if (f <= 3.0f && !target.isPotionActive(MobEffects.WEAKNESS) && this.rand.nextFloat() < 0.25f) {
                potiontype = PotionTypes.WEAKNESS;
            }
            final EntityPotion entityPotion;
            final EntityPotion entitypotion = entityPotion = new EntityPotion(this.world, (EntityLivingBase)this, PotionUtils.addPotionToItemStack(new ItemStack((Item)Items.SPLASH_POTION), potiontype));
            entityPotion.rotationPitch += 20.0f;
            entitypotion.shoot(d2, d3 + f * 0.2f, d4, 0.75f, 8.0f);
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0f, 0.8f + this.rand.nextFloat() * 0.4f);
            this.world.spawnEntity(entitypotion);
        }
    }
    
    public float getEyeHeight() {
        return 1.62f;
    }
    
    public void setSwingingArms(final boolean swingingArms) {
    }
    
    static {
        MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
        MODIFIER = new AttributeModifier(EntitySpiderWitch.MODIFIER_UUID, "Drinking speed penalty", -0.25, 0).setSaved(false);
        IS_AGGRESSIVE = EntityDataManager.createKey(EntitySpiderWitch.class, DataSerializers.BOOLEAN);
        CLIMBING = EntityDataManager.createKey(EntitySpiderWitch.class, DataSerializers.BOOLEAN);
    }
}
