//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "G:\t\MutatedMobsModFix\Minecraft-Deobfuscator3000\1.12 stable mappings"!

//Decompiled by Procyon!

package mmm.renders;

import net.minecraftforge.fml.client.registry.*;
import mmm.common.entities.projectiles.*;
import mmm.common.entities.mobs.boss.*;
import mmm.common.entities.mobs.*;
import net.minecraft.entity.Entity;

public class EntityRenderRegistry
{
    private static <T extends Entity> void reg(final Class<T> entityClass, final IRenderFactory<? super T> factory) {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, factory);
    }

    public static void Load() {
        reg(EntitySlimeCreeper.class, RenderSlimeCreeper::new);
        reg(EntityIlliusionerCreeper.class, RenderIlliusionerCreeper::new);
        reg(EntityZombieSpiderPigman.class, RenderZombieSpiderPigman::new);
        reg(EntityEnderGolem.class, RenderEnderGolem::new);
        reg(EntityBlazeSkeleton.class, RenderBlazeSkeleton::new);
        reg(EntityEnderManSkeleton.class, RenderEndermanSkeleton::new);
        reg(EntityShulkerGhast.class, RenderShulkerGhast::new);
        reg(EntitySpiderGuardian.class, RenderSpiderGuardian::new);
        reg(EntitySpiderCreeper.class, RenderCreeperSpider::new);
        reg(EntitySpiderPig.class, RenderSpiderPig::new);
        reg(EntityBlazePig.class, RenderBlazePig::new);
        reg(EntityGhastShulker.class, RenderGhastShulker::new);
        reg(EntityShulkerFireball.class, RenderShulkerFireball::new);
        reg(EntityElderSpiderGuardian.class, RenderElderSpiderGuardian::new);
        reg(EntitySpiderWitch.class, RenderSpiderWitch::new);
        reg(EntitySilverfishCreeper.class, RenderSilverfishCreeper::new);
        reg(EntityIlliusionerWitherSkeleton.class, RenderIliusionWitherSkeleton::new);
        reg(EntityIlliusionerGhast.class, RenderIlliusionerGhast::new);
        reg(EntitySquidFireball.class, manager -> new RenderFakeFireball(manager, 0.0f));
        reg(EntityZombieSpider.class, RenderSpiderZombie::new);
        reg(EntitySlimeGolem.class, RenderSlimeGolem::new);
        reg(EntitySquidGhast.class, RenderSquidGhast::new);
        reg(EntityCreeperPig.class, RenderCreeperPig::new);
        reg(EntityCreepervoker.class, RenderCreepervoker::new);
        reg(EntityCreeperfangs.class, RenderCreeperFangs::new);
        reg(EntityShulkerWither.class, RenderShulkerWither::new);
        reg(EntityShulkerSkull.class, RenderShulkerSkull::new);
        reg(EntityWitherShulker.class, RenderWitherShulker::new);
        reg(EntityHuskSpider.class, RenderSpiderHusk::new);
        reg(EntityCreeperGolem.class, RenderCreeperGolem::new);
        reg(EntityBlazeGolem.class, RenderBlazeGolem::new);
        reg(EntityGuardianGolem.class, RenderGuardianGolem::new);
        reg(EntitySquidCreeper.class, RenderSquidCreeper::new);
        reg(EntityEndermanCreeper.class, RenderEndermanCreeper::new);
        reg(EntityZombieCreeper.class, RenderZombieCreeper::new);
        reg(EntitySpook.class, RenderSpook::new);
        reg(EntityWitherBullet.class, RenderWitherBullet::new);
        reg(EntityEnderBall.class, manager -> new RenderEnderball(manager, 1.0f));
        reg(EntityEnderSnowman.class, RenderEnderSnowman::new);
        reg(EntityBlazeSnowman.class, RenderBlazeSnowman::new);
        reg(EntitySantaSpider.class, RenderSantaSpider::new);
        reg(EntityPolarWolf.class, RenderPolarWolf::new);
        reg(EntityDeadPolarWolf.class, RenderDeadPolarWolf::new);
        reg(EntityBlenderman.class, RenderBlenderman::new);
    }
}
