package net.whateclipse.tellurite.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.whateclipse.tellurite.Tellurite;
import net.whateclipse.tellurite.client.renderer.BloodProjectileRenderer;
import net.whateclipse.tellurite.entities.ModEntities;

@EventBusSubscriber(modid = Tellurite.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BLOOD_PROJECTILE.get(), BloodProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.GRAPPLING_HOOK.get(), net.whateclipse.tellurite.client.renderer.GrapplingHookRenderer::new);
    }
}
