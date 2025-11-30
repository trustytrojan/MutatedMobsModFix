//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.eventHandler;

import net.minecraftforge.event.entity.living.*;
import mmm.common.entities.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import mmm.common.entities.mobs.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.particle.*;
import net.minecraftforge.fml.relauncher.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import java.util.*;

public class ConfettiHandler
{
    public static final String[] TIME_SINCE_IGNITED;
    public static final String[] FUSE_TIME;
    
    @SubscribeEvent
    public void creeperExplodeEvent(final LivingEvent.LivingUpdateEvent event) {
        EntityHalfCreeper creeper = null;
        if (event.getEntityLiving() instanceof EntityHalfCreeper) {
            creeper = (EntityHalfCreeper)event.getEntityLiving();
        }
        if (creeper != null && creeper.getCreeperState() == 1) {
            final int ignitedTime = (int)ReflectionHelper.getPrivateValue((Class)EntityHalfCreeper.class, creeper, ConfettiHandler.TIME_SINCE_IGNITED);
            final int fuseTime = (int)ReflectionHelper.getPrivateValue((Class)EntityHalfCreeper.class, creeper, ConfettiHandler.FUSE_TIME);
            if (ignitedTime >= fuseTime - 1 && this.willExplodeToConfetti()) {
                creeper.getEntityWorld().playSound((EntityPlayer)null, creeper.posX, creeper.posY, creeper.posZ, SoundEvents.ENTITY_FIREWORK_TWINKLE, SoundCategory.BLOCKS, 0.5f, (1.0f + (creeper.getEntityWorld().rand.nextFloat() - creeper.getEntityWorld().rand.nextFloat()) * 0.2f) * 0.7f);
                if (creeper.getEntityWorld().isRemote) {
                    this.spawnParticles(creeper);
                }
                if (creeper instanceof EntitySilverfishCreeper) {
                    this.spawnParticles(creeper);
                    ((EntitySilverfishCreeper)creeper).split();
                    creeper.setDead();
                }
                else if (creeper instanceof EntitySquidCreeper) {
                    ((EntitySquidCreeper)creeper).Detonate();
                    this.spawnParticles(creeper);
                    creeper.setDead();
                }
                else {
                    this.spawnParticles(creeper);
                    creeper.setDead();
                }
                if (ConfigHandler.O_damagesPlayers) {
                    this.damagePlayers(creeper);
                }
            }
        }
    }
    
    private boolean willExplodeToConfetti() {
        final Random rand = new Random();
        return rand.nextInt(100) < ConfigHandler.O_confettiChance && ConfigHandler.O_confettiChance != 0;
    }
    
    private void damagePlayers(final EntityHalfCreeper creeper) {
        final float explosionStrength = creeper.getPowered() ? 2.0f : 1.0f;
        final Explosion explosion = new Explosion(creeper.getEntityWorld(), creeper, creeper.posX, creeper.posY, creeper.posZ, 3.0f * explosionStrength, false, false);
        explosion.doExplosionA();
    }
    
    @SideOnly(Side.CLIENT)
    private void spawnParticles(final EntityHalfCreeper creeper) {
        Minecraft.getMinecraft().effectRenderer.addEffect((Particle)new ParticleFirework.Starter(creeper.getEntityWorld(), creeper.posX, creeper.posY + 0.5, creeper.posZ, 0.0, 0.0, 0.0, Minecraft.getMinecraft().effectRenderer, this.generateTag()));
    }
    
    private NBTTagCompound generateTag() {
        final NBTTagCompound fireworkTag = new NBTTagCompound();
        new NBTTagCompound();
        final NBTTagCompound fireworkItemTag = new NBTTagCompound();
        new NBTTagCompound();
        final NBTTagList nbttaglist = new NBTTagList();
        new NBTTagList();
        final List<Integer> list = Lists.<Integer>newArrayList();
        final Random rand = new Random();
        list.add(ItemDye.DYE_COLORS[1]);
        list.add(ItemDye.DYE_COLORS[11]);
        list.add(ItemDye.DYE_COLORS[4]);
        for (int i = 0; i < rand.nextInt(3) + 3; ++i) {
            list.add(ItemDye.DYE_COLORS[rand.nextInt(15)]);
        }
        final int[] colours = new int[list.size()];
        for (int j = 0; j < colours.length; ++j) {
            colours[j] = list.get(j);
        }
        fireworkTag.setIntArray("Colors", colours);
        fireworkTag.setBoolean("Flicker", true);
        fireworkTag.setByte("Type", (byte)4);
        nbttaglist.appendTag((NBTBase)fireworkTag);
        fireworkItemTag.setTag("Explosions", (NBTBase)nbttaglist);
        return fireworkItemTag;
    }
    
    static {
        TIME_SINCE_IGNITED = new String[] { "timeSinceIgnited", "timeSinceIgnited", "bq" };
        FUSE_TIME = new String[] { "fuseTime", "fuseTime", "br" };
    }
}
