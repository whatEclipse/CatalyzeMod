package net.whateclipse.tellurite.sounds;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.tellurite.Tellurite;

public class ModSounds {
    @SuppressWarnings("null")
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, Tellurite.MODID);

    /**
     * To add a sound:
     * 1. Register it here using registerSoundEvent("your_sound_name")
     * 2. Add an entry in src/main/resources/assets/tellurite/sounds.json
     * 3. Place the .ogg file in src/main/resources/assets/tellurite/sounds/your_sound_name.ogg
     */
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOOD_PROJECTILE_HIT = registerSoundEvent("blood_projectile_hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLOOD_SPEWING = registerSoundEvent("blood_spewing");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        @SuppressWarnings("null")
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Tellurite.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(@SuppressWarnings("null") IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
