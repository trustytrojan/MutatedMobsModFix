//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import net.minecraftforge.common.*;
import net.minecraft.util.datafix.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.ai.*;
import mmm.eventHandler.*;
import net.minecraft.nbt.*;
import net.minecraft.world.storage.loot.*;
import javax.annotation.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.network.datasync.*;

public class EntityBlazeSnowman extends EntityGolem implements IRangedAttackMob, IShearable
{
    public static final net.minecraft.world.biome.Biome[] SPAWN_BIOMES = new net.minecraft.world.biome.Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH };
    private float heightOffset;
    private int heightOffsetUpdateTime;
    private static final DataParameter<Byte> PUMPKIN_EQUIPPED;
    
    public EntityBlazeSnowman(final World worldIn) {
        super(worldIn);
        this.heightOffset = 0.5f;
        this.setSize(0.7f, 1.9f);
        this.isImmuneToFire = true;
    }
    
    public static void registerFixesSnowman(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityBlazeSnowman.class);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIAttackRanged((IRangedAttackMob)this, 1.25, 3, 50.0f));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater((EntityCreature)this, 1.0, 1.0000001E-5f));
        this.tasks.addTask(3, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(4, new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget((EntityCreature)this, EntityLiving.class, 10, true, false, IMob.MOB_SELECTOR));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_BlazeSnowman);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.40000000298023225);
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityBlazeSnowman.PUMPKIN_EQUIPPED, (byte)16);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Pumpkin", this.isPumpkinEquipped());
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("Pumpkin")) {
            this.setPumpkinEquipped(compound.getBoolean("Pumpkin"));
        }
    }
    
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_BLAZE;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }
    
    public float getBrightness() {
        return 1.0f;
    }
    
    public void onLivingUpdate() {
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }
        if (this.world.isRemote) {
            if (this.rand.nextInt(24) == 0 && !this.isSilent()) {
                this.world.playSound(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f, false);
            }
            for (int i = 0; i < 2; ++i) {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0, new int[0]);
            }
        }
        super.onLivingUpdate();
    }
    
    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0f);
        }
        --this.heightOffsetUpdateTime;
        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5f + (float)this.rand.nextGaussian() * 3.0f;
        }
        final EntityLivingBase entitylivingbase = this.getAttackTarget();
        if (entitylivingbase != null && entitylivingbase.posY + entitylivingbase.getEyeHeight() > this.posY + this.getEyeHeight() + this.heightOffset) {
            this.motionY += (0.30000001192092896 - this.motionY) * 0.30000001192092896;
            this.isAirBorne = true;
        }
        super.updateAITasks();
    }
    
    public void fall(final float distance, final float damageMultiplier) {
    }
    
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float distanceFactor) {
        final double d0 = this.getDistanceSq((Entity)target);
        final double d2 = target.posX - this.posX;
        final double d3 = target.getEntityBoundingBox().minY + target.height / 2.0f - (this.posY + this.height / 2.0f);
        final double d4 = target.posZ - this.posZ;
        final float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5f;
        final EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.world, (EntityLivingBase)this, d2 + this.getRNG().nextGaussian() * f, d3, d4 + this.getRNG().nextGaussian() * f);
        entitysmallfireball.posY = this.posY + this.height / 2.0f + 0.5;
        this.world.spawnEntity((Entity)entitysmallfireball);
    }
    
    public float getEyeHeight() {
        return 1.7f;
    }
    
    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BLAZE_AMBIENT;
    }
    
    @Nullable
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SNOWMAN_HURT;
    }
    
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SNOWMAN_DEATH;
    }
    
    public boolean isPumpkinEquipped() {
        return ((byte)this.dataManager.get(EntityBlazeSnowman.PUMPKIN_EQUIPPED) & 0x10) != 0x0;
    }
    
    public void setPumpkinEquipped(final boolean pumpkinEquipped) {
        final byte b0 = (byte)this.dataManager.get(EntityBlazeSnowman.PUMPKIN_EQUIPPED);
        if (pumpkinEquipped) {
            this.dataManager.set(EntityBlazeSnowman.PUMPKIN_EQUIPPED, (byte)(b0 | 0x10));
        }
        else {
            this.dataManager.set(EntityBlazeSnowman.PUMPKIN_EQUIPPED, (byte)(b0 & 0xFFFFFFEF));
        }
    }
    
    public boolean isShearable(final ItemStack item, final IBlockAccess world, final BlockPos pos) {
        return this.isPumpkinEquipped();
    }
    
    public List<ItemStack> onSheared(final ItemStack item, final IBlockAccess world, final BlockPos pos, final int fortune) {
        this.setPumpkinEquipped(false);
        return Lists.<ItemStack>newArrayList();
    }
    
    public void setSwingingArms(final boolean swingingArms) {
    }
    
    static {
        PUMPKIN_EQUIPPED = EntityDataManager.createKey(EntityBlazeSnowman.class, DataSerializers.BYTE);
    }
}
