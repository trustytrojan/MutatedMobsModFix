//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.library;

import mmm.eventHandler.*;
import net.minecraftforge.fml.common.*;

public class ModLoader
{
    public static boolean isCreeperConfettiLoaded;
    public static boolean isMuchMoreSpidersModLoaded;
    public static boolean isMobArmorModLoaded;
    
    public static void CheckForMods() {
        if (ConfigHandler.DetectCreeperConfettiMod) {
            ModLoader.isCreeperConfettiLoaded = Loader.isModLoaded("creeperconfetti");
        }
        if (ConfigHandler.DetectMuchMoreSpidersMod) {
            ModLoader.isMuchMoreSpidersModLoaded = Loader.isModLoaded("muchmorespiders");
        }
        if (ConfigHandler.DetectMobArmorMod) {
            ModLoader.isMobArmorModLoaded = Loader.isModLoaded("mobarmormod");
        }
    }
    
    static {
        ModLoader.isCreeperConfettiLoaded = false;
        ModLoader.isMuchMoreSpidersModLoaded = false;
        ModLoader.isMobArmorModLoaded = false;
    }
}
