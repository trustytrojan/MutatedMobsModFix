//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.client.proxy;

import mmm.common.proxy.*;
import mmm.renders.*;
import mmm.common.items.*;
import net.minecraftforge.fml.common.event.*;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(final FMLPreInitializationEvent e) {
        EntityRenderRegistry.Load();
        super.preInit(e);
    }
    
    @Override
    public void init(final FMLInitializationEvent e) {
        super.init(e);
    }
    
    @Override
    public void registerRenders() {
        ModItems.registerRenders();
    }
    
    @Override
    public void postInit(final FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
