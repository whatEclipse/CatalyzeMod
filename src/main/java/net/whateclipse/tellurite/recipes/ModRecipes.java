package net.whateclipse.tellurite.recipes;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.tellurite.Tellurite;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, Tellurite.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, TelluriteSmithingRecipeSerializer> INDUCTOR_SMITHING_SERIALIZER = RECIPE_SERIALIZERS
            .register("inductor_smithing", TelluriteSmithingRecipeSerializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
