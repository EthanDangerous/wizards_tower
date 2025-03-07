package net.brothers_trouble.wizards_tower.datagen;

import net.brothers_trouble.wizards_tower.block.ModBlocks;
import net.brothers_trouble.wizards_tower.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.TOWERWOOD.get()); // Ensure this exists
        dropSelf(ModBlocks.TOWERWOOD_PLANKS.get()); // Ensure this exists
        dropSelf(ModBlocks.MAGESTONE.get()); // Ensure this exists
        dropSelf(ModBlocks.MAGESTONE_PILLAR.get()); // Ensure this exists
        dropSelf(ModBlocks.MAGESTONE_BRICKS.get()); // Ensure this exists
        dropSelf(ModBlocks.SOURCERERSHROOM.get()); // Ensure this exists
        dropSelf(ModBlocks.HEART_OF_THE_TOWER.get()); // Ensure this exists

        dropSelf(ModBlocks.TOWERWOOD_PLANK_STAIRS.get());
        this.add(ModBlocks.TOWERWOOD_PLANK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.TOWERWOOD_PLANK_SLAB.get()));

        dropSelf(ModBlocks.TOWERWOOD_PLANK_FENCE.get());
        dropSelf(ModBlocks.TOWER_CROP.get());

        dropSelf(ModBlocks.TOWER_DOOR_BOTTOM.get());
        dropSelf(ModBlocks.TOWER_DOOR_MIDDLE.get());
        dropSelf(ModBlocks.TOWER_DOOR_TOP.get());
        dropSelf(ModBlocks.TOWER_DOOR_BOTTOM_FLIPPED.get());
        dropSelf(ModBlocks.TOWER_DOOR_MIDDLE_FLIPPED.get());
        dropSelf(ModBlocks.TOWER_DOOR_TOP_FLIPPED.get());
    }


    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                pBlock, this.applyExplosionDecay(
                        pBlock, LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}