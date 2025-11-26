//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.projectiles;

import net.minecraft.entity.projectile.*;
import net.minecraft.util.datafix.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraftforge.event.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.network.datasync.*;

public class EntityShulkerSkull extends EntityFireball
{
    private static final DataParameter<Boolean> INVULNERABLE;
    
    public EntityShulkerSkull(final World worldIn) {
        super(worldIn);
        this.setSize(0.3125f, 0.3125f);
    }
    
    public EntityShulkerSkull(final World worldIn, final EntityLivingBase shooter, final double accelX, final double accelY, final double accelZ) {
        super(worldIn, shooter, accelX, accelY, accelZ);
        this.setSize(0.3125f, 0.3125f);
    }
    
    public static void registerFixesWitherSkull(final DataFixer fixer) {
        EntityFireball.registerFixesFireball(fixer, "ShulkerSkull");
    }
    
    protected float getMotionFactor() {
        return this.isInvulnerable() ? 0.73f : super.getMotionFactor();
    }
    
    @SideOnly(Side.CLIENT)
    public EntityShulkerSkull(final World worldIn, final double x, final double y, final double z, final double accelX, final double accelY, final double accelZ) {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
        this.setSize(0.3125f, 0.3125f);
    }
    
    public boolean isBurning() {
        return false;
    }
    
    public float getExplosionResistance(final Explosion explosionIn, final World worldIn, final BlockPos pos, final IBlockState blockStateIn) {
        float f = super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn);
        final Block block = blockStateIn.getBlock();
        if (this.isInvulnerable() && block.canEntityDestroy(blockStateIn, (IBlockAccess)worldIn, pos, (Entity)this) && ForgeEventFactory.onEntityDestroyBlock(this.shootingEntity, pos, blockStateIn)) {
            f = Math.min(0.8f, f);
        }
        return f;
    }
    
    protected void onImpact(final RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.entityHit != null) {
                if (this.shootingEntity != null) {
                    if (result.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8.0f)) {
                        if (result.entityHit.isEntityAlive()) {
                            this.applyEnchantments(this.shootingEntity, result.entityHit);
                        }
                        else {
                            this.shootingEntity.heal(5.0f);
                        }
                    }
                }
                else {
                    result.entityHit.attackEntityFrom(DamageSource.MAGIC, 5.0f);
                }
                if (result.entityHit instanceof EntityLivingBase) {
                    int i = 0;
                    if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
                        i = 10;
                    }
                    else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                        i = 40;
                    }
                    if (i > 0) {
                        ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.WITHER, 20 * i, 1));
                        ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 40 * i, 1));
                    }
                }
            }
            this.world.newExplosion((Entity)this, this.posX, this.posY, this.posZ, 2.0f, false, this.world.getGameRules().getBoolean("mobGriefing"));
            this.setDead();
        }
    }
    
    public boolean canBeCollidedWith() {
        return false;
    }
    
    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        return false;
    }
    
    protected void entityInit() {
        this.dataManager.register((DataParameter)EntityShulkerSkull.INVULNERABLE, (Object)false);
    }
    
    public boolean isInvulnerable() {
        return (boolean)this.dataManager.get((DataParameter)EntityShulkerSkull.INVULNERABLE);
    }
    
    public void setInvulnerable(final boolean invulnerable) {
        this.dataManager.set((DataParameter)EntityShulkerSkull.INVULNERABLE, (Object)invulnerable);
    }
    
    protected boolean isFireballFiery() {
        return false;
    }
    
    static {
        INVULNERABLE = EntityDataManager.createKey((Class)EntityShulkerSkull.class, DataSerializers.BOOLEAN);
    }
}
