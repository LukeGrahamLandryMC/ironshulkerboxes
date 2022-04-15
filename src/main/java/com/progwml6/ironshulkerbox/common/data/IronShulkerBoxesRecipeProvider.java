package com.progwml6.ironshulkerbox.common.data;

import com.progwml6.ironshulkerbox.IronShulkerBoxes;
import com.progwml6.ironshulkerbox.common.boxes.UpgradableBoxTier;
import com.progwml6.ironshulkerbox.common.items.BoxUpgradeType;
import com.progwml6.ironshulkerbox.common.items.IronShulkerBoxesItems;
import com.progwml6.ironshulkerbox.common.recipes.IronShulkerBoxRecipeBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;

import java.util.Objects;
import java.util.function.Consumer;

public class IronShulkerBoxesRecipeProvider extends RecipeProvider implements IConditionBuilder {
  private static final TagKey<Item> SILVER_TAG = ItemTags.create(new ResourceLocation("forge", "ingots/silver"));
  
  public IronShulkerBoxesRecipeProvider(DataGenerator generatorIn) {
    super(generatorIn);
  }
  
  @Override
  protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
    for (DyeColor dyeColor : DyeColor.values()){
      String color = dyeColor.getName() + "/";
      String group = "ironshulkerbox:" + dyeColor.getName() + "_shulker_box";
      
      this.registerSilverBoxRecipe(consumer, getFor(UpgradableBoxTier.SILVER, dyeColor), getFor(UpgradableBoxTier.COPPER, dyeColor), getFor(UpgradableBoxTier.IRON, dyeColor), color, group);
      this.registerCopperBoxRecipe(consumer, getFor(UpgradableBoxTier.COPPER, dyeColor), ShulkerBoxBlock.getBlockByColor(dyeColor).asItem(), color, group);
      this.registerIronBoxRecipe(consumer, getFor(UpgradableBoxTier.IRON, dyeColor), getFor(UpgradableBoxTier.COPPER, dyeColor), ShulkerBoxBlock.getBlockByColor(dyeColor).asItem(), color, group);
      this.registerGoldBoxRecipe(consumer, getFor(UpgradableBoxTier.GOLD, dyeColor), getFor(UpgradableBoxTier.IRON, dyeColor), getFor(UpgradableBoxTier.SILVER, dyeColor), color, group);
      this.registerDiamondBoxRecipe(consumer, getFor(UpgradableBoxTier.DIAMOND, dyeColor), getFor(UpgradableBoxTier.GOLD, dyeColor), getFor(UpgradableBoxTier.SILVER, dyeColor), color, group);
      this.registerCrystalBoxRecipe(consumer, getFor(UpgradableBoxTier.CRYSTAL, dyeColor), getFor(UpgradableBoxTier.DIAMOND, dyeColor), color, group);
      this.registerObsidianBoxRecipe(consumer, getFor(UpgradableBoxTier.OBSIDIAN, dyeColor), getFor(UpgradableBoxTier.DIAMOND, dyeColor), color, group);
      
    }
    
