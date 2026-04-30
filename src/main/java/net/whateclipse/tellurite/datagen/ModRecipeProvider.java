package net.whateclipse.tellurite.datagen;

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
import net.whateclipse.tellurite.items.ModItems;
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

                // Netherite Scythe Telucite Inductor recipes builder

                addInductorRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.BLAZING_INDUCTOR.get(),
                                "blazing");
                addInductorRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.FREEZING_INDUCTOR.get(),
                                "freezing");
                addInductorRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.BLINDING_INDUCTOR.get(),
                                "blinding");
                addInductorRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.VENOMOUS_INDUCTOR.get(),
                                "venomous");
                addInductorRecipes(recipeOutput, ModItems.COMBAT_TEMPLATE.get(), ModItems.SERRATED_INDUCTOR.get(),
                                "serrated");

                inductorSmithing(recipeOutput, ModItems.SPECIAL_TEMPLATE.get(), ModItems.NETHERITE_SCYTHE.get(),
                                ModItems.BLOOD_REAPER_INDUCTOR.get(), ModItems.NETHERITE_SCYTHE.get(),
                                "blood_reaper_inductor_netherite_scythe");

                addPiercingRecipe(recipeOutput);
                addHasteRecipes(recipeOutput);
                addInductorCraftingRecipes(recipeOutput);
        }

        private void addInductorRecipes(RecipeOutput recipeOutput, net.minecraft.world.item.Item template,
                        net.minecraft.world.item.Item inductor, String prefix) {
                // Swords
                net.minecraft.world.item.Item[] swords = {
                                Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD,
                                Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD
                };
                for (net.minecraft.world.item.Item weapon : swords) {
                        inductorSmithing(recipeOutput, template, weapon, inductor, weapon,
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
                        inductorSmithing(recipeOutput, template, weapon, inductor, weapon,
                                        prefix + "_" + net.minecraft.core.registries.BuiltInRegistries.ITEM
                                                        .getKey(weapon)
                                                        .getPath());
                }
                // Netherite Scythe
                inductorSmithing(recipeOutput, template, ModItems.NETHERITE_SCYTHE.get(), inductor,
                                ModItems.NETHERITE_SCYTHE.get(), prefix + "_netherite_scythe");
                // Trident
                if (inductor != ModItems.SERRATED_INDUCTOR.get()) {
                        inductorSmithing(recipeOutput, template, Items.TRIDENT, inductor, Items.TRIDENT,
                                        prefix + "_trident");
                        // Mace
                        inductorSmithing(recipeOutput, template, Items.MACE, inductor, Items.MACE, prefix + "_mace");
                }
        }

        // Piercing Telucite Inductor recipes builder (Trident only)

        private void addPiercingRecipe(RecipeOutput recipeOutput) {
                inductorSmithing(recipeOutput, ModItems.SPECIAL_TEMPLATE.get(), Items.TRIDENT,
                                ModItems.PIERCING_INDUCTOR.get(), Items.TRIDENT, "piercing_inductor_trident");
        }

        // Haste Telucite Inductor recipes builder (Tools only)

        private void addHasteRecipes(RecipeOutput recipeOutput) {
                net.minecraft.world.item.Item template = ModItems.TOOL_TEMPLATE.get();
                net.minecraft.world.item.Item inductor = ModItems.HASTE_INDUCTOR.get();
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
                        inductorSmithing(recipeOutput, template, tool, inductor, tool,
                                        prefix + "_" + net.minecraft.core.registries.BuiltInRegistries.ITEM
                                                        .getKey(tool)
                                                        .getPath());
                }
        }

        // Telucite Inductor Smithing recipe builder

        private void inductorSmithing(RecipeOutput recipeOutput, net.minecraft.world.item.Item template,
                        net.minecraft.world.item.Item base, net.minecraft.world.item.Item addition,
                        net.minecraft.world.item.Item result, String recipeName) {
                Ingredient templateIng = Ingredient.of(template);
                Ingredient baseIng = Ingredient.of(base);
                Ingredient additionIng = Ingredient.of(addition);
                net.minecraft.world.item.ItemStack resultStack = new net.minecraft.world.item.ItemStack(result);

                net.whateclipse.tellurite.recipes.TelluriteSmithingRecipe recipe = new net.whateclipse.tellurite.recipes.TelluriteSmithingRecipe(
                                templateIng, baseIng, additionIng, resultStack);

                net.minecraft.resources.ResourceLocation id = net.minecraft.resources.ResourceLocation
                                .fromNamespaceAndPath(net.whateclipse.tellurite.Tellurite.MODID, recipeName);

                recipeOutput.accept(id, recipe, null);

        }

        private void addInductorCraftingRecipes(RecipeOutput recipeOutput) {
                // Blazing Telucite Inductor
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLAZING_INDUCTOR.get())
                                .pattern(" # ")
                                .pattern("#!#")
                                .pattern(" # ")
                                .define('#', Items.BLAZE_ROD)
                                .define('!', ModItems.TELUMITE_NODULE.get())
                                .unlockedBy("has_telumite_nodule", has(ModItems.TELUMITE_NODULE.get()))
                                .save(recipeOutput);

                // Freezing Telucite Inductor
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FREEZING_INDUCTOR.get())
                                .pattern(" # ")
                                .pattern("#!#")
                                .pattern(" # ")
                                .define('#', Items.BLUE_ICE)
                                .define('!', ModItems.TELUMITE_NODULE.get())
                                .unlockedBy("has_telumite_nodule", has(ModItems.TELUMITE_NODULE.get()))
                                .save(recipeOutput);

                // Venomous Telucite Inductor
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VENOMOUS_INDUCTOR.get())
                                .pattern(" # ")
                                .pattern("#!#")
                                .pattern(" # ")
                                .define('#', Items.POISONOUS_POTATO)
                                .define('!', ModItems.TELUMITE_NODULE.get())
                                .unlockedBy("has_telumite_nodule", has(ModItems.TELUMITE_NODULE.get()))
                                .save(recipeOutput);

                // Blinding Telucite Inductor
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLINDING_INDUCTOR.get())
                                .pattern(" # ")
                                .pattern("#!#")
                                .pattern(" # ")
                                .define('#', Items.ECHO_SHARD)
                                .define('!', ModItems.TELUMITE_NODULE.get())
                                .unlockedBy("has_telumite_nodule", has(ModItems.TELUMITE_NODULE.get()))
                                .save(recipeOutput);

                // Haste Telucite Inductor
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HASTE_INDUCTOR.get())
                                .pattern(" # ")
                                .pattern("#!#")
                                .pattern(" # ")
                                .define('#', Items.GOLDEN_CARROT)
                                .define('!', ModItems.TELUMITE_NODULE.get())
                                .unlockedBy("has_telumite_nodule", has(ModItems.TELUMITE_NODULE.get()))
                                .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLOOD_REAPER_INDUCTOR.get())
                                .pattern(" # ")
                                .pattern("-!-")
                                .pattern(" + ")
                                .define('-', Items.ROTTEN_FLESH)
                                .define('!', ModItems.TELUMITE_NODULE.get())
                                .define('#', Items.IRON_INGOT)
                                .define('+', Items.RED_DYE)
                                .unlockedBy("has_telumite_nodule", has(ModItems.TELUMITE_NODULE.get()))
                                .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SERRATED_INDUCTOR.get())
                                .pattern(" # ")
                                .pattern("#!#")
                                .pattern(" # ")
                                .define('#', Items.ROTTEN_FLESH)
                                .define('!', ModItems.TELUMITE_NODULE.get())
                                .unlockedBy("has_telumite_nodule", has(ModItems.TELUMITE_NODULE.get()))
                                .save(recipeOutput);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PIERCING_INDUCTOR.get())
                                .pattern(" # ")
                                .pattern("-!-")
                                .pattern(" # ")
                                .define('#', Items.PRISMARINE_SHARD)
                                .define('!', ModItems.TELUMITE_NODULE.get())
                                .define('-', Items.PRISMARINE_CRYSTALS)
                                .unlockedBy("has_telumite_nodule", has(ModItems.TELUMITE_NODULE.get()))
                                .save(recipeOutput);
        }
}

