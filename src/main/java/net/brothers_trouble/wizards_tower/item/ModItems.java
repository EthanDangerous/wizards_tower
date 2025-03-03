package net.brothers_trouble.wizards_tower.item;

import net.brothers_trouble.wizards_tower.WizardsTower;
import net.brothers_trouble.wizards_tower.block.ModBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WizardsTower.MOD_ID);

    public static final RegistryObject<Item> ANIMUSGEM = ITEMS.register("animus_gem",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FILLEDANIMUSGEM = ITEMS.register("filled_animus_gem",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TOWERSEEDS = ITEMS.register("tower_seeds",
            () -> new ItemNameBlockItem(ModBlocks.TOWER_CROP.get(), new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
