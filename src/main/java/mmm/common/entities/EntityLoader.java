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
    private static int id;
    
    public static void init() {
        if (ConfigHandler.EnableSlimeCreeper) {
            register(EntitySlimeCreeper.class, "SlimeCreeper", 10752, 5635925);
        }
        if (ConfigHandler.EnableZombieSpiderPigman) {
            register(EntityZombieSpiderPigman.class, "ZombieSpiderPigman", 0, 11141120);
        }
        if (ConfigHandler.EnableEnderGolem) {
            register(EntityEnderGolem.class, "EnderGolem", 0, 16777215);
        }
        if (ConfigHandler.EnableBlazeSkeleton) {
            register(EntityBlazeSkeleton.class, "BlazeSkeleton", 2763264, 16755200);
        }
        if (ConfigHandler.EnableEnderSkeleton) {
            register(EntityEnderManSkeleton.class, "EndermanSkeleton", 0, 11141290);
        }
        if (ConfigHandler.EnableShulkerGhast) {
            register(EntityShulkerGhast.class, "ShulkerGhast", 11141290, 16733695);
        }
        if (ConfigHandler.EnableSpiderGuardian) {
            register(EntitySpiderGuardian.class, "SpiderGuardian", 43690, 0);
        }
        if (ConfigHandler.EnableSpiderCreeper) {
            register(EntitySpiderCreeper.class, "SpiderCreeper", 0, 43520);
        }
        if (ConfigHandler.EnableSpiderPig) {
            register(EntitySpiderPig.class, "SpiderPig", 16733695, 16733695);
        }
        if (ConfigHandler.EnableBlazePig) {
            register(EntityBlazePig.class, "BlazePig", 16733695, 16755200);
        }
        if (ConfigHandler.EnableGhastShulker) {
            register(EntityGhastShulker.class, "GhastShulker", 16733695, 11141290);
        }
        if (ConfigHandler.EnableElderSpiderGuardian) {
            register(EntityElderSpiderGuardian.class, "ElderSpiderGuardian", 0, 43690);
        }
        if (ConfigHandler.EnableSpiderWitch) {
            register(EntitySpiderWitch.class, "SpiderWitch", 11141290, 5635925);
        }
        registerNoEgg(EntityShulkerFireball.class, "ShulkerFireball");
        registerNoEgg(EntityCreeperfangs.class, "Creeperfangs");
        registerNoEgg(EntityShulkerSkull.class, "shulker_skull");
        registerNoEgg(EntityWitherBullet.class, "wither_bullet");
        registerNoEgg(EntityEnderBall.class, "ender_ball");
        if (ConfigHandler.EnableSilverfishCreeper) {
            register(EntitySilverfishCreeper.class, "SilverfishCreeper", 11184810, 5592405);
        }
        if (ConfigHandler.EnableIlliusionerCreeper) {
            register(EntityIlliusionerCreeper.class, "IlliusionCreeper", 170, 5592575);
        }
        if (ConfigHandler.EnableIlliusionerWitherSkeleton) {
            register(EntityIlliusionerWitherSkeleton.class, "IlliusionerWitherSkeleton", 170, 5592575);
        }
        if (ConfigHandler.EnableIlliusionerGhast) {
            register(EntityIlliusionerGhast.class, "IlliusionerGhast", 170, 5592575);
        }
        if (ConfigHandler.EnableZombieSpider) {
            register(EntityZombieSpider.class, "spider_zombie", 0, 10752);
        }
        if (ConfigHandler.EnableSlimeGolem) {
            register(EntitySlimeGolem.class, "slime_golem", 10752, 5635925);
        }
        if (ConfigHandler.EnableSquidGhast) {
            register(EntitySquidGhast.class, "squid_ghast", 16777215, 0);
        }
        if (ConfigHandler.EnableCreeperPig) {
            register(EntityCreeperPig.class, "creeper_pig", 16733695, 5635925);
        }
        if (ConfigHandler.EnableCreepervoker) {
            register(EntityCreepervoker.class, "creepervoker", 5635925, 5592405);
        }
        if (ConfigHandler.EnableHuskSpider) {
            register(EntityHuskSpider.class, "husk_spider", 10752, 16777045);
        }
        if (ConfigHandler.EnableZombieCreeper) {
            register(EntityZombieCreeper.class, "zombie_creeper", 10752, 5635925);
        }
        if (ConfigHandler.EnableCreeperGolem) {
            register(EntityCreeperGolem.class, "creeper_golem", 5635925, 16777215);
        }
        if (ConfigHandler.EnableBlazeGolem) {
            register(EntityBlazeGolem.class, "blaze_golem", 16755200, 16777215);
        }
        if (ConfigHandler.EnableGuardianGolem) {
            register(EntityGuardianGolem.class, "guardian_golem", 43520, 16777215);
        }
        if (ConfigHandler.EnableSquidCreeper) {
            register(EntitySquidCreeper.class, "squid_creeper", 0, 170);
        }
        if (ConfigHandler.EnableEnderCreeper) {
            register(EntityEndermanCreeper.class, "enderman_creeper", 11141290, 5635925);
        }
        if (ConfigHandler.EnableEnderSnowman) {
            register(EntityEnderSnowman.class, "ender_snowman", 11141290, 16777215);
        }
        if (ConfigHandler.EnableBlazeSnowman) {
            register(EntityBlazeSnowman.class, "blaze_snowman", 16755200, 16777215);
        }
        if (ConfigHandler.EnableShulkerWither) {
            register(EntityShulkerWither.class, "shulker_wither", 0, 0);
        }
        if (ConfigHandler.EnableWitherShulker) {
            register(EntityWitherShulker.class, "wither_shulker", 0, 0);
        }
        if (ConfigHandler.EnableSpook) {
            register(EntitySpook.class, "spook", 0, 0);
        }
        if (ConfigHandler.EnableSantaSpider) {
            register(EntitySantaSpider.class, "santa_spider", 16733525, 5635925);
        }
        if (ConfigHandler.EnablePolarWolf) {
            register(EntityPolarWolf.class, "polar_wolf", 4144959, 4144959);
        }
        if (ConfigHandler.EnablePolarWolf && ConfigHandler.EnableDeadPolarWolf) {
            registerNoEgg(EntityDeadPolarWolf.class, "deadpolar_wolf");
        }
        if (ConfigHandler.EnableBlenderman) {
            register(EntityBlenderman.class, "blender_man", 16755200, 16755200);
        }
    }
    
    public static void register(final Class<? extends Entity> EntityClass, final String entityNameIn, final int solidColorIn, final int spotColorIn) {
        EntityRegistry.registerModEntity(new ResourceLocation("mmm:" + entityNameIn), EntityClass, entityNameIn, ++EntityLoader.id, (Object)MainClass.instance, 64, 1, true, solidColorIn, spotColorIn);
        RegisterSpawn();
    }
    
    public static void registerNoEgg(final Class<? extends Entity> EntityClass, final String entityNameIn) {
        EntityRegistry.registerModEntity(new ResourceLocation("mmm:" + entityNameIn), EntityClass, entityNameIn, ++EntityLoader.id, (Object)MainClass.instance, 64, 1, true);
    }
    
    public static void RegisterSpawn() {
        if (ConfigHandler.EnableSilverfishCreeper) {
            EntityRegistry.addSpawn(EntitySilverfishCreeper.class, ConfigHandler.M_SilverfishCreeperSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        }
        if (ConfigHandler.EnableSquidCreeper) {
            EntityRegistry.addSpawn(EntitySquidCreeper.class, ConfigHandler.M_SquidCreeperSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        }
        if (ConfigHandler.EnableSpiderCreeper) {
            EntityRegistry.addSpawn(EntitySpiderCreeper.class, ConfigHandler.M_CreeperSpiderSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        }
        if (ConfigHandler.EnableZombieSpider) {
            EntityRegistry.addSpawn(EntityZombieSpider.class, ConfigHandler.M_ZombieSpiderSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        }
        if (ConfigHandler.EnableSpiderWitch) {
            EntityRegistry.addSpawn(EntitySpiderWitch.class, ConfigHandler.M_WitchSpiderSpawnRate, ConfigHandler.Min_SpiderMobAmount, ConfigHandler.Max_SpiderMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        }
        if (ConfigHandler.EnableIlliusionerCreeper) {
            EntityRegistry.addSpawn(EntityIlliusionerCreeper.class, ConfigHandler.M_IlliusionerCreeperSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        }
        if (ConfigHandler.EnableSlimeCreeper) {
            EntityRegistry.addSpawn(EntitySlimeCreeper.class, ConfigHandler.M_SlimeCreeperSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.SWAMPLAND, Biomes.MUTATED_SWAMPLAND });
        }
        if (ConfigHandler.EnableEnderSkeleton) {
            EntityRegistry.addSpawn(EntityEnderManSkeleton.class, ConfigHandler.M_EndermanSkeletonSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.PLAINS, Biomes.SKY });
        }
        if (ConfigHandler.EnableEnderCreeper) {
            EntityRegistry.addSpawn(EntityEndermanCreeper.class, ConfigHandler.M_EndermanCreeperSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.PLAINS, Biomes.SKY, Biomes.BEACH, Biomes.DESERT, Biomes.FOREST, Biomes.SWAMPLAND, Biomes.HELL, Biomes.EXTREME_HILLS, Biomes.MESA, Biomes.JUNGLE, Biomes.ROOFED_FOREST, Biomes.SAVANNA, Biomes.REDWOOD_TAIGA, Biomes.TAIGA, Biomes.STONE_BEACH });
        }
        if (ConfigHandler.EnableBlazeSkeleton) {
            EntityRegistry.addSpawn(EntityBlazeSkeleton.class, ConfigHandler.M_BlazeSkeletonSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.HELL });
        }
        if (ConfigHandler.EnableZombieSpiderPigman) {
            EntityRegistry.addSpawn(EntityZombieSpiderPigman.class, ConfigHandler.M_PigmanSpiderSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.HELL });
        }
        if (ConfigHandler.EnableIlliusionerWitherSkeleton) {
            EntityRegistry.addSpawn(EntityIlliusionerWitherSkeleton.class, ConfigHandler.M_IlliusionerWitherSkeletonSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.HELL });
        }
        if (ConfigHandler.EnableCreeperPig) {
            EntityRegistry.addSpawn(EntityCreeperPig.class, ConfigHandler.M_CreeperPigSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        }
        if (ConfigHandler.EnableZombieCreeper) {
            EntityRegistry.addSpawn(EntityZombieCreeper.class, ConfigHandler.M_ZombieCreeperSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
        }
        if (ConfigHandler.EnableHuskSpider) {
            EntityRegistry.addSpawn(EntityHuskSpider.class, ConfigHandler.M_HuskSpiderSpawnRate, ConfigHandler.Min_SpiderMobAmount, ConfigHandler.Max_SpiderMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.MESA, Biomes.MUSHROOM_ISLAND });
        }
        if (ConfigHandler.EnableCreepervoker) {
            EntityRegistry.addSpawn(EntityCreepervoker.class, ConfigHandler.M_CreepervokerSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.SWAMPLAND, Biomes.MUTATED_SWAMPLAND });
        }
        if (ConfigHandler.EnableSquidGhast) {
            EntityRegistry.addSpawn(EntitySquidGhast.class, ConfigHandler.M_SquidGhastSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.OCEAN, Biomes.RIVER, Biomes.DEEP_OCEAN, Biomes.FROZEN_RIVER, Biomes.BEACH });
        }
        if (ConfigHandler.EnableSpiderGuardian) {
            EntityRegistry.addSpawn(EntitySpiderGuardian.class, ConfigHandler.M_SpiderGuardianSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.OCEAN, Biomes.RIVER, Biomes.DEEP_OCEAN, Biomes.FROZEN_RIVER, Biomes.BEACH });
        }
        if (ConfigHandler.EnableElderSpiderGuardian) {
            EntityRegistry.addSpawn(EntityElderSpiderGuardian.class, ConfigHandler.M_ElderSpiderGuardianSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.OCEAN, Biomes.RIVER, Biomes.DEEP_OCEAN, Biomes.FROZEN_RIVER, Biomes.BEACH });
        }
        if (ConfigHandler.EnableWitherShulker) {
            EntityRegistry.addSpawn(EntityWitherShulker.class, ConfigHandler.M_WitherShulkerSpawnRate, 0, 1, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
            if (ConfigHandler.EnableShulkerWither) {
                EntityRegistry.addSpawn(EntityShulkerWither.class, ConfigHandler.M_ShulkerWitherSpawnRate, 0, 1, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
            }
            if (ConfigHandler.EnableSantaSpider) {
                EntityRegistry.addSpawn(EntitySantaSpider.class, ConfigHandler.M_SantaSpiderSpawnRate, 0, 1, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
            }
            if (ConfigHandler.EnableSpook) {
                EntityRegistry.addSpawn(EntitySpook.class, 0, 1, ConfigHandler.Max_GolemAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
            }
            if (ConfigHandler.EnableBlazeGolem) {
                EntityRegistry.addSpawn(EntityBlazeGolem.class, ConfigHandler.M_BlazeGolemSpawnRate, ConfigHandler.Min_GolemAmount, ConfigHandler.Max_GolemAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
            }
            if (ConfigHandler.EnableSlimeGolem) {
                EntityRegistry.addSpawn(EntitySlimeGolem.class, ConfigHandler.M_SlimeGolemSpawnRate, ConfigHandler.Min_GolemAmount, ConfigHandler.Max_GolemAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
            }
            if (ConfigHandler.EnableCreeperGolem) {
                EntityRegistry.addSpawn(EntityCreeperGolem.class, ConfigHandler.M_CreeperGolemSpawnRate, ConfigHandler.Min_GolemAmount, ConfigHandler.Max_GolemAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
            }
            if (ConfigHandler.EnableGuardianGolem) {
                EntityRegistry.addSpawn(EntityGuardianGolem.class, ConfigHandler.M_GuardianGolemSpawnRate, ConfigHandler.Min_GolemAmount, ConfigHandler.Max_GolemAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
            }
            if (ConfigHandler.EnableBlazeSnowman) {
                EntityRegistry.addSpawn(EntityBlazeSnowman.class, ConfigHandler.M_BlazeSnowmanSpawnRate, ConfigHandler.Min_GolemAmount, ConfigHandler.Max_GolemAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
            }
            if (ConfigHandler.EnableEnderSnowman) {
                EntityRegistry.addSpawn(EntityEnderSnowman.class, ConfigHandler.M_EnderSnowmanSpawnRate, ConfigHandler.Min_GolemAmount, ConfigHandler.Max_GolemAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
            }
            if (ConfigHandler.EnableSquidCreeper) {
                EntityRegistry.addSpawn(EntitySquidCreeper.class, ConfigHandler.M_SquidCreeperSpawnRate, ConfigHandler.Min_CreeperMobAmount, ConfigHandler.Max_CreeperMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.TAIGA, Biomes.MESA, Biomes.PLAINS, Biomes.SWAMPLAND, Biomes.SAVANNA, Biomes.RIVER, Biomes.REDWOOD_TAIGA, Biomes.ROOFED_FOREST, Biomes.STONE_BEACH });
            }
            if (ConfigHandler.EnablePolarWolf) {
                EntityRegistry.addSpawn(EntityPolarWolf.class, ConfigHandler.M_PolarWolfSpawnRate, 5, 10, EnumCreatureType.MONSTER, new Biome[] { Biomes.ICE_PLAINS, Biomes.ICE_MOUNTAINS, Biomes.COLD_TAIGA, Biomes.COLD_BEACH, Biomes.EXTREME_HILLS, Biomes.TAIGA });
            }
            if (ConfigHandler.EnableBlazePig) {
                EntityRegistry.addSpawn(EntityBlazePig.class, ConfigHandler.M_BlazePigSpawnRate, ConfigHandler.Min_MobAmount, ConfigHandler.Max_MobAmount, EnumCreatureType.CREATURE, new Biome[] { Biomes.HELL });
            }
            if (ConfigHandler.EnableSpiderPig) {
                EntityRegistry.addSpawn(EntitySpiderPig.class, ConfigHandler.M_SpiderPigSpawnRate, ConfigHandler.Min_SpiderMobAmount, ConfigHandler.Max_SpiderMobAmount, EnumCreatureType.CREATURE, new Biome[] { Biomes.EXTREME_HILLS });
            }
            if (ConfigHandler.EnableShulkerGhast) {
                EntityRegistry.addSpawn(EntityShulkerGhast.class, ConfigHandler.M_ShulkerGhastSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.HELL });
            }
            if (ConfigHandler.EnableIlliusionerGhast) {
                EntityRegistry.addSpawn(EntityIlliusionerGhast.class, ConfigHandler.M_IlliusionerGhastSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.HELL });
            }
            if (ConfigHandler.EnableGhastShulker) {
                EntityRegistry.addSpawn(EntityGhastShulker.class, ConfigHandler.M_GhastShulkerSpawnRate, ConfigHandler.Min_EvilMobAmount, ConfigHandler.Max_EvilMobAmount, EnumCreatureType.MONSTER, new Biome[] { Biomes.SKY });
            }
            if (ConfigHandler.EnableEnderGolem) {
                EntityRegistry.addSpawn(EntityEnderGolem.class, ConfigHandler.M_EnderGolemSpawnRate, ConfigHandler.Min_GolemAmount, ConfigHandler.Max_GolemAmount, EnumCreatureType.CREATURE, new Biome[] { Biomes.SKY, Biomes.PLAINS });
            }
        }
    }
    
    static {
        EntityLoader.id = 0;
    }
}
