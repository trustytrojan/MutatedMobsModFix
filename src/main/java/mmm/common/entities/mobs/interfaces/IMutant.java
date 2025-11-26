//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities.mobs.interfaces;

public interface IMutant
{
    default boolean CanMutate() {
        return false;
    }
    
    default boolean CanDefuse() {
        return false;
    }
    
    default boolean HasSpecialAbillities() {
        return false;
    }
}
