//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm;

import mmm.common.proxy.*;
import net.minecraftforge.fml.common.*;
import mmm.common.entities.*;
import mmm.library.*;
import net.minecraftforge.common.*;
import mmm.eventHandler.*;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = "mmm", version = "1.0.9", name = "Mutated Mobs Mod Fix", acceptedMinecraftVersions = "[1.12]")
public class MainClass
{
    @SidedProxy(clientSide = "mmm.client.proxy.ClientProxy", serverSide = "mmm.common.proxy.CommonProxy")
    public static CommonProxy proxy;
    @Mod.Instance("mmm")
    public static MainClass instance;
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent e) {
        MainClass.proxy.preInit(e);
        EntityLoader.init();
        MainClass.proxy.registerRenders();
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent e) {
        if (ModLoader.isCreeperConfettiLoaded) {
            MinecraftForge.EVENT_BUS.register(new ConfettiHandler());
            MainClass.proxy.init(e);
        }
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent e) {
        ModLoader.CheckForMods();
        MainClass.proxy.postInit(e);
    }
}
