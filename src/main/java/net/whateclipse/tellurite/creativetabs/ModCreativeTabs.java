package net.whateclipse.tellurite.creativetabs;

import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.tellurite.Tellurite;
import net.whateclipse.tellurite.items.ModItems;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, Tellurite.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TELLURITE_TAB = CREATIVE_MODE_TABS
            .register("tellurite_tab", () -> CreativeModeTab.builder()
                    .title(net.minecraft.network.chat.Component.translatable("creativetab.tellurite.tellurite_tab"))
                    .withTabsBefore(net.minecraft.world.item.CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.NETHERITE_SCYTHE.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.NETHERITE_SCYTHE.get());
                        output.accept(ModItems.SERRATED_INDUCTOR.get());
                        output.accept(ModItems.BLAZING_INDUCTOR.get());
                        output.accept(ModItems.FREEZING_INDUCTOR.get());
                        output.accept(ModItems.VENOMOUS_INDUCTOR.get());
                        output.accept(ModItems.BLINDING_INDUCTOR.get());
                        output.accept(ModItems.HASTE_INDUCTOR.get());
                        output.accept(ModItems.THROAT_SLIT_INDUCTOR.get());
                        output.accept(ModItems.TETHER_INDUCTOR.get());
                        output.accept(ModItems.PIERCING_INDUCTOR.get());
                        output.accept(ModItems.SHATTERING_INDUCTOR.get());
                        output.accept(ModItems.BLOOD_REAPER_INDUCTOR.get());
                        output.accept(ModItems.RESILIENCE_INDUCTOR.get());
                        output.accept(ModItems.PERCEPTION_INDUCTOR.get());
                        output.accept(ModItems.TITAN_SKIN_INDUCTOR.get());
                        output.accept(ModItems.AGILITY_INDUCTOR.get());
                        output.accept(ModItems.HEAVY_WEIGHT_INDUCTOR.get());
                        output.accept(ModItems.TELUMITE_NODULE.get());
                        output.accept(ModItems.COMBAT_TEMPLATE.get());
                        output.accept(ModItems.SPECIAL_TEMPLATE.get());
                        output.accept(ModItems.ARMOR_TEMPLATE.get());
                        output.accept(ModItems.TOOL_TEMPLATE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}