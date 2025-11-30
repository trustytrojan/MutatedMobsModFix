//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import net.minecraft.entity.monster.*;
import mmm.common.entities.mobs.interfaces.*;
import net.minecraft.entity.ai.attributes.*;
import javax.annotation.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import mmm.eventHandler.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.projectile.*;
import java.util.*;
import net.minecraft.network.datasync.*;
import net.minecraft.world.*;
import mmm.common.entities.projectiles.*;
import net.minecraft.entity.ai.*;
import com.google.common.base.*;

public class EntityGhastShulker extends EntityGolem implements IMob, IMutant
{
    private static final UUID COVERED_ARMOR_BONUS_ID;
    private static final AttributeModifier COVERED_ARMOR_BONUS_MODIFIER;
    protected static final DataParameter<EnumFacing> ATTACHED_FACE;
    protected static final DataParameter<com.google.common.base.Optional<BlockPos>> ATTACHED_BLOCK_POS;
    protected static final DataParameter<Byte> PEEK_TICK;
        public static final net.minecraft.world.biome.Biome[] SPAWN_BIOMES = new net.minecraft.world.biome.Biome[] { Biomes.SKY };
    private float prevPeekAmount;
    private float peekAmount;
    private BlockPos currentAttachmentPosition;
    private int clientSideTeleportInterpolation;
    
