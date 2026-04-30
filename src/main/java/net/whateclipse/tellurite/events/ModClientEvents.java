package net.whateclipse.tellurite.events;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.whateclipse.tellurite.util.TextUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.whateclipse.tellurite.Tellurite;
import net.whateclipse.tellurite.effects.ModEffects;
import net.whateclipse.tellurite.particles.ModParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import javax.annotation.Nonnull;

@EventBusSubscriber(modid = Tellurite.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.has(DataComponents.CUSTOM_DATA)) {
            CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
            if (customData != null && customData.contains("tellurite")) {
                if (Screen.hasShiftDown()) {
                    net.minecraft.nbt.CompoundTag modTag = customData.copyTag().getCompound("tellurite");
                    for (String key : modTag.getAllKeys()) {
                        if (modTag.getBoolean(key)) {
                            int[] gradientColors = switch (key) {

                                // Telucite Inductor gradient color array

                                case "blazing" -> new int[] { 0xF5B027, 0xF54927, 0xE6B553 };
                                case "freezing" -> new int[] { 0xDFF6FF, 0xC6E7FF, 0xA7DADC };
                                case "venomous" -> new int[] { 0x5B7F2A, 0x6FAE2E, 0x8BCF3F };
                                case "blinding" -> new int[] { 0x1B6F7A, 0x0B3442, 0x071B24 };
                                case "serrated" -> new int[] { 0x747474, 0xA30000, 0x470000 };
                                case "piercing" -> new int[] { 0x00CED1, 0x40E0D0, 0xAFEEEE };
                                case "haste" -> new int[] { 0xFFD700, 0xFFE135, 0xFFF700 };
                                case "blood_reaper" -> new int[] { 0x8A0303, 0x470000, 0x2E0000 };

                                default -> null;
                            };

                            if (gradientColors != null) {
                                String translated = I18n.get("tooltip.tellurite." + key);
                                for (String line : translated.split("\n")) {
                                    event.getToolTip()
                                            .add(TextUtil.createAnimatedGradientComponent(line, gradientColors));
                                }
                            } else {
                                event.getToolTip()
                                        .add(Component.translatable("tooltip.tellurite." + key)
                                                .withStyle(ChatFormatting.RED));
                            }
                        }
                    }
                } else {
                    String translated = I18n.get("tooltip.tellurite.hold_shift");
                    event.getToolTip().add(TextUtil.createAnimatedGradientComponent(translated,
                            0xD6B36A, 0xE4C98A, 0xC2A45D));
                }
            }
        }
    }

    @EventBusSubscriber(modid = Tellurite.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticleTypes.BLOOD_PARTICLE.get(), BloodParticle.Provider::new);
            event.registerSpriteSet(ModParticleTypes.BLOOD_BUBBLE_PARTICLE.get(), BloodBubbleParticle.Provider::new);
        }
    }

    // Blood particle summoner class

    public static class BloodParticle extends TextureSheetParticle {
        protected BloodParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites, double xSpeed,
                double ySpeed, double zSpeed) {
            super(level, x, y, z, xSpeed, ySpeed, zSpeed);
            this.xd = 0.0D;
            this.zd = 0.0D;
            this.yd = ySpeed;
            this.friction = 0.8F;
            this.gravity = 0.5F;
            this.quadSize *= 0.85F;
            this.lifetime = 10 + this.random.nextInt(10);
            this.pickSprite(sprites);
        }

        @Override
        public net.minecraft.client.particle.ParticleRenderType getRenderType() {
            return net.minecraft.client.particle.ParticleRenderType.PARTICLE_SHEET_OPAQUE;
        }

        // Blood particle provider class

        public static class Provider implements ParticleProvider<net.minecraft.core.particles.SimpleParticleType> {
            private final SpriteSet sprites;

            public Provider(SpriteSet sprites) {
                this.sprites = sprites;
            }

            @Override
            @Nonnull
            public Particle createParticle(@Nonnull net.minecraft.core.particles.SimpleParticleType type,
                    @Nonnull ClientLevel level,
                    double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
                return new BloodParticle(level, x, y, z, this.sprites, xSpeed, ySpeed, zSpeed);
            }
        }
    }

    public static class BloodBubbleParticle extends TextureSheetParticle {
        private final SpriteSet sprites;

        protected BloodBubbleParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites, double xSpeed,
                double ySpeed, double zSpeed) {
            super(level, x, y, z, xSpeed, ySpeed, zSpeed);
            this.sprites = sprites;
            this.xd = xSpeed;
            this.yd = ySpeed;
            this.zd = zSpeed;
            this.friction = 0.96F;
            this.gravity = 0.0F;
            this.quadSize = 0.2F + this.random.nextFloat() * 0.15F;
            this.lifetime = 10 + this.random.nextInt(8);
            this.setSpriteFromAge(sprites);
        }

        @Override
        public void tick() {
            super.tick();
            if (!this.removed) {
                this.setSpriteFromAge(this.sprites);
            }
        }

        @Override
        @Nonnull
        public net.minecraft.client.particle.ParticleRenderType getRenderType() {
            return net.minecraft.client.particle.ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
        }

        public static class Provider implements ParticleProvider<net.minecraft.core.particles.SimpleParticleType> {
            private final SpriteSet sprites;

            public Provider(SpriteSet sprites) {
                this.sprites = sprites;
            }

            @Override
            @Nonnull
            public Particle createParticle(@Nonnull net.minecraft.core.particles.SimpleParticleType type,
                    @Nonnull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed,
                    double zSpeed) {
                return new BloodBubbleParticle(level, x, y, z, this.sprites, xSpeed, ySpeed, zSpeed);
            }
        }
    }
}
