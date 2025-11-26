//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import net.minecraft.util.datafix.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.ai.*;
import mmm.eventHandler.*;
import net.minecraft.nbt.*;
import net.minecraft.world.storage.loot.*;
import javax.annotation.*;
import mmm.common.entities.projectiles.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.network.datasync.*;

public class EntityEnderSnowman extends EntityGolem implements IRangedAttackMob, IShearable
{
    private static final DataParameter<Byte> PUMPKIN_EQUIPPED;
    
    public EntityEnderSnowman(final World worldIn) {
        super(worldIn);
        this.setSize(0.7f, 1.9f);
    }
    
    public static void registerFixesSnowman(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityEnderSnowman.class);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(1, (EntityAIBase)new EntityAIAttackRanged((IRangedAttackMob)this, 1.25, 2, 50.0f));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0, 1.0000001E-5f));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityLiving.class, 10, true, false, IMob.MOB_SELECTOR));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_EnderSnowman);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.40000000298023225);
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityEnderSnowman.PUMPKIN_EQUIPPED, (byte)16);
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
    
    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        if (!source.isMagicDamage() && source.getImmediateSource() instanceof EntityLivingBase) {
            final EntityLivingBase entitylivingbase = (EntityLivingBase)source.getImmediateSource();
            if (source.isMagicDamage()) {
                entitylivingbase.attackEntityFrom(DamageSource.CACTUS, 5.0f);
            }
        }
        return super.attackEntityFrom(source, amount);
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.world.isRemote) {
            final int i = MathHelper.floor(this.posX);
            final int j = MathHelper.floor(this.posY);
            final int k = MathHelper.floor(this.posZ);
            if (this.isWet()) {
                this.attackEntityFrom(DamageSource.DROWN, 1.0f);
            }
        }
    }
    
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_SNOWMAN;
    }
    
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float distanceFactor) {
        final EntityEnderBall entitysnowball = new EntityEnderBall(this.world, (EntityLivingBase)this);
        final double d0 = target.posY + target.getEyeHeight() - 1.100000023841858;
        final double d2 = target.posX - this.posX;
        final double d3 = d0 - entitysnowball.posY;
        final double d4 = target.posZ - this.posZ;
        final float f = MathHelper.sqrt(d2 * d2 + d4 * d4) * 0.2f;
        entitysnowball.shoot(d2, d3 + f, d4, 1.6f, 12.0f);
        this.playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.world.spawnEntity((Entity)entitysnowball);
        this.teleportRandomly();
    }
    
    public float getEyeHeight() {
        return 1.7f;
    }
    
    protected boolean teleportRandomly() {
        final double d0 = this.posX + (this.rand.nextDouble() - 0.5) * 8.0;
        final double d2 = this.posY + (this.rand.nextInt(64) - 32);
        final double d3 = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0;
        return this.teleportTo(d0, d2, d3);
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
    
    public boolean isPumpkinEquipped() {
        return ((byte)this.dataManager.get(EntityEnderSnowman.PUMPKIN_EQUIPPED) & 0x10) != 0x0;
    }
    
    public void setPumpkinEquipped(final boolean pumpkinEquipped) {
        final byte b0 = (byte)this.dataManager.get(EntityEnderSnowman.PUMPKIN_EQUIPPED);
        if (pumpkinEquipped) {
            this.dataManager.set(EntityEnderSnowman.PUMPKIN_EQUIPPED, (byte)(b0 | 0x10));
        }
        else {
            this.dataManager.set(EntityEnderSnowman.PUMPKIN_EQUIPPED, (byte)(b0 & 0xFFFFFFEF));
        }
    }
    
    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SNOWMAN_AMBIENT;
    }
    
    @Nullable
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SNOWMAN_HURT;
    }
    
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SNOWMAN_DEATH;
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
        PUMPKIN_EQUIPPED = EntityDataManager.createKey(EntityEnderSnowman.class, DataSerializers.BYTE);
    }
}
