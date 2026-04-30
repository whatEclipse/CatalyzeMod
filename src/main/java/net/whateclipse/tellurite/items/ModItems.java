package net.whateclipse.tellurite.items;

import io.netty.util.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.tellurite.Tellurite;
import net.whateclipse.tellurite.items.weapons.NetheriteScytheItem;

public class ModItems {
        public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Tellurite.MODID);

        public static final DeferredItem<NetheriteScytheItem> NETHERITE_SCYTHE = ITEMS.register("netherite_scythe",
                        NetheriteScytheItem::new);

        // Weapon Telucite Inductors
        public static final DeferredItem<Item> SERRATED_INDUCTOR = ITEMS.register("serrated_inductor",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> BLAZING_INDUCTOR = ITEMS.register("blazing_inductor",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> FREEZING_INDUCTOR = ITEMS.register("freezing_inductor",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> VENOMOUS_INDUCTOR = ITEMS.register("venomous_inductor",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> BLINDING_INDUCTOR = ITEMS.register("blinding_inductor",
                        () -> new Item(new Item.Properties()));

        // Tool Telucite Inductors
        public static final DeferredItem<Item> HASTE_INDUCTOR = ITEMS.register("haste_inductor",
                        () -> new Item(new Item.Properties()));

        // Special Telucite Inductors
        public static final DeferredItem<Item> THROAT_SLIT_INDUCTOR = ITEMS.register("throat_slit_inductor",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> TETHER_INDUCTOR = ITEMS.register("tether_inductor",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> PIERCING_INDUCTOR = ITEMS.register("piercing_inductor",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> SHATTERING_INDUCTOR = ITEMS.register("shattering_inductor",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> BLOOD_REAPER_INDUCTOR = ITEMS.register("blood_reaper_inductor",
                        () -> new Item(new Item.Properties()));

        // Armor Telucite Inductors
        public static final DeferredItem<Item> RESILIENCE_INDUCTOR = ITEMS.register("resilience_inductor",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> PERCEPTION_INDUCTOR = ITEMS.register("perception_inductor",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> TITAN_SKIN_INDUCTOR = ITEMS.register("titan_skin_inductor",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> AGILITY_INDUCTOR = ITEMS.register("agility_inductor",
                        () -> new Item(new Item.Properties()));
        public static final DeferredItem<Item> HEAVY_WEIGHT_INDUCTOR = ITEMS.register("heavy_weight_inductor",
                        () -> new Item(new Item.Properties()));

        // Base Telucite Inductor (use as base item for template)
        public static final DeferredItem<Item> TELUMITE_NODULE = ITEMS.register("telumite_nodule",
                        () -> new Item(new Item.Properties()));

        // Templates (use for specified inductors)

        // Weapon Templates (used for every weapon)
        public static final DeferredItem<Item> COMBAT_TEMPLATE = ITEMS.register("combat_template",
                        () -> new Item(new Item.Properties()));

        // Special Weapon Templates (used for every special weapon)
        public static final DeferredItem<Item> SPECIAL_TEMPLATE = ITEMS.register("special_template",
                        () -> new Item(new Item.Properties()));

        // Armor Templates (used for every armor)
        public static final DeferredItem<Item> ARMOR_TEMPLATE = ITEMS.register("armor_template",
                        () -> new Item(new Item.Properties()));

        // Tool Templates (used for every tool)
        public static final DeferredItem<Item> TOOL_TEMPLATE = ITEMS.register("tool_template",
                        () -> new Item(new Item.Properties()));

        // Debug items
        public static final DeferredItem<Item> BLOOD_PROJECTILE_DEBUG = ITEMS.register("blood_projectile_debug",
                        () -> new net.whateclipse.tellurite.items.custom.BloodProjectileDebugItem(new Item.Properties()));

        public static final DeferredItem<Item> GRAPPLING_HOOK = ITEMS.register("grappling_hook",
                        () -> new net.whateclipse.tellurite.items.custom.GrapplingHookItem(new Item.Properties()));

        public static void register(IEventBus eventBus) {
                ITEMS.register(eventBus);
        }
}
