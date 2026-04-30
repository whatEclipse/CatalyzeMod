package net.whateclipse.tellurite.network;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.whateclipse.tellurite.Tellurite;
import net.whateclipse.tellurite.network.packet.HasteAbilityPayload;

@EventBusSubscriber(modid = Tellurite.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModMessages {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                HasteAbilityPayload.TYPE,
                HasteAbilityPayload.STREAM_CODEC,
                ServerPayloadHandler::handleHasteAbility);
    }
}
