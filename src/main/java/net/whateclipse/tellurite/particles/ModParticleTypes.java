package net.whateclipse.tellurite.particles;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.tellurite.Tellurite;

public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister
            .create(Registries.PARTICLE_TYPE, Tellurite.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLOOD_PARTICLE = PARTICLE_TYPES
            .register("blood_particle", () -> new SimpleParticleType(false));
            
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLOOD_BUBBLE_PARTICLE = PARTICLE_TYPES
            .register("blood_bubble_particle", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
