//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.projectiles;

import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.util.datafix.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EntityEnderBall extends EntityThrowable
{
    public EntityEnderBall(final World worldIn) {
        super(worldIn);
    }
    
    public EntityEnderBall(final World worldIn, final EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }
    
    public EntityEnderBall(final World worldIn, final double x, final double y, final double z) {
        super(worldIn, x, y, z);
    }
    
    public static void registerFixesSnowball(final DataFixer fixer) {
        EntityThrowable.registerFixesThrowable(fixer, "Enderball");
    }
    
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(final byte id) {
        if (id == 3) {
            for (int i = 0; i < 8; ++i) {
                this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }
    
    protected void onImpact(final RayTraceResult result) {
        if (result.entityHit != null) {
            final int i = 3;
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)i);
        }
        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }
}
