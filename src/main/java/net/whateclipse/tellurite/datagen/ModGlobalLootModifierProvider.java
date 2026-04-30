package net.whateclipse.tellurite.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.whateclipse.tellurite.Tellurite;
import net.whateclipse.tellurite.items.ModItems;
import net.whateclipse.tellurite.loot.AddItemModifier;

import java.util.concurrent.CompletableFuture;

/**
 * Data generator for Global Loot Modifiers.
 * Adds telumite nodule to ancient city chest loot tables.
 */
public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {

        public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
                super(output, registries, Tellurite.MODID);
        }

        @Override
        protected void start() {
                // Add guaranteed telumite nodule to the ancient city portal chest
                // (city_center)
                add("add_guaranteed_telumite_nodule_to_portal_chest",
                                new AddItemModifier(
                                                new LootItemCondition[] {
                                                                LootTableIdCondition.builder(
                                                                                ResourceLocation.withDefaultNamespace(
                                                                                                "chests/ancient_city_city_center"))
                                                                                .build()
                                                },
                                                ModItems.TELUMITE_NODULE.get(),
                                                1));

                // Add telumite nodule to other ancient city chests with a 15% chance
                add("add_telumite_nodule_to_ancient_city",
                                new AddItemModifier(
                                                new LootItemCondition[] {
                                                                // Only apply to ancient city chest loot table
                                                                LootTableIdCondition.builder(
                                                                                ResourceLocation.withDefaultNamespace(
                                                                                                "chests/ancient_city"))
                                                                                .build(),
                                                                // 15% chance as requested
                                                                net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition
                                                                                .randomChance(0.15f).build()
                                                },
                                                ModItems.TELUMITE_NODULE.get(),
                                                1));
        }
}
