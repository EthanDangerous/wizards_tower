package net.brothers_trouble.wizards_tower.datagen;

import net.brothers_trouble.wizards_tower.WizardsTower;
import net.brothers_trouble.wizards_tower.block.ModBlocks;
import net.brothers_trouble.wizards_tower.block.custom.TowerDoorBottomBlock;
import net.brothers_trouble.wizards_tower.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, WizardsTower.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        stairsBlock(ModBlocks.TOWERWOOD_PLANK_STAIRS.get(), blockTexture(ModBlocks.TOWERWOOD_PLANKS.get()));
        slabBlock(ModBlocks.TOWERWOOD_PLANK_SLAB.get(), blockTexture(ModBlocks.TOWERWOOD_PLANKS.get()), blockTexture(ModBlocks.TOWERWOOD_PLANKS.get()));

        fenceBlock(ModBlocks.TOWERWOOD_PLANK_FENCE.get(), blockTexture(ModBlocks.TOWERWOOD_PLANKS.get()));

        blockItem(ModBlocks.TOWERWOOD_PLANK_STAIRS);
        blockItem(ModBlocks.TOWERWOOD_PLANK_SLAB);

        simpleBlockWithItem(ModBlocks.TOWER_DOOR_MIDDLE.get(), models().getExistingFile(modLoc("block/tower_door_middle")));
        flippingDoor();
        simpleBlockWithItem(ModBlocks.TOWER_DOOR_TOP.get(), models().getExistingFile(modLoc("block/tower_door_top")));
        simpleBlockWithItem(ModBlocks.TOWER_DOOR_MIDDLE_FLIPPED.get(), models().getExistingFile(modLoc("block/tower_door_middle_flipped")));
        simpleBlockWithItem(ModBlocks.TOWER_DOOR_BOTTOM_FLIPPED.get(), models().getExistingFile(modLoc("block/tower_door_bottom_flipped")));
        simpleBlockWithItem(ModBlocks.TOWER_DOOR_TOP_FLIPPED.get(), models().getExistingFile(modLoc("block/tower_door_top_flipped")));
    }

    private void flippingDoor() {
        getVariantBuilder(ModBlocks.TOWER_DOOR_BOTTOM.get()).forAllStates(state -> {
            if(state.getValue(TowerDoorBottomBlock.FLIPPED)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("tower_door_bottom_flipped",
                        ResourceLocation.fromNamespaceAndPath(WizardsTower.MOD_ID, "block/" + "tower_door_bottom_flipped")))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("tower_door_bottom",
                        ResourceLocation.fromNamespaceAndPath(WizardsTower.MOD_ID, "block/" + "tower_door_bottom")))};
            }
        });
        simpleBlockItem(ModBlocks.TOWER_DOOR_BOTTOM.get(), models().cubeAll("tower_door_bottom",
                ResourceLocation.fromNamespaceAndPath(WizardsTower.MOD_ID, "block/" + "tower_door_bottom")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockItem(RegistryObject<? extends Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("wizardstower:block/" +
                ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockItem(RegistryObject<? extends Block> blockRegistryObject, String appendix) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("wizardstower:block/" +
                ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath() + appendix));
    }
}