    public EntityGhastShulker(final World worldIn) {
        super(worldIn);
        this.setSize(4.5f, 4.5f);
        this.prevRenderYawOffset = 180.0f;
        this.renderYawOffset = 180.0f;
        this.isImmuneToFire = true;
        this.currentAttachmentPosition = null;
        this.experienceValue = 10;
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficulty, @Nullable final IEntityLivingData livingdata) {
        this.renderYawOffset = 180.0f;
        this.prevRenderYawOffset = 180.0f;
        this.rotationYaw = 180.0f;
        this.prevRotationYaw = 180.0f;
        this.rotationYawHead = 180.0f;
        this.prevRotationYawHead = 180.0f;
        return super.onInitialSpawn(difficulty, livingdata);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(4, new AIAttack());
        this.tasks.addTask(7, new AIPeek());
        this.tasks.addTask(8, new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new AIAttackNearest(this));
        this.targetTasks.addTask(3, new AIDefenseAttack(this));
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }
    
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_GHAST_AMBIENT;
    }
    
    public void playLivingSound() {
        if (!this.isClosed()) {
            super.playLivingSound();
        }
    }
    
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SHULKER_DEATH;
    }
    
    protected SoundEvent getHurtSound(final DamageSource damageSourceIn) {
        return this.isClosed() ? SoundEvents.ENTITY_SHULKER_HURT_CLOSED : SoundEvents.ENTITY_SHULKER_HURT;
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityGhastShulker.ATTACHED_FACE, EnumFacing.DOWN);
        this.dataManager.register(EntityGhastShulker.ATTACHED_BLOCK_POS, com.google.common.base.Optional.absent());
        this.dataManager.register(EntityGhastShulker.PEEK_TICK, (byte)0);
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_GhastShulker);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0);
    }
    
    protected EntityBodyHelper createBodyHelper() {
        return new BodyHelper((EntityLivingBase)this);
    }
    
    public static void registerFixesShulker(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, EntityGhastShulker.class);
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(EntityGhastShulker.ATTACHED_FACE, EnumFacing.getFront((int)compound.getByte("AttachFace")));
        this.dataManager.set(EntityGhastShulker.PEEK_TICK, compound.getByte("Peek"));
        if (compound.hasKey("APX")) {
            final int i = compound.getInteger("APX");
            final int j = compound.getInteger("APY");
            final int k = compound.getInteger("APZ");
            this.dataManager.set(EntityGhastShulker.ATTACHED_BLOCK_POS, com.google.common.base.Optional.of(new BlockPos(i, j, k)));
        }
        else {
            this.dataManager.set(EntityGhastShulker.ATTACHED_BLOCK_POS, com.google.common.base.Optional.absent());
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setByte("AttachFace", (byte)((EnumFacing)this.dataManager.get(EntityGhastShulker.ATTACHED_FACE)).getIndex());
        compound.setByte("Peek", (byte)this.dataManager.get(EntityGhastShulker.PEEK_TICK));
        final BlockPos blockpos = this.getAttachmentPos();
        if (blockpos != null) {
            compound.setInteger("APX", blockpos.getX());
            compound.setInteger("APY", blockpos.getY());
            compound.setInteger("APZ", blockpos.getZ());
        }
    }
    
    public void onUpdate() {
        super.onUpdate();
        BlockPos blockpos = (BlockPos)((com.google.common.base.Optional)this.dataManager.get(EntityGhastShulker.ATTACHED_BLOCK_POS)).orNull();
        if (blockpos == null && !this.world.isRemote) {
            blockpos = new BlockPos(this);
            this.dataManager.set(EntityGhastShulker.ATTACHED_BLOCK_POS, com.google.common.base.Optional.of(blockpos));
        }
        if (this.isRiding()) {
            blockpos = null;
            final float f = this.getRidingEntity().rotationYaw;
            this.rotationYaw = f;
            this.renderYawOffset = f;
            this.prevRenderYawOffset = f;
            this.clientSideTeleportInterpolation = 0;
        }
        else if (!this.world.isRemote) {
            final IBlockState iblockstate = this.world.getBlockState(blockpos);
            if (iblockstate.getMaterial() != Material.AIR) {
                if (iblockstate.getBlock() == Blocks.PISTON_EXTENSION) {
                    final EnumFacing enumfacing = (EnumFacing)iblockstate.getValue(BlockPistonBase.FACING);
                    if (this.world.isAirBlock(blockpos.offset(enumfacing))) {
                        blockpos = blockpos.offset(enumfacing);
                        this.dataManager.set(EntityGhastShulker.ATTACHED_BLOCK_POS, com.google.common.base.Optional.of(blockpos));
                    }
                    else {
                        this.tryTeleportToNewPosition();
                    }
                }
                else if (iblockstate.getBlock() == Blocks.PISTON_HEAD) {
                    final EnumFacing enumfacing2 = (EnumFacing)iblockstate.getValue(BlockPistonExtension.FACING);
                    if (this.world.isAirBlock(blockpos.offset(enumfacing2))) {
                        blockpos = blockpos.offset(enumfacing2);
                        this.dataManager.set(EntityGhastShulker.ATTACHED_BLOCK_POS, com.google.common.base.Optional.of(blockpos));
                    }
                    else {
                        this.tryTeleportToNewPosition();
                    }
                }
                else {
                    this.tryTeleportToNewPosition();
                }
            }
            BlockPos blockpos2 = blockpos.offset(this.getAttachmentFacing());
            if (!this.world.isBlockNormalCube(blockpos2, false)) {
                boolean flag = false;
                for (final EnumFacing enumfacing3 : EnumFacing.values()) {
                    blockpos2 = blockpos.offset(enumfacing3);
                    if (this.world.isBlockNormalCube(blockpos2, false)) {
                        this.dataManager.set(EntityGhastShulker.ATTACHED_FACE, enumfacing3);
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    this.tryTeleportToNewPosition();
                }
            }
            final BlockPos blockpos3 = blockpos.offset(this.getAttachmentFacing().getOpposite());
            if (this.world.isBlockNormalCube(blockpos3, false)) {
                this.tryTeleportToNewPosition();
            }
        }
        final float f2 = this.getPeekTick() * 0.01f;
        this.prevPeekAmount = this.peekAmount;
        if (this.peekAmount > f2) {
            this.peekAmount = MathHelper.clamp(this.peekAmount - 0.05f, f2, 1.0f);
        }
        else if (this.peekAmount < f2) {
            this.peekAmount = MathHelper.clamp(this.peekAmount + 0.05f, 0.0f, f2);
        }
        if (blockpos != null) {
            if (this.world.isRemote) {
                if (this.clientSideTeleportInterpolation > 0 && this.currentAttachmentPosition != null) {
                    --this.clientSideTeleportInterpolation;
                }
                else {
                    this.currentAttachmentPosition = blockpos;
                }
            }
            this.posX = blockpos.getX() + 0.5;
            this.posY = blockpos.getY();
            this.posZ = blockpos.getZ() + 0.5;
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.lastTickPosX = this.posX;
            this.lastTickPosY = this.posY;
            this.lastTickPosZ = this.posZ;
            final double d3 = 0.5 - MathHelper.sin((0.5f + this.peekAmount) * 3.1415927f) * 0.5;
            final double d4 = 0.5 - MathHelper.sin((0.5f + this.prevPeekAmount) * 3.1415927f) * 0.5;
            final double d5 = d3 - d4;
            double d6 = 0.0;
            double d7 = 0.0;
            double d8 = 0.0;
            final EnumFacing enumfacing4 = this.getAttachmentFacing();
            switch (enumfacing4) {
                case DOWN: {
                    this.setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.5, this.posY, this.posZ - 0.5, this.posX + 0.5, this.posY + 1.0 + d3, this.posZ + 0.5));
                    d7 = d5;
                    break;
                }
                case UP: {
                    this.setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.5, this.posY - d3, this.posZ - 0.5, this.posX + 0.5, this.posY + 1.0, this.posZ + 0.5));
                    d7 = -d5;
                    break;
                }
                case NORTH: {
                    this.setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.5, this.posY, this.posZ - 0.5, this.posX + 0.5, this.posY + 1.0, this.posZ + 0.5 + d3));
                    d8 = d5;
                    break;
                }
                case SOUTH: {
                    this.setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.5, this.posY, this.posZ - 0.5 - d3, this.posX + 0.5, this.posY + 1.0, this.posZ + 0.5));
                    d8 = -d5;
                    break;
                }
                case WEST: {
                    this.setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.5, this.posY, this.posZ - 0.5, this.posX + 0.5 + d3, this.posY + 1.0, this.posZ + 0.5));
                    d6 = d5;
                    break;
                }
                case EAST: {
                    this.setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.5 - d3, this.posY, this.posZ - 0.5, this.posX + 0.5, this.posY + 1.0, this.posZ + 0.5));
                    d6 = -d5;
                    break;
                }
            }
            if (d5 > 0.0) {
                final List<Entity> list = (List<Entity>)this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
                if (!list.isEmpty()) {
                    for (final Entity entity : list) {
                        if (!(entity instanceof EntityGhastShulker) && !entity.noClip) {
                            entity.move(MoverType.SHULKER, d6, d7, d8);
                        }
                    }
                }
            }
        }
    }
    
    public void LaunchShulkers() {
        for (int i = 0; i < 6; ++i) {
            final double d7 = this.getAttackTarget().posX + (this.getAttackTarget().getRNG().nextDouble() - 0.5) * 8.0;
            final double d8 = MathHelper.clamp(this.getAttackTarget().posY + (this.getAttackTarget().getRNG().nextInt(16) - 8), 0.0, (double)(this.getAttackTarget().world.getActualHeight() - 4));
            final double d9 = this.getAttackTarget().posZ + (this.getAttackTarget().getRNG().nextDouble() - 0.5) * 8.0;
            final EntityShulkerBullet entityshulkerbullet = new EntityShulkerBullet(this.world, (EntityLivingBase)this, this.getAttackTarget(), (EnumFacing.Axis)null);
            this.world.spawnEntity(entityshulkerbullet);
            this.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 2.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }
    
    public void move(final MoverType type, final double x, final double y, final double z) {
        if (type == MoverType.SHULKER_BOX) {
            this.tryTeleportToNewPosition();
        }
        else {
            super.move(type, x, y, z);
        }
    }
    
    public void setPosition(final double x, final double y, final double z) {
        super.setPosition(x, y, z);
        if (this.dataManager != null && this.ticksExisted != 0) {
            final com.google.common.base.Optional<BlockPos> optional = (com.google.common.base.Optional<BlockPos>)this.dataManager.get(EntityGhastShulker.ATTACHED_BLOCK_POS);
            final com.google.common.base.Optional<BlockPos> optional2 = (com.google.common.base.Optional<BlockPos>)com.google.common.base.Optional.of(new BlockPos(x, y, z));
            if (!optional2.equals(optional)) {
                this.dataManager.set(EntityGhastShulker.ATTACHED_BLOCK_POS, optional2);
                this.dataManager.set(EntityGhastShulker.PEEK_TICK, (byte)0);
                this.isAirBorne = true;
            }
        }
    }
    
    protected boolean tryTeleportToNewPosition() {
        if (!this.isAIDisabled() && this.isEntityAlive()) {
            final BlockPos blockpos = new BlockPos(this);
            for (int i = 0; i < 5; ++i) {
                BlockPos blockpos2 = blockpos.add(8 - this.rand.nextInt(17), 8 - this.rand.nextInt(17), 8 - this.rand.nextInt(17));
                if (blockpos2.getY() > 0 && this.world.isAirBlock(blockpos2) && this.world.isInsideWorldBorder(this) && this.world.getCollisionBoxes(this, new AxisAlignedBB(blockpos2)).isEmpty()) {
                    boolean flag = false;
                    for (final EnumFacing enumfacing : EnumFacing.values()) {
                        if (this.world.isBlockNormalCube(blockpos2.offset(enumfacing), false)) {
                            this.dataManager.set(EntityGhastShulker.ATTACHED_FACE, enumfacing);
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        final EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)this, (double)blockpos2.getX(), (double)blockpos2.getY(), (double)blockpos2.getZ(), 0.0f);
                        if (MinecraftForge.EVENT_BUS.post((Event)event)) {
                            flag = false;
                        }
                        blockpos2 = new BlockPos(event.getTargetX(), event.getTargetY(), event.getTargetZ());
                    }
                    if (flag) {
                        this.playSound(SoundEvents.ENTITY_SHULKER_TELEPORT, 1.0f, 1.0f);
                        this.dataManager.set(EntityGhastShulker.ATTACHED_BLOCK_POS, com.google.common.base.Optional.of(blockpos2));
                        this.dataManager.set(EntityGhastShulker.PEEK_TICK, (byte)0);
                        this.setAttackTarget((EntityLivingBase)null);
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevRenderYawOffset = 180.0f;
        this.renderYawOffset = 180.0f;
        this.rotationYaw = 180.0f;
    }
    
    public void notifyDataManagerChange(final DataParameter<?> key) {
        if (EntityGhastShulker.ATTACHED_BLOCK_POS.equals(key) && this.world.isRemote && !this.isRiding()) {
            final BlockPos blockpos = this.getAttachmentPos();
            if (blockpos != null) {
                if (this.currentAttachmentPosition == null) {
                    this.currentAttachmentPosition = blockpos;
                }
                else {
                    this.clientSideTeleportInterpolation = 6;
                }
                this.posX = blockpos.getX() + 0.5;
                this.posY = blockpos.getY();
                this.posZ = blockpos.getZ() + 0.5;
                this.prevPosX = this.posX;
                this.prevPosY = this.posY;
                this.prevPosZ = this.posZ;
                this.lastTickPosX = this.posX;
                this.lastTickPosY = this.posY;
                this.lastTickPosZ = this.posZ;
            }
        }
        super.notifyDataManagerChange(key);
    }
    
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(final double x, final double y, final double z, final float yaw, final float pitch, final int posRotationIncrements, final boolean teleport) {
        this.newPosRotationIncrements = 0;
    }
    
    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        if (this.isClosed()) {
            final Entity entity = source.getImmediateSource();
            if (entity instanceof EntityArrow) {
                return false;
            }
        }
        if (super.attackEntityFrom(source, amount)) {
            if (this.getHealth() < this.getMaxHealth() * 0.5 && this.rand.nextInt(4) == 0) {
                this.tryTeleportToNewPosition();
            }
            return true;
        }
        return false;
    }
    
    private boolean isClosed() {
        return this.getPeekTick() == 0;
    }
    
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.isEntityAlive() ? this.getEntityBoundingBox() : null;
    }
    
    public EnumFacing getAttachmentFacing() {
        return (EnumFacing)this.dataManager.get(EntityGhastShulker.ATTACHED_FACE);
    }
    
    @Nullable
    public BlockPos getAttachmentPos() {
        return (BlockPos)((com.google.common.base.Optional)this.dataManager.get(EntityGhastShulker.ATTACHED_BLOCK_POS)).orNull();
    }
    
    public void setAttachmentPos(@Nullable final BlockPos pos) {
        this.dataManager.set(EntityGhastShulker.ATTACHED_BLOCK_POS, com.google.common.base.Optional.fromNullable(pos));
    }
    
    public int getPeekTick() {
        return (byte)this.dataManager.get(EntityGhastShulker.PEEK_TICK);
    }
    
    public void updateArmorModifier(final int p_184691_1_) {
        if (!this.world.isRemote) {
            this.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(EntityGhastShulker.COVERED_ARMOR_BONUS_MODIFIER);
            if (p_184691_1_ == 0) {
                this.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(EntityGhastShulker.COVERED_ARMOR_BONUS_MODIFIER);
                this.playSound(SoundEvents.ENTITY_SHULKER_CLOSE, 1.0f, 1.0f);
            }
            else {
                this.playSound(SoundEvents.ENTITY_SHULKER_OPEN, 1.0f, 1.0f);
            }
        }
        this.dataManager.set(EntityGhastShulker.PEEK_TICK, (byte)p_184691_1_);
    }
    
    @SideOnly(Side.CLIENT)
    public float getClientPeekAmount(final float p_184688_1_) {
        return this.prevPeekAmount + (this.peekAmount - this.prevPeekAmount) * p_184688_1_;
    }
    
    @SideOnly(Side.CLIENT)
    public int getClientTeleportInterp() {
        return this.clientSideTeleportInterpolation;
    }
    
    @SideOnly(Side.CLIENT)
    public BlockPos getOldAttachPos() {
        return this.currentAttachmentPosition;
    }
    
    public float getEyeHeight() {
        return 4.5f;
    }
    
    public int getVerticalFaceSpeed() {
        return 180;
    }
    
    public int getHorizontalFaceSpeed() {
        return 180;
    }
    
    public void applyEntityCollision(final Entity entityIn) {
    }
    
    public float getCollisionBorderSize() {
        return 0.0f;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isAttachedToBlock() {
        return this.currentAttachmentPosition != null && this.getAttachmentPos() != null;
    }
    
    static {
        COVERED_ARMOR_BONUS_ID = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
        COVERED_ARMOR_BONUS_MODIFIER = new AttributeModifier(EntityGhastShulker.COVERED_ARMOR_BONUS_ID, "Covered armor bonus", 20.0, 0).setSaved(false);
        ATTACHED_FACE = EntityDataManager.createKey(EntityGhastShulker.class, DataSerializers.FACING);
        ATTACHED_BLOCK_POS = EntityDataManager.createKey(EntityGhastShulker.class, DataSerializers.OPTIONAL_BLOCK_POS);
        PEEK_TICK = EntityDataManager.createKey(EntityGhastShulker.class, DataSerializers.BYTE);
    }
    
    class AIAttack extends EntityAIBase
    {
        private int attackTime;
        
        public AIAttack() {
            this.setMutexBits(3);
        }
        
        public boolean shouldExecute() {
            final EntityLivingBase entitylivingbase = EntityGhastShulker.this.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive() && EntityGhastShulker.this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
        }
        
        public void startExecuting() {
            this.attackTime = 10;
            EntityGhastShulker.this.updateArmorModifier(100);
        }
        
        public void resetTask() {
            EntityGhastShulker.this.updateArmorModifier(0);
        }
        
        public void updateTask() {
            if (EntityGhastShulker.this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
                --this.attackTime;
                final EntityLivingBase entitylivingbase = EntityGhastShulker.this.getAttackTarget();
                EntityGhastShulker.this.getLookHelper().setLookPositionWithEntity(entitylivingbase, 180.0f, 180.0f);
                final double d0 = EntityGhastShulker.this.getDistanceSq(entitylivingbase);
                if (d0 < 400.0) {
                    if (this.attackTime <= 0) {
                        this.attackTime = 10 + EntityGhastShulker.this.rand.nextInt(10) * 20 / 2;
                        final EntityShulkerFireball EntityGhastShulkerbullet = new EntityShulkerFireball(EntityGhastShulker.this.world, (EntityLivingBase)EntityGhastShulker.this, entitylivingbase, EntityGhastShulker.this.getAttachmentFacing().getAxis());
                        EntityGhastShulker.this.world.spawnEntity(EntityGhastShulkerbullet);
                        EntityGhastShulker.this.playSound(SoundEvents.ENTITY_GHAST_SCREAM, 1.0f, (EntityGhastShulker.this.rand.nextFloat() - EntityGhastShulker.this.rand.nextFloat()) * 0.2f + 1.0f);
                        EntityGhastShulker.this.LaunchShulkers();
                    }
                }
                else {
                    EntityGhastShulker.this.setAttackTarget((EntityLivingBase)null);
                }
                super.updateTask();
            }
        }
    }
    
    class AIAttackNearest extends EntityAINearestAttackableTarget<EntityPlayer>
    {
        public AIAttackNearest(final EntityGhastShulker shulker) {
            super(shulker, EntityPlayer.class, true);
        }
        
        public boolean shouldExecute() {
            return EntityGhastShulker.this.world.getDifficulty() != EnumDifficulty.PEACEFUL && super.shouldExecute();
        }
        
        protected AxisAlignedBB getTargetableArea(final double targetDistance) {
            final EnumFacing enumfacing = ((EntityGhastShulker)this.taskOwner).getAttachmentFacing();
            if (enumfacing.getAxis() == EnumFacing.Axis.X) {
                return this.taskOwner.getEntityBoundingBox().grow(4.0, targetDistance, targetDistance);
            }
            return (enumfacing.getAxis() == EnumFacing.Axis.Z) ? this.taskOwner.getEntityBoundingBox().grow(targetDistance, targetDistance, 4.0) : this.taskOwner.getEntityBoundingBox().grow(targetDistance, 4.0, targetDistance);
        }
    }
    
    static class AIDefenseAttack extends EntityAINearestAttackableTarget<EntityLivingBase>
    {
        public AIDefenseAttack(final EntityGhastShulker shulker) {
            super(shulker, EntityLivingBase.class, 10, true, false, new Predicate<EntityLivingBase>() {
                public boolean apply(@Nullable final EntityLivingBase p_apply_1_) {
                    return p_apply_1_ instanceof IMob;
                }
            });
        }
        
        public boolean shouldExecute() {
            return this.taskOwner.getTeam() != null && super.shouldExecute();
        }
        
        protected AxisAlignedBB getTargetableArea(final double targetDistance) {
            final EnumFacing enumfacing = ((EntityGhastShulker)this.taskOwner).getAttachmentFacing();
            if (enumfacing.getAxis() == EnumFacing.Axis.X) {
                return this.taskOwner.getEntityBoundingBox().grow(4.0, targetDistance, targetDistance);
            }
            return (enumfacing.getAxis() == EnumFacing.Axis.Z) ? this.taskOwner.getEntityBoundingBox().grow(targetDistance, targetDistance, 4.0) : this.taskOwner.getEntityBoundingBox().grow(targetDistance, 4.0, targetDistance);
        }
    }
    
    class AIPeek extends EntityAIBase
    {
        private int peekTime;
        
        private AIPeek() {
        }
        
        public boolean shouldExecute() {
            return EntityGhastShulker.this.getAttackTarget() == null && EntityGhastShulker.this.rand.nextInt(40) == 0;
        }
        
        public boolean shouldContinueExecuting() {
            return EntityGhastShulker.this.getAttackTarget() == null && this.peekTime > 0;
        }
        
        public void startExecuting() {
            this.peekTime = 20 * (1 + EntityGhastShulker.this.rand.nextInt(3));
            EntityGhastShulker.this.updateArmorModifier(30);
        }
        
        public void resetTask() {
            if (EntityGhastShulker.this.getAttackTarget() == null) {
                EntityGhastShulker.this.updateArmorModifier(0);
            }
        }
        
        public void updateTask() {
            --this.peekTime;
        }
    }
    
    class BodyHelper extends EntityBodyHelper
    {
        public BodyHelper(final EntityLivingBase theEntity) {
            super(theEntity);
        }
        
        public void updateRenderAngles() {
        }
    }
}
