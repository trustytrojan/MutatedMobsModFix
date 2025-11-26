//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.eventHandler;

import net.minecraftforge.common.config.*;

@Config(modid = "mmm", name = "RolikMods/MutatedMobs_Config")
public class ConfigHandler
{
    @Config.Comment({ "The percent chance for a creeper mutant to explode into confetti.(Works only if Creeper Confetti mod is installed!)" })
    public static int O_confettiChance = 100;

    @Config.Comment({ "Set to true if you still want creeper mutant to damage players.(Works only if Creeper Confetti mod is installed!)" })
    public static boolean O_damagesPlayers = false;

    @Config.Comment({ "Enable Creeper Golems Finishing Move!" })
    public static boolean S_CreeperGolemExplodeOnDeath = true;

    @Config.Comment({ "Enable Blaze Golems Cooking Food Abillity!" })
    public static boolean S_BlazeGolemCookFoodOnRightClick = true;

    @Config.Comment({ "Enable Guardian Golems Laser Eye Abillity!(Not working yet!)" })
    public static boolean S_GuardianGolemLaserEye = true;

    @Config.Comment({ "Evil Creeper Pigs, Creeper Pigs now become agressive to players!" })
    public static boolean S_EvilCreeperPigs = true;

    @Config.Comment({ "Enable Blaze Skeletons Dual damage when burning!" })
    public static boolean S_BlazeSkeletonDualDamage = true;

    @Config.Comment({ "Set Mob and Bosses Spawnrate" })
    public static int M_BlazePigSpawnRate = 10;
    public static int M_BlazeSkeletonSpawnRate = 20;
    public static int M_ShulkerGhastSpawnRate = 10;
    public static int M_GhastShulkerSpawnRate = 10;
    public static int M_CreepervokerSpawnRate = 20;
    public static int M_CreeperSpiderSpawnRate = 15;
    public static int M_IlliusionerCreeperSpawnRate = 15;
    public static int M_IlliusionerWitherSkeletonSpawnRate = 10;
    public static int M_EndermanSkeletonSpawnRate = 20;
    public static int M_EnderGolemSpawnRate = 0;
    public static int M_ZombieCreeperSpawnRate = 20;
    public static int M_CreeperPigSpawnRate = 20;
    public static int M_SilverfishCreeperSpawnRate = 25;
    public static int M_SlimeCreeperSpawnRate = 20;
    public static int M_SpiderGuardianSpawnRate = 10;
    public static int M_PigmanSpiderSpawnRate = 30;
    public static int M_WitchSpiderSpawnRate = 15;
    public static int M_SpiderPigSpawnRate = 10;
    public static int M_ZombieSpiderSpawnRate = 25;
    public static int M_SquidGhastSpawnRate = 10;
    public static int M_HuskSpiderSpawnRate = 20;
    public static int M_WitherShulkerSpawnRate = 0;
    public static int M_ShulkerWitherSpawnRate = 0;
    public static int M_GuardianGolemSpawnRate = 0;
    public static int M_SlimeGolemSpawnRate = 0;
    public static int M_CreeperGolemSpawnRate = 0;
    public static int M_BlazeGolemSpawnRate = 0;
    public static int M_ElderSpiderGuardianSpawnRate = 0;
    public static int M_IlliusionerGhastSpawnRate = 0;
    public static int M_SpookSpawnRate = 0;
    public static int M_SquidCreeperSpawnRate = 20;
    public static int M_EndermanCreeperSpawnRate = 10;
    public static int M_EnderSnowmanSpawnRate = 0;
    public static int M_BlazeSnowmanSpawnRate = 0;
    public static int M_SantaSpiderSpawnRate = 0;
    public static int M_WitherGolemSpawnRate = 0;
    public static int M_PolarWolfSpawnRate = 10;
    public static int M_BlendermanSpawnRate = 0;

    @Config.Comment({ "Sets limit for specific type of mobs in one chunk!" })
    public static int Min_MobAmount = 1;
    public static int Max_MobAmount = 2;
    public static int Min_GolemAmount = 1;
    public static int Max_GolemAmount = 1;
    public static int Min_EvilMobAmount = 1;
    public static int Max_EvilMobAmount = 3;
    public static int Min_CreeperMobAmount = 1;
    public static int Max_CreeperMobAmount = 3;
    public static int Min_SpiderMobAmount = 1;
    public static int Max_SpiderMobAmount = 3;

