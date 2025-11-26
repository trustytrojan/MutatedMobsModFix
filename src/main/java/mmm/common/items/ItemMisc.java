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

public class ItemMisc extends Item
{
    public ItemMisc(final String unlocalizedName) {
        this.setCreativeTab(CreativeTabs.MISC);
        this.setUnlocalizedName(unlocalizedName);
    }
    
    public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        if (stack.getItem() == ModItems.wither_star) {
            tooltip.add("It's unknown what is the purpose of this star but...");
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(final ItemStack stack) {
        return stack.getItem() == ModItems.wither_star;
    }
}
