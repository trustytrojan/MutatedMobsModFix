//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.common.entities;

import mmm.eventHandler.*;
import mmm.common.entities.projectiles.*;
import mmm.common.entities.mobs.boss.*;
import mmm.common.entities.mobs.*;
import net.minecraft.util.*;
import mmm.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraft.entity.*;
import net.minecraft.world.biome.*;
import net.minecraft.init.*;

public class EntityLoader
{
    private static int id = 0;
    
    public static void init() {
        register(EntitySlimeCreeper.class, "SlimeCreeper", 10752, 5635925);
        register(EntityZombieSpiderPigman.class, "ZombieSpiderPigman", 0, 11141120);
        register(EntityEnderGolem.class, "EnderGolem", 0, 16777215);
        register(EntityBlazeSkeleton.class, "BlazeSkeleton", 2763264, 16755200);
        register(EntityEnderManSkeleton.class, "EndermanSkeleton", 0, 11141290);
        register(EntityShulkerGhast.class, "ShulkerGhast", 11141290, 16733695);
        register(EntitySpiderGuardian.class, "SpiderGuardian", 43690, 0);
        register(EntitySpiderCreeper.class, "SpiderCreeper", 0, 43520);
        register(EntitySpiderPig.class, "SpiderPig", 16733695, 16733695);
        register(EntityBlazePig.class, "BlazePig", 16733695, 16755200);
        register(EntityGhastShulker.class, "GhastShulker", 16733695, 11141290);
        register(EntityElderSpiderGuardian.class, "ElderSpiderGuardian", 0, 43690);
        register(EntitySpiderWitch.class, "SpiderWitch", 11141290, 5635925);
        registerNoEgg(EntityShulkerFireball.class, "ShulkerFireball");
        registerNoEgg(EntityCreeperfangs.class, "Creeperfangs");
        registerNoEgg(EntityShulkerSkull.class, "shulker_skull");
        registerNoEgg(EntityWitherBullet.class, "wither_bullet");
        registerNoEgg(EntityEnderBall.class, "ender_ball");
        register(EntitySilverfishCreeper.class, "SilverfishCreeper", 11184810, 5592405);
        register(EntityIlliusionerCreeper.class, "IlliusionCreeper", 170, 5592575);
        register(EntityIlliusionerWitherSkeleton.class, "IlliusionerWitherSkeleton", 170, 5592575);
        register(EntityIlliusionerGhast.class, "IlliusionerGhast", 170, 5592575);
        register(EntityZombieSpider.class, "spider_zombie", 0, 10752);
        register(EntitySlimeGolem.class, "slime_golem", 10752, 5635925);
        register(EntitySquidGhast.class, "squid_ghast", 16777215, 0);
        register(EntityCreeperPig.class, "creeper_pig", 16733695, 5635925);
        register(EntityCreepervoker.class, "creepervoker", 5635925, 5592405);
        register(EntityHuskSpider.class, "husk_spider", 10752, 16777045);
        register(EntityZombieCreeper.class, "zombie_creeper", 10752, 5635925);
        register(EntityCreeperGolem.class, "creeper_golem", 5635925, 16777215);
        register(EntityBlazeGolem.class, "blaze_golem", 16755200, 16777215);
        register(EntityGuardianGolem.class, "guardian_golem", 43520, 16777215);
        register(EntitySquidCreeper.class, "squid_creeper", 0, 170);
        register(EntityEndermanCreeper.class, "enderman_creeper", 11141290, 5635925);
        register(EntityEnderSnowman.class, "ender_snowman", 11141290, 16777215);
        register(EntityBlazeSnowman.class, "blaze_snowman", 16755200, 16777215);
        register(EntityShulkerWither.class, "shulker_wither", 0, 0);
        register(EntityWitherShulker.class, "wither_shulker", 0, 0);
        register(EntitySpook.class, "spook", 0, 0);
        register(EntitySantaSpider.class, "santa_spider", 16733525, 5635925);
        register(EntityPolarWolf.class, "polar_wolf", 4144959, 4144959);
        registerNoEgg(EntityDeadPolarWolf.class, "deadpolar_wolf");
        register(EntityBlenderman.class, "blender_man", 16755200, 16755200);

        RegisterSpawn();
    }
    