    @Config.Comment({ "Mutated Mobs Health && Attack" })
    public static int HP_BlazeGolem = 140;
    public static int HP_BlazePig = 30;
    public static int HP_BlazeSkeleton = 50;
    public static int HP_BlazeSnowman = 24;
    public static int HP_CreeperGolem = 140;
    public static int HP_CreeperPig = 30;
    public static int HP_CreeperVoker = 40;
    public static int HP_ElderSpiderGuardian = 150;
    public static int HP_EnderGolem = 140;
    public static int HP_EnderCreeper = 60;
    public static int HP_EndermanSkeleton = 60;
    public static int HP_EnderSnowman = 46;
    public static int HP_GhastShulker = 60;
    public static int HP_GuardianGolem = 100;
    public static int HP_HuskSpider = 36;
    public static int HP_IlliusionerCreeper = 24;
    public static int HP_IlliusionerWitherSkeleton = 40;
    public static int HP_IlliusionerGhast = 20;
    public static int HP_ShulkerGhast = 40;
    public static int HP_SilverfishCreeper = 10;
    public static int HP_SpiderCreeper = 36;
    public static int HP_SpiderGuardian = 50;
    public static int HP_SpiderPig = 26;
    public static int HP_SpiderWitch = 50;
    public static int HP_SquidCreeper = 26;
    public static int HP_SquidGhast = 60;
    public static int HP_ZombieCreeper = 40;
    public static int HP_ZombieSpider = 36;
    public static int HP_ZombieSpiderPigman = 36;
    public static int HP_WitherShulker = 350;
    public static int HP_ShulkerWither = 350;
    public static int HP_Spook = 360;
    public static int HP_SantaSpider = 250;
    public static int HP_PolarWolf = 100;
    public static int HP_DeadPolarWolf = 100;
    public static int HP_Blenderman = 60;

    public static int ATK_BlazeGolem_MIN = 7;
    public static int ATK_BlazeGolem_MAX = 20;
    public static int ATK_BlazePig = 6;
    public static int ATK_BlazeSkeleton_Arrow = 6;
    public static int ATK_BlazeSkeleton_Melee = 6;
    public static int ATK_Blenderman_MIN = 7;
    public static int ATK_Blenderman_MAX = 14;
    public static int ATK_CreeperGolem_MIN = 7;
    public static int ATK_CreeperGolem_MAX = 21;
    public static int ATK_DeadPolarWolf = 10;
    public static int ATK_PolarWolf = 10;
    public static int ATK_ElderSpiderGuardian = 12;
    public static int ATK_EnderGolem_MIN = 6;
    public static int ATK_EnderGolem_MAX = 14;
    public static int ATK_EnderSkeleton_Arrow = 8;
    public static int ATK_EnderSkeleton_Melee = 6;
    public static int ATK_GuardianGolem_MIN = 7;
    public static int ATK_GuardianGolem_MAX = 15;
    public static int ATK_HuskSpider = 5;
    public static int ATK_WitherSkeletonIlliusioner = 7;
    public static int ATK_WitherSkeletonIlliusioner_Clone = 0;
    public static int ATK_SlimeGolem_MIN = 7;
    public static int ATK_SlimeGolem_MAX = 15;
    public static int ATK_CreeperSpider = 7;
    public static int ATK_SpiderGuardian = 8;
    public static int ATK_ZombieSpider = 6;
    public static int ATK_ZombieSpiderPigman = 7;
    public static int ATK_SantaSpider = 10;
    public static int ATK_Spook = 10;

    @Config.Comment({ "Set Maximum Slime Monster Size" })
    public static int SIZE_SlimeGolem_MAX = 3;
    public static int SIZE_SlimeCreeper_MAX = 4;

    @Config.Comment({ "Enable/Disable Mobs" })
    public static boolean EnableBlazeGolem = true;
    public static boolean EnableBlazePig = true;
    public static boolean EnableBlazeSkeleton = true;
    public static boolean EnableBlazeSnowman = true;
    public static boolean EnableCreeperGolem = true;
    public static boolean EnableCreeperPig = true;
    public static boolean EnableCreepervoker = true;
    public static boolean EnableElderSpiderGuardian = true;
    public static boolean EnableEnderGolem = true;
    public static boolean EnableEnderSkeleton = true;
    public static boolean EnableEnderSnowman = true;
    public static boolean EnableEnderCreeper = true;
    public static boolean EnableGhastShulker = true;
    public static boolean EnableGuardianGolem = true;
    public static boolean EnableHuskSpider = true;
    public static boolean EnableIlliusionerCreeper = true;
    public static boolean EnableIlliusionerWitherSkeleton = true;
    public static boolean EnableIlliusionerGhast = true;
    public static boolean EnableSlimeGolem = true;
    public static boolean EnableSlimeCreeper = true;
    public static boolean EnableShulkerGhast = true;
    public static boolean EnableSilverfishCreeper = true;
    public static boolean EnableSpiderCreeper = true;
    public static boolean EnableSpiderGuardian = true;
    public static boolean EnableSpiderPig = true;
    public static boolean EnableSpiderWitch = true;
    public static boolean EnableSquidCreeper = true;
    public static boolean EnableSquidGhast = true;
    public static boolean EnableZombieCreeper = true;
    public static boolean EnableZombieSpider = true;
    public static boolean EnableZombieSpiderPigman = true;
    public static boolean EnableWitherShulker = true;
    public static boolean EnableShulkerWither = true;
    public static boolean EnableSpook = true;
    public static boolean EnableSantaSpider = true;
    public static boolean EnablePolarWolf = true;
    public static boolean EnableDeadPolarWolf = true;
    public static boolean EnableBlenderman = true;

    @Config.Comment({ "Should defuser work on mutants?" })
    public static boolean CanDefuse = true;

    @Config.Comment({ "Should Minecraft Check for specific mods?" })
    public static boolean DetectCreeperConfettiMod = true;
    public static boolean DetectMuchMoreSpidersMod = true;
    public static boolean DetectMobArmorMod = true;
    public static boolean DetectExtraCreaturesMod = true;
    public static boolean DetectUtilityGolemsMod = true;
    public static boolean DetectExtraAddons = true;
}
