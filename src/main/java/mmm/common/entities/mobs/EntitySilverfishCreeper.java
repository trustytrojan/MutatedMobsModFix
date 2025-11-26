//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs;

import mmm.common.entities.*;
import net.minecraft.world.*;
import mmm.common.entities.ai.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import mmm.eventHandler.*;
import net.minecraft.util.datafix.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.storage.loot.*;
import javax.annotation.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.network.datasync.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;

public class EntitySilverfishCreeper extends EntityHalfCreeper
{
    private static final DataParameter<Integer> STATE = EntityDataManager.createKey(EntitySilverfishCreeper.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(EntitySilverfishCreeper.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(EntitySilverfishCreeper.class, DataSerializers.BOOLEAN);
    private AISummonSilverfish summonSilverfish;
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime;
    private int explosionRadius;
    private int droppedSkulls;
    
    public EntitySilverfishCreeper(final World worldIn) {
        super(worldIn);
        this.fuseTime = 30;
        this.explosionRadius = 3;
        this.setSize(0.6f, 1.7f);
    }
    
    protected void initEntityAI() {
        this.tasks.addTask(2, (EntityAIBase)new EntityAICreeperSwell2(this));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, (Class)EntityOcelot.class, 6.0f, 1.0, 1.2));
        this.summonSilverfish = new AISummonSilverfish(this);
        this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(3, (EntityAIBase)this.summonSilverfish);
        this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0, false));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 0.8));
        this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, true));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ConfigHandler.HP_SilverfishCreeper);
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
        this.dataManager.register(STATE, -1);
        this.dataManager.register(POWERED, false);
        this.dataManager.register(IGNITED, false);
    }
    
    public static void registerFixesCreeper(final DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, (Class)EntitySilverfishCreeper.class);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if ((Boolean)this.dataManager.get((DataParameter)EntitySilverfishCreeper.POWERED)) {
            compound.setBoolean("powered", true);
        }
        compound.setShort("Fuse", (short)this.fuseTime);
        compound.setByte("ExplosionRadius", (byte)this.explosionRadius);
        compound.setBoolean("ignited", this.hasIgnited());
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set((DataParameter)EntitySilverfishCreeper.POWERED, (Object)compound.getBoolean("powered"));
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
        }
        super.onUpdate();
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.world.isRemote) {
            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY);
            int k = MathHelper.floor(this.posZ);
            if (!this.world.getGameRules().getBoolean("mobGriefing")) {
                return;
            }
            for (int l = 0; l < 4; ++l) {
                i = MathHelper.floor(this.posX + (l % 2 * 2 - 1) * 0.25f);
                j = MathHelper.floor(this.posY);
                k = MathHelper.floor(this.posZ + (l / 2 % 2 * 2 - 1) * 0.25f);
                final BlockPos blockpos = new BlockPos(i, j, k);
                if (this.world.getBlockState(blockpos).getMaterial() == Material.ROCK && Blocks.MONSTER_EGG.canPlaceBlockAt(this.world, blockpos)) {
                    this.world.setBlockState(blockpos, Blocks.MONSTER_EGG.getDefaultState());
                }
                else if (this.world.getBlockState(blockpos).getMaterial() == Material.GROUND && Blocks.MONSTER_EGG.canPlaceBlockAt(this.world, blockpos)) {
                    this.world.setBlockState(blockpos, Blocks.MONSTER_EGG.getDefaultState());
                }
            }
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
        if (this.world.getGameRules().getBoolean("doMobLoot")) {
            if (cause.getTrueSource() instanceof EntitySkeleton) {
                final int i = Item.getIdFromItem(Items.RECORD_13);
                final int j = Item.getIdFromItem(Items.RECORD_WAIT);
                final int k = i + this.rand.nextInt(j - i + 1);
                this.dropItem(Item.getItemById(k), 1);
            }
            else if (cause.getTrueSource() instanceof EntitySilverfishCreeper && cause.getTrueSource() != this && ((EntitySilverfishCreeper)cause.getTrueSource()).getPowered() && ((EntitySilverfishCreeper)cause.getTrueSource()).ableToCauseSkullDrop()) {
                ((EntitySilverfishCreeper)cause.getTrueSource()).incrementDroppedSkulls();
                this.entityDropItem(new ItemStack(Items.SKULL, 1, 4), 0.0f);
            }
        }
    }
    
    public boolean attackEntityAsMob(final Entity entityIn) {
        return true;
    }
    
    public boolean getPowered() {
        return (boolean)this.dataManager.get((DataParameter)EntitySilverfishCreeper.POWERED);
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
        return (int)this.dataManager.get((DataParameter)EntitySilverfishCreeper.STATE);
    }
    
    public void setCreeperState(final int state) {
        this.dataManager.set((DataParameter)EntitySilverfishCreeper.STATE, (Object)state);
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
            final boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
            final float f = this.getPowered() ? 2.0f : 1.0f;
            this.dead = true;
            this.world.createExplosion((Entity)this, this.posX, this.posY, this.posZ, this.explosionRadius * f, flag);
            this.split();
            this.setDead();
            this.spawnLingeringCloud();
        }
    }
    
    public void split() {
        if (!this.world.isRemote && !this.isDead) {
            final EntitySilverfish entitypigzombie = new EntitySilverfish(this.world);
            final EntitySilverfish entitypigzombie2 = new EntitySilverfish(this.world);
            final EntitySilverfish entitypigzombie3 = new EntitySilverfish(this.world);
            final EntitySilverfish entitypigzombie4 = new EntitySilverfish(this.world);
            final EntitySilverfish entitypigzombie5 = new EntitySilverfish(this.world);
            final EntitySilverfish entitypigzombie6 = new EntitySilverfish(this.world);
            final EntitySilverfish entitypigzombie7 = new EntitySilverfish(this.world);
            final EntitySilverfish entitypigzombie8 = new EntitySilverfish(this.world);
            final EntitySilverfish entitypigzombie9 = new EntitySilverfish(this.world);
            final EntitySilverfish entitypigzombie10 = new EntitySilverfish(this.world);
            entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie.setNoAI(this.isAIDisabled());
            entitypigzombie.setSprinting(true);
            entitypigzombie2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie2.setNoAI(this.isAIDisabled());
            entitypigzombie2.setSprinting(true);
            entitypigzombie3.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie3.setNoAI(this.isAIDisabled());
            entitypigzombie3.setSprinting(true);
            entitypigzombie4.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie4.setNoAI(this.isAIDisabled());
            entitypigzombie4.setSprinting(true);
            entitypigzombie5.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie5.setNoAI(this.isAIDisabled());
            entitypigzombie5.setSprinting(true);
            entitypigzombie6.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie6.setNoAI(this.isAIDisabled());
            entitypigzombie6.setSprinting(true);
            entitypigzombie7.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie7.setNoAI(this.isAIDisabled());
            entitypigzombie7.setSprinting(true);
            entitypigzombie8.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie8.setNoAI(this.isAIDisabled());
            entitypigzombie8.setSprinting(true);
            entitypigzombie9.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie9.setNoAI(this.isAIDisabled());
            entitypigzombie9.setSprinting(true);
            entitypigzombie10.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entitypigzombie10.setNoAI(this.isAIDisabled());
            entitypigzombie10.setSprinting(true);
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
                entitypigzombie6.setCustomNameTag(this.getCustomNameTag());
                entitypigzombie6.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
                entitypigzombie7.setCustomNameTag(this.getCustomNameTag());
                entitypigzombie7.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
                entitypigzombie8.setCustomNameTag(this.getCustomNameTag());
                entitypigzombie8.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
                entitypigzombie9.setCustomNameTag(this.getCustomNameTag());
                entitypigzombie9.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
                entitypigzombie10.setCustomNameTag(this.getCustomNameTag());
                entitypigzombie10.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            this.world.spawnEntity((Entity)entitypigzombie);
            this.world.spawnEntity((Entity)entitypigzombie2);
            this.world.spawnEntity((Entity)entitypigzombie3);
            this.world.spawnEntity((Entity)entitypigzombie4);
            this.world.spawnEntity((Entity)entitypigzombie5);
            this.world.spawnEntity((Entity)entitypigzombie6);
            this.world.spawnEntity((Entity)entitypigzombie7);
            this.world.spawnEntity((Entity)entitypigzombie8);
            this.world.spawnEntity((Entity)entitypigzombie9);
            this.world.spawnEntity((Entity)entitypigzombie10);
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
            this.world.spawnEntity((Entity)entityareaeffectcloud);
        }
    }
    
    public boolean hasIgnited() {
        return (boolean)this.dataManager.get((DataParameter)EntitySilverfishCreeper.IGNITED);
    }
    
    public void ignite() {
        this.dataManager.set((DataParameter)EntitySilverfishCreeper.IGNITED, (Object)true);
    }
    
    public boolean ableToCauseSkullDrop() {
        return this.droppedSkulls < 1 && this.world.getGameRules().getBoolean("doMobLoot");
    }
    
    public void incrementDroppedSkulls() {
        ++this.droppedSkulls;
    }
    
    static class AIHideInStone extends EntityAIWander
    {
        private EnumFacing facing;
        private boolean doMerge;
        
        public AIHideInStone(final EntitySilverfishCreeper silverfishIn) {
            super((EntityCreature)silverfishIn, 1.0, 10);
            this.setMutexBits(1);
        }
        
        public boolean shouldExecute() {
            if (this.entity.getAttackTarget() != null) {
                return false;
            }
            if (!this.entity.getNavigator().noPath()) {
                return false;
            }
            final Random random = this.entity.getRNG();
            if (this.entity.world.getGameRules().getBoolean("mobGriefing") && random.nextInt(10) == 0) {
                this.facing = EnumFacing.random(random);
                final BlockPos blockpos = new BlockPos(this.entity.posX, this.entity.posY + 0.5, this.entity.posZ).offset(this.facing);
                final IBlockState iblockstate = this.entity.world.getBlockState(blockpos);
                if (BlockSilverfish.canContainSilverfish(iblockstate)) {
                    return this.doMerge = true;
                }
            }
            this.doMerge = false;
            return super.shouldExecute();
        }
        
        public boolean shouldContinueExecuting() {
            return !this.doMerge && super.shouldContinueExecuting();
        }
        
        public void startExecuting() {
            if (!this.doMerge) {
                super.startExecuting();
            }
            else {
                final World world = this.entity.world;
                final BlockPos blockpos = new BlockPos(this.entity.posX, this.entity.posY + 0.5, this.entity.posZ).offset(this.facing);
                final IBlockState iblockstate = world.getBlockState(blockpos);
                if (BlockSilverfish.canContainSilverfish(iblockstate)) {
                    world.setBlockState(blockpos, Blocks.MONSTER_EGG.getDefaultState().withProperty((IProperty)BlockSilverfish.VARIANT, (Comparable)BlockSilverfish.EnumType.forModelBlock(iblockstate)), 3);
                    this.entity.spawnExplosionParticle();
                    this.entity.setDead();
                }
            }
        }
    }
    
    static class AISummonSilverfish extends EntityAIBase
    {
        private final EntitySilverfishCreeper silverfish;
        private int lookForFriends;
        
        public AISummonSilverfish(final EntitySilverfishCreeper silverfishIn) {
            this.silverfish = silverfishIn;
        }
        
        public void notifyHurt() {
            if (this.lookForFriends == 0) {
                this.lookForFriends = 20;
            }
        }
        
        public boolean shouldExecute() {
            return this.lookForFriends > 0;
        }
        
        public void updateTask() {
            --this.lookForFriends;
            if (this.lookForFriends <= 0) {
                final World world = this.silverfish.world;
                final Random random = this.silverfish.getRNG();
                final BlockPos blockpos = new BlockPos((Entity)this.silverfish);
                for (int i = 0; i <= 5 && i >= -5; i = ((i <= 0) ? 1 : 0) - i) {
                    for (int j = 0; j <= 10 && j >= -10; j = ((j <= 0) ? 1 : 0) - j) {
                        for (int k = 0; k <= 10 && k >= -10; k = ((k <= 0) ? 1 : 0) - k) {
                            final BlockPos blockpos2 = blockpos.add(j, i, k);
                            final IBlockState iblockstate = world.getBlockState(blockpos2);
                            if (iblockstate.getBlock() == Blocks.MONSTER_EGG) {
                                if (world.getGameRules().getBoolean("mobGriefing")) {
                                    world.destroyBlock(blockpos2, true);
                                }
                                else {
                                    world.setBlockState(blockpos2, ((BlockSilverfish.EnumType)iblockstate.getValue((IProperty)BlockSilverfish.VARIANT)).getModelBlock(), 3);
                                }
                                if (random.nextBoolean()) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
