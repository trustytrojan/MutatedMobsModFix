//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.projectiles;

import net.minecraft.entity.projectile.*;
import java.awt.Color;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.datafix.*;

public class EntitySquidFireball extends EntityFireball
{
    public int explosionPower;
    Color col;
    
    public EntitySquidFireball(final World worldIn) {
        super(worldIn);
        this.explosionPower = 1;
    }
    
    public boolean canBeCollidedWith() {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public EntitySquidFireball(final World worldIn, final double x, final double y, final double z, final double accelX, final double accelY, final double accelZ) {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
        this.explosionPower = 1;
    }
    
    public EntitySquidFireball(final World worldIn, final EntityLivingBase shooter, final double accelX, final double accelY, final double accelZ) {
        super(worldIn, shooter, accelX, accelY, accelZ);
        this.explosionPower = 1;
    }
    
    protected void onImpact(final RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.entityHit != null) {
                result.entityHit.attackEntityFrom(DamageSource.MAGIC, 5.0f);
                this.applyEnchantments(this.shootingEntity, result.entityHit);
            }
            this.shootingEntity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 50));
            this.spawnLingeringCloud();
            this.shootingEntity.heal(1.0f);
            this.setDead();
        }
    }
    
    private void spawnLingeringCloud() {
        final Collection<PotionEffect> collection = (Collection<PotionEffect>)this.shootingEntity.getActivePotionEffects();
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
    
    public static void registerFixesLargeFireball(final DataFixer fixer) {
        EntityFireball.registerFixesFireball(fixer, "SquidFireball");
    }
}
