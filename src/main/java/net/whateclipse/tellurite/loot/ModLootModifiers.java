package net.whateclipse.tellurite.loot;

import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.whateclipse.tellurite.Tellurite;

import java.util.function.Supplier;

/**
 * Registry class for Global Loot Modifier serializers.
 */
public class ModLootModifiers {

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister
            .create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Tellurite.MODID);

    public static final Supplier<MapCodec<AddItemModifier>> ADD_ITEM = LOOT_MODIFIER_SERIALIZERS.register("add_item",
            AddItemModifier.CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
