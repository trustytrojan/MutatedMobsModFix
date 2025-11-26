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

public class ItemGoldMobFuser extends Item
{
    public EntityLivingBase marked;
    public EntityLivingBase marked2;
    
    public ItemGoldMobFuser(final String unlocalizedName) {
        this.setCreativeTab(CreativeTabs.MISC);
        this.setUnlocalizedName(unlocalizedName);
        this.setMaxDamage(100);
    }
    
    public boolean hitEntity(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        if (this.marked == null) {
            this.marked = target;
        }
        else if (this.marked != null && this.marked2 == null) {
            this.marked2 = target;
        }
        return true;
    }
    
    public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        tooltip.add("\u00A73This item can make any mob become one!");
        if (this.marked != null) {
            tooltip.add("\u00A73First Mob: " + this.marked.getName());
        }
        if (this.marked2 != null) {
            tooltip.add("\u00A73Second Mob: " + this.marked2.getName());
        }
        tooltip.add("\u00A73" + this.getMaxDamage(stack) + " Uses");
    }
    
    public ActionResult<ItemStack> onItemRightClick(final World worldIn, final EntityPlayer playerIn, final EnumHand handIn) {
        final ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (this.marked != null && this.marked2 != null) {
            this.SpawnMutant(worldIn, playerIn);
        }
        if (playerIn.isSneaking()) {
            this.marked = null;
            this.marked2 = null;
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)itemstack);
    }
    
    public void SpawnMutant(final World world, final EntityPlayer playerIn) {
        if (this.marked instanceof EntityCreeper && this.marked2 instanceof EntitySpider && !world.isRemote) {
            final EntitySpiderCreeper entitycreeper = new EntitySpiderCreeper(world);
            entitycreeper.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            world.spawnEntity((Entity)entitycreeper);
            this.marked = null;
            this.marked2 = null;
            world.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_TOTEM_USE, SoundCategory.NEUTRAL, 0.5f, 0.4f / (ItemGoldMobFuser.itemRand.nextFloat() * 0.4f + 0.8f));
        }
        if (this.marked instanceof EntityEnderman && this.marked2 instanceof EntityIronGolem && !world.isRemote) {
            final EntityEnderGolem entitycreeper2 = new EntityEnderGolem(world);
            entitycreeper2.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            world.spawnEntity((Entity)entitycreeper2);
            this.marked = null;
            this.marked2 = null;
        }
        if (this.marked instanceof EntityWitch && this.marked2 instanceof EntitySpider && !world.isRemote) {
            final EntitySpiderWitch entitycreeper3 = new EntitySpiderWitch(world);
            entitycreeper3.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            world.spawnEntity((Entity)entitycreeper3);
            this.marked = null;
            this.marked2 = null;
        }
        if (this.marked instanceof EntitySlime && this.marked2 instanceof EntityCreeper && !world.isRemote) {
            final EntitySlimeCreeper entitycreeper4 = new EntitySlimeCreeper(world);
            entitycreeper4.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            world.spawnEntity((Entity)entitycreeper4);
            this.marked = null;
            this.marked2 = null;
        }
        if (this.marked instanceof EntityBlaze && this.marked2 instanceof EntitySkeleton && !world.isRemote) {
            final EntityBlazeSkeleton entitycreeper5 = new EntityBlazeSkeleton(world);
            entitycreeper5.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            entitycreeper5.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack((Item)Items.BOW));
            world.spawnEntity((Entity)entitycreeper5);
            this.marked = null;
            this.marked2 = null;
        }
        if (this.marked instanceof EntitySpider && this.marked2 instanceof EntityPig && !world.isRemote) {
            final EntitySpiderPig entitycreeper6 = new EntitySpiderPig(world);
            entitycreeper6.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            world.spawnEntity((Entity)entitycreeper6);
            this.marked = null;
            this.marked2 = null;
        }
        if (this.marked instanceof EntityBlaze && this.marked2 instanceof EntityPig && !world.isRemote) {
            final EntityBlazePig entitycreeper7 = new EntityBlazePig(world);
            entitycreeper7.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            world.spawnEntity((Entity)entitycreeper7);
            this.marked = null;
            this.marked2 = null;
        }
        if (this.marked instanceof EntitySpider && this.marked2 instanceof EntityGuardian && !world.isRemote) {
            final EntitySpiderGuardian entitycreeper8 = new EntitySpiderGuardian(world);
            entitycreeper8.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            world.spawnEntity((Entity)entitycreeper8);
            this.marked = null;
            this.marked2 = null;
        }
        if (this.marked instanceof EntitySpider && this.marked2 instanceof EntityElderGuardian && !world.isRemote) {
            final EntityElderSpiderGuardian entitycreeper9 = new EntityElderSpiderGuardian(world);
            entitycreeper9.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            world.spawnEntity((Entity)entitycreeper9);
            this.marked = null;
            this.marked2 = null;
        }
        if (this.marked instanceof EntityCreeper && this.marked2 instanceof EntityIllusionIllager && !world.isRemote) {
            final EntityIlliusionerCreeper entitycreeper10 = new EntityIlliusionerCreeper(world);
            entitycreeper10.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            world.spawnEntity((Entity)entitycreeper10);
            this.marked = null;
            this.marked2 = null;
        }
        if (this.marked instanceof EntityWitherSkeleton && this.marked2 instanceof EntityIllusionIllager && !world.isRemote) {
            final EntityIlliusionerWitherSkeleton entitycreeper11 = new EntityIlliusionerWitherSkeleton(world);
            entitycreeper11.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            entitycreeper11.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
            world.spawnEntity((Entity)entitycreeper11);
            this.marked = null;
            this.marked2 = null;
        }
        if (this.marked instanceof EntityEnderman && this.marked2 instanceof EntitySkeleton && !world.isRemote) {
            final EntityEnderManSkeleton entitycreeper12 = new EntityEnderManSkeleton(world);
            entitycreeper12.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            entitycreeper12.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack((Item)Items.BOW));
            world.spawnEntity((Entity)entitycreeper12);
            this.marked = null;
            this.marked2 = null;
        }
        if (this.marked instanceof EntityPigZombie && this.marked2 instanceof EntitySpider && !world.isRemote) {
            final EntityZombieSpiderPigman entitycreeper13 = new EntityZombieSpiderPigman(world);
            entitycreeper13.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            entitycreeper13.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
            world.spawnEntity((Entity)entitycreeper13);
            this.marked = null;
            this.marked2 = null;
        }
        if (this.marked instanceof EntityGhast && this.marked2 instanceof EntityShulker && !world.isRemote) {
            final EntityGhastShulker entitycreeper14 = new EntityGhastShulker(world);
            entitycreeper14.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            world.spawnEntity((Entity)entitycreeper14);
            this.marked = null;
            this.marked2 = null;
        }
        if (this.marked instanceof EntityShulker && this.marked2 instanceof EntityGhast && !world.isRemote) {
            final EntityShulkerGhast entitycreeper15 = new EntityShulkerGhast(world);
            entitycreeper15.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            world.spawnEntity((Entity)entitycreeper15);
            this.marked = null;
            this.marked2 = null;
        }
        if (this.marked instanceof EntitySilverfish && this.marked2 instanceof EntityCreeper && !world.isRemote) {
            final EntitySilverfishCreeper entitycreeper16 = new EntitySilverfishCreeper(world);
            entitycreeper16.setLocationAndAngles(this.marked.posX, this.marked.posY, this.marked.posZ, this.marked.rotationYaw, 0.0f);
            this.marked.setDead();
            this.marked2.setDead();
            world.spawnEntity((Entity)entitycreeper16);
            this.marked = null;
            this.marked2 = null;
        }
    }
}
