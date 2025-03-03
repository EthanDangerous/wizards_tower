package net.brothers_trouble.wizards_tower.block;

import net.brothers_trouble.wizards_tower.WizardsTower;
import net.brothers_trouble.wizards_tower.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, WizardsTower.MOD_ID);

    public static final RegistryObject<Block> TOWERWOOD = registerBlock("towerwood",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(-1f, 3600000f).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> TOWERWOOD_PLANKS = registerBlock("towerwood_planks",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(-1f, 3600000f).sound(SoundType.WOOD)));


    public static final RegistryObject<StairBlock> TOWERWOOD_PLANK_STAIRS = registerBlock("towerwood_plank_stairs",
            () -> new StairBlock(ModBlocks.TOWERWOOD_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(-1f, 3600000f).sound(SoundType.WOOD)));

    public static final RegistryObject<SlabBlock> TOWERWOOD_PLANK_SLAB = registerBlock("towerwood_plank_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(-1f, 3600000f).sound(SoundType.WOOD)));

    public static final RegistryObject<FenceBlock> TOWERWOOD_PLANK_FENCE = registerBlock("towerwood_plank_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().strength(-1f, 3600000f).sound(SoundType.WOOD)));


    public static final RegistryObject<Block> MAGESTONE = registerBlock("magestone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(-1f, 3600000f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> MAGESTONE_BRICKS = registerBlock("magestone_bricks",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(-1f, 3600000f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> MAGESTONE_PILLAR = registerBlock("magestone_pillar",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(-1f, 3600000f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> SOURCERERSHROOM = registerBlock("sourcerershroom",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(-1f, 3600000f).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> HEART_OF_THE_TOWER = registerBlock("heart_of_the_tower",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(-1f, 3600000f).sound(SoundType.TRIAL_SPAWNER)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