    this.addUpgradesRecipes(consumer);
  }
  
  private Block getFor(UpgradableBoxTier tier, DyeColor color){
    return tier.blocks.get(color).get();
  }

  private void addUpgradesRecipes(Consumer<FinishedRecipe> consumer) {
    String folder = "upgrades/";

    ShapedRecipeBuilder
      .shaped(IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.VANILLA_TO_IRON).get())
      .define('M', Tags.Items.INGOTS_IRON)
      .define('S', Items.SHULKER_SHELL)
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
      .save(consumer,
        prefix(IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.VANILLA_TO_IRON).get(), folder));

    ShapedRecipeBuilder.shaped(IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.IRON_TO_GOLD).get())
      .define('S', Tags.Items.INGOTS_IRON)
      .define('M', Tags.Items.INGOTS_GOLD)
      .pattern("MSM")
      .pattern("MMM")
      .pattern("MMM")
      .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
      .save(consumer,
        prefix(IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.IRON_TO_GOLD).get(), folder));

    ShapedRecipeBuilder
      .shaped(IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.GOLD_TO_DIAMOND).get())
      .define('M', Tags.Items.GEMS_DIAMOND)
      .define('S', Tags.Items.INGOTS_GOLD)
      .define('G', Tags.Items.GLASS)
      .pattern("GMG")
      .pattern("GSG")
      .pattern("GMG")
      .unlockedBy("has_glass", has(Tags.Items.GLASS))
      .save(consumer,
        prefix(IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.GOLD_TO_DIAMOND).get(), folder));

    ShapedRecipeBuilder
      .shaped(IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.DIAMOND_TO_OBSIDIAN).get())
      .define('M', Blocks.OBSIDIAN)
      .define('G', Tags.Items.GLASS)
      .pattern("MGM")
      .pattern("MMM")
      .pattern("MMM")
      .unlockedBy("has_glass", has(Tags.Items.GLASS))
      .save(consumer,
        prefix(IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.DIAMOND_TO_OBSIDIAN).get(), folder));

    ShapedRecipeBuilder
      .shaped(IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.DIAMOND_TO_CRYSTAL).get())
      .define('S', Blocks.OBSIDIAN)
      .define('G', Tags.Items.GLASS)
      .pattern("GSG")
      .pattern("GGG")
      .pattern("GGG")
      .unlockedBy("has_glass", has(Tags.Items.GLASS))
      .save(consumer,
        prefix(IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.DIAMOND_TO_CRYSTAL).get(), folder));

    ResourceLocation woodToCopperChestUpgradeId = prefix(
      IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.VANILLA_TO_COPPER).get(), folder);
    ConditionalRecipe.builder()
      .addCondition(not(new TagEmptyCondition("forge:ingots/copper")))
      .addRecipe(ShapedRecipeBuilder
        .shaped(IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.VANILLA_TO_COPPER).get())
        .define('M', Tags.Items.INGOTS_COPPER)
        .define('S', Items.SHULKER_SHELL)
        .pattern("MMM")
        .pattern("MSM")
        .pattern("MMM")
        .unlockedBy("has_item", has(Items.SHULKER_SHELL))::save)
      .setAdvancement(location("recipes/ironshulkerbox/upgrades/vanilla_to_copper_shulker_box_upgrade"),
        ConditionalAdvancement.builder()
          .addCondition(not(new TagEmptyCondition("forge:ingots/copper")))
          .addAdvancement(Advancement.Builder.advancement()
            .parent(new ResourceLocation("recipes/root"))
            .rewards(AdvancementRewards.Builder.recipe(woodToCopperChestUpgradeId))
            .addCriterion("has_item", has(Items.SHULKER_SHELL))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(woodToCopperChestUpgradeId))
            .requirements(RequirementsStrategy.OR))
      ).build(consumer, woodToCopperChestUpgradeId);

    ResourceLocation copperToIronChestUpgrade = prefix(
      IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.COPPER_TO_IRON).get(), folder);
    ConditionalRecipe.builder()
      .addCondition(not(new TagEmptyCondition("forge:ingots/copper")))
      .addRecipe(ShapedRecipeBuilder
        .shaped(IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.COPPER_TO_IRON).get())
        .define('M', Tags.Items.INGOTS_IRON)
        .define('S', Tags.Items.INGOTS_COPPER)
        .define('G', Tags.Items.GLASS)
        .pattern("GGG")
        .pattern("MSM")
        .pattern("MGM")
        .unlockedBy("has_item", has(ItemTags.PLANKS))::save)
      .setAdvancement(location("recipes/ironshulkerbox/upgrades/copper_to_iron_shulker_box_upgrade"),
        ConditionalAdvancement.builder()
          .addCondition(not(new TagEmptyCondition("forge:ingots/copper")))
          .addAdvancement(Advancement.Builder.advancement()
            .parent(new ResourceLocation("recipes/root"))
            .rewards(AdvancementRewards.Builder.recipe(copperToIronChestUpgrade))
            .addCriterion("has_item", has(Tags.Items.GLASS))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(copperToIronChestUpgrade))
            .requirements(RequirementsStrategy.OR))
      ).build(consumer, copperToIronChestUpgrade);

    ResourceLocation copperToSilverChestUpgrade = prefix(
      IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.COPPER_TO_SILVER).get(), folder);
    ConditionalRecipe.builder()
      .addCondition(
        and(not(new TagEmptyCondition("forge:ingots/copper")), not(new TagEmptyCondition("forge:ingots/silver"))))
      .addRecipe(ShapedRecipeBuilder
        .shaped(IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.COPPER_TO_SILVER).get())
        .define('M', SILVER_TAG)
        .define('S', Tags.Items.INGOTS_COPPER)
        .pattern("MSM")
        .pattern("MMM")
        .pattern("MMM")
        .unlockedBy("has_item", has(Tags.Items.INGOTS_COPPER))::save)
      .setAdvancement(location("recipes/ironshulkerbox/upgrades/copper_to_silver_shulker_box_upgrade"),
        ConditionalAdvancement.builder()
          .addCondition(
            and(not(new TagEmptyCondition("forge:ingots/copper")), not(new TagEmptyCondition("forge:ingots/silver"))))
          .addAdvancement(Advancement.Builder.advancement()
            .parent(new ResourceLocation("recipes/root"))
            .rewards(AdvancementRewards.Builder.recipe(copperToSilverChestUpgrade))
            .addCriterion("has_item", has(Tags.Items.INGOTS_COPPER))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(copperToSilverChestUpgrade))
            .requirements(RequirementsStrategy.OR))
      ).build(consumer, copperToSilverChestUpgrade);

    ResourceLocation silverToGoldChestUpgrade = prefix(
      IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.SILVER_TO_GOLD).get(), folder);
    ConditionalRecipe.builder()
      .addCondition(not(new TagEmptyCondition("forge:ingots/silver")))
      .addRecipe(ShapedRecipeBuilder
        .shaped(IronShulkerBoxesItems.UPGRADES.get(BoxUpgradeType.SILVER_TO_GOLD).get())
        .define('M', Tags.Items.INGOTS_GOLD)
        .define('S', Tags.Items.INGOTS_COPPER)
        .define('G', Tags.Items.GLASS)
        .pattern("MSM")
        .pattern("GGG")
        .pattern("MGM")
        .unlockedBy("has_item", has(Tags.Items.GLASS))::save)
      .setAdvancement(location("recipes/ironshulkerbox/upgrades/silver_to_gold_shulker_box_upgrade"),
        ConditionalAdvancement.builder()
          .addCondition(not(new TagEmptyCondition("forge:ingots/silver")))
          .addAdvancement(Advancement.Builder.advancement()
            .parent(new ResourceLocation("recipes/root"))
            .rewards(AdvancementRewards.Builder.recipe(silverToGoldChestUpgrade))
            .addCriterion("has_item", has(Tags.Items.GLASS))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(silverToGoldChestUpgrade))
            .requirements(RequirementsStrategy.OR))
      ).build(consumer, silverToGoldChestUpgrade);
  }

  protected static ResourceLocation prefix(ItemLike item, String prefix) {
    ResourceLocation loc = Objects.requireNonNull(item.asItem().getRegistryName());
    return location(prefix + loc.getPath());
  }

  private static ResourceLocation location(String id) {
    return new ResourceLocation(IronShulkerBoxes.MOD_ID, id);
    
  }

  private void registerCopperBoxRecipe(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike input, String color, String group) {
    ResourceLocation vanillaToCopperShulkerBox = location("shulkerboxes/" + color + "copper/vanilla_copper_shulker_box");
    ConditionalRecipe.builder()
      .addCondition(not(new TagEmptyCondition("forge:ingots/copper")))
      .addRecipe(IronShulkerBoxRecipeBuilder.shapedRecipe(result)
        .setGroup(group)
        .key('M', Tags.Items.INGOTS_COPPER)
        .key('S', input)
        .patternLine("MMM")
        .patternLine("MSM")
        .patternLine("MMM")
        .addCriterion("has_item", has(Tags.Items.INGOTS_COPPER))::build)
      .setAdvancement(location("recipes/ironshulkerbox/shulkerboxes/" + color + "copper/vanilla_copper_shulker_box"),
        ConditionalAdvancement.builder()
          .addCondition(not(new TagEmptyCondition("forge:ingots/copper")))
          .addAdvancement(Advancement.Builder.advancement()
            .parent(new ResourceLocation("recipes/root"))
            .rewards(AdvancementRewards.Builder.recipe(vanillaToCopperShulkerBox))
            .addCriterion("has_item", has(Tags.Items.INGOTS_COPPER))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(vanillaToCopperShulkerBox))
            .requirements(RequirementsStrategy.OR))
      ).build(consumer, vanillaToCopperShulkerBox);
  }

  private void registerSilverBoxRecipe(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike input, ItemLike inputTwo, String color, String group) {
    ResourceLocation copperToSilverShulkerBox = location("shulkerboxes/" + color + "silver/copper_silver_shulker_box");
    ConditionalRecipe.builder()
      .addCondition(not(new TagEmptyCondition("forge:ingots/copper")))
      .addRecipe(IronShulkerBoxRecipeBuilder.shapedRecipe(result)
        .setGroup(group)
        .key('M', SILVER_TAG)
        .key('S', input)
        .patternLine("MMM")
        .patternLine("MSM")
        .patternLine("MMM")
        .addCriterion("has_item", has(SILVER_TAG))::build)
      .setAdvancement(location("recipes/ironshulkerbox/shulkerboxes/" + color + "silver/copper_silver_shulker_box"),
        ConditionalAdvancement.builder()
          .addCondition(not(new TagEmptyCondition("forge:ingots/silver")))
          .addAdvancement(Advancement.Builder.advancement()
            .parent(new ResourceLocation("recipes/root"))
            .rewards(AdvancementRewards.Builder.recipe(copperToSilverShulkerBox))
            .addCriterion("has_item", has(SILVER_TAG))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(copperToSilverShulkerBox))
            .requirements(RequirementsStrategy.OR))
      ).build(consumer, copperToSilverShulkerBox);

    ResourceLocation ironToSilverShulkerBox = location("shulkerboxes/" + color + "silver/iron_silver_shulker_box");
    ConditionalRecipe.builder()
      .addCondition(not(new TagEmptyCondition("forge:ingots/copper")))
      .addRecipe(IronShulkerBoxRecipeBuilder.shapedRecipe(result)
        .setGroup(group)
        .key('M', SILVER_TAG)
        .key('S', inputTwo)
        .key('G', Tags.Items.GLASS)
        .patternLine("MGM")
        .patternLine("GSG")
        .patternLine("MGM")
        .addCriterion("has_item", has(SILVER_TAG))::build)
      .setAdvancement(location("recipes/ironshulkerbox/shulkerboxes/" + color + "silver/iron_silver_shulker_box"),
        ConditionalAdvancement.builder()
          .addCondition(not(new TagEmptyCondition("forge:ingots/silver")))
          .addAdvancement(Advancement.Builder.advancement()
            .parent(new ResourceLocation("recipes/root"))
            .rewards(AdvancementRewards.Builder.recipe(ironToSilverShulkerBox))
            .addCriterion("has_item", has(SILVER_TAG))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(ironToSilverShulkerBox))
            .requirements(RequirementsStrategy.OR))
      ).build(consumer, ironToSilverShulkerBox);
  }

  private void registerIronBoxRecipe(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike input, ItemLike inputTwo, String color, String group) {
    IronShulkerBoxRecipeBuilder.shapedRecipe(result)
      .setGroup(group)
      .key('G', Tags.Items.GLASS)
      .key('S', input)
      .key('M', Tags.Items.INGOTS_IRON)
      .patternLine("MGM")
      .patternLine("GSG")
      .patternLine("MGM")
      .addCriterion("has_gold", has(Tags.Items.INGOTS_IRON))
      .build(consumer, location("shulkerboxes/" + color + "iron/copper_iron_shulker_box"));

    IronShulkerBoxRecipeBuilder.shapedRecipe(result)
      .setGroup(group)
      .key('S', inputTwo)
      .key('M', Tags.Items.INGOTS_IRON)
      .patternLine("MMM")
      .patternLine("MSM")
      .patternLine("MMM")
      .addCriterion("has_gold", has(Tags.Items.INGOTS_GOLD))
      .build(consumer, location("shulkerboxes/" + color + "iron/vanilla_iron_shulker_box"));
  }

  private void registerGoldBoxRecipe(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike input, ItemLike inputTwo, String color, String group) {
    IronShulkerBoxRecipeBuilder.shapedRecipe(result)
      .setGroup(group)
      .key('S', input)
      .key('M', Tags.Items.INGOTS_GOLD)
      .patternLine("MMM")
      .patternLine("MSM")
      .patternLine("MMM")
      .addCriterion("has_gold", has(Tags.Items.INGOTS_GOLD))
      .build(consumer, location("shulkerboxes/" + color + "gold/iron_gold_shulker_box"));

    IronShulkerBoxRecipeBuilder.shapedRecipe(result)
      .setGroup(group)
      .key('G', Tags.Items.GLASS)
      .key('S', inputTwo)
      .key('M', Tags.Items.INGOTS_GOLD)
      .patternLine("MGM")
      .patternLine("GSG")
      .patternLine("MGM")
      .addCriterion("has_gold", has(Tags.Items.INGOTS_GOLD))
      .build(consumer, location("shulkerboxes/" + color + "gold/silver_gold_shulker_box"));
  }

  private void registerDiamondBoxRecipe(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike input, ItemLike inputTwo, String color, String group) {
    IronShulkerBoxRecipeBuilder.shapedRecipe(result)
      .setGroup(group)
      .key('G', Tags.Items.GLASS)
      .key('S', input)
      .key('M', Tags.Items.GEMS_DIAMOND)
      .patternLine("GGG")
      .patternLine("MSM")
      .patternLine("GGG")
      .addCriterion("has_diamonds", has(Tags.Items.GEMS_DIAMOND))
      .build(consumer, location("shulkerboxes/" + color + "diamond/gold_diamond_shulker_box"));

    ResourceLocation silverToDiamondShulkerBox = location("shulkerboxes/" + color + "diamond/silver_diamond_shulker_box");
    ConditionalRecipe.builder()
      .addCondition(not(new TagEmptyCondition("forge:ingots/silver")))
      .addRecipe(IronShulkerBoxRecipeBuilder.shapedRecipe(result)
        .setGroup(group)
        .key('G', Tags.Items.GLASS)
        .key('M', Tags.Items.GEMS_DIAMOND)
        .key('S', inputTwo)
        .patternLine("GGG")
        .patternLine("GSG")
        .patternLine("MMM")
        .addCriterion("has_item", has(Tags.Items.GEMS_DIAMOND))::build)
      .setAdvancement(location("recipes/ironshulkerbox/shulkerboxes/" + color + "diamond/silver_diamond_shulker_box"),
        ConditionalAdvancement.builder()
          .addCondition(not(new TagEmptyCondition("forge:ingots/silver")))
          .addAdvancement(Advancement.Builder.advancement()
            .parent(new ResourceLocation("recipes/root"))
            .rewards(AdvancementRewards.Builder.recipe(silverToDiamondShulkerBox))
            .addCriterion("has_item", has(Tags.Items.GEMS_DIAMOND))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(silverToDiamondShulkerBox))
            .requirements(RequirementsStrategy.OR))
      ).build(consumer, silverToDiamondShulkerBox);
  }

  private void registerCrystalBoxRecipe(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike input, String color, String group) {
    IronShulkerBoxRecipeBuilder.shapedRecipe(result)
      .setGroup(group)
      .key('G', Tags.Items.GLASS)
      .key('S', input)
      .patternLine("GGG")
      .patternLine("GSG")
      .patternLine("GGG")
      .addCriterion("has_glass", has(Tags.Items.GLASS))
      .build(consumer, location("shulkerboxes/" + color + "crystal/diamond_crystal_shulker_box"));
  }

  private void registerObsidianBoxRecipe(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike input, String color, String group) {
    IronShulkerBoxRecipeBuilder.shapedRecipe(result)
      .setGroup(group)
      .key('M', Items.OBSIDIAN)
      .key('S', input)
      .patternLine("MMM")
      .patternLine("MSM")
      .patternLine("MMM")
      .addCriterion("has_obsidian", has(Items.OBSIDIAN))
      .build(consumer, location("shulkerboxes/" + color + "obsidian/diamond_obsidian_shulker_box"));
  }

  private static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> p_206407_) {
    return inventoryTrigger(ItemPredicate.Builder.item().of(p_206407_).build());
  }
}
