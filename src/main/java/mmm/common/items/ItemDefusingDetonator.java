//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.items;

import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import javax.annotation.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import mmm.common.entities.mobs.*;

public class ItemDefusingDetonator extends Item
{
    public EntityLivingBase marked;
    private int explosionRadius;
    
    public ItemDefusingDetonator(final String unlocalizedName) {
        this.explosionRadius = 1;
        this.setCreativeTab(CreativeTabs.MISC);
        this.setUnlocalizedName(unlocalizedName);
        this.setMaxDamage(1);
        this.maxStackSize = 1;
        this.marked = null;
    }
    
    public boolean hitEntity(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker) {
        if (this.marked == null) {
            this.marked = target;
        }
        return true;
    }
    
    public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        tooltip.add("Will cause mutant to defuse, also has weaker explosion!");
        tooltip.add("Attach bomb to mob and then detonate it!");
        if (this.marked != null) {
            tooltip.add("Attached to: " + this.marked.getName());
        }
        tooltip.add("\u00A73Uses: " + this.getMaxDamage(stack));
    }
    
    public ActionResult<ItemStack> onItemRightClick(final World worldIn, final EntityPlayer playerIn, final EnumHand handIn) {
        final ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (this.marked != null) {
            if (!worldIn.isRemote) {
                final boolean flag = worldIn.getGameRules().getBoolean("mobGriefing");
                worldIn.createExplosion(playerIn, this.marked.posX, this.marked.posY, this.marked.posZ, (float)this.explosionRadius, flag);
                this.Defusion(worldIn);
                this.marked = null;
                if (!playerIn.isCreative()) {
                    itemstack.damageItem(10, (EntityLivingBase)playerIn);
                }
            }
            if (playerIn.isSneaking()) {
                this.marked = null;
            }
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }
    
    public void Defusion(final World world) {
        if (this.marked instanceof EntityBlazeGolem && !world.isRemote) {
            final EntityBlaze entitypigzombie = new EntityBlaze(world);
            final EntityIronGolem entitypigzombie2 = new EntityIronGolem(world);
            entitypigzombie.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie2.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie);
            this.marked.world.spawnEntity(entitypigzombie2);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityBlazePig && !world.isRemote) {
            final EntityBlaze entitypigzombie = new EntityBlaze(world);
            final EntityPig entitypigzombie3 = new EntityPig(world);
            entitypigzombie.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie3.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie);
            this.marked.world.spawnEntity(entitypigzombie3);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityBlazeSkeleton && !world.isRemote) {
            final EntityBlaze entitypigzombie = new EntityBlaze(world);
            final EntitySkeleton entitypigzombie4 = new EntitySkeleton(world);
            entitypigzombie.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie4.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie4.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack((Item)Items.BOW));
            this.marked.world.spawnEntity(entitypigzombie);
            this.marked.world.spawnEntity(entitypigzombie4);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityEnderManSkeleton && !world.isRemote) {
            final EntityEnderman entitypigzombie5 = new EntityEnderman(world);
            final EntitySkeleton entitypigzombie4 = new EntitySkeleton(world);
            entitypigzombie5.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie4.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie4.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack((Item)Items.BOW));
            this.marked.world.spawnEntity(entitypigzombie5);
            this.marked.world.spawnEntity(entitypigzombie4);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityCreeperGolem && !world.isRemote) {
            final EntityCreeper entitypigzombie6 = new EntityCreeper(world);
            final EntityIronGolem entitypigzombie2 = new EntityIronGolem(world);
            entitypigzombie6.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie2.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie6);
            this.marked.world.spawnEntity(entitypigzombie2);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityCreeperPig && !world.isRemote) {
            final EntityCreeper entitypigzombie6 = new EntityCreeper(world);
            final EntityPig entitypigzombie3 = new EntityPig(world);
            entitypigzombie6.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie3.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie6);
            this.marked.world.spawnEntity(entitypigzombie3);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityCreepervoker && !world.isRemote) {
            final EntityCreeper entitypigzombie6 = new EntityCreeper(world);
            final EntityEvoker entitypigzombie7 = new EntityEvoker(world);
            entitypigzombie6.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie7.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie6);
            this.marked.world.spawnEntity(entitypigzombie7);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityElderSpiderGuardian && !world.isRemote) {
            final EntitySpider entitypigzombie8 = new EntitySpider(world);
            final EntityElderGuardian entitypigzombie9 = new EntityElderGuardian(world);
            entitypigzombie8.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie9.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie8);
            this.marked.world.spawnEntity(entitypigzombie9);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityEnderGolem && !world.isRemote) {
            final EntityIronGolem entitypigzombie10 = new EntityIronGolem(world);
            final EntityEnderman entitypigzombie11 = new EntityEnderman(world);
            entitypigzombie10.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie11.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie10);
            this.marked.world.spawnEntity(entitypigzombie11);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityGuardianGolem && !world.isRemote) {
            final EntityGuardian entitypigzombie12 = new EntityGuardian(world);
            final EntityIronGolem entitypigzombie2 = new EntityIronGolem(world);
            entitypigzombie12.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie2.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie12);
            this.marked.world.spawnEntity(entitypigzombie2);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityGhastShulker && !world.isRemote) {
            final EntityGhast entitypigzombie13 = new EntityGhast(world);
            final EntityShulker entitypigzombie14 = new EntityShulker(world);
            entitypigzombie13.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie14.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie13);
            this.marked.world.spawnEntity(entitypigzombie14);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityHuskSpider && !world.isRemote) {
            final EntityHusk entitypigzombie15 = new EntityHusk(world);
            final EntitySpider entitypigzombie16 = new EntitySpider(world);
            entitypigzombie15.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie16.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie15);
            this.marked.world.spawnEntity(entitypigzombie16);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityIlliusionerCreeper && !world.isRemote) {
            final EntityIllusionIllager entitypigzombie17 = new EntityIllusionIllager(world);
            final EntityCreeper entitypigzombie18 = new EntityCreeper(world);
            entitypigzombie17.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie18.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie17);
            this.marked.world.spawnEntity(entitypigzombie18);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityIlliusionerGhast && !world.isRemote) {
            final EntityGhast entitypigzombie13 = new EntityGhast(world);
            final EntityIllusionIllager entitypigzombie19 = new EntityIllusionIllager(world);
            entitypigzombie13.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie19.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie13);
            this.marked.world.spawnEntity(entitypigzombie19);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityIlliusionerWitherSkeleton && !world.isRemote) {
            final EntityIllusionIllager entitypigzombie17 = new EntityIllusionIllager(world);
            final EntityWitherSkeleton entitypigzombie20 = new EntityWitherSkeleton(world);
            entitypigzombie17.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie20.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie20.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
            this.marked.world.spawnEntity(entitypigzombie17);
            this.marked.world.spawnEntity(entitypigzombie20);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityShulkerGhast && !world.isRemote) {
            final EntityGhast entitypigzombie21 = new EntityGhast(world);
            final EntityShulker entitypigzombie22 = new EntityShulker(world);
            entitypigzombie22.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie21.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie22);
            this.marked.world.spawnEntity(entitypigzombie21);
            this.marked.setDead();
        }
        if (this.marked instanceof EntitySilverfishCreeper && !world.isRemote) {
            final EntityCreeper entitypigzombie6 = new EntityCreeper(world);
            final EntitySilverfish entitypigzombie23 = new EntitySilverfish(world);
            entitypigzombie6.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie23.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie6);
            this.marked.world.spawnEntity(entitypigzombie23);
            this.marked.setDead();
        }
        if (this.marked instanceof EntitySlimeCreeper && !world.isRemote) {
            final EntitySlime entitypigzombie24 = new EntitySlime(world);
            final EntityCreeper entitypigzombie18 = new EntityCreeper(world);
            entitypigzombie24.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie18.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie24);
            this.marked.world.spawnEntity(entitypigzombie18);
            this.marked.setDead();
        }
        if (this.marked instanceof EntitySlimeGolem && !world.isRemote) {
            final EntityIronGolem entitypigzombie10 = new EntityIronGolem(world);
            final EntitySlime entitypigzombie25 = new EntitySlime(world);
            entitypigzombie10.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie25.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie10);
            this.marked.world.spawnEntity(entitypigzombie25);
            this.marked.setDead();
        }
        if (this.marked instanceof EntitySpiderCreeper && !world.isRemote) {
            final EntityCreeper entitypigzombie6 = new EntityCreeper(world);
            final EntitySpider entitypigzombie16 = new EntitySpider(world);
            entitypigzombie6.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie16.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie6);
            this.marked.world.spawnEntity(entitypigzombie16);
            this.marked.setDead();
        }
        if (this.marked instanceof EntitySpiderGuardian && !world.isRemote) {
            final EntitySpider entitypigzombie8 = new EntitySpider(world);
            final EntityGuardian entitypigzombie26 = new EntityGuardian(world);
            entitypigzombie8.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie26.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie8);
            this.marked.world.spawnEntity(entitypigzombie26);
            this.marked.setDead();
        }
        if (this.marked instanceof EntitySpiderWitch && !world.isRemote) {
            final EntityWitch entitypigzombie27 = new EntityWitch(world);
            final EntitySpider entitypigzombie16 = new EntitySpider(world);
            entitypigzombie27.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie16.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie27);
            this.marked.world.spawnEntity(entitypigzombie16);
            this.marked.setDead();
        }
        if (this.marked instanceof EntitySpiderPig && !world.isRemote) {
            final EntityPig entitypigzombie28 = new EntityPig(world);
            final EntitySpider entitypigzombie16 = new EntitySpider(world);
            entitypigzombie28.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie16.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie28);
            this.marked.world.spawnEntity(entitypigzombie16);
            this.marked.setDead();
        }
        if (this.marked instanceof EntitySquidCreeper && !world.isRemote) {
            final EntitySquid entitypigzombie29 = new EntitySquid(world);
            final EntityCreeper entitypigzombie18 = new EntityCreeper(world);
            entitypigzombie29.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie18.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie29);
            this.marked.world.spawnEntity(entitypigzombie18);
            this.marked.setDead();
        }
        if (this.marked instanceof EntitySquidGhast && !world.isRemote) {
            final EntitySquid entitypigzombie29 = new EntitySquid(world);
            final EntityGhast entitypigzombie30 = new EntityGhast(world);
            entitypigzombie29.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie30.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie29);
            this.marked.world.spawnEntity(entitypigzombie30);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityZombieCreeper && !world.isRemote) {
            final EntityCreeper entitypigzombie6 = new EntityCreeper(world);
            final EntityZombie entitypigzombie31 = new EntityZombie(world);
            entitypigzombie6.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie31.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie6);
            this.marked.world.spawnEntity(entitypigzombie31);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityZombieSpiderPigman && !world.isRemote) {
            final EntityPigZombie entitypigzombie32 = new EntityPigZombie(world);
            final EntitySpider entitypigzombie16 = new EntitySpider(world);
            entitypigzombie32.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie16.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie32.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
            this.marked.world.spawnEntity(entitypigzombie32);
            this.marked.world.spawnEntity(entitypigzombie16);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityZombieSpider && !world.isRemote) {
            final EntityZombie entitypigzombie33 = new EntityZombie(world);
            final EntitySpider entitypigzombie16 = new EntitySpider(world);
            entitypigzombie33.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie16.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie33);
            this.marked.world.spawnEntity(entitypigzombie16);
            this.marked.setDead();
        }
        if (this.marked instanceof EntityEndermanCreeper && !world.isRemote) {
            final EntityCreeper entitypigzombie6 = new EntityCreeper(world);
            final EntityEnderman entitypigzombie11 = new EntityEnderman(world);
            entitypigzombie6.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            entitypigzombie11.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, this.marked.rotationPitch);
            this.marked.world.spawnEntity(entitypigzombie6);
            this.marked.world.spawnEntity(entitypigzombie11);
            this.marked.setDead();
        }
    }
}
