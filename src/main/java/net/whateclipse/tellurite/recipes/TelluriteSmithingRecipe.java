package net.whateclipse.tellurite.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.whateclipse.tellurite.items.ModItems;
import net.minecraft.world.item.component.CustomData;

public class TelluriteSmithingRecipe extends SmithingTransformRecipe {
    final Ingredient template;
    final Ingredient base;
    final Ingredient addition;
    final ItemStack result;

    public TelluriteSmithingRecipe(Ingredient template, Ingredient base, Ingredient addition, ItemStack result) {
        super(template, base, addition, result);
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public Ingredient getTemplate() {
        return template;
    }

    public Ingredient getBase() {
        return base;
    }

    public Ingredient getAddition() {
        return addition;
    }

    public ItemStack getResult() {
        return result;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider registries) {
        ItemStack base = input.base();
        ItemStack addition = input.addition();
        ItemStack template = input.template();

        // Check if logic matches
        if (this.isTemplateIngredient(template) && this.isBaseIngredient(base) && this.isAdditionIngredient(addition)) {
            String tagKey = null;
            if (addition.is(ModItems.BLAZING_INDUCTOR.get()))
                tagKey = "blazing";
            else if (addition.is(ModItems.FREEZING_INDUCTOR.get()))
                tagKey = "freezing";
            else if (addition.is(ModItems.BLINDING_INDUCTOR.get()))
                tagKey = "blinding";
            else if (addition.is(ModItems.VENOMOUS_INDUCTOR.get()))
                tagKey = "venomous";
            else if (addition.is(ModItems.SERRATED_INDUCTOR.get()))
                tagKey = "serrated";
            else if (addition.is(ModItems.BLOOD_REAPER_INDUCTOR.get()))
                tagKey = "blood_reaper";
            else if (addition.is(ModItems.PIERCING_INDUCTOR.get()))
                tagKey = "piercing";
            else if (addition.is(ModItems.HASTE_INDUCTOR.get()))
                tagKey = "haste";

            if (tagKey != null) {
                ItemStack resultStack = base.copy();

                // Add the custom data
                CompoundTag modTag = new CompoundTag();
                modTag.putBoolean(tagKey, true);

                CompoundTag rootTag = new CompoundTag();
                rootTag.put("tellurite", modTag);

                // Overwrite existing inductor data by removing it first
                CustomData existing = resultStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
                resultStack.set(DataComponents.CUSTOM_DATA, existing.update(tag -> {
                    tag.remove("tellurite");
                    tag.merge(rootTag);
                }));

                return resultStack;
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.INDUCTOR_SMITHING_SERIALIZER.get();
    }
}
