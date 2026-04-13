package net.whateclipse.catalyze_mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.whateclipse.catalyze_mod.items.ModItems;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

        public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
                super(output, registries);
        }

        @Override
        protected void buildRecipes(RecipeOutput recipeOutput) {

                // Netherite Scythe recipe builder

                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.NETHERITE_SCYTHE.get())
                                .pattern("NNS")
                                .pattern(" S ")
                                .pattern("S  ")
                                .define('S', Items.STICK)
                                .define('N', Items.NETHERITE_INGOT)
                                .unlockedBy("has_stick", has(Items.NETHERITE_INGOT)).save(recipeOutput);

                // Netherite Scythe Catalyst recipes builder

                addCatalystRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.BLAZING_CATALYST.get(),
                                "blazing");
                addCatalystRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.FREEZING_CATALYST.get(),
                                "freezing");
                addCatalystRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.BLINDING_CATALYST.get(),
                                "blinding");
                addCatalystRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.VENOMOUS_CATALYST.get(),
                                "venomous");
                addCatalystRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.SERRATED_CATALYST.get(),
                                "serrated");

                catalystSmithing(recipeOutput, ModItems.SPECIAL_TEMPLATE.get(), ModItems.NETHERITE_SCYTHE.get(),
                                ModItems.BLOOD_REAPER_CATALYST.get(), ModItems.NETHERITE_SCYTHE.get(),
                                "blood_reaper_catalyst_netherite_scythe");

                addPiercingRecipe(recipeOutput);
                addHasteRecipes(recipeOutput);
                addCatalystCraftingRecipes(recipeOutput);
        }

        private void addCatalystRecipes(RecipeOutput recipeOutput, net.minecraft.world.item.Item template,
                        net.minecraft.world.item.Item catalyst, String prefix) {
                // Swords
                net.minecraft.world.item.Item[] swords = {
                                Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD,
                                Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD
                };
                for (net.minecraft.world.item.Item weapon : swords) {
                        catalystSmithing(recipeOutput, template, weapon, catalyst, weapon,
                                        prefix + "_" + net.minecraft.core.registries.BuiltInRegistries.ITEM
                                                        .getKey(weapon)
                                                        .getPath());
                }
                // Axes
                net.minecraft.world.item.Item[] axes = {
                                Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE,
                                Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE
                };
                for (net.minecraft.world.item.Item weapon : axes) {
                        catalystSmithing(recipeOutput, template, weapon, catalyst, weapon,
                                        prefix + "_" + net.minecraft.core.registries.BuiltInRegistries.ITEM
                                                        .getKey(weapon)
                                                        .getPath());
                }
                // Netherite Scythe
                catalystSmithing(recipeOutput, template, ModItems.NETHERITE_SCYTHE.get(), catalyst,
                                ModItems.NETHERITE_SCYTHE.get(), prefix + "_netherite_scythe");
                // Trident
                if (catalyst != ModItems.SERRATED_CATALYST.get()) {
                        catalystSmithing(recipeOutput, template, Items.TRIDENT, catalyst, Items.TRIDENT,
                                        prefix + "_trident");
                        // Mace
                        catalystSmithing(recipeOutput, template, Items.MACE, catalyst, Items.MACE, prefix + "_mace");
                }
        }

        // Piercing Catalyst recipes builder (Trident only)

        private void addPiercingRecipe(RecipeOutput recipeOutput) {
                catalystSmithing(recipeOutput, ModItems.SPECIAL_TEMPLATE.get(), Items.TRIDENT,
                                ModItems.PIERCING_CATALYST.get(), Items.TRIDENT, "piercing_catalyst_trident");
        }

        // Haste Catalyst recipes builder (Tools only)

        private void addHasteRecipes(RecipeOutput recipeOutput) {
                net.minecraft.world.item.Item template = ModItems.TOOL_TEMPLATE.get();
                net.minecraft.world.item.Item catalyst = ModItems.HASTE_CATALYST.get();
                String prefix = "haste";

                net.minecraft.world.item.Item[] tools = {
                                Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE,
                                Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE,
                                Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE,
                                Items.NETHERITE_AXE,
                                Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.GOLDEN_SHOVEL,
                                Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL,
                                Items.WOODEN_HOE, Items.STONE_HOE, Items.IRON_HOE, Items.GOLDEN_HOE, Items.DIAMOND_HOE,
                                Items.NETHERITE_HOE
                };

                for (net.minecraft.world.item.Item tool : tools) {
                        catalystSmithing(recipeOutput, template, tool, catalyst, tool,
                                        prefix + "_" + net.minecraft.core.registries.BuiltInRegistries.ITEM
                                                        .getKey(tool)
                                                        .getPath());
                }
        }

        // Catalyst Smithing recipe builder

        private void catalystSmithing(RecipeOutput recipeOutput, net.minecraft.world.item.Item template,
                        net.minecraft.world.item.Item base, net.minecraft.world.item.Item addition,
                        net.minecraft.world.item.Item result, String recipeName) {
                Ingredient templateIng = Ingredient.of(template);
                Ingredient baseIng = Ingredient.of(base);
                Ingredient additionIng = Ingredient.of(addition);
                net.minecraft.world.item.ItemStack resultStack = new net.minecraft.world.item.ItemStack(result);

                net.whateclipse.catalyze_mod.recipes.CatalystSmithingRecipe recipe = new net.whateclipse.catalyze_mod.recipes.CatalystSmithingRecipe(
                                templateIng, baseIng, additionIng, resultStack);

                net.minecraft.resources.ResourceLocation id = net.minecraft.resources.ResourceLocation
                                .fromNamespaceAndPath(net.whateclipse.catalyze_mod.Catalyze_mod.MODID, recipeName);

                recipeOutput.accept(id, recipe, null);

        }

        private void addCatalystCraftingRecipes(RecipeOutput recipeOutput) {
                // Blazing Catalyst
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLAZING_CATALYST.get())
                                .pattern(" # ")
                                .pattern("#!#")
                                .pattern(" # ")
                                .define('#', Items.BLAZE_ROD)
                                .define('!', ModItems.DORMANT_CATALYST.get())
                                .unlockedBy("has_dormant_catalyst", has(ModItems.DORMANT_CATALYST.get()))
                                .save(recipeOutput);

                // Freezing Catalyst
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FREEZING_CATALYST.get())
                                .pattern(" # ")
                                .pattern("#!#")
                                .pattern(" # ")
                                .define('#', Items.BLUE_ICE)
                                .define('!', ModItems.DORMANT_CATALYST.get())
                                .unlockedBy("has_dormant_catalyst", has(ModItems.DORMANT_CATALYST.get()))
                                .save(recipeOutput);

                // Venomous Catalyst
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VENOMOUS_CATALYST.get())
                                .pattern(" # ")
                                .pattern("#!#")
                                .pattern(" # ")
                                .define('#', Items.POISONOUS_POTATO)
                                .define('!', ModItems.DORMANT_CATALYST.get())
                                .unlockedBy("has_dormant_catalyst", has(ModItems.DORMANT_CATALYST.get()))
                                .save(recipeOutput);

                // Blinding Catalyst
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLINDING_CATALYST.get())
                                .pattern(" # ")
                                .pattern("#!#")
                                .pattern(" # ")
                                .define('#', Items.ECHO_SHARD)
                                .define('!', ModItems.DORMANT_CATALYST.get())
                                .unlockedBy("has_dormant_catalyst", has(ModItems.DORMANT_CATALYST.get()))
                                .save(recipeOutput);

                // Haste Catalyst
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HASTE_CATALYST.get())
                                .pattern(" # ")
                                .pattern("#!#")
                                .pattern(" # ")
                                .define('#', Items.GOLDEN_CARROT)
                                .define('!', ModItems.DORMANT_CATALYST.get())
                                .unlockedBy("has_dormant_catalyst", has(ModItems.DORMANT_CATALYST.get()))
                                .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLOOD_REAPER_CATALYST.get())
                                .pattern(" # ")
                                .pattern("-!-")
                                .pattern(" + ")
                                .define('-', Items.ROTTEN_FLESH)
                                .define('!', ModItems.DORMANT_CATALYST.get())
                                .define('#', Items.IRON_INGOT)
                                .define('+', Items.RED_DYE)
                                .unlockedBy("has_dormant_catalyst", has(ModItems.DORMANT_CATALYST.get()))
                                .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SERRATED_CATALYST.get())
                                .pattern(" # ")
                                .pattern("#!#")
                                .pattern(" # ")
                                .define('#', Items.ROTTEN_FLESH)
                                .define('!', ModItems.DORMANT_CATALYST.get())
                                .unlockedBy("has_dormant_catalyst", has(ModItems.DORMANT_CATALYST.get()))
                                .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PIERCING_CATALYST.get())
                                .pattern(" # ")
                                .pattern("-!-")
                                .pattern(" # ")
                                .define('#', Items.PRISMARINE_SHARD)
                                .define('!', ModItems.DORMANT_CATALYST.get())
                                .define('-', Items.PRISMARINE_CRYSTALS)
                                .unlockedBy("has_dormant_catalyst", has(ModItems.DORMANT_CATALYST.get()))
                                .save(recipeOutput);
        }
}
