//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.items;

import net.minecraft.item.*;
import mmm.eventHandler.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.registries.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraftforge.client.model.*;

public class ModItems
{
    public static Item ironmob_fuser;
    public static Item goldmob_fuser;
    public static Item diamondmob_fuser;
    public static Item emeraldmob_fuser;
    public static Item detonator;
    public static Item packed_detonator;
    public static Item shulker_detonator;
    public static Item defusing_detonator;
    public static Item firework_detonator;
    public static Item porkchop_blazing;
    public static Item porkchop_creeper;
    public static Item porkchop_chargedcreeper;
    public static Item wither_star;
    
    public static final void register() {
        registerItem(ModItems.ironmob_fuser = new ItemIronMobFuser("ironmob_fuser"));
        registerItem(ModItems.goldmob_fuser = new ItemGoldMobFuser("goldmob_fuser"));
        registerItem(ModItems.diamondmob_fuser = new ItemDiamondMobFuser("diamondmob_fuser"));
        registerItem(ModItems.emeraldmob_fuser = new ItemEmeraldMobFuser("emeraldmob_fuser"));
        registerItem(ModItems.detonator = new ItemDetonator("detonator"));
        registerItem(ModItems.packed_detonator = new ItemPackedDetonator("packed_detonator"));
        registerItem(ModItems.shulker_detonator = new ItemShulkerDetonator("shulker_detonator"));
        if (ConfigHandler.EnableSantaSpider) {
            registerItem(ModItems.firework_detonator = new ItemFireworkDetonator("firework_detonator"));
        }
        if (ConfigHandler.CanDefuse) {
            registerItem(ModItems.defusing_detonator = new ItemDefusingDetonator("defusing_detonator"));
        }
        if (ConfigHandler.EnableBlazePig) {
            registerItem(ModItems.porkchop_blazing = (Item)new ItemSpecialFood("porkchop_blazing", 10, true));
        }
        if (ConfigHandler.EnableCreeperPig) {
            registerItem(ModItems.porkchop_creeper = (Item)new ItemSpecialFood("porkchop_creeper", 2, true));
            registerItem(ModItems.porkchop_chargedcreeper = (Item)new ItemSpecialFood("porkchop_chargedcreeper", 8, true));
        }
        registerItem(ModItems.wither_star = new ItemMisc("wither_star"));
    }
    
    public static void registerItem(final Item Item) {
        Item.setRegistryName(Item.getUnlocalizedName().substring(5));
        ForgeRegistries.ITEMS.register(Item);
    }
    
    public static void registerRenders() {
        registerRender(ModItems.ironmob_fuser);
        registerRender(ModItems.goldmob_fuser);
        registerRender(ModItems.diamondmob_fuser);
        registerRender(ModItems.emeraldmob_fuser);
        registerRender(ModItems.detonator);
        registerRender(ModItems.packed_detonator);
        if (ConfigHandler.EnableShulkerGhast) {
            registerRender(ModItems.shulker_detonator);
        }
        if (ConfigHandler.EnableSantaSpider) {
            registerRender(ModItems.firework_detonator);
        }
        if (ConfigHandler.CanDefuse) {
            registerRender(ModItems.defusing_detonator);
        }
        registerRender(ModItems.wither_star);
        if (ConfigHandler.EnableBlazePig) {
            registerRender(ModItems.porkchop_blazing);
        }
        if (ConfigHandler.EnableCreeperPig) {
            registerRender(ModItems.porkchop_creeper);
            registerRender(ModItems.porkchop_chargedcreeper);
        }
    }
    
    public static void registerRender(final Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation("mmm", item.getUnlocalizedName().substring(5)), "inventory"));
    }
    
    public static void registerRender(final Item item, final int meta, final String fileName) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation("mmm", fileName), "inventory"));
    }
}
