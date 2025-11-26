//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.eventHandler;

import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.client.event.*;
import net.minecraftforge.common.config.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Mod.EventBusSubscriber
class EventHandler
{
    @SubscribeEvent
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals("mmm")) {
            ConfigManager.sync("mmm", Config.Type.INSTANCE);
        }
    }
}
