//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.items;

import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import javax.annotation.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraft.init.*;
import java.awt.Color;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;

public class ItemFireworkDetonator extends Item
{
    public EntityLivingBase marked;
    private int explosionRadius;
    private ItemStack firework;
    
    public ItemFireworkDetonator(final String unlocalizedName) {
        this.explosionRadius = 2;
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
        tooltip.add("\u00A73Will summon fireworks on target!");
        tooltip.add("\u00A73Attach bomb to mob and then detonate it!");
        if (this.marked != null) {
            tooltip.add("\u00A73Attached to: " + this.marked.getName());
        }
        tooltip.add("\u00A73Uses: " + this.getMaxDamage(stack));
    }
    
    public void getRandomColorFireWork() {
        (this.firework = new ItemStack(Items.FIREWORKS, 1)).setTagCompound(new NBTTagCompound());
        final NBTTagCompound data = new NBTTagCompound();
        data.setByte("Flight", (byte)1);
        final NBTTagList list = new NBTTagList();
        final NBTTagCompound fireworkData = new NBTTagCompound();
        fireworkData.setByte("Trail", (byte)1);
        fireworkData.setByte("Type", (byte)3);
        fireworkData.setIntArray("Colors", new int[] { new Color(ItemFireworkDetonator.itemRand.nextInt(255), ItemFireworkDetonator.itemRand.nextInt(255), ItemFireworkDetonator.itemRand.nextInt(255)).getRGB() });
        list.appendTag((NBTBase)fireworkData);
        data.setTag("Explosions", (NBTBase)list);
        this.firework.getTagCompound().setTag("Fireworks", (NBTBase)data);
    }
    
    public ActionResult<ItemStack> onItemRightClick(final World worldIn, final EntityPlayer playerIn, final EnumHand handIn) {
        final ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (this.marked != null) {
            if (!worldIn.isRemote) {
                final boolean flag = worldIn.getGameRules().getBoolean("mobGriefing");
                worldIn.createExplosion((Entity)playerIn, this.marked.posX, this.marked.posY, this.marked.posZ, (float)this.explosionRadius, flag);
                this.getRandomColorFireWork();
                final EntityFireworkRocket rocket = new EntityFireworkRocket(worldIn, this.marked.posX, this.marked.posY, this.marked.posZ, this.firework.copy());
                worldIn.spawnEntity((Entity)rocket);
                this.marked.startRiding((Entity)rocket);
                this.marked = null;
                if (!playerIn.isCreative()) {
                    itemstack.damageItem(10, (EntityLivingBase)playerIn);
                }
            }
            if (playerIn.isSneaking()) {
                this.marked = null;
            }
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, (Object)itemstack);
    }
}
