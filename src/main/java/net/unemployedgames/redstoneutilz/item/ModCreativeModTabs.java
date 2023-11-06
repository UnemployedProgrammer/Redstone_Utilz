package net.unemployedgames.redstoneutilz.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.block.ModBlocks;

public class ModCreativeModTabs {

    public static Boolean TABSORTED = false;

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RedstoneMod.Mod_ID);


    public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main_mod_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.REDSTONE))
                    .title(Component.translatable("creativetab.redstone_utilz.main"))
                    .displayItems((pParameters, pOutput) -> {

                    pOutput.accept(ModBlocks.COPYCAT_BLOCK.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
