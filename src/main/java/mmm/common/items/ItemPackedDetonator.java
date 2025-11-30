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

public class ItemPackedDetonator extends Item
{
    public EntityLivingBase marked;
    private int explosionRadius;
    
    public ItemPackedDetonator(final String unlocalizedName) {
        this.explosionRadius = 6;
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
        tooltip.add("\u00A733x Detonators stored into 1!");
        tooltip.add("\u00A73Attach bomb to mob and then detonate it!");
        if (this.marked != null) {
            tooltip.add("\u00A73Attached to: " + this.marked.getName());
        }
        tooltip.add("\u00A73Uses: " + this.getMaxDamage(stack));
    }
    
    public ActionResult<ItemStack> onItemRightClick(final World worldIn, final EntityPlayer playerIn, final EnumHand handIn) {
        final ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (this.marked != null) {
            if (!worldIn.isRemote) {
                final boolean flag = worldIn.getGameRules().getBoolean("mobGriefing");
                worldIn.createExplosion(playerIn, this.marked.posX, this.marked.posY, this.marked.posZ, (float)this.explosionRadius, flag);
                this.marked = null;
                if (!playerIn.isCreative()) {
                    itemstack.damageItem(10, (EntityLivingBase)playerIn);
                }
            }
            if (playerIn.isSneaking()) {
                this.marked = null;
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
}
