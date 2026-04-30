package net.whateclipse.tellurite.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class TelluriteSmithingRecipeSerializer implements RecipeSerializer<TelluriteSmithingRecipe> {
    public static final MapCodec<TelluriteSmithingRecipe> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Ingredient.CODEC.fieldOf("template").forGetter(TelluriteSmithingRecipe::getTemplate),
                    Ingredient.CODEC.fieldOf("base").forGetter(TelluriteSmithingRecipe::getBase),
                    Ingredient.CODEC.fieldOf("addition").forGetter(TelluriteSmithingRecipe::getAddition),
                    ItemStack.STRICT_CODEC.fieldOf("result").forGetter(TelluriteSmithingRecipe::getResult))
                    .apply(instance, TelluriteSmithingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, TelluriteSmithingRecipe> STREAM_CODEC = StreamCodec
            .composite(
                    Ingredient.CONTENTS_STREAM_CODEC, TelluriteSmithingRecipe::getTemplate,
                    Ingredient.CONTENTS_STREAM_CODEC, TelluriteSmithingRecipe::getBase,
                    Ingredient.CONTENTS_STREAM_CODEC, TelluriteSmithingRecipe::getAddition,
                    ItemStack.STREAM_CODEC, TelluriteSmithingRecipe::getResult,
                    TelluriteSmithingRecipe::new);

    @Override
    public MapCodec<TelluriteSmithingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, TelluriteSmithingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
