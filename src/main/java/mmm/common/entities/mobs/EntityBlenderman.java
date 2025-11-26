//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.*;
import mmm.eventHandler.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import javax.annotation.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.world.storage.loot.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.network.datasync.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.util.math.*;

public class EntityBlenderman extends EntityMob implements IMutant
{
    private static final UUID ATTACKING_SPEED_BOOST_ID;
    private static final AttributeModifier ATTACKING_SPEED_BOOST;
    private static final Set<Block> CARRIABLE_BLOCKS;
    private static final DataParameter<com.google.common.base.Optional<IBlockState>> CARRIED_BLOCK;
    private static final DataParameter<Boolean> SCREAMING;
    private int lastCreepySound;
    private int targetChangeTime;
    Village village;
    private int attackTimer;
    private float heightOffset;
    private int heightOffsetUpdateTime;
    
    public EntityBlenderman(final World worldIn) {
        super(worldIn);
        this.attackTimer = 10;
        this.heightOffset = 0.5f;
        this.setSize(0.6f, 2.1f);
        this.stepHeight = 1.0f;
        this.setPathPriority(PathNodeType.WATER, -1.0f);
        this.isImmuneToFire = true;
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(7, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0, 0.0f));
        this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.tasks.addTask(10, (EntityAIBase)new AIPlaceBlock(this));
        this.tasks.addTask(11, (EntityAIBase)new AITakeBlock(this));
        this.targetTasks.addTask(1, (EntityAIBase)new AIFindPlayer(this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0, true));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveTowardsTarget((EntityCreature)this, 0.7, 70.0f));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, 0.7, true));
        this.tasks.addTask(4, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0));
        this.tasks.addTask(6, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 0.6));
        this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0f));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityWolf.class, true));
        this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityGolem.class, true));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_Blenderman);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35000001192092894);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(164.0);
    }
    
    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 0.5f);
        }
        --this.heightOffsetUpdateTime;
        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5f + (float)this.rand.nextGaussian() * 3.0f;
        }
        if (this.getAttackTarget() != null && this.onGround) {
            this.motionY += (0.40000001192092893 - this.motionY) * 0.30000001192092896;
            this.isAirBorne = true;
            this.setFire(5);
            this.setSprinting(true);
        }
        final EntityLivingBase entitylivingbase = this.getAttackTarget();
        if (entitylivingbase != null && entitylivingbase.posY + entitylivingbase.getEyeHeight() > this.posY + this.getEyeHeight() + this.heightOffset) {
            this.motionY += (0.40000001192092893 - this.motionY) * 0.40000001192092893;
            this.isAirBorne = true;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }
    
    public float getBrightness() {
        return 1.0f;
    }
    
    protected void collideWithEntity(final Entity entityIn) {
        if (entityIn instanceof IMob && !(entityIn instanceof EntityCreeper) && this.getRNG().nextInt(20) == 0) {
            this.setAttackTarget((EntityLivingBase)entityIn);
        }
        super.collideWithEntity(entityIn);
    }
    
    public void setAttackTarget(@Nullable final EntityLivingBase entitylivingbaseIn) {
        super.setAttackTarget(entitylivingbaseIn);
        final IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        if (entitylivingbaseIn == null) {
            this.targetChangeTime = 0;
            this.dataManager.set(EntityBlenderman.SCREAMING, false);
            iattributeinstance.removeModifier(EntityBlenderman.ATTACKING_SPEED_BOOST);
        }
        else {
            this.targetChangeTime = this.ticksExisted;
            this.dataManager.set(EntityBlenderman.SCREAMING, true);
            if (!iattributeinstance.hasModifier(EntityBlenderman.ATTACKING_SPEED_BOOST)) {
                iattributeinstance.applyModifier(EntityBlenderman.ATTACKING_SPEED_BOOST);
            }
        }
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityBlenderman.CARRIED_BLOCK, com.google.common.base.Optional.absent());
        this.dataManager.register(EntityBlenderman.SCREAMING, false);
    }
    
    public void playEndermanSound() {
        if (this.ticksExisted >= this.lastCreepySound + 400) {
            this.lastCreepySound = this.ticksExisted;
            if (!this.isSilent()) {
                this.world.playSound(this.posX, this.posY + this.getEyeHeight(), this.posZ, SoundEvents.ENTITY_ENDERMEN_STARE, this.getSoundCategory(), 2.5f, 1.0f, false);
            }
        }
    }
    
    public void notifyDataManagerChange(final DataParameter<?> key) {
        if (EntityBlenderman.SCREAMING.equals(key) && this.isScreaming() && this.world.isRemote) {
            this.playEndermanSound();
        }
        super.notifyDataManagerChange(key);
    }
    
    public static void registerFixesEnderman(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityBlenderman.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        final IBlockState iblockstate = this.getHeldBlockState();
        if (iblockstate != null) {
            compound.setShort("carried", (short)Block.getIdFromBlock(iblockstate.getBlock()));
            compound.setShort("carriedData", (short)iblockstate.getBlock().getMetaFromState(iblockstate));
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        IBlockState iblockstate;
        if (compound.hasKey("carried", 8)) {
            iblockstate = Block.getBlockFromName(compound.getString("carried")).getStateFromMeta(compound.getShort("carriedData") & 0xFFFF);
        }
        else {
            iblockstate = Block.getBlockById((int)compound.getShort("carried")).getStateFromMeta(compound.getShort("carriedData") & 0xFFFF);
        }
        if (iblockstate == null || iblockstate.getBlock() == null || iblockstate.getMaterial() == Material.AIR) {
            iblockstate = null;
        }
        this.setHeldBlockState(iblockstate);
    }
    
    private boolean shouldAttackPlayer(final EntityPlayer player) {
        final ItemStack itemstack = (ItemStack)player.inventory.armorInventory.get(3);
        if (itemstack.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN)) {
            return false;
        }
        final Vec3d vec3d = player.getLook(1.0f).normalize();
        Vec3d vec3d2 = new Vec3d(this.posX - player.posX, this.getEntityBoundingBox().minY + this.getEyeHeight() - (player.posY + player.getEyeHeight()), this.posZ - player.posZ);
        final double d0 = vec3d2.lengthVector();
        vec3d2 = vec3d2.normalize();
        final double d2 = vec3d.dotProduct(vec3d2);
        return d2 > 1.0 - 0.025 / d0 && player.canEntityBeSeen((Entity)this);
    }
    
    public float getEyeHeight() {
        return 2.55f;
    }
    
    public void onLivingUpdate() {
        if (this.getAttackTarget() != null && this.world.isRemote) {
            if (this.attackTimer > 0) {
                --this.attackTimer;
            }
            if (!this.onGround && this.motionY < 0.0) {
                this.motionY *= 0.6;
            }
            if (this.world.isRemote) {
                if (this.rand.nextInt(24) == 0 && !this.isSilent()) {
                    this.world.playSound(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f, false);
                }
                for (int i = 0; i < 2; ++i) {
                    this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0, new int[0]);
                }
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
        this.isJumping = false;
        super.onLivingUpdate();
    }
    
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(final byte id) {
        if (id == 4) {
            this.attackTimer = 10;
            this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0f, 1.0f);
        }
        else {
            super.handleStatusUpdate(id);
        }
    }
    
    protected boolean teleportRandomly() {
        final double d0 = this.posX + (this.rand.nextDouble() - 0.5) * 14.0;
        final double d2 = this.posY + (this.rand.nextInt(64) - 32);
        final double d3 = this.posZ + (this.rand.nextDouble() - 0.5) * 14.0;
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
    
    public void FireBall() {
        final double d1 = this.getAttackTarget().posX - this.posX;
        final double d2 = this.getAttackTarget().getEntityBoundingBox().minY + this.getAttackTarget().height / 2.0f - (this.posY + this.height / 2.0f);
        final double d3 = this.getAttackTarget().posZ - this.posZ;
        for (int i = 0; i < 8; ++i) {
            final EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.world, (EntityLivingBase)this, d1 + this.getRNG().nextGaussian() * 10.0, d2, d3 + this.getRNG().nextGaussian() * 10.0);
            entitysmallfireball.posY = this.posY + this.height / 2.0f + 0.5;
            this.world.spawnEntity((Entity)entitysmallfireball);
        }
    }
    
    protected SoundEvent getAmbientSound() {
        return this.isScreaming() ? SoundEvents.ENTITY_ENDERMEN_SCREAM : SoundEvents.ENTITY_BLAZE_AMBIENT;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ENDERMEN_HURT;
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BLAZE_DEATH;
    }
    
    public boolean attackEntityAsMob(final Entity par1Entity) {
        this.attackTimer = 10;
        this.world.setEntityState((Entity)this, (byte)4);
        final boolean flag = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)(ConfigHandler.ATK_Blenderman_MIN + this.rand.nextInt(ConfigHandler.ATK_Blenderman_MAX)));
        if (flag) {
            this.applyEnchantments((EntityLivingBase)this, par1Entity);
            par1Entity.setFire(5);
            this.FireBall();
            this.teleportRandomly();
        }
        this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0f, 1.0f);
        return true;
    }
    
    public void fall(final float distance, final float damageMultiplier) {
    }
    
    @SideOnly(Side.CLIENT)
    public int getAttackTimer() {
        return this.attackTimer;
    }
    
    protected int decreaseAirSupply(final int air) {
        return air;
    }
    
    protected void dropEquipment(final boolean wasRecentlyHit, final int lootingModifier) {
        super.dropEquipment(wasRecentlyHit, lootingModifier);
        final IBlockState iblockstate = this.getHeldBlockState();
        if (iblockstate != null) {
            final Item item = Item.getItemFromBlock(iblockstate.getBlock());
            final int i = item.getHasSubtypes() ? iblockstate.getBlock().getMetaFromState(iblockstate) : 0;
            this.entityDropItem(new ItemStack(item, 1, i), 0.0f);
        }
    }
    
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_ENDERMAN;
    }
    
    public void setHeldBlockState(@Nullable final IBlockState state) {
        this.dataManager.set(EntityBlenderman.CARRIED_BLOCK, com.google.common.base.Optional.fromNullable(state));
    }
    
    @Nullable
    public IBlockState getHeldBlockState() {
        return (IBlockState)((com.google.common.base.Optional)this.dataManager.get(EntityBlenderman.CARRIED_BLOCK)).orNull();
    }
    
    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        }
        if (source instanceof EntityDamageSourceIndirect) {
            for (int i = 0; i < 64; ++i) {
                if (this.teleportRandomly()) {
                    return true;
                }
            }
            return false;
        }
        final boolean flag = super.attackEntityFrom(source, amount);
        if (source.isUnblockable() && this.rand.nextInt(10) != 0) {
            this.teleportRandomly();
        }
        return flag;
    }
    
    public void setSwingingArms(final boolean swingingArms) {
    }
    
    public static void setCarriable(final Block block, final boolean canCarry) {
        if (canCarry) {
            EntityBlenderman.CARRIABLE_BLOCKS.add(block);
        }
        else {
            EntityBlenderman.CARRIABLE_BLOCKS.remove(block);
        }
    }
    
    public static boolean getCarriable(final Block block) {
        return EntityBlenderman.CARRIABLE_BLOCKS.contains(block);
    }
    
    public boolean isScreaming() {
        return (boolean)this.dataManager.get(EntityBlenderman.SCREAMING);
    }
    
    static {
        ATTACKING_SPEED_BOOST_ID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
        ATTACKING_SPEED_BOOST = new AttributeModifier(EntityBlenderman.ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", 0.15000000596046448, 0).setSaved(false);
        CARRIABLE_BLOCKS = Sets.newIdentityHashSet();
        CARRIED_BLOCK = EntityDataManager.createKey(EntityBlenderman.class, DataSerializers.OPTIONAL_BLOCK_STATE);
        SCREAMING = EntityDataManager.createKey(EntityBlenderman.class, DataSerializers.BOOLEAN);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.TNT);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.CACTUS);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.CLAY);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.PUMPKIN);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.MELON_BLOCK);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.MYCELIUM);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.NETHERRACK);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.TNT);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.DIAMOND_BLOCK);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.IRON_BLOCK);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.GOLD_BLOCK);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.CHEST);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.ENDER_CHEST);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.FURNACE);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.CRAFTING_TABLE);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.IRON_DOOR);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.ACACIA_DOOR);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.BIRCH_DOOR);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.JUNGLE_DOOR);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.OAK_DOOR);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.SPRUCE_DOOR);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.ACACIA_DOOR);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.LOG);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.LOG2);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.BED);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.BOOKSHELF);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.ENCHANTING_TABLE);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.BREWING_STAND);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.DISPENSER);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.CAULDRON);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.REDSTONE_BLOCK);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.CAKE);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.REEDS);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.TRAPDOOR);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.TRAPPED_CHEST);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.HOPPER);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.DROPPER);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.GLASS);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.GLASS_PANE);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.STAINED_GLASS);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.STAINED_GLASS_PANE);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.PLANKS);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.STICKY_PISTON);
        EntityBlenderman.CARRIABLE_BLOCKS.add((Block)Blocks.SKULL);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.GLOWSTONE);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.EMERALD_BLOCK);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.JUKEBOX);
        EntityBlenderman.CARRIABLE_BLOCKS.add(Blocks.REDSTONE_LAMP);
    }
    
    static class AIFindPlayer extends EntityAINearestAttackableTarget<EntityPlayer>
    {
        private final EntityBlenderman enderman;
        private EntityPlayer player;
        private int aggroTime;
        private int teleportTime;
        
        public AIFindPlayer(final EntityBlenderman p_i45842_1_) {
            super((EntityCreature)p_i45842_1_, EntityPlayer.class, false);
            this.enderman = p_i45842_1_;
        }
        
        public boolean shouldExecute() {
            final double d0 = this.getTargetDistance();
            this.player = this.enderman.world.getNearestAttackablePlayer(this.enderman.posX, this.enderman.posY, this.enderman.posZ, d0, d0, (Function)null, new Predicate<EntityPlayer>() {
                public boolean apply(@Nullable final EntityPlayer p_apply_1_) {
                    return p_apply_1_ != null && AIFindPlayer.this.enderman.shouldAttackPlayer(p_apply_1_);
                }
            });
            return this.player != null;
        }
        
        public void startExecuting() {
            this.aggroTime = 5;
            this.teleportTime = 0;
        }
        
        public void resetTask() {
            this.player = null;
            super.resetTask();
        }
        
        public boolean shouldContinueExecuting() {
            if (this.player == null) {
                return (this.targetEntity != null && ((EntityPlayer)this.targetEntity).isEntityAlive()) || super.shouldContinueExecuting();
            }
            if (!this.enderman.shouldAttackPlayer(this.player)) {
                return false;
            }
            this.enderman.faceEntity((Entity)this.player, 10.0f, 10.0f);
            return true;
        }
        
        public void updateTask() {
            if (this.player != null) {
                if (--this.aggroTime <= 0) {
                    this.targetEntity = this.player;
                    this.player = null;
                    super.startExecuting();
                }
            }
            else {
                if (this.targetEntity != null) {
                    if (this.enderman.shouldAttackPlayer((EntityPlayer)this.targetEntity)) {
                        if (((EntityPlayer)this.targetEntity).getDistanceSq((Entity)this.enderman) < 16.0) {
                            this.enderman.teleportRandomly();
                        }
                        this.teleportTime = 0;
                    }
                    else if (((EntityPlayer)this.targetEntity).getDistanceSq((Entity)this.enderman) > 256.0 && this.teleportTime++ >= 30 && this.enderman.teleportToEntity((Entity)this.targetEntity)) {
                        this.teleportTime = 0;
                    }
                }
                super.updateTask();
            }
        }
    }
    
    static class AIPlaceBlock extends EntityAIBase
    {
        private final EntityBlenderman enderman;
        
        public AIPlaceBlock(final EntityBlenderman p_i45843_1_) {
            this.enderman = p_i45843_1_;
        }
        
        public boolean shouldExecute() {
            return this.enderman.getHeldBlockState() != null && this.enderman.world.getGameRules().getBoolean("mobGriefing") && this.enderman.getRNG().nextInt(2000) == 0;
        }
        
        public void updateTask() {
            final Random random = this.enderman.getRNG();
            final World world = this.enderman.world;
            final int i = MathHelper.floor(this.enderman.posX - 1.0 + random.nextDouble() * 2.0);
            final int j = MathHelper.floor(this.enderman.posY + random.nextDouble() * 2.0);
            final int k = MathHelper.floor(this.enderman.posZ - 1.0 + random.nextDouble() * 2.0);
            final BlockPos blockpos = new BlockPos(i, j, k);
            final IBlockState iblockstate = world.getBlockState(blockpos);
            final IBlockState iblockstate2 = world.getBlockState(blockpos.down());
            final IBlockState iblockstate3 = this.enderman.getHeldBlockState();
            if (iblockstate3 != null && this.canPlaceBlock(world, blockpos, iblockstate3.getBlock(), iblockstate, iblockstate2)) {
                world.setBlockState(blockpos, iblockstate3, 3);
                this.enderman.setHeldBlockState(null);
            }
        }
        
        private boolean canPlaceBlock(final World p_188518_1_, final BlockPos p_188518_2_, final Block p_188518_3_, final IBlockState p_188518_4_, final IBlockState p_188518_5_) {
            return p_188518_3_.canPlaceBlockAt(p_188518_1_, p_188518_2_) && p_188518_4_.getMaterial() == Material.AIR && p_188518_5_.getMaterial() != Material.AIR && p_188518_5_.isFullCube();
        }
    }
    
    static class AITakeBlock extends EntityAIBase
    {
        private final EntityBlenderman enderman;
        
        public AITakeBlock(final EntityBlenderman p_i45841_1_) {
            this.enderman = p_i45841_1_;
        }
        
        public boolean shouldExecute() {
            return this.enderman.getHeldBlockState() == null && this.enderman.world.getGameRules().getBoolean("mobGriefing") && this.enderman.getRNG().nextInt(20) == 0;
        }
        
        public void updateTask() {
            final Random random = this.enderman.getRNG();
            final World world = this.enderman.world;
            final int i = MathHelper.floor(this.enderman.posX - 2.0 + random.nextDouble() * 4.0);
            final int j = MathHelper.floor(this.enderman.posY + random.nextDouble() * 3.0);
            final int k = MathHelper.floor(this.enderman.posZ - 2.0 + random.nextDouble() * 4.0);
            final BlockPos blockpos = new BlockPos(i, j, k);
            final IBlockState iblockstate = world.getBlockState(blockpos);
            final Block block = iblockstate.getBlock();
            final RayTraceResult raytraceresult = world.rayTraceBlocks(new Vec3d((double)(MathHelper.floor(this.enderman.posX) + 0.5f), (double)(j + 0.5f), (double)(MathHelper.floor(this.enderman.posZ) + 0.5f)), new Vec3d((double)(i + 0.5f), (double)(j + 0.5f), (double)(k + 0.5f)), false, true, false);
            final boolean flag = raytraceresult != null && raytraceresult.getBlockPos().equals(blockpos);
            if (EntityBlenderman.CARRIABLE_BLOCKS.contains(block) && flag) {
                this.enderman.setHeldBlockState(iblockstate);
                world.setBlockToAir(blockpos);
            }
        }
    }
}
