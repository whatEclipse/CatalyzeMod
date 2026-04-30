package net.whateclipse.tellurite.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.whateclipse.tellurite.Tellurite;
import net.whateclipse.tellurite.network.packet.HasteAbilityPayload;
import net.whateclipse.tellurite.util.ModKeyBindings;

@EventBusSubscriber(modid = Tellurite.MODID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (ModKeyBindings.HASTE_ABILITY_KEY.consumeClick()) {
            PacketDistributor.sendToServer(new HasteAbilityPayload());
        }
    }
}
