//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.items;

import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import javax.annotation.*;
import java.util.*;
import net.minecraft.client.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;

public class ItemSpecialFood extends ItemFood
{
    public ItemSpecialFood(final String unlocalizedName, final int amount, final boolean isWolfFood) {
        super(amount, isWolfFood);
        this.setCreativeTab(CreativeTabs.FOOD);
        this.setUnlocalizedName(unlocalizedName);
    }
    
    public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        if (stack.getItem() == ModItems.porkchop_blazing) {
            tooltip.add("Swimming in lava is now possible!");
            tooltip.add("Gives quite a lot of food.");
        }
        if (stack.getItem() == ModItems.porkchop_creeper) {
            tooltip.add("Tastes the same as eating raw TNT!");
            tooltip.add("Not worth to eat it only if you have a lot!");
        }
        if (stack.getItem() == ModItems.porkchop_chargedcreeper) {
            tooltip.add("Tastes the same as eating 5x TNT!");
            tooltip.add("Be carefull as this item is destructive!");
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(final ItemStack stack) {
        return stack.getItem() == ModItems.porkchop_chargedcreeper;
    }
    
    protected void onFoodEaten(final ItemStack stack, final World worldIn, final EntityPlayer player) {
        if (!worldIn.isRemote && stack.getItem() == ModItems.porkchop_blazing) {
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 400));
        }
        else if (!worldIn.isRemote && stack.getItem() == ModItems.porkchop_creeper) {
            final boolean flag = worldIn.getGameRules().getBoolean("mobGriefing");
            worldIn.createExplosion((Entity)player, player.posX, player.posY + 2.0, player.posZ, 2.0f, flag);
        }
        else if (!worldIn.isRemote && stack.getItem() == ModItems.porkchop_chargedcreeper) {
            final boolean flag = worldIn.getGameRules().getBoolean("mobGriefing");
            worldIn.createExplosion((Entity)player, player.posX, player.posY + 2.0, player.posZ, 10.0f, flag);
        }
    }
}
