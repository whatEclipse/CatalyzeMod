package net.whateclipse.catalyze_mod.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.whateclipse.catalyze_mod.Catalyze_mod;
import net.whateclipse.catalyze_mod.client.renderer.BloodProjectileRenderer;
import net.whateclipse.catalyze_mod.entities.ModEntities;

@EventBusSubscriber(modid = Catalyze_mod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BLOOD_PROJECTILE.get(), BloodProjectileRenderer::new);
    }
}
