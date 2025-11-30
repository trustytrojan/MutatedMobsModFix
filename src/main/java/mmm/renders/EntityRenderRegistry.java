//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.fml.client.registry.*;
import mmm.common.entities.projectiles.*;
import mmm.common.entities.mobs.boss.*;
import mmm.common.entities.mobs.*;

public class EntityRenderRegistry
{
    public static void Load() {
        RenderingRegistry.registerEntityRenderingHandler(EntitySlimeCreeper.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntitySlimeCreeper> createRenderFor(final RenderManager manager) {
                return (Render<? super EntitySlimeCreeper>)new RenderSlimeCreeper(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityIlliusionerCreeper.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityIlliusionerCreeper> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityIlliusionerCreeper>)new RenderIlliusionerCreeper(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieSpiderPigman.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityZombieSpiderPigman> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityZombieSpiderPigman>)new RenderZombieSpiderPigman(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityEnderGolem.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityEnderGolem> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityEnderGolem>)new RenderEnderGolem(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityBlazeSkeleton.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityBlazeSkeleton> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityBlazeSkeleton>)new RenderBlazeSkeleton(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityEnderManSkeleton.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityEnderManSkeleton> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityEnderManSkeleton>)new RenderEndermanSkeleton(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityShulkerGhast.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityShulkerGhast> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityShulkerGhast>)new RenderShulkerGhast(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntitySpiderGuardian.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntitySpiderGuardian> createRenderFor(final RenderManager manager) {
                return (Render<? super EntitySpiderGuardian>)new RenderSpiderGuardian(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntitySpiderCreeper.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntitySpiderCreeper> createRenderFor(final RenderManager manager) {
                return (Render<? super EntitySpiderCreeper>)new RenderCreeperSpider(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntitySpiderPig.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntitySpiderPig> createRenderFor(final RenderManager manager) {
                return (Render<? super EntitySpiderPig>)new RenderSpiderPig(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityBlazePig.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityBlazePig> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityBlazePig>)new RenderBlazePig(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityGhastShulker.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityGhastShulker> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityGhastShulker>)new RenderGhastShulker(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityShulkerFireball.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityShulkerFireball> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityShulkerFireball>)new RenderShulkerFireball(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityElderSpiderGuardian.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityElderSpiderGuardian> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityElderSpiderGuardian>)new RenderElderSpiderGuardian(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntitySpiderWitch.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntitySpiderWitch> createRenderFor(final RenderManager manager) {
                return (Render<? super EntitySpiderWitch>)new RenderSpiderWitch(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntitySilverfishCreeper.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntitySilverfishCreeper> createRenderFor(final RenderManager manager) {
                return (Render<? super EntitySilverfishCreeper>)new RenderSilverfishCreeper(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityIlliusionerWitherSkeleton.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityIlliusionerWitherSkeleton> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityIlliusionerWitherSkeleton>)new RenderIliusionWitherSkeleton(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityIlliusionerGhast.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityIlliusionerGhast> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityIlliusionerGhast>)new RenderIlliusionerGhast(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntitySquidFireball.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntitySquidFireball> createRenderFor(final RenderManager manager) {
                return (Render<? super EntitySquidFireball>)new RenderFakeFireball(manager, 0.0f);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieSpider.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityZombieSpider> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityZombieSpider>)new RenderSpiderZombie(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntitySlimeGolem.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntitySlimeGolem> createRenderFor(final RenderManager manager) {
                return (Render<? super EntitySlimeGolem>)new RenderSlimeGolem(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntitySquidGhast.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntitySquidGhast> createRenderFor(final RenderManager manager) {
                return (Render<? super EntitySquidGhast>)new RenderSquidGhast(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityCreeperPig.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityCreeperPig> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityCreeperPig>)new RenderCreeperPig(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityCreepervoker.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityCreepervoker> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityCreepervoker>)new RenderCreepervoker(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityCreeperfangs.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityCreeperfangs> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityCreeperfangs>)new RenderCreeperFangs(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityShulkerWither.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityShulkerWither> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityShulkerWither>)new RenderShulkerWither(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityShulkerSkull.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityShulkerSkull> createRenderFor(final RenderManager manager) {
                return new RenderShulkerSkull(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityWitherShulker.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityWitherShulker> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityWitherShulker>)new RenderWitherShulker(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityHuskSpider.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityHuskSpider> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityHuskSpider>)new RenderSpiderHusk(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityCreeperGolem.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityCreeperGolem> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityCreeperGolem>)new RenderCreeperGolem(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityBlazeGolem.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityBlazeGolem> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityBlazeGolem>)new RenderBlazeGolem(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityGuardianGolem.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityGuardianGolem> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityGuardianGolem>)new RenderGuardianGolem(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntitySquidCreeper.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntitySquidCreeper> createRenderFor(final RenderManager manager) {
                return (Render<? super EntitySquidCreeper>)new RenderSquidCreeper(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityEndermanCreeper.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityEndermanCreeper> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityEndermanCreeper>)new RenderEndermanCreeper(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieCreeper.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityZombieCreeper> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityZombieCreeper>)new RenderZombieCreeper(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntitySpook.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntitySpook> createRenderFor(final RenderManager manager) {
                return (Render<? super EntitySpook>)new RenderSpook(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityWitherBullet.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityWitherBullet> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityWitherBullet>)new RenderWitherBullet(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityEnderBall.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityEnderBall> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityEnderBall>)new RenderEnderball(manager, 1.0f);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityEnderSnowman.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityEnderSnowman> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityEnderSnowman>)new RenderEnderSnowman(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityBlazeSnowman.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityBlazeSnowman> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityBlazeSnowman>)new RenderBlazeSnowman(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntitySantaSpider.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntitySantaSpider> createRenderFor(final RenderManager manager) {
                return (Render<? super EntitySantaSpider>)new RenderSantaSpider(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityPolarWolf.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityPolarWolf> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityPolarWolf>)new RenderPolarWolf(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityDeadPolarWolf.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityDeadPolarWolf> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityDeadPolarWolf>)new RenderDeadPolarWolf(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityBlenderman.class, (IRenderFactory)new IRenderFactory() {
            public Render<? super EntityBlenderman> createRenderFor(final RenderManager manager) {
                return (Render<? super EntityBlenderman>)new RenderBlenderman(manager);
            }
        });
    }
}
