package net.unemployedgames.redstoneutilz.content.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.unemployedgames.redstoneutilz.RedstoneMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RedstoneMod.Mod_ID);


    ///public static final RegistryObject<Item> WERWOLVE_FER = ITEMS.register("werwolve_fer",
            //() -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ZINC_INGOT = ITEMS.register("zinc_ingot",
        () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