    public static void register(final Class<? extends Entity> EntityClass, final String entityNameIn, final int solidColorIn, final int spotColorIn) {
        EntityRegistry.registerModEntity(new ResourceLocation("mmm:" + entityNameIn), EntityClass, entityNameIn, ++EntityLoader.id, (Object)MainClass.instance, 64, 1, true, solidColorIn, spotColorIn);
    }
    
    public static void registerNoEgg(final Class<? extends Entity> EntityClass, final String entityNameIn) {
        EntityRegistry.registerModEntity(new ResourceLocation("mmm:" + entityNameIn), EntityClass, entityNameIn, ++EntityLoader.id, (Object)MainClass.instance, 64, 1, true);
    }
    
    public static void RegisterSpawn() {
        EntityRegistry.addSpawn(EntitySilverfishCreeper.class, ConfigHandler.M_SilverfishCreeperSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntitySquidCreeper.class, ConfigHandler.M_SquidCreeperSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntitySpiderCreeper.class, ConfigHandler.M_CreeperSpiderSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntityZombieSpider.class, ConfigHandler.M_ZombieSpiderSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntitySpiderWitch.class, ConfigHandler.M_WitchSpiderSpawnRate, ConfigHandler.Min_SpiderMobAmount, ConfigHandler.Max_SpiderMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntityIlliusionerCreeper.class, ConfigHandler.M_IlliusionerCreeperSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntitySlimeCreeper.class, ConfigHandler.M_SlimeCreeperSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.SWAMPLAND, Biomes.MUTATED_SWAMPLAND });
        EntityRegistry.addSpawn(EntityEnderManSkeleton.class, ConfigHandler.M_EndermanSkeletonSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.PLAINS, Biomes.SKY });
        EntityRegistry.addSpawn(EntityEndermanCreeper.class, ConfigHandler.M_EndermanCreeperSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.PLAINS, Biomes.SKY, Biomes.BEACH, Biomes.DESERT, Biomes.FOREST, Biomes.SWAMPLAND, Biomes.HELL, Biomes.EXTREME_HILLS, Biomes.MESA, Biomes.JUNGLE, Biomes.ROOFED_FOREST, Biomes.SAVANNA, Biomes.REDWOOD_TAIGA, Biomes.TAIGA, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntityBlazeSkeleton.class, ConfigHandler.M_BlazeSkeletonSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.HELL });
        EntityRegistry.addSpawn(EntityZombieSpiderPigman.class, ConfigHandler.M_PigmanSpiderSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.HELL });
        EntityRegistry.addSpawn(EntityIlliusionerWitherSkeleton.class, ConfigHandler.M_IlliusionerWitherSkeletonSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.HELL });
        EntityRegistry.addSpawn(EntityCreeperPig.class, ConfigHandler.M_CreeperPigSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntityZombieCreeper.class, ConfigHandler.M_ZombieCreeperSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntityHuskSpider.class, ConfigHandler.M_HuskSpiderSpawnRate, ConfigHandler.Min_SpiderMobAmount, ConfigHandler.Max_SpiderMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.MESA, Biomes.MUSHROOM_ISLAND });
        EntityRegistry.addSpawn(EntityCreepervoker.class, ConfigHandler.M_CreepervokerSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.SWAMPLAND, Biomes.MUTATED_SWAMPLAND });
        EntityRegistry.addSpawn(EntitySquidGhast.class, ConfigHandler.M_SquidGhastSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.OCEAN, Biomes.RIVER, Biomes.DEEP_OCEAN, Biomes.FROZEN_RIVER, Biomes.BEACH });
        EntityRegistry.addSpawn(EntitySpiderGuardian.class, ConfigHandler.M_SpiderGuardianSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.OCEAN, Biomes.RIVER, Biomes.DEEP_OCEAN, Biomes.FROZEN_RIVER, Biomes.BEACH });
        EntityRegistry.addSpawn(EntityElderSpiderGuardian.class, ConfigHandler.M_ElderSpiderGuardianSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.OCEAN, Biomes.RIVER, Biomes.DEEP_OCEAN, Biomes.FROZEN_RIVER, Biomes.BEACH });
        EntityRegistry.addSpawn(EntityWitherShulker.class, ConfigHandler.M_WitherShulkerSpawnRate, 0, 1, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntityShulkerWither.class, ConfigHandler.M_ShulkerWitherSpawnRate, 0, 1, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntitySantaSpider.class, ConfigHandler.M_SantaSpiderSpawnRate, 0, 1, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntitySpook.class, 0, 1, ConfigHandler.Max_GolemAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntityBlazeGolem.class, ConfigHandler.M_BlazeGolemSpawnRate, ConfigHandler.Min_GolemAmount, ConfigHandler.Max_GolemAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntitySlimeGolem.class, ConfigHandler.M_SlimeGolemSpawnRate, ConfigHandler.Min_GolemAmount, ConfigHandler.Max_GolemAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntityCreeperGolem.class, ConfigHandler.M_CreeperGolemSpawnRate, ConfigHandler.Min_GolemAmount, ConfigHandler.Max_GolemAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntityGuardianGolem.class, ConfigHandler.M_GuardianGolemSpawnRate, ConfigHandler.Min_GolemAmount, ConfigHandler.Max_GolemAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntityBlazeSnowman.class, ConfigHandler.M_BlazeSnowmanSpawnRate, ConfigHandler.Min_GolemAmount, ConfigHandler.Max_GolemAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntityEnderSnowman.class, ConfigHandler.M_EnderSnowmanSpawnRate, ConfigHandler.Min_GolemAmount, ConfigHandler.Max_GolemAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntitySquidCreeper.class, ConfigHandler.M_SquidCreeperSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        EntityRegistry.addSpawn(EntityPolarWolf.class, ConfigHandler.M_PolarWolfSpawnRate, 5, 10, EnumCreatureType.MONSTER, new Biome[] { Biomes.ICE_PLAINS, Biomes.ICE_MOUNTAINS, Biomes.COLD_TAIGA, Biomes.COLD_BEACH, Biomes.EXTREME_HILLS, Biomes.TAIGA });
        EntityRegistry.addSpawn(EntityBlazePig.class, ConfigHandler.M_BlazePigSpawnRate, ConfigHandler.Min_MobAmount, ConfigHandler.Max_MobAmount, EnumCreatureType.CREATURE, new Biome[] { Biomes.HELL });
        EntityRegistry.addSpawn(EntitySpiderPig.class, ConfigHandler.M_SpiderPigSpawnRate, ConfigHandler.Min_SpiderMobAmount, ConfigHandler.Max_SpiderMobAmount, EnumCreatureType.CREATURE, new Biome[] { Biomes.EXTREME_HILLS });
        EntityRegistry.addSpawn(EntityShulkerGhast.class, ConfigHandler.M_ShulkerGhastSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.HELL });
        EntityRegistry.addSpawn(EntityIlliusionerGhast.class, ConfigHandler.M_IlliusionerGhastSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.HELL });
        EntityRegistry.addSpawn(EntityGhastShulker.class, ConfigHandler.M_GhastShulkerSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.SKY });
        EntityRegistry.addSpawn(EntityEnderGolem.class, ConfigHandler.M_EnderGolemSpawnRate, ConfigHandler.Min_GolemAmount, ConfigHandler.Max_GolemAmount, EnumCreatureType.CREATURE, new Biome[] { Biomes.SKY, Biomes.PLAINS });
    }
}